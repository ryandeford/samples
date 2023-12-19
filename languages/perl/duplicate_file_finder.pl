# initial script for finding and comparing duplicate files

use strict;
use warnings;

use Getopt::Std;

use File::Spec;
use File::Find;
use File::Compare;
use Cwd 'abs_path';
use POSIX qw/strftime/;
use File::Copy 'move';
use Data::UUID;

use Data::Dumper;

use constant USAGE =>
"\n\nUsage: perl $0 -s path/to/sourcedir [-c path/to/comparedir] [-m path/to/moveduplicatesdir] [-d] [-i] [-a] [-q] [-f]\n\n";

# Minimum file size (in bytes) needed for targeted matches and file cleanup
use constant MIN_FILE_SIZE_FILTER => 1;

# Time interval (in seconds) that status updates will occur
use constant STATUS_UPDATE_INTERVAL => 60;

# Whitelist of file patterns used to target certain files during processing
# Examples:
#   qr{.*DCIM/.+}
#   qr{.*Pictures/.+}
#   qr{.*Messaging/.+}
#   qr{.*\.jpg}
#   qr{.*\.mp4}
use constant FILE_WHITELIST_PATTERNS => (

);

# Blacklist of file patterns used to ignore certain files during processing
# (Note that items in this blacklist will take precedence over the whitelist)
# Examples:
#   qr{.*Android/data/.+}
#   qr{.*DCIM/\.thumbnails/.+}
#   qr{.*\.DS_Store}
#   qr{.*[c|C][a|A][c|C][h|H][e|E].*/.+}
#   qr{(?!.*/(DCIM|Edited|GOSMS|Messaging|Pictures|Snapchat|[d|D]ownload)).*}
use constant FILE_BLACKLIST_PATTERNS => (

);

# Cache used to store target comparison files paths and sizes
my %comparisonFileSystemCache;

print "\nWelcome to Duplicate File Finder!\n";
printf "Starting process at %s\n\n", getTimeStamp();

# fetch directory paths and flags from command line arguments
my (
  $sourceDirectory,
  $comparisonDirectory,
  $duplicatesDirectory,
  $deleteFlag,
  $ignoreFilenamesFlag,
  $allFilesFlag,
  $quietFlag,
  $forceCleanupFlag
) = getScriptParamaters();

printSetupSummary(
  $sourceDirectory,
  $comparisonDirectory,
  $duplicatesDirectory
);

# gather and compare file data contained within each provided directory
compareDirectories(
  $sourceDirectory,
  $comparisonDirectory,
  $duplicatesDirectory
);

