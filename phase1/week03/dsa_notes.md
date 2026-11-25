# Week 3 DSA Notes: Binary Search, Binary Search on the Answer, Linked List

---

## 1. Binary Search

### When to think "binary search"
- The input is **sorted**, or
- there's a yes/no question whose answers look like **`false false false true true true`** as the index grows (or the other way round).

The second case is the important one. A sorted array is just one example of it: "is `nums[i] >= target`?" gives `F F F T T T`. Binary search finds **where the answers switch**, in log₂(n) steps.

| n | steps (log₂ n) |
|---|---|
| 1,000 | 10 |
| 10⁶ | 20 |
| 10⁹ | 30 |

### Template A: find an exact value (#704)
```cpp
int lo = 0, hi = n - 1;                 // hi is INSIDE the range
while (lo <= hi) {                      // the range [lo, hi] is not empty
    int mid = lo + (hi - lo) / 2;
    if (nums[mid] == target) return mid;
    if (nums[mid] < target) lo = mid + 1;   // mid and everything left of it is too small
    else hi = mid - 1;                      // mid and everything right of it is too big
}
return -1;                              // the range became empty: not found
```

### Template B: find the first `true` (lower bound). Learn this one well.
```cpp
int lo = 0, hi = n;                     // hi is OUTSIDE: hi = n means "no true at all"
while (lo < hi) {                       // stop when lo == hi
    int mid = lo + (hi - lo) / 2;
    if (isTrue(mid)) hi = mid;          // mid might be the first true: keep it
    else lo = mid + 1;                  // mid is false: the first true is to its right
}
return lo;                              // the first index where isTrue holds (or n)
```
**What stays true the whole time (the invariant):** everything left of `lo` is `false`, and everything from `hi` onwards is `true`. When `lo == hi`, that index is the switch point.

**Why it always stops:** `mid` rounds down, so `mid < hi`. Each step either moves `hi` down to `mid` or moves `lo` up to `mid + 1`. The range shrinks every time.

Most problems this week come down to: *"what is `isTrue(i)`?"* Examples:

| Question | `isTrue(i)` |
|---|---|
| where would `target` be inserted? | `nums[i] >= target` |
| last position of `target` | (first `i` with `nums[i] > target`) − 1 |
| minimum in a rotated array | `nums[i] <= nums[n-1]` (are we in the lower part yet?) |

### Rotated sorted arrays (#33, #153): the key idea
`[4,5,6,7,0,1,2]` isn't sorted, but cut it anywhere at `mid` and **at least one half is sorted**. You can tell which half by comparing `nums[lo]` with `nums[mid]`. In a sorted half you can check "is the target inside?" with two comparisons. If it is, go there. If not, go to the other half.
Draw the array as two rising lines with a drop between them before coding.

### Traps
- **`(lo + hi) / 2` overflows** when `lo + hi > 2.1×10⁹`. Index ranges are small, but in §2 the range goes up to 10⁹. Always write `lo + (hi - lo) / 2`.
- **Infinite loop:** `lo = mid` together with a `mid` that rounds down. With `lo = 3, hi = 4`: `mid = 3`, `lo = 3` again, forever. In Template B, `lo` always becomes `mid + 1`.
- **Mixing the templates:** `while (lo <= hi)` with `hi = mid` can loop forever. Choose one template and keep all of its parts.
- An empty array, or a target smaller or bigger than every element: trace both.

---

## 2. Binary search on the answer

Some problems don't give you a sorted array, but they ask for **the minimum value that works** (or the maximum):

