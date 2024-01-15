
import sys
import os
import re

# Create a compiled regular expression to help verify lines provided in the target input file
lineExpectedFormatRegex = re.compile('^\d+\s+[\w-]+$', re.IGNORECASE)

# Decodes message contained within provided input message filename and returns a string to represent the resolved message contents
def decode(message_file: str):

  # Confirm that the provided input message file exists and is accessible, and if not throw an error
  if not os.path.isfile(message_file):
    raise Exception('Unable to read/process the provided input file, please double check your filename and confirm accessibility: input file: "{}"'.format(message_file))

  # Create dictionary to map provided line numbers to words
  linesMap = {}
  
  # Open the provided input message file
  with open(message_file) as file:
  
    # Keep track of actual line number being read from input file to help with logging and error messages
    lineNumber = 0
    
    # Read through the input message file line-by-line
    for line in file:
      lineNumber += 1
      
      # Strip unnecessary whitespace from the start and end of input line
      line = line.strip()
      
      # If the current line is empty/blank, skip it and move on
      if line == "":
        continue
      
      # Confirm that the input line matches our expected format of '<line number> <word>', and if not throw an error
      if not lineExpectedFormatRegex.match(line):
        raise Exception('Detected input line that fails to match the expected format: line number = {}: line value = "{}": expected format = "{}"'.format(lineNumber, line, lineExpectedFormatRegex))
      
      # Split the line tokens on whitespace and store the target line number + word values
      lineTokens = line.split()
      lineTargetNumber = int(lineTokens[0])
      lineTargetWord = lineTokens[1]
      
      # If the provided line number has already been provided previously, throw an error
      if lineTargetNumber in linesMap:
        lineAlreadySeen = '{} {}'.format(lineTargetNumber, linesMap[lineTargetNumber])
        raise Exception('Detected input line that contains an already-seen line number token...please review input file for correctness: line number = {}: line value = "{}": line already seen = "{}"'.format(lineNumber, line, lineAlreadySeen))
      
      # Add the provided line number + word to our dictionary mapping
      linesMap[lineTargetNumber] = lineTargetWord
    
    # Calculate the total number of provided line => word mappings created
    totalLinesAvailable = len(linesMap)
    
    # Create an array to store decoded message words
    decodedMessageWords = []
    
    # Create values to store our target pyramid tree level and right-most line index
    targetTreeLevel = 0
    targetLineIndex = 0
    
    # Iterate through all available line mappings and work to decode the input messages
    while targetLineIndex < totalLinesAvailable:
      targetTreeLevel += 1
      targetLineIndex += targetTreeLevel
      
      if not targetLineIndex in linesMap:
        raise Exception('Unable to locate the expected line number token in stored lines map...please review input file for correctness: target line index: {}'.format(targetLineIndex))
      
      decodedMessageWords.append(linesMap[targetLineIndex])
    
    # Join all resolved/decoded messages into a single string separated by a space character
    decodedMessage = ' '.join(decodedMessageWords)
    
    # Return the decoded message string
    return decodedMessage

# If this script is run as a main program, capture the target input file to read through and print decoded message to stdout
if __name__ == '__main__':

  try:
    # Create variable to store our target filename string
    targetFile = None
    
    # If arguments were supplied to this script, fetch the first argument and use it as our target filename
    if len(sys.argv) > 1:
      targetFile = sys.argv[1]
    # Otherwise if nothing was supplied when calling this script, capture it from the user via stdin
    else:
      targetFile = input('Enter Target Input Message File: ')
    
    # Confirm to the user which target file is going to be processed
    print('Target Input Message File = "{}"'.format(targetFile))
    
    # Calculate the decoded message
    decodedMessage = decode(targetFile)
    
    # Output the decoded message
    print('Decoded Output Message = "{}"'.format(decodedMessage))
  except Exception as e:
    print('Whoops! An error occurred trying to decode message: {}'.format(e), file = sys.stderr)