sub compareDirectories {
  my $sourceDirectory = shift;
  my $comparisonDirectory = shift;
  my $duplicatesDirectory = shift;

  printProcessingNotifications();

  printf "\nCurrently searching directories and collecting file information...\n";

  my $totalSourceFilesTraversed = 0;
  my $totalSourceFilesAnalyzed = 0;
  my $totalSourceDirectoriesAnalyzed = 0;
  my $totalDuplicateFileCount = 0;
  my $totalDuplicateFileSize = 0;
  my $lastStatusUpdateTime = time;

  find({
  preprocess =>
    sub {
      my @fileListing = @_;
      my $directory = $File::Find::dir;
      my @wantedFiles;

      $totalSourceDirectoriesAnalyzed++;

      foreach my $file (@fileListing) {
        $totalSourceFilesTraversed++;

        my $filename = File::Spec->catfile($directory, $file);
        next if !isFileTargetedForProcessing($filename);

        push @wantedFiles, $file;
      }

      return @wantedFiles;
    },
  wanted =>
    sub {
      my $sourceFilename = $File::Find::name;
      my $sourceFilenameBase = $_;

      return if !-f $sourceFilename;

      $totalSourceFilesAnalyzed++;

      if (keys %comparisonFileSystemCache) {
        foreach my $comparisonFilename (keys %comparisonFileSystemCache) {
          printStatusUpdate(
            \$lastStatusUpdateTime,
            $totalSourceFilesTraversed,
            $totalSourceFilesAnalyzed,
            $totalSourceDirectoriesAnalyzed,
            $sourceFilename,
            $comparisonFilename
          );

          my (
            $isDuplicate,
            $duplicateFileSize
          ) = processFiles($sourceFilename, $comparisonFilename, $duplicatesDirectory);

          if ($isDuplicate) {
            $totalDuplicateFileCount++;
            $totalDuplicateFileSize += $duplicateFileSize;
          }
        }

        return;
      }

      find({
      preprocess =>
        sub {
          my @fileListing = @_;
          my $directory = $File::Find::dir;
          my @wantedFiles;

          foreach my $file (@fileListing) {
            my $filename = File::Spec->catfile($directory, $file);
            next if !isFileTargetedForProcessing($filename);

            push @wantedFiles, $file;
          }

          return @wantedFiles;
        },
      wanted =>
        sub {
          my $comparisonFilename = $File::Find::name;
          my $comparisonFilenameBase = $_;

          return if !-f $comparisonFilename;

          my $comparisonFileSize = getFileSize($comparisonFilename);
          $comparisonFileSystemCache{$comparisonFilename} = $comparisonFileSize;

          printStatusUpdate(
            \$lastStatusUpdateTime,
            $totalSourceFilesTraversed,
            $totalSourceFilesAnalyzed,
            $totalSourceDirectoriesAnalyzed,
            $sourceFilename,
            $comparisonFilename
          );

          my (
            $isDuplicate,
            $duplicateFileSize
          ) = processFiles($sourceFilename, $comparisonFilename, $duplicatesDirectory);

          if ($isDuplicate) {
            $totalDuplicateFileCount++;
            $totalDuplicateFileSize += $duplicateFileSize;
          }
        }}, $comparisonDirectory);
    }}, $sourceDirectory);

  printProcessingSummary(
    $totalSourceFilesAnalyzed,
    $totalSourceDirectoriesAnalyzed,
    $totalDuplicateFileCount,
    $totalDuplicateFileSize
  );
}

sub processFiles {
  my $sourceFilename = shift;
  my $comparisonFilename = shift;
  my $duplicatesDirectory = shift;

  return (0,0) if $sourceFilename eq $comparisonFilename;

  my $sourceFilenameBase = getFilenameBase($sourceFilename);
  my $comparisonFilenameBase = getFilenameBase($comparisonFilename);

  if (!isIgnoreFilenamesMode()) {
    return (0,0) if $sourceFilenameBase ne $comparisonFilenameBase;
  }

  my $sourceFileSize = getFileSize($sourceFilename);

  my $comparisonFileSize;
  if (defined $comparisonFileSystemCache{$comparisonFilename}) {
    $comparisonFileSize = $comparisonFileSystemCache{$comparisonFilename};
  }
  else {
    $comparisonFileSize = getFileSize($comparisonFilename);
  }

  return (0,0) if $sourceFileSize != $comparisonFileSize;

  my $comparisonResult = compare($sourceFilename, $comparisonFilename);

  if ($comparisonResult == -1) {
    die sprintf 'ERROR: An error occurred while comparing the following files: %s and %s', $sourceFilename, $comparisonFilename;
  }
  elsif ($comparisonResult == 0) {
    my $sourceFileModifiedTime = getFileModifiedTime($sourceFilename);
    my $comparisonFileModifiedTime = getFileModifiedTime($comparisonFilename);

    if ($comparisonFileModifiedTime == $sourceFileModifiedTime) {
      if (!isQuietMode()) {
        printf "  >>> Warning: These duplicate files have the same modified time, so a master can't be determined: %s <=> %s\n", $comparisonFilename, $sourceFilename;
      }

      if (!isForceCleanupMode()) {
        return (0,0);
      }
    }
    elsif ($comparisonFileModifiedTime < $sourceFileModifiedTime) {
      if (!isQuietMode()) {
        printf "  >>> Warning: These duplicate files are identical but the modified times are opposite from the expected source: %s <=> %s\n", $comparisonFilename, $sourceFilename;
      }

      if (!isForceCleanupMode()) {
        return (0,0);
      }
    }

    if (isDeleteMode()) {
      printf "  >>> Deleting: %s (matches: %s)\n", $comparisonFilename, $sourceFilename;
      unlink $comparisonFilename;
      delete $comparisonFileSystemCache{$comparisonFilename};
    }
    else {
      printf "  >>> Duplicate: %s (matches: %s)\n", $comparisonFilename, $sourceFilename;

      if (defined $duplicatesDirectory) {
        my $destinationFilename = getAbsoluteFilePath(File::Spec->catfile($duplicatesDirectory, $comparisonFilenameBase));

        safelyMoveFile($comparisonFilename, $destinationFilename);
        delete $comparisonFileSystemCache{$comparisonFilename};
      }
    }

    return (1, $comparisonFileSize);
  }
}

