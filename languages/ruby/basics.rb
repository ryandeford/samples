
class StringUtils
  def self.concat(*strings)
    concatenated_string = ''

    strings.each {|string|
      concatenated_string += string
    }

    return concatenated_string
  end

  def self.reverse(string)
    reversed_string = ''

    for index in (string.length-1).downto(0)
      reversed_string += string[index]
    end

    return reversed_string
  end
end

if __FILE__ == $0
  concatenated_string = StringUtils.concat('abc', '123', 'mmm', '456', 'xyz', '789')
  puts "The concatenated string is: #{concatenated_string}"

  ['', '1', '12', '123', '1234', '12345'].each {|test_string|
    puts("The test string '#{test_string}' reversed is: '#{StringUtils.reverse(test_string)}'")
  }
end
