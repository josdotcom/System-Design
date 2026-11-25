# Week 3 Test (closed book, 45 min, 20 marks)

Write your answers on paper or in a separate file. Then open the answer key and self-grade honestly.

## Part A: DSA (8 marks)
1. (2) This code is meant to return the first index with `nums[i] >= target`. Find an input where it never stops, and fix it.
```cpp
int lo = 0, hi = n;
while (lo < hi) {
    int mid = (lo + hi) / 2;
    if (nums[mid] >= target) hi = mid;
    else lo = mid;
}
return lo;
```
2. (2) **#410 Split Array Largest Sum** (not this week's problem): split `nums` (non-negative) into `k` contiguous parts so that the **largest part-sum is as small as possible**. Using binary search on the answer: what are `lo`, `hi` and `feasible(x)`, and why is `feasible` monotonic? No code needed.
3. (2) In Floyd's cycle detection (`slow` +1, `fast` +2), why are the two pointers **guaranteed to meet** if there's a cycle, and why can't `fast` jump over `slow`?
4. (2) **Code on paper, 10 min:** return the middle node of a linked list. For an even length, return the **second** middle (`[1,2,3,4]` → 3). State time and space.

## Part B: HLD (6 marks)
5. (2) Describe the cache-aside **read** path and **write** path. Why does the write path delete the key instead of updating it?
6. (2) The home-page key (read 20,000 times/s) expires at 9:00:00, and the DB goes to 100% CPU for 15 seconds. Name the problem and give two fixes.
7. (1) Which write strategy for a "views" counter on videos, and what's the risk you accept?
8. (1) After a deploy, some users still see the old CSS for a day. What's the fix, so it never happens again?

## Part C: LLD / Java (6 marks)
9. (2) Which pattern fixes this, and which SOLID principle does the current code break? Write the interface and one implementing class.
```java
double shippingCost(String courier, double kg) {
    if (courier.equals("DELHIVERY")) return 40 + 10 * kg;
    else if (courier.equals("BLUEDART")) return 60 + 15 * kg;
    else if (courier.equals("INDIA_POST")) return 25 + 5 * kg;
    throw new IllegalArgumentException(courier);
}
```
10. (2) An `Order` must notify inventory, email and analytics when it's placed. Write the observer interface, and the 3 methods on `Order` that handle subscribing and notifying (signatures + bodies, Java).
11. (2) Write a thread-safe Singleton in Java (any correct way). Give one reason an interviewer might push back on using a Singleton.

---

<details><summary>🔑 Answer key (open only after you finish)</summary>

**1.** `lo = mid` never moves `lo` forward when `mid == lo`. Example: `nums = [1, 5]`, `target = 3`: `lo = 0, hi = 2, mid = 1`, `5 >= 3` → `hi = 1`. Now `lo = 0, hi = 1, mid = 0`, `1 < 3` → `lo = 0` again, forever. Fix: `lo = mid + 1` (mid is too small, so it can't be the answer). Also acceptable to mention: use `lo + (hi - lo) / 2`. (1 mark: failing input traced, 1 mark: fix with the reason.)

**2.** `lo = max(nums)` (a part must hold at least the biggest element), `hi = sum(nums)` (one part holds everything, which always works). `feasible(x)` = greedily fill parts left to right, starting a new part whenever adding the next number would go over `x`, then check `parts <= k`. Monotonic: if every part can stay ≤ x with ≤ k parts, it can also stay ≤ x + 1 (same split works). Find the first `x` where `feasible` is true. O(n log(sum)). (1 mark: lo/hi, 1 mark: feasible + monotonic reason.)

**3.** Once both are in the cycle, each step `fast` gains exactly 1 position on `slow` (+2 vs +1), so the gap (counted along the cycle) shrinks by 1 every step: d, d−1, …, 1, 0. It can't skip from 1 to −1, because it only changes by 1. So they meet within one lap of the cycle. Without a cycle, `fast` reaches `nullptr` first.

**4.**
```cpp
ListNode* middleNode(ListNode* head) {
    ListNode* slow = head;
    ListNode* fast = head;
    while (fast && fast->next) {
        slow = slow->next;
        fast = fast->next->next;
    }
    return slow;
}
```
Time O(n), space O(1). Trace: `[1,2,3,4]`: (1,1) → (2,3) → (3, null) → stop, return 3 ✓. `[1]` → returns 1 ✓. (1 mark: correct + returns the second middle, 1 mark: complexity.) Counting the length first and walking n/2 is also full marks if correct.

**5.** Read: check the cache → hit: return it → miss: read the DB, store it in the cache with a TTL, return it. Write: update the DB, then **delete** the key. Why delete: two concurrent writers can update the cache in the wrong order and leave an old value there until the TTL expires; deleting means the next read loads the current value from the DB (worst case: one extra miss). It also avoids caching data nobody reads.

**6.** **Cache stampede** (thundering herd). Fixes (any two): only one request rebuilds the key (a Redis lock with `SET NX`) while the others wait or get the old value; refresh the hot key in the background before it expires; never let hot keys expire (refresh them on a schedule). Jitter is the fix for an *avalanche*, so it gets half credit here.

**7.** **Write-back**: `INCR` in Redis and flush to the DB every few seconds in batches. Risk: if Redis crashes before the flush, the last few seconds of views are lost, which is acceptable for a view counter.

**8.** Content-hashed (versioned) file names, `style.3f9a2c.css`, with a long `max-age`. Each deploy produces a new file name, and the HTML points to it, so no browser or CDN can serve the old file for the new page.

**9.** **Strategy**. It breaks **O** (open/closed): every new courier means editing this method.
```java
interface ShippingCostStrategy { double cost(double kg); }
class DelhiveryCost implements ShippingCostStrategy {
    public double cost(double kg) { return 40 + 10 * kg; }
}
```
(1 mark: Strategy + O, 1 mark: interface + class.) A Factory picking the strategy from the courier name is a bonus, not required.

**10.**
```java
interface OrderListener { void onOrderPlaced(Order order); }

class Order {
    private final List<OrderListener> listeners = new ArrayList<>();
    void subscribe(OrderListener l)   { listeners.add(l); }
    void unsubscribe(OrderListener l) { listeners.remove(l); }
    void place() {
        // ... mark as placed ...
        for (OrderListener l : listeners) l.onOrderPlaced(this);
    }
}
```
(1 mark: interface, 1 mark: the three methods.) A listener list in an `OrderService` instead of `Order` is also correct.

**11.** Any one of these:
```java
enum Registry { INSTANCE; }
```
```java
class Registry {
    private Registry() {}
    private static class Holder { static final Registry INSTANCE = new Registry(); }
    static Registry getInstance() { return Holder.INSTANCE; }
}
```
or double-checked locking with a `volatile` field, or a `synchronized` `getInstance()`. A version without a private constructor or with an unsynchronized `if (instance == null)` gets 0 for the code part. Pushback (any one): it's hidden global state; it's hard to replace with a fake in tests; classes that call `getInstance()` hide their dependencies (breaks D). The better habit: create one instance and inject it.

</details>