sub isFileTargetedForProcessing {
  my $filename = shift;

  my $absoluteFilename = getAbsoluteFilePath($filename);

  if (!defined $absoluteFilename) {
    if (!isQuietMode()) {
      print "  >>> Warning: The following file could not be processed, perhaps it is a symbolic link?: $filename\n";
    }

    return 0;
  }

  my $filenameBase = getFilenameBase($absoluteFilename);

  if (!isAllFilesMode()) {
    return 0 if $filenameBase =~ /^\./;
  }

  return 0 if isFileBlacklisted($absoluteFilename);

  if (-f $absoluteFilename) {
    return 0 if !isFileWhitelisted($absoluteFilename);

    if (MIN_FILE_SIZE_FILTER > 0) {
      my $fileSize = getFileSize($absoluteFilename);
      return 0 if $fileSize < MIN_FILE_SIZE_FILTER;
    }
  }

  return 1;
}

sub printStatusUpdate {
  my $lastStatusUpdateTime = shift;
  my $totalSourceFilesTraversed = shift;
  my $totalSourceFilesAnalyzed = shift;
  my $totalSourceDirectoriesAnalyzed = shift;
  my $sourceFilename = shift;
  my $comparisonFilename = shift;

  return if time - $$lastStatusUpdateTime < STATUS_UPDATE_INTERVAL;

  $$lastStatusUpdateTime = time;

  printf
    "\n[Status]: %s Traversed %d source files (analyzed %d) across %d relevant dirs\n",
    getTimeStamp($$lastStatusUpdateTime),
    $totalSourceFilesTraversed,
    $totalSourceFilesAnalyzed,
    $totalSourceDirectoriesAnalyzed;
  printf "Comparing %s <=> %s\n", $sourceFilename, $comparisonFilename;
}

sub printProcessingNotifications {
  if (defined FILE_WHITELIST_PATTERNS) {
    printf "\nNote: The following file path patterns will be whitelisted:\n";
    foreach my $fileWhitelistPattern (FILE_WHITELIST_PATTERNS) {
      print "  $fileWhitelistPattern\n";
    }
  }

  if (defined FILE_BLACKLIST_PATTERNS) {
    printf "\nNote: The following file path patterns will be blacklisted:\n";
    foreach my $fileBlacklistPattern (FILE_BLACKLIST_PATTERNS) {
      print "  $fileBlacklistPattern\n";
    }
  }

  if (isDeleteMode()) {
    print "\nNote: Deletion of duplicate files is turned ON\n";
  }

  if (isIgnoreFilenamesMode()) {
    print "\nNote: Filenames are being ignored during file comparisons\n";
  }

  if (isAllFilesMode()) {
    print "\nNote: All hidden files and folders are being analyzed\n";
  }

  if (isQuietMode()) {
    print "\nNote: Running in quiet mode and suppressing some warnings\n";
  }

  if (isForceCleanupMode()) {
    print "\nNote: Duplicate files with matching timestamps are being cleaned\n";
  }
}

sub printProcessingSummary {
  my $totalSourceFilesAnalyzed = shift;
  my $totalSourceDirectoriesAnalyzed = shift;
  my $totalDuplicateFileCount = shift;
  my $totalDuplicateFileSize = shift;

  print "\nDone processing...\n";
  printf
    "  Analyzed %d relevant source files across %d source directories\n",
    $totalSourceFilesAnalyzed,
    $totalSourceDirectoriesAnalyzed;
  printf
    "  Found %d duplicate files using %0.0f bytes of space (%.2f MB / %.2f GB)\n",
    $totalDuplicateFileCount,
    $totalDuplicateFileSize,
    convertBytesToMB($totalDuplicateFileSize),
    convertBytesToGB($totalDuplicateFileSize);
}

