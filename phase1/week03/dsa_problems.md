# Week 3 Problems (18 + 1 redo + 1 stretch)

Status: `✅` solved alone (**including** checklist steps 1–3 without being asked) · `🟡` needed a hint or a prompt · `R` read the solution (re-solve in 2 days, add it to the tracker's re-solve queue)
Time limits: E 15m · M 25m · H 40m.

| # | Problem | Diff | Pattern | Day | Status | Time | Notes |
|---|---|---|---|---|---|---|---|
| 704 | [Binary Search](https://leetcode.com/problems/binary-search/) | E | exact-match binary search | Mon | | | |
| 35 | [Search Insert Position](https://leetcode.com/problems/search-insert-position/) | E | first index with `nums[i] >= target` | Mon | | | |
| 74 | [Search a 2D Matrix](https://leetcode.com/problems/search-a-2d-matrix/) | M | treat the matrix as one sorted array | Mon | | | |
| 33 | [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/) ⭐ | M | one half is always sorted | Tue | | | |
| 153 | [Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/) | M | compare `mid` with one end | Tue | | | |
| 875 | [Koko Eating Bananas](https://leetcode.com/problems/koko-eating-bananas/) ⭐ | M | binary search on the answer | Wed | | | |
| 1011 | [Capacity To Ship Packages Within D Days](https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/) | M | binary search on the answer | Wed | | | |
| 3 | [Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/) (**redo**) | M | variable window | Wed | | | |
| 34 | [Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/) ⭐ | M | two boundary searches | Thu | | | |
| 162 | [Find Peak Element](https://leetcode.com/problems/find-peak-element/) | M | binary search on a slope | Thu | | | |
| 206 | [Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/) ⭐ | E | 3 pointers (prev, cur, next) | Fri | | | |
| 21 | [Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists/) ⭐ | E | dummy node | Fri | | | |
| 141 | [Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/) | E | fast/slow pointers | Sat | | | |
| 876 | [Middle of the Linked List](https://leetcode.com/problems/middle-of-the-linked-list/) | E | fast/slow pointers | Sat | | | |
| 19 | [Remove Nth Node From End of List](https://leetcode.com/problems/remove-nth-node-from-end-of-list/) ⭐ | M | two pointers n apart + dummy | Sat | | | |
| 143 | [Reorder List](https://leetcode.com/problems/reorder-list/) | M | middle + reverse + merge | Sat | | | |
| 2 | [Add Two Numbers](https://leetcode.com/problems/add-two-numbers/) | M | carry + dummy node | Sat | | | |
| 138 | [Copy List with Random Pointer](https://leetcode.com/problems/copy-list-with-random-pointer/) | M | hash map old → new | Sun | | | |
| 146 | [LRU Cache](https://leetcode.com/problems/lru-cache/) ⭐ | M | hash map + doubly linked list | Sun | | | |
| 287 | [Find the Duplicate Number](https://leetcode.com/problems/find-the-duplicate-number/) 🏋️ | M | cycle detection on indices | Sun (stretch) | | | |

⭐ = asked very often, so make sure you can do it cold.

**For every binary search problem, say these out loud before coding** (they're part of step 2):
1. What is `lo`, what is `hi`, and is `hi` **inside** or **outside** the range?
2. What's true when the loop ends?
3. After each comparison, which half do you throw away, and **why can't the answer be in it**?

**Self-check at the end of the week:**
1. #35 and #875 use the same template. What is the "sorted array of false…false true…true" in #875?
2. In #206, which node do you lose if you rewire `cur->next` before saving it?
3. #143 is three easier problems glued together. Which three?
