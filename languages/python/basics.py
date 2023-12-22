import sys
import unittest
import re


def max_pair_indices(items: list):
    if len(items) < 2:
        return None
    elif len(items) == 2:
        return [0, 1]
    else:
        max_sum = items[0] + items[1]
        max_indices = [0, 1]

        for i in range(2, len(items)):
            current_sum = items[i] + items[i - 1]
            if current_sum > max_sum:
                max_sum = current_sum
                max_indices = [i - 1, i]

        return max_indices


def print_welcome_message():
    print("Welcome...")
    print("(type 'q' or 'quit' to exit)")


def run_program_loop():
    exit_command_pattern = re.compile('^\s*q|quit\s*$')
    while 1:
        user_input = None
        try:
            user_input = input('Enter a list of comma-separated numbers and hit enter: ')
        except KeyboardInterrupt:
            print()
            exit_program()
        if exit_command_pattern.match(user_input):
            exit_program()
        process_user_input(user_input)


def process_user_input(user_input: str):
    items_parsed = []
    try:
        items_parsed = [int(value.strip()) for value in user_input.split(",")]
    except Exception as e:
        print(f'Detected a non-integer input value, please try again: {e}')
        return
    print(f'> Items Parsed: {items_parsed}')
    max_pair_indices_result = max_pair_indices(items_parsed)
    print(f'> Max Pair Indices: {max_pair_indices_result}')


def exit_program():
    print('Exiting...')
    sys.exit(0)


if __name__ == '__main__':
    print_welcome_message()
    run_program_loop()


class MaxPairIndicesTests(unittest.TestCase):
    def test_max_pair_indices_empty_list(self):
        result_empty_list = max_pair_indices([])
        self.assertEqual(None, result_empty_list)

    def test_max_pair_indices_one_item_list(self):
        result_one_item_list = max_pair_indices([1])
        self.assertEqual(None, result_one_item_list)

    def test_max_pair_indices_two_item_list(self):
        result_two_item_list = max_pair_indices([1, 2])
        self.assertEqual([0, 1], result_two_item_list)

    def test_max_pair_indices_three_item_list(self):
        result_two_item_list = max_pair_indices([1, 2, 3])
        self.assertEqual([1, 2], result_two_item_list)

    def test_max_pair_indices_three_item_list_with_ties(self):
        result_two_item_list_with_ties = max_pair_indices([1, 2, 3, 1, 2, 3])
        self.assertEqual([1, 2], result_two_item_list_with_ties)

    def test_max_pair_indices_various_lists(self):
        for test in [
            [[0, 0, 0], [0, 1]],
            [[0, 1, 0], [0, 1]],
            [[0, 1, 1], [1, 2]],
            [[1, 1, 1], [0, 1]],
            [[1, 1, 1, 1], [0, 1]],
            [[1, 2, 2, 2], [1, 2]],
            [[1, 1, 2, 2], [2, 3]],
            [[1, 1, 3, 2], [2, 3]],
            [[-1, -2, -3], [0, 1]],
            [[-1, -2, 3], [1, 2]],
            [[-1, -2, -3, 0], [0, 1]],
            [[-1, -2, -3, 1], [2, 3]],
            [[5, 2, 8, -10, 33, 17, 2, -100], [4, 5]],
            [[5, 2, 8, -10, 33, 17, 2, -100, 200, -100, 200], [7, 8]],
            [[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 0], [0, 1]],
            [[1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 7], [9, 10]],
            [[3, 1, 2, 9, 3], [3, 4]]
        ]:
            test_input = test[0]
            test_expected_result = test[1]
            result = max_pair_indices(test_input)
            self.assertEqual(test_expected_result, result)