sub printSetupSummary {
  my $sourceDirectory = shift;
  my $comparisonDirectory = shift;
  my $duplicatesDirectory = shift;

  print "Setting up analysis for the following directories...\n";
  printf "  SrcDir: %s\n", $sourceDirectory;
  printf "  CmpDir: %s\n", $comparisonDirectory ne $sourceDirectory ? $comparisonDirectory : '(Comparing source directory with itself)';
  printf "  DupDir: %s\n", $duplicatesDirectory if defined $duplicatesDirectory;
}

sub safelyMoveFile {
  my $sourceFilename = shift;
  my $destinationFilename = shift;

  if (-e $destinationFilename) {
    my $uniqueString = getUniqueString();
    $destinationFilename .= $uniqueString;

    if (!isQuietMode()) {
      printf "  >>> Warning: A file with the same base name already exists in the duplicates directory, adding a unique string: $sourceFilename <=> $destinationFilename\n";
    }
  }

  if (-e $destinationFilename) {
    die "ERROR: A GUID collision has occurred! The destination file aleady exists: $destinationFilename";
  }

  move($sourceFilename, $destinationFilename) or die "ERROR: Unable to move the following file: $sourceFilename: $!";
}

sub isDirectoryAChildOf {
  my $directoryA = shift;
  my $directoryB = shift;

  foreach my $directory (($directoryA, $directoryB)) {
    if (!-d $directory) {
      die "ERROR: An invalid directory name was provided: $directory";
    }
  }

  if ($directoryA =~ /^$directoryB.*\//) {
    return 1;
  }

  return 0;
}

sub getFileSize {
  my $filename = shift;

  my $fileSize = -s $filename;
  return $fileSize;
}

sub getFileModifiedTime {
  my $path = shift;

  my @fileStat = stat $path;
  return $fileStat[9];
}

sub getAbsoluteFilePath {
  my $path = shift;

  return abs_path($path);
}

sub getFilenameBase {
  my $path = shift;

  my $filenameBase = '';
  if ($path =~ /\/([^\/]+)$/) {
    $filenameBase = $1;
  }
  else {
    die "ERROR: Could not calculate file base name: $path";
  }

  return $filenameBase;
}

sub isFileWhitelisted {
  my $file = shift;

  return 1 if !defined FILE_WHITELIST_PATTERNS;

  for my $fileWhitelistPattern (FILE_WHITELIST_PATTERNS) {
    return 1 if ($file =~ /^$fileWhitelistPattern$/);
  }

  return 0;
}

sub isFileBlacklisted {
  my $file = shift;

  return 0 if !defined FILE_BLACKLIST_PATTERNS;

  for my $fileBlacklistPattern (FILE_BLACKLIST_PATTERNS) {
    return 1 if ($file =~ /^$fileBlacklistPattern$/);
  }

  return 0;
}

sub getUniqueString {
  my $guidGenerator = Data::UUID->new;
  my $guid = $guidGenerator->create();
  my $guidString = $guidGenerator->to_string($guid);

  return $guidString;
}

sub getTimeStamp {
  my $time = shift;

  if (!defined $time) {
    $time = time;
  }

  return strftime('%H:%M:%S', localtime($time));
}

sub convertBytesToMB {
  my $bytes = shift;
  return $bytes/1024/1024;
}

sub convertBytesToGB {
  my $bytes = shift;
  return $bytes/1024/1024/1024;
}

sub isDeleteMode {
  if (!defined $deleteFlag) {
    die 'ERROR: Unable to fetch delete flag: Undefined';
  }

  return $deleteFlag;
}

sub isIgnoreFilenamesMode {
  if (!defined $ignoreFilenamesFlag) {
    die 'ERROR: Unable to fetch ignore filenames flag: Undefined';
  }

  return $ignoreFilenamesFlag;
}

sub isAllFilesMode {
  if (!defined $allFilesFlag) {
    die 'ERROR: Unable to fetch all files flag: Undefined';
  }

  return $allFilesFlag;
}

sub isQuietMode {
   if (!defined $quietFlag) {
     die 'ERROR: Unable to fetch quiet flag: Undefined';
   }

   return $quietFlag;
}

sub isForceCleanupMode {
  if (!defined $forceCleanupFlag) {
    die 'ERROR: Unable to fetch force cleanup flag: Undefined';
  }

  return $forceCleanupFlag;
}

