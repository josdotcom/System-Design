# Week 1 DSA Notes: Hashing · Prefix Sum · Two Pointers

## 0. How to approach ANY problem (use this for all 150)

1. **Restate** the problem and ask about constraints: n up to? negatives? duplicates? sorted?
2. **Work an example by hand**, including one edge case (empty, single element, all the same).
3. **Brute force first**, out loud, with its complexity. Then: "can I do better?"
4. **Match a pattern** using the constraint table below.
5. **Code**, then **dry-run** your own example. Then edge cases.

| n up to | Target complexity | Usually means |
|---|---|---|
| 10–20 | O(2ⁿ), O(n!) | backtracking / bitmask |
| 500 | O(n³) | 3 nested loops, interval DP |
| 5,000 | O(n²) | 2-D DP, all pairs |
| 10⁵–10⁶ | O(n log n) or O(n) | sort, heap, hashing, two pointers, binary search |
| 10⁹+ | O(log n) or O(1) | binary search on the answer, math |

---

## 1. Hashing

**When:** "have I seen X before?", "count occurrences", "group things by some property", "find a pair that sums to …".
**Trade:** O(n) extra space to turn an O(n²) search into O(n).

```cpp
// Two Sum: store what you've seen; for each x, look up its complement.
vector<int> twoSum(vector<int>& a, int target) {
    unordered_map<int,int> idx;                    // value -> index
    for (int i = 0; i < (int)a.size(); i++) {
        auto it = idx.find(target - a[i]);
        if (it != idx.end()) return {it->second, i};
        idx[a[i]] = i;                             // insert AFTER checking (so we don't use the same element twice)
    }
    return {};
}
```

**Derived-key trick (Group Anagrams):** the key is not the string itself but its *canonical form*, e.g. the sorted string, or a count signature like `"1#0#2#..."`. Things that should be grouped together must share the same key.

**Longest Consecutive Sequence trick:** put everything in a set. Only start counting from `x` if `x-1` is *not* in the set, so each number is visited at most twice → O(n).

**C++ gotchas**
- `m[key]` **inserts** a default value if the key is missing. Use `m.count(key)` or `m.find(key)` when you're only checking.
- `unordered_map` has worst-case O(n) per operation (hacked inputs on Codeforces). On LeetCode this is fine. `map` is O(log n) and ordered.
- For lowercase letters, `int cnt[26]{}` beats a map: faster and simpler.

---

## 2. Prefix Sum

**When:** "sum/count of a subarray", many range queries, "subarray with sum = k".
`pre[i]` = sum of the first i elements → `sum(l..r) = pre[r+1] - pre[l]`.

**The key idea (#560 Subarray Sum Equals K):**
sum(l..r) = k ⇔ pre[r+1] − pre[l] = k ⇔ **pre[l] = pre[r+1] − k**.
So while walking, count how many earlier prefixes equal `current − k`. This works **with negative numbers**, unlike sliding window.

```cpp
int subarraySum(vector<int>& a, int k) {
    unordered_map<long long,int> seen{{0, 1}};     // empty prefix: sum 0, seen once
    long long cur = 0; int ans = 0;
    for (int x : a) {
        cur += x;
        if (auto it = seen.find(cur - k); it != seen.end()) ans += it->second;
        seen[cur]++;
    }
    return ans;
}
```

**Variations you'll meet:**
- Count of subarrays divisible by k → store `((cur % k) + k) % k` (the `+ k` fixes negative remainders in C++).
- Longest subarray with sum k → store the **first index** each prefix value was seen (#523 uses this too).
- Product except self (#238) → a prefix product from the left times a suffix product from the right, with no division.

---

## 3. Two Pointers

Three shapes. Learn to recognize which one you're looking at.

**(a) Opposite ends** (sorted array or palindrome): `l = 0, r = n-1`, move one of them based on a comparison.
```cpp
// Two Sum II (sorted): too small → move l right; too big → move r left.
while (l < r) {
    int s = a[l] + a[r];
    if (s == t) return {l+1, r+1};
    (s < t) ? l++ : r--;
}
```
*Why it's correct:* if `a[l] + a[r] < t`, then `a[l]` can't pair with anything (r is the largest left), so drop it.

**(b) Read / write (same direction):** `w` = next slot to write, `r` scans. Used for in-place filtering (#26, #283).
```cpp
int w = 0;
for (int r = 0; r < n; r++) if (keep(a[r])) a[w++] = a[r];
```

**(c) Fix one, then two pointers on the rest (3Sum):** sort, loop over `i`, run (a) on `i+1..n-1`. **Skip duplicates** at both `i` and after finding a match. That's where most bugs are.

**Container With Most Water (#11):** always move the **shorter** wall. Moving the taller one can never give more area, because width shrinks and the height is still capped by the shorter wall.

**Dutch National Flag (#75):** `lo, mid, hi`. Everything before `lo` is 0, `lo..mid-1` is 1, everything after `hi` is 2. If `a[mid] == 2`, swap it with `hi--` and **don't** advance `mid` (the swapped-in value hasn't been checked yet).

---

## 4. Bonus: Kadane (#53)
"Best subarray ending here" = max(x, best ending at previous + x). Track the global max.
```cpp
int best = a[0], cur = a[0];
for (int i = 1; i < n; i++) { cur = max(a[i], cur + a[i]); best = max(best, cur); }
```
This is your first taste of DP. In Week 7 you'll see it's a 1-D DP with O(1) space.

## 5. Boyer–Moore voting (#169)
Keep a candidate and a count. When the count is 0, the next element becomes the candidate. Same element → count++, different → count--. A majority element (> n/2 occurrences) survives all the cancelling.

## 6. Bucket sort: use the value as an index (#347)
**When:** the values you want to sort by fall in a **small, known range** (exam scores 0–100, ages 0–150, counts 1..n).
Then you don't need comparison sorting (O(n log n)). Make an array indexed by the value and drop each item into its slot.

**Example: sort students by score (0–100).**
```cpp
vector<vector<string>> bucket(101);          // index = score
for (auto& [name, score] : students)
    bucket[score].push_back(name);           // O(1) per student
for (int s = 100; s >= 0; s--)               // walk high → low
    for (auto& name : bucket[s]) cout << name << " ";
```
Time **O(n + R)**, where R is the size of the range. Space **O(n + R)**. When R ≤ n this is **O(n)**, which beats O(n log n).

**Why each slot is a list:** several items can share the same value (two students with 90).
**Key question to ask yourself:** "What is the *smallest* and *largest* value the thing I'm sorting by can take?" If that range is bounded by n, a bucket array works.
