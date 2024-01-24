package main

import (
  "fmt"
  "reflect"
)

type SampleType struct {
  id string
  name string
  description string
}

type SliceMetrics struct {
  t reflect.Type
  length int
  capacity int
  value string
}

func main() {
  createSliceTests()
  createMultiTypeSliceTests()
  reverseStringTests()
  getFibRecursiveTests()
  getFibIterativeTests()

  fmt.Println("All Tests Passed!")
}

func createSlice[T any](values ...T) []T {
  slice := []T{}
  
  for _, value := range values {
    slice = append(slice, value)
  }
  
  return slice
}

func createSliceTests() {
  if !reflect.DeepEqual(createSlice[any](), []any{}) {
    panic("createSlice created an incorrect value for empty-any list")
  }
  if !reflect.DeepEqual(createSlice[int](), []int{}) {
    panic("createSlice created an incorrect value for empty-int list")
  }
  if !reflect.DeepEqual(createSlice[int32](), []int32{}) {
    panic("createSlice created an incorrect value for empty-char list")
  }
  if !reflect.DeepEqual(createSlice[string](), []string{}) {
    panic("createSlice created an incorrect value for empty-string list")
  }
  if !reflect.DeepEqual(createSlice[any](1.0), []any{1.0}) {
    panic("createSlice created an incorrect value for single-any list")
  }
  if !reflect.DeepEqual(createSlice(1), []int{1}) {
    panic("createSlice created an incorrect value for single-int list")
  }
  if !reflect.DeepEqual(createSlice('a'), []int32{'a'}) {
    panic("createSlice created an incorrect value for single-char list")
  }
  if !reflect.DeepEqual(createSlice("test"), []string{"test"}) {
    panic("createSlice created an incorrect value for single-string list")
  }
  if !reflect.DeepEqual(createSlice[any](1,1.0,'a',"test",nil), []any{1,1.0,'a',"test",nil}) {
    panic("createSlice created an incorrect value for multi-any list")
  }
  if !reflect.DeepEqual(createSlice(1,2,3,4,5), []int{1,2,3,4,5}) {
    panic("createSlice created an incorrect value for multi-int list")
  }
  if !reflect.DeepEqual(createSlice('a','b','c','d','e'), []int32{'a','b','c','d','e'}) {
    panic("createSlice created an incorrect value for multi-char list")
  }
  if !reflect.DeepEqual(createSlice("test","abc","123","...",""), []string{"test","abc","123","...",""}) {
    panic("createSlice created an incorrect value for multi-string list")
  }
}

func createMultiTypeSlice(values ...any) []any {
  slice := []any{}
  
  for _, value := range values {
    slice = append(slice, value)
  }
  
  return slice
}

func createMultiTypeSliceTests() {
  if !reflect.DeepEqual(createMultiTypeSlice(), []any{}) {
    panic("createSlice created an incorrect value for empty list")
  }
  if !reflect.DeepEqual(createMultiTypeSlice(1,1.0,'a',"test"), []any{1,1.0,'a',"test"}) {
    panic("createSlice created an incorrect value for multi-type list")
  }
}

func reverseString(s string) string {
  reversed := ""
  
  for i := len(s) -1; i >= 0; i-- {
    reversed += string(s[i])
  }
  
  return reversed
}

func reverseStringTests() {
  tests := map[string]string {
    "": "",
    "a": "a",
    "abc": "cba",
    "_test* *test_": "_tset* *tset_",
  }
  
  for input, expected := range tests {
    output := reverseString(input)
    if output != expected {
      panic(fmt.Sprintf("reverseString output did not match expected value: input: %v, output: %v, expected: %v", input, output, expected))
    }
  }
}

func getFibRecursive(i int) int {
  switch i {
  case 0:
    return 0
  case 1:
    return 1
  default:
    return getFibRecursive(i-1) + getFibRecursive(i-2)
  }
}

func getFibRecursiveTests() {
  tests := map[int]int {
    0: 0,
    1: 1,
    2: 1,
    3: 2,
    4: 3,
    5: 5,
    6: 8,
    7: 13,
    8: 21,
    9: 34,
    10: 55,
  }
  
  for input, expected := range tests {
    output := getFibRecursive(input)
    if output != expected {
      panic(fmt.Sprintf("getFibRecursive output did not match expected value: input: %v, output: %v, expected: %v", input, output, expected))
    }
  }
}

func getFibIterative(i int) int {
  switch i {
  case 0:
    return 0
  case 1:
    return 1
  default:
    var result int
    prevprev := 0
    prev := 1
    
    for v := 2; v <= i; v++ {
      result = prev + prevprev
      prevprev = prev
      prev = result
    }
    
    return result
  }
}

func getFibIterativeTests() {
  tests := map[int]int {
    0: 0,
    1: 1,
    2: 1,
    3: 2,
    4: 3,
    5: 5,
    6: 8,
    7: 13,
    8: 21,
    9: 34,
    10: 55,
  }
  
  for input, expected := range tests {
    output := getFibIterative(input)
    if output != expected {
      panic(fmt.Sprintf("getFibIterative output did not match expected value: input: %v, output: %v, expected: %v", input, output, expected))
    }
  }
}

func printSlice[T any](s []T) {
  fmt.Printf("%v\n", s)
}

func printSliceMetrics[T any](s []T) {
  fmt.Printf("slice metrics: type: %T, length: %v, capacity: %v, value = %v\n", s, len(s), cap(s), s)
}
