# Week 1 HLD Flashcards: Latency Numbers + Availability

Source: `hld_notes.md` §2. Cover the answers, say yours out loud, then check.
Re-run these on Friday and again at the start of Week 2.

---

## Q1. Ordering (fastest → slowest)
Put these in order:
A. SSD random read · B. RAM read · C. Round trip India ↔ US · D. Simple DB query by indexed key · E. L1 cache

<details><summary>Answer</summary>

**E → B → A → D → C**
L1 ~1 ns → RAM ~100 ns → SSD ~100 µs → DB query ~1–5 ms → India↔US ~150–250 ms.
</details>

---

## Q2. Why is Redis "slow"?
Redis keeps data **in RAM** (~100 ns read), yet a Redis GET from your app server takes **~0.5–1 ms**,
about 10,000× slower. Where does the rest of that time go?

<details><summary>Answer</summary>

The **network round trip** inside the datacenter (~0.5 ms), app server → Redis → back. The RAM read is tiny next to it.
- **Latency** = how long one trip takes (length of the road). This is what you pay here.
- **Bandwidth** = how much data per second (number of lanes). It barely matters for a ~100-byte GET; it matters for video and backups.

**Interview answer:** "Redis is in-memory, but every call still pays a ~0.5 ms network round trip, so it's fast
compared to a DB (1–5 ms), not compared to a local RAM read. For very hot data you also cache in-process."
</details>

---

## Q3. Availability in series
A request goes **load balancer (99.9%) → app server (99.9%) → database (99.9%)**. If any one is down, the request fails.
1. Overall availability?
2. Downtime per month: more or less than 43 min, and how much?

<details><summary>Answer</summary>

1. 0.999³ ≈ **99.7%**. Shortcut: add the downtimes, 0.1% + 0.1% + 0.1% = 0.3% down.
2. 0.3% down = 3 × 43 min ≈ **129 min ≈ 2 h+ per month**, so **more**.

**Takeaway:** every component in series adds its downtime, so more hops means a less available system.
(Common mistake: multiplying 43 × 0.997. Work with the *downtime* %, not the uptime %.)
</details>

---

## Q4. Availability in parallel (redundancy)
You replace the single DB with **two replicas in parallel**, each 99.9%. The request succeeds if **at least one**
is up. They fail independently. What's the availability of the DB layer?

Hint: for the layer to be down, *both* replicas must be down at the same moment.
Is that like adding the chances, or like rolling a 1 on two dice at once?

<details><summary>Answer</summary>

P(one down) = 0.001 → P(both down) = 0.001 × 0.001 = **0.000001** (3 + 3 = 6 decimal places).
As a %: 0.000001 × 100 = 0.0001% down → **99.9999% up** ("six nines", ~30 s downtime/year).

Same logic as two coins: P(both heads) = ½ × ½ = ¼, so independent events that all have to happen **multiply**.

**Takeaway:** series = add the downtimes (gets worse); parallel = multiply the downtimes (gets much better).
One extra replica took the DB from 3 nines to 6 nines. That's why you add replicas.
Catch: the LB and app server are still single 99.9% boxes, so the whole request is still ≈ 99.8%.
**The weakest single component in series sets the limit.**

(Mistakes on Nov 11: added instead of multiplied → 199.9%; dropped a zero → 0.00001; forgot ×100 → 99.999999%.)
</details>

---

## Q5. Latency budget
A user in **India** opens a page; the app server is in the **US** (round trip ≈ 200 ms). The server makes
**5 DB queries one after another**, ~5 ms each, DB in the same datacenter.
1. Roughly how long until the user gets the response? 2. What dominates?

<details><summary>Answer</summary>

200 + 5 × 5 = **225 ms**. The cross-continent round trip is ~90% of it.
</details>

---

## Q6. Which fix helps more?
Same setup, one change allowed:
(a) run the 5 queries **in parallel**, or (b) deploy app + DB in a **Mumbai** region (user RTT ≈ 20 ms), queries still sequential.

<details><summary>Answer</summary>

(a) 200 + 5 = **205 ms** · (b) 20 + 25 = **45 ms**, so **(b)**.
**Takeaway:** fix the biggest part first. You can't speed up light, but you can shorten the distance.
Cost of (b): two DB copies must stay in sync, so say in one line "multi-region cuts latency, but now I need cross-region replication."
</details>

---

## Q7. Storage estimation
**1 M daily active users**, each uploads **2 photos/day**, **500 KB** each. Storage per day? Per year (365 ≈ 400)?

<details><summary>Answer</summary>

10⁶ × 2 × 500 KB = 10⁹ KB = **1 TB/day** → 1 TB × 400 = **400 TB/year**.
**Takeaway:** use the number to decide something: "400 TB/year → object storage (S3), not the DB, not one machine."
(Nov 11 slip: 1000 × 400 written as 4,000,000. Write the zeros out once when unsure.)
</details>

---

## Q8. The nines in practice
The promise is **99.99%**/month. Each deploy = **10 min down**, ~**4 deploys/month**. Met? If not, which nines do you meet?

<details><summary>Answer</summary>

40 min/month. 99.99% allows **4.3 min**, so **no**. 99.9% allows **43 min**, so yes, barely,
which leaves only **3 min** for every real outage that month.
</details>

---

## Q9. Zero-downtime deploys
1. To meet 99.99% with 4 deploys/month (no other outages), what's the max downtime per deploy?
2. How can a deploy cause **zero** downtime? (Hint: Q4, what did a second replica give you?)

<details><summary>Answer</summary>

1. 4.3 / 4 ≈ **~1 min per deploy**. That's unrealistic if you stop and restart a server.
2. **Rolling deploy:** with ≥ 2 servers behind the load balancer, update them one at a time.
   The LB stops sending new traffic to server A (**draining**: let its in-flight requests finish), you update A, A passes its
   health check, the LB puts it back in rotation, then you repeat for B. At least one server is always serving, so users see no downtime.
   (Related: blue-green deploy, which runs a full new copy and switches the LB over in one step.)
</details>

---

## Mistakes to watch (from Nov 11)
- Mixing up **latency** with **bandwidth**.
- Applying math to **uptime %** when you should work with **downtime %**.
- Availability can never be above 100%. If you get 199.9%, you added when you should have multiplied.
