
package main

import "fmt"

func main() {
  fmt.Println("+++ Sample Basics in Go +++")

  println("Generating sample slice of ints:")
  sample_numbers := []int{1, 2, 3, 4, 5, 10, 100}
  println(stringify(sample_numbers))

  println("Generating sample slice of strings:")
  sample_strings := []string{"abc", "123", "xyz"}
  println(stringify(sample_strings))

  println("Sum of sample ints:")
  sample_numbers_sum := sum(sample_numbers...)
  println(stringify(sample_numbers_sum))

  println("Concatenation of sample strings:")
  sample_strings_concat := concat(sample_strings...)
  println(stringify(sample_strings_concat))

  println("Building and running sample tests:")
  type Test struct {
    name string
    expected interface{}
    actual interface{}
  }
  tests := []Test{
    Test{name: "SumSampleTest", expected: 125, actual: sample_numbers_sum},
    Test{name: "ConcatSampleTest", expected: "abc123xyz", actual: sample_strings_concat},
  }
  type TestResult struct {
    success bool
    scenario Test
    message string
  }
  for _, test := range tests {
    result := TestResult{scenario: test}
    if (test.actual == test.expected) {
      result.success = true
      result.message = "PASSED!"
    } else {
      result.success = false
      result.message = "FAILED!"
    }
    println(stringify(result))
  }
}

func add(a int, b int) int {
  return a + b
}

func sum(numbers ...int) int {
  var sum int = 0

  for _, n := range numbers {
    sum += n
  }

  return sum
}

func concat(items ...string) string {
  var result string = ""

  for _, x := range items {
    result += x
  }

  return result
}

func stringify(item interface{}) string {
  return fmt.Sprintf("%+v", item)
}

func println(message string) {
  fmt.Println(message)
}

