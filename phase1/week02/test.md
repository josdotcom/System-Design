# Week 2 Test (closed book, 45 min, 20 marks)

Write your answers on paper or in a separate file. Then open the answer key and self-grade honestly.

## Part A: DSA (8 marks)
1. (2) The sliding window template has a `while` loop inside a `for` loop. Why is it still O(n)?
2. (2) #209 (shortest subarray with sum ≥ target) uses a sliding window, but #560 (count subarrays with sum = k) can't. What's the difference in the input that decides this?
3. (2) For "next greater element", is the monotonic stack increasing or decreasing (bottom → top)? Why do we push **indices** instead of values?
4. (2) **Code on paper, 10 min:** given a string `s`, return the length of the longest substring with **at most 2 distinct characters**. State time and space complexity.

## Part B: HLD (6 marks)
5. (2) Give one advantage and one disadvantage each of vertical and horizontal scaling.
6. (1) Uploads take 30 s and normal API calls take 50 ms, on the same servers. Which load-balancing algorithm, and why?
7. (3) You have 3 app servers behind round robin, and users get logged out randomly. Explain the cause, give two fixes, and say which one you'd pick and why.

## Part C: LLD / Java (6 marks)
8. (2) Which SOLID principle does this break, and how do you fix it?
```java
class ReportService {
    private final MySqlReportRepository repo = new MySqlReportRepository();
}
```
9. (1) `class Ostrich extends Bird` and `Bird` has `fly()`. Which principle is at risk, and what's the fix?
10. (3) Write a Java enum `Plan` with values `FREE` (max 1 project), `PRO` (max 10), `TEAM` (max 100), and a method that returns the limit. Then show one line that checks whether a user on `PRO` with 7 projects can create another one.

---

<details><summary>🔑 Answer key (open only after you finish)</summary>

**1.** Both pointers only move right. `r` moves n times. `l` never moves left and can't pass `r`, so it moves at most n times **in total**, across all the `while` loops together. About 2n steps → O(n).

**2.** **Negative numbers.** #209 has only positive numbers, so adding an element always makes the sum bigger and removing one always makes it smaller: "too big → shrink" is always right. #560 allows negatives, so shrinking might make the sum *bigger*, and the window can't decide which way to move. Use prefix sum + hash map instead.

**3.** **Decreasing.** Every index on the stack is still waiting for something bigger. When a bigger value arrives it pops everything smaller, so what remains is decreasing. Indices give you both the value (`nums[idx]`) and the distance (`i - idx`); a value can't give you its position back.

**4.**
```cpp
int lengthOfLongestSubstringTwoDistinct(string s) {
    vector<int> cnt(128, 0);
    int l = 0, distinct = 0, best = 0;
    for (int r = 0; r < (int)s.size(); r++) {
        if (cnt[s[r]]++ == 0) distinct++;
        while (distinct > 2) {
            if (--cnt[s[l]] == 0) distinct--;
            l++;
        }
        best = max(best, r - l + 1);
    }
    return best;
}
```
Time O(n), space O(1) (a fixed 128-entry array). Full marks also for a `unordered_map<char,int>` version that erases a key when its count reaches 0 and uses `map.size()`.

**5.** Vertical: + no code changes, simple; − hardware ceiling, single point of failure, cost rises steeply. Horizontal: + almost unlimited, survives a machine failure; − needs stateless servers + a load balancer, more moving parts.

**6.** **Least connections.** Round robin would keep handing new requests to a server that's stuck with several 30 s uploads. Least connections sends new work to whichever server is least busy right now.

**7.** Cause: sessions are stored **in each server's memory**, so a request routed to a different server doesn't know the user. Fixes: (a) sticky sessions, (b) move the session out of the server: JWT, or a session ID + Redis. Pick (b): with sticky sessions, a server crash logs out all its users, load gets uneven, and deploys are harder. With (b) any server can handle any request.

**8.** **D**, Dependency Inversion. The service creates a concrete MySQL class itself, so you can't swap it or test without a real DB. Fix: define `interface ReportRepository`, make the field `private final ReportRepository repo;`, and pass it in through the constructor.

**9.** **L**, Liskov Substitution. An ostrich can't fly, so `fly()` would have to throw, and code that works for any `Bird` breaks. Fix: move `fly()` into a separate `Flyer` interface that only flying birds implement.

**10.**
```java
enum Plan {
    FREE(1), PRO(10), TEAM(100);
    private final int maxProjects;
    Plan(int maxProjects) { this.maxProjects = maxProjects; }
    int getMaxProjects() { return maxProjects; }
}
// check:
boolean canCreate = 7 < Plan.PRO.getMaxProjects();   // true
```
(1 mark: values with numbers, 1 mark: field + constructor + getter, 1 mark: the check using `<`, not `<=`.)

</details>