sub getScriptParamaters {
  my %opts;
  getopts('s:c:m:diaqf', \%opts);

  my $sourceDirectory = $opts{s};
  my $comparisonDirectory = $opts{c};
  my $duplicatesDirectory = $opts{m};
  my $deleteFlag = $opts{d};
  my $ignoreFilenamesFlag = $opts{i};
  my $allFilesFlag = $opts{a};
  my $quietFlag = $opts{q};
  my $forceCleanupFlag = $opts{f};

  # Fetch optional flag used for executing deletions
  if (!defined $deleteFlag) {
    $deleteFlag = 0;
  }

  # Fetch optional flag used for ignoring filenames when performing file comparisons
  if (!defined $ignoreFilenamesFlag) {
    $ignoreFilenamesFlag = 0;
  }

  # Fetch optional flag used for traversing hidden files and folders
  if (!defined $allFilesFlag) {
    $allFilesFlag = 0;
  }

  # Fetch optional flag used to suppress warnings and help limit messaging
  if (!defined $quietFlag) {
    $quietFlag = 0;
  }

  # Fetch optional flag used to force cleanup of duplicates when timestamps match or are opposite of expected
  if (!defined $forceCleanupFlag) {
    $forceCleanupFlag = 0;
  }

  # Fetch and validate required source directory paramater
  if (!defined $sourceDirectory) {
    die 'ERROR: A source directory name must be supplied'.USAGE;
  }

  if ($sourceDirectory eq '') {
    die "ERROR: An empty source directory name was provided: '$sourceDirectory'";
  }

  $sourceDirectory = getAbsoluteFilePath($sourceDirectory);
  if (!-d $sourceDirectory) {
    die "ERROR: An invalid source directory name was provided: '$sourceDirectory'";
  }

  # Fetch and validate optional comparison directory paramater
  if (defined $comparisonDirectory) {
    if ($comparisonDirectory eq '') {
      die "ERROR: An empty comparison directory name was provided: '$comparisonDirectory'";
    }

    $comparisonDirectory = getAbsoluteFilePath($comparisonDirectory);
    if (!-d $comparisonDirectory) {
      die "ERROR: An invalid comparison directory name was provided: '$comparisonDirectory'";
    }
  }
  else {
    $comparisonDirectory = $sourceDirectory;
  }

  if (defined $duplicatesDirectory) {
    if ($deleteFlag) {
      die 'ERROR: The delete flag cannot be provided when a duplicates directory has been specified';
    }

    if ($duplicatesDirectory eq '') {
      die "ERROR: An empty duplicates directory name was provided: '$duplicatesDirectory'";
    }

    $duplicatesDirectory = getAbsoluteFilePath($duplicatesDirectory);
    if (!-d $duplicatesDirectory) {
      die "ERROR: An invalid duplicates directory name was provided: '$duplicatesDirectory'";
    }

    if ($duplicatesDirectory eq $sourceDirectory) {
      die 'ERROR: The source and duplicates directories are not allowed to be the same';
    }

    if ($duplicatesDirectory eq $comparisonDirectory) {
      die 'ERROR: The comparison and duplicates directories are not allowed to be the same';
    }

    if (isDirectoryAChildOf($duplicatesDirectory, $sourceDirectory)) {
      die 'ERROR: The duplicates directory cannot be a subdirectory of the source directory';
    }

    if (isDirectoryAChildOf($sourceDirectory, $duplicatesDirectory)) {
      die 'ERROR: The source directory cannot be a subdirectory of the duplicates directory';
    }

    if (isDirectoryAChildOf($duplicatesDirectory, $comparisonDirectory)) {
      die 'ERROR: The duplicates directory cannot be a subdirectory of the comparison directory';
    }

    if (isDirectoryAChildOf($comparisonDirectory, $duplicatesDirectory)) {
      die 'ERROR: The comparison directory cannot be a subdirectory of the duplicates directory';
    }
  }

  return (
    $sourceDirectory,
    $comparisonDirectory,
    $duplicatesDirectory,
    $deleteFlag,
    $ignoreFilenamesFlag,
    $allFilesFlag,
    $quietFlag,
    $forceCleanupFlag
  );
}

