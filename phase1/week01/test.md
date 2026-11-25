# Week 1 Test (closed book, 45 min, 20 marks)

Write your answers on paper or in a separate file. Then open the answer key and self-grade honestly.

## Part A: DSA (8 marks)
1. (2) Give the time and space complexity of the optimal Group Anagrams solution if you sort each word to build the key. What key gives better time complexity?
2. (2) Why does the sliding window approach **fail** for "count subarrays with sum = k" when the array has negative numbers, and what do you use instead?
3. (2) In 3Sum, where exactly must you skip duplicates? Name both places.
4. (2) **Code on paper, 10 min:** given a sorted array, return the number of unique elements, in place, with O(1) extra space.

## Part B: HLD (6 marks)
5. (2) List the steps between pressing Enter on a URL and receiving the HTML, in order.
6. (1) Roughly how much slower is a round trip India ↔ US than a Redis GET in the same datacenter (order of magnitude)?
7. (3) URL shortener: 10M new URLs/day, read:write 100:1, 500 B per record, kept 5 years. Find write QPS, read QPS, and total storage.

## Part C: LLD / Java (6 marks)
8. (1) What does `new String("a") == "a"` evaluate to in Java, and why?
9. (2) When would you choose an abstract class over an interface? Give one concrete example.
10. (3) Rewrite this so adding a new notification channel needs **no change** to `notify`:
```java
void notify(String type, String msg) {
    if (type.equals("EMAIL")) sendEmail(msg);
    else if (type.equals("SMS")) sendSms(msg);
}
```

---

<details><summary>🔑 Answer key (open only after you finish)</summary>

1. Sorting keys: O(n · k log k) time, O(n·k) space (k = max word length). A count-array key (26 counts, e.g. `"1#0#2#..."`) gives O(n·k).
2. A sliding window assumes that growing the window increases the sum and shrinking it decreases the sum. With negative numbers that isn't true, so you can't decide which pointer to move. Use **prefix sum + hash map** of counts (`seen[cur-k]`).
3. (a) The outer loop: `if (i > 0 && a[i] == a[i-1]) continue;` (b) After finding a triplet: advance `l` while `a[l] == a[l-1]` (and/or move `r` down past duplicates).
4. ```cpp
   int removeDuplicates(vector<int>& a) {
       if (a.empty()) return 0;
       int w = 1;
       for (int r = 1; r < (int)a.size(); r++)
           if (a[r] != a[w-1]) a[w++] = a[r];
       return w;
   }
   ```
5. DNS resolve → TCP handshake → TLS handshake → HTTP request (via CDN/LB) → app server (cache/DB) → response → browser renders and fetches assets.
6. ~150–250 ms vs ~0.5–1 ms → roughly **100–500×** slower (2–3 orders of magnitude).
7. Write: 10M / 10⁵ = **~100/s** (exact ≈116). Read: **~10,000/s**. Storage: 10M × 500 B = 5 GB/day × 365 × 5 ≈ **~9 TB** (~27 TB with 3× replication).
8. `false`. `new String` creates a new object, and `==` compares references. Use `.equals()`.
9. When related types share **state or code**, e.g. `abstract class Vehicle { String plate; Instant entryTime; ... }` with `Car`/`Bike` subclasses. An interface is for a capability shared by unrelated types.
10. ```java
    interface Notifier { void send(String msg); }
    class EmailNotifier implements Notifier { public void send(String m) { /* email */ } }
    class SmsNotifier   implements Notifier { public void send(String m) { /* sms */ } }
    // Registry instead of if-else:
    Map<String, Notifier> notifiers = Map.of("EMAIL", new EmailNotifier(), "SMS", new SmsNotifier());
    void notify(String type, String msg) { notifiers.get(type).send(msg); } // + handle unknown type
    ```
    A new channel = a new class + one registry entry. This is the Strategy pattern + Open/Closed principle.

**Scoring:** 17–20 → on track. 12–16 → fine, note the weak area in the tracker. < 12 → tell me, and Week 2 starts with a recap.
</details>

## Part D: Practice estimate #2 (not graded, answer here)
Photo app: 5M DAU × 10% × 2 photos × 1 MB = **1 TB/day** → ~1.1 PB in 3 years. Conclusion: use blob storage (S3), not a database.