> "What's the **slowest** eating speed that still finishes in `h` hours?" (#875)

Ask yourself: if speed `x` works, does `x + 1` also work? **Yes.** Eating faster never hurts. So over all possible speeds the answers look like `F F F T T T`, and that's Template B again. This time you search over **values**, not indices.

**Recipe:**
1. `feasible(x)`: a function that answers "does `x` work?" Usually a greedy O(n) simulation.
2. **Monotonic:** check that if `x` works, every bigger `x` works too. Say this out loud in the interview, because it's *why* binary search is allowed.
3. Range: `lo` = the smallest value that could possibly be the answer, `hi` = a value that **definitely** works.
4. Template B over `[lo, hi]`.

**Complexity:** O(n · log(range)). n = 10⁴, range = 10⁹ → 10⁴ × 30 = 3×10⁵ operations.

### Worked example (not one of this week's problems): integer square root
"Return ⌊√x⌋ for 0 ≤ x ≤ 2³¹ − 1."
- `isTrue(k)` = `k * k > x`. Answers look like `F F F T T` → find the first true `k`, and the answer is `k − 1`.
- Range: `lo = 0`, `hi = x + 1` (definitely true).
- Overflow: `k * k` with `k` ≈ 2³¹ needs `long long`, and so does `hi = x + 1`.

```cpp
int mySqrt(int x) {
    long long lo = 0, hi = (long long)x + 1;
    while (lo < hi) {
        long long mid = lo + (hi - lo) / 2;
        if (mid * mid > x) hi = mid;
        else lo = mid + 1;
    }
    return (int)(lo - 1);
}
```

### Traps
- `feasible()` counting can overflow too: a sum of 10⁴ values up to 10⁹ is 10¹³ → `long long`.
- `ceil(a / b)` with integers: `(a + b - 1) / b`. Don't use `ceil()` on doubles.
- Choosing `lo` too small can make `feasible()` divide by zero (speed 0).

---

## 3. Linked List

```cpp
struct ListNode {
    int val;
    ListNode* next;
    ListNode(int v) : val(v), next(nullptr) {}
};
```
No indexes and no random access. All you can do is follow `next`. Every linked list problem comes down to **rewiring pointers without losing part of the list.**

### Habit 1: draw it
Draw 3–4 boxes with arrows on paper, and change the arrows **one line of code at a time**. Most linked list bugs are an arrow you overwrote before saving where it pointed.

### Habit 2: the dummy node
When the **head itself might change** (removed, or a new list is built), put a fake node in front of it:
```cpp
// Remove every node with value val (#203, not one of this week's problems)
ListNode* removeElements(ListNode* head, int val) {
    ListNode dummy(0);
    dummy.next = head;
    ListNode* cur = &dummy;
    while (cur->next) {
        if (cur->next->val == val) cur->next = cur->next->next;   // skip it (in an interview, say you'd delete it)
        else cur = cur->next;
    }
    return dummy.next;   // NOT head: head itself may have been removed
}
```
Without the dummy node you'd need a separate `if` for "the head matches", and that's exactly the special-case patch the checklist warns about.

### Habit 3: fast and slow pointers
`slow` moves 1 step and `fast` moves 2 steps at a time.

| Use | How |
|---|---|
| Middle of the list | when `fast` reaches the end, `slow` is in the middle |
| Is there a cycle? | if `fast` ever **meets** `slow`, there's a cycle; if `fast` hits `nullptr`, there isn't |
| n-th from the end | move one pointer `n` steps first, then move both at the same speed |

**Why fast and slow must meet in a cycle:** once both are inside the cycle, the gap between them shrinks by exactly 1 each step (fast +2, slow +1), so it reaches 0. Fast can't jump over slow. This is the "why" an interviewer asks for.

### Habit 4: the loop condition
- `while (cur)`: visit every node.
- `while (cur->next)`: stop **at** the last node (you need it to link something after it).
- `while (fast && fast->next)`: safe for 2 steps. Check `fast` first, or `fast->next` crashes on `nullptr`.

### Complexity talk
Almost everything here is O(n) time. Say clearly whether you use **O(1) extra space** (pointers only) or O(n) (a hash map or a copy). Interviewers often ask "can you do it without the map?"

### Traps
- Returning `head` after the head changed. Return `dummy.next` or the new head.
- Losing the rest of the list: save `next` **before** you change `cur->next`.
- Lists with 0, 1 or 2 nodes: trace all three.
- An even vs odd length for "middle": know which middle your loop returns.
