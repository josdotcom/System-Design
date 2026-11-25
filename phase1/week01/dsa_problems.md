# Week 1 Problems (22 + 1 stretch)

Status: `✅` solved alone · `🟡` needed a hint · `R` read the solution (re-solve tomorrow, add it to the tracker's re-solve queue)
Time limits: E 15m · M 25m · H 40m. Write down **time and space complexity** for every problem before you submit.

| # | Problem | Diff | Pattern | Day | Status | Time | Notes |
|---|---|---|---|---|---|---|---|
| 1 | [Two Sum](https://leetcode.com/problems/two-sum/) | E | hash map: value → index | Mon | | | |
| 217 | [Contains Duplicate](https://leetcode.com/problems/contains-duplicate/) | E | hash set | Mon | | | |
| 242 | [Valid Anagram](https://leetcode.com/problems/valid-anagram/) | E | count array[26] | Mon | | | |
| 49 | [Group Anagrams](https://leetcode.com/problems/group-anagrams/) | M | hash with a *derived key* | Tue | | | |
| 347 | [Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/) | M | count + bucket sort (or heap) | Tue | | | |
| 238 | [Product of Array Except Self](https://leetcode.com/problems/product-of-array-except-self/) | M | prefix × suffix | Wed | | | |
| 128 | [Longest Consecutive Sequence](https://leetcode.com/problems/longest-consecutive-sequence/) | M | set, start only at sequence heads | Wed | | | |
| 36 | [Valid Sudoku](https://leetcode.com/problems/valid-sudoku/) | M | sets per row/col/box | Thu | | | |
| 169 | [Majority Element](https://leetcode.com/problems/majority-element/) | E | Boyer–Moore voting | Thu | | | |
| 724 | [Find Pivot Index](https://leetcode.com/problems/find-pivot-index/) | E | prefix sum | Fri | | | |
| 303 | [Range Sum Query - Immutable](https://leetcode.com/problems/range-sum-query-immutable/) | E | prefix sum | Fri | | | |
| 560 | [Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/) ⭐ | M | prefix sum + hash map of counts | Fri | | | |
| 125 | [Valid Palindrome](https://leetcode.com/problems/valid-palindrome/) | E | two pointers, opposite ends | Sat | | | |
| 167 | [Two Sum II](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) | M | two pointers on a sorted array | Sat | | | |
| 26 | [Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/) | E | read/write pointers | Sat | | | |
| 283 | [Move Zeroes](https://leetcode.com/problems/move-zeroes/) | E | read/write pointers | Sat | | | |
| 88 | [Merge Sorted Array](https://leetcode.com/problems/merge-sorted-array/) | E | fill from the back | Sat | | | |
| 75 | [Sort Colors](https://leetcode.com/problems/sort-colors/) | M | Dutch national flag (3 pointers) | Sat | | | |
| 523 | [Continuous Subarray Sum](https://leetcode.com/problems/continuous-subarray-sum/) | M | prefix sum mod k + first-seen index | Sat | | | |
| 15 | [3Sum](https://leetcode.com/problems/3sum/) ⭐ | M | sort + fix one + two pointers, skip dups | Sun | | | |
| 11 | [Container With Most Water](https://leetcode.com/problems/container-with-most-water/) | M | two pointers, move the shorter side | Sun | | | |
| 53 | [Maximum Subarray](https://leetcode.com/problems/maximum-subarray/) | M | Kadane | Sun | | | |
| 42 | [Trapping Rain Water](https://leetcode.com/problems/trapping-rain-water/) 🏋️ | H | two pointers with leftMax/rightMax | Sun (stretch) | | | |

⭐ = asked very often, so make sure you can do it cold.

**Self-check at the end of the week:** for #560 and #15, can you explain in one sentence *why* the approach is correct?
