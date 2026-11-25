# Week 2 DSA Notes: Sliding Window, Stack, Monotonic Stack

---

## 1. Sliding Window

### When to think "window"
The question asks about a **contiguous** subarray or substring (longest, shortest, count, max sum), **and** adding an element only ever moves the condition one way.

> "Longest substring with no repeats": adding a character can only *break* the rule, and removing one can only *fix* it. That one-way behaviour is what lets the left edge move forward and never come back.

**When it fails:** sums with **negative numbers**. Adding a negative number makes the sum smaller, so "too big → shrink" stops being true. That's why #560 (Week 1) needed prefix sum + hash map, not a window.

### Two kinds of window

**Fixed size k:** add the new element on the right and drop the one that fell off the left.

```cpp
int sum = 0, best = INT_MIN;
for (int r = 0; r < n; r++) {
    sum += nums[r];                       // add the new right element
    if (r >= k) sum -= nums[r - k];       // drop the element that left the window
    if (r >= k - 1) best = max(best, sum);  // the window is full from r = k-1 on
}
```

**Variable size:** grow the right edge every step, and shrink the left edge **while** the window breaks the rule.

```cpp
int l = 0, best = 0;
for (int r = 0; r < n; r++) {
    add(nums[r]);                         // 1. grow: take in the new element
    while (windowIsInvalid()) {           // 2. shrink until the window is valid again
        remove(nums[l]);
        l++;
    }
    best = max(best, r - l + 1);          // 3. now the window is valid: record it
}
```

| Question asks for | Where you update the answer |
|---|---|
| **Longest** valid window | **after** the `while` (the window is valid again) |
| **Shortest** window that meets a target (#209) | **inside** the `while`, before you shrink (every shrink step is a valid window) |

### Why it's O(n) even with a `while` inside a `for`
`r` moves right n times. `l` also only moves right, and it can't pass `r`, so it moves at most n times **in total**, across all iterations. Total work ≈ 2n. When asked about a nested loop's complexity, count **how many times each pointer moves overall**, not "loop × loop".

### What the window remembers
| Rule | Keep |
|---|---|
| No repeats | `count[128]` or a last-seen index per character |
| At most k bad elements | a counter of bad elements |
| Sum limit (non-negative numbers) | a running `sum` |
| Same letters as another string (anagram) | `count[26]` for the window vs `count[26]` for the target |

### Traps
- `if` instead of `while` when shrinking: one shrink step might not be enough.
- Window length is `r - l + 1`, not `r - l`.
- `vector<int> count(26)` for lowercase letters only. Check the constraints: "printable ASCII" needs 128.

---

## 2. Stack

LIFO: last in, first out. In C++: `stack<int> st;` with `push`, `pop`, `top`, `empty`, `size`. All of them are O(1).

**When to think "stack":** something must be **matched with the most recent unmatched thing**. Brackets, undo, "evaluate when the operator arrives", going back up a path.

**⚠️ Trap:** `st.top()` or `st.pop()` on an empty stack is **undefined behaviour** in C++. It doesn't throw an exception, it may just return garbage. Always check `!st.empty()` first, and think about what an empty stack *means* in your problem.

A `vector<int>` works as a stack too (`push_back`, `back`, `pop_back`), and lets you look at any element. Some solutions prefer that.

---

## 3. Monotonic Stack

### The problem it solves
"For every element, find the **next greater** (or smaller) element." Brute force checks everything to the right: O(n²). A monotonic stack does it in **O(n)**.

### The idea
Keep a stack of **indices whose answer hasn't been found yet**. Their values are in **decreasing** order from bottom to top. When a new element arrives that is bigger than the top, **it is the answer for the top**: pop the top and record it. Keep popping while the new element is still bigger. Then push the new index.

```cpp
vector<int> nextGreater(n, -1);
stack<int> st;                               // indices, values decreasing bottom → top
for (int i = 0; i < n; i++) {
    while (!st.empty() && nums[i] > nums[st.top()]) {
        nextGreater[st.top()] = nums[i];     // nums[i] is the first bigger value to its right
        st.pop();
    }
    st.push(i);
}
// anything still on the stack has no greater element to its right (stays -1)
```

Trace on `[2, 1, 5]`:

| i | nums[i] | pops (answer found) | stack after (values) |
|---|---|---|---|
| 0 | 2 | none | [2] |
| 1 | 1 | none (1 < 2) | [2, 1] |
| 2 | 5 | 1 → 5, then 2 → 5 | [5] |

Result: `[5, 5, -1]`

### Why O(n)
Each index is **pushed once and popped at most once**. That's at most 2n stack operations in total, same argument as the sliding window.

### Which variant?
| You want | Stack keeps values | Pop while |
|---|---|---|
| next **greater** | decreasing | `nums[i] > top` |
| next **smaller** | increasing | `nums[i] < top` |
| **distance** to the answer (#739) | store **indices**, not values | answer = `i - popped index` |
| circular array (#503) | loop `i` from 0 to `2n - 1`, use `i % n` | |

**Store indices, not values.** From an index you can get the value (`nums[idx]`) *and* the distance (`i - idx`). From a value you can't get the index back.

### #84 (Sunday): one hint only
For each bar, the biggest rectangle that uses that bar's full height stretches left and right until it hits a **shorter** bar. Which "next smaller" questions does that turn into?
