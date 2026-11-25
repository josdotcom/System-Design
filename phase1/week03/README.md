# Week 3 (Nov 23–29): Binary Search, Linked List + Caching + Patterns I

**DSA:** Binary Search (classic, and "on the answer") · Linked List
**HLD:** Caching: why and where, read/write strategies, eviction, the 3 classic cache failures, CDN, Redis
**LLD:** Patterns I: Strategy, Factory, Observer, Singleton, Builder · **new design: Stock Price Alert System**

Files: `dsa_notes.md` · `dsa_problems.md` · `hld_notes.md` · `lld_notes.md` · `lld_exercise.md` · `test.md`

Unfinished Week 2 work is listed in **Week 2 backlog** at the end of this file. It's separate from the daily plan below.

---

## What's new this week

- **A stricter "Solved".** Steps 1–3 of the checklist below must happen **without me asking**. If I have to prompt for clarifying questions, the approach, or the complexity, log it as **Hint**. Your code is usually right the first time. What's missing is the talking, and interviewers score the talking too.
- **Heavier LLD.** From now on there's a new small design every week. This week's is a Stock Price Alert System, and it uses all 5 patterns. By Weeks 6–7 these become timed 90-minute rounds, like the midterm.
- **Redo this week:** #3 Longest Substring (Week 2 Hint: the `"abba"` trap), cold, on Wednesday.

### Before-you-code checklist (unprompted, every problem)
| # | Step | What it sounds like |
|---|---|---|
| 1 | Clarifying questions: **extremes + output** | "Empty? One element? All equal? Can it be not found? What do I return then?" |
| 2 | Approach in 2–3 lines, **and why it works** | "One half is always sorted, because…" |
| 3 | Complexity **with the number** | O(n log M), n = 10⁴, M = 10⁹ → 10⁴ × 30 = 3×10⁵ |
| 4 | Overflow | `lo + hi` with values up to 10⁹ → 2×10⁹ > 2.1×10⁹ ✗ |
| 5 | Code that follows your approach | if one input fails, find the cause. Don't add an `if` for that input |
| 6 | Trace the smallest input | n = 1, n = 2, target at either end |

---

### Mon, Nov 23 (2h)
- [ ] Read `dsa_notes.md` §1 Binary Search (15m)
- [ ] DSA: #704 Binary Search, #35 Search Insert Position, #74 Search a 2D Matrix
- [ ] HLD: `hld_notes.md` §1 Why cache, and where caches live

### Tue, Nov 24 (2h)
- [ ] DSA: #33 Search in Rotated Sorted Array ⭐, #153 Find Minimum in Rotated Sorted Array
- [ ] LLD: `lld_notes.md` §1 Strategy, §2 Factory
- [ ] LLD: `lld_exercise.md` **Part A** on paper (classes + fields). Send it to me

### Wed, Nov 25 (2h)
- [ ] Read `dsa_notes.md` §2 Binary search on the answer (15m)
- [ ] DSA: #875 Koko Eating Bananas ⭐, #1011 Capacity To Ship Packages Within D Days
- [ ] **Redo #3 Longest Substring Without Repeating Characters cold** (20 min). Trace `"abba"` before submitting
- [ ] HLD: `hld_notes.md` §2 Read/write strategies, §3 Eviction + the 3 classic cache failures

### Thu, Nov 26 (2h)
- [ ] DSA: #34 Find First and Last Position of Element ⭐, #162 Find Peak Element
- [ ] LLD: `lld_notes.md` §3 Observer, §4 Singleton, §5 Builder
- [ ] LLD: `lld_exercise.md` **Part B** on paper (who owns each action + which pattern goes where). Send it to me

### Fri, Nov 27 (2h)
- [ ] Read `dsa_notes.md` §3 Linked List (15m)
- [ ] DSA: #206 Reverse Linked List ⭐, #21 Merge Two Sorted Lists ⭐
- [ ] HLD warm-up (10m): flashcards from Weeks 1 and 2. Say **"quiz me"**
- [ ] HLD: `hld_notes.md` §4 CDN, §5 Redis. Then answer the §6 practice questions out loud

### Sat, Nov 28 (5h)
- [ ] DSA block (2.5h): #141 Linked List Cycle, #876 Middle of the Linked List, #19 Remove Nth Node From End ⭐, #143 Reorder List, #2 Add Two Numbers
- [ ] LLD block (2.5h): `lld_exercise.md` **Part C**: code the alert system. **It must compile and run** with the expected demo output

### Sun, Nov 29 (5h)
- [ ] DSA: #138 Copy List with Random Pointer, #146 LRU Cache ⭐ (the linked list from Friday + the eviction policy from Wednesday)
- [ ] Stretch, only if you have energy: #287 Find the Duplicate Number
- [ ] LLD: `lld_exercise.md` **Part D** (2 new requirements, no edits to existing classes) + review questions
- [ ] **Week 3 `test.md`**, closed book (45m). Self-grade
- [ ] Fill the Weekly Log row in `Prep_Tracker.xlsx` + copy `templates/weekly_review.md` → `review.md` here
- [ ] Tell Claude: **"Week 3 done, review my tracker"**

### Job search this week (~1.5h, any day)
- [ ] **Finish the resume fixes** (`Downloads/resume_todo.md`) and send the resume to me for one review pass. Everything below needs it
- [ ] Update Naukri + LinkedIn (headline "Backend Engineer, Java / .NET", open to work, recruiters only). Refresh Naukri once a week
- [ ] Send 3–5 referral requests
- [ ] Apply to **5 practice-tier companies** (ones you'd accept but aren't your dream). Log any interview questions you get

---

## Week 2 backlog (do it whenever you have spare time; it isn't part of the plan above)

Files are in `phase1/week02/`. Same order as the Week 2 README:

| Was due | Item | Done |
|---|---|---|
| Tue | LLD `lld_notes.md` §1 (the 4-step method) + Library **Part A** (next step: `Loan` fields) | [ ] |
| Wed | #424 Longest Repeating Character Replacement ⭐, #567 Permutation in String | [ ] |
| Wed | **Redo #560 Subarray Sum Equals K cold** | [ ] |
| Wed | HLD `hld_notes.md` §2 Load balancers | [ ] |
| Thu | #209 Minimum Size Subarray Sum; `dsa_notes.md` §2 Stack, then #20 Valid Parentheses ⭐ | [ ] |
| Thu | LLD `lld_notes.md` §2 SOLID S, O, L + Library **Part B** (model code) | [ ] |
| Fri | #155 Min Stack, #150 Evaluate Reverse Polish Notation | [ ] |
| Fri | HLD `hld_notes.md` §3 Stateless services + the 1 → 1M users walkthrough | [ ] |
| Sat | `dsa_notes.md` §3 Monotonic Stack, then #739 ⭐, #496, #503, #901 | [ ] |
| Sat | LLD `lld_notes.md` §2 SOLID I, D + Library **Part C** (service + demo, must run) + review questions | [ ] |
| Sun | #84 Largest Rectangle in Histogram ⭐ (H) (+ #239 stretch) | [ ] |
| Sun | Week 1 `test.md` + Week 2 `test.md` (45m each) | [ ] |
| Wk 1 | Week 1 flashcards ("quiz me") | [ ] |

**Tip:** if a weekday runs out of time, the LLD backlog matters most. Week 3's Observer and Strategy are much easier once the Library is done, and LLD is the biggest risk for the midterm.
