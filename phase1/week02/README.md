# Week 2 (Nov 16–22): Sliding Window, Stack + Scaling + SOLID

**DSA:** Sliding Window (fixed and variable) · Stack · Monotonic Stack
**HLD:** Vertical vs horizontal scaling · Load balancers · Stateless services
**LLD:** How to approach an LLD problem · SOLID · **finish the Library Management System** (carried over from Week 1)

Files: `dsa_notes.md` · `dsa_problems.md` · `hld_notes.md` · `lld_notes.md` · `lld_exercise.md` · `test.md`

---

## What changed from Week 1 (based on how Week 1 went)

- **Week 1 was too full.** Carried-over items are built into the days below, so they don't pile up again. Weekdays have 2 problems (plus a redo on Mon/Wed), not 3.
- **No Sunday contest.** The 8 AM slot clashes with your other work.
- **LLD starts from the basics.** Week 1 showed the design step (turning requirements into classes) is new to you. `lld_notes.md` §1 teaches the 4-step method, and the Library is finished in small pieces over Tue, Thu and Sat.
- **Before-you-code checklist.** In Week 1 these steps only happened when I asked. Do them on your own this week, for every problem:

| # | Step | What it sounds like |
|---|---|---|
| 1 | Clarifying questions | "Sorted? Negatives? Duplicates? Empty input?" |
| 2 | Approach in 2–3 lines, **and why it works** | not just "it's Kadane" |
| 3 | Complexity **with the number** | O(n²), n = 3000 → 9×10⁶, fine |
| 4 | Overflow | largest possible value vs 2.1×10⁹ |
| 5 | Code, following your approach | if one input fails, find the cause, don't add an `if` for that input |
| 6 | Trace the smallest input | n = 1, n = 2, all equal |

---

### Mon, Nov 16 (2h)
- [ ] Read `dsa_notes.md` §1 Sliding Window (15m)
- [ ] DSA: #643 Maximum Average Subarray I, #121 Best Time to Buy and Sell Stock
- [ ] **Redo #75 Sort Colors cold** (20 min). Write the 3 cases down before coding. No XOR swap
- [ ] HLD warm-up: Week 1 flashcards, the 9 cards plus "divisible by k → compare remainders" (10m). Say "quiz me" and I'll ask them
- [ ] HLD: `hld_notes.md` §1 Vertical vs horizontal scaling

### Tue, Nov 17 (2h)
- [ ] DSA: #3 Longest Substring Without Repeating Characters ⭐, #1004 Max Consecutive Ones III
- [ ] LLD: `lld_notes.md` §1 How to approach an LLD problem
- [ ] LLD: `lld_exercise.md` Part A, finish the Library design on paper (`Loan`, then who owns borrow/return/search, then the failure rules). No code yet

### Wed, Nov 18 (2h)
- [ ] DSA: #424 Longest Repeating Character Replacement ⭐, #567 Permutation in String
- [ ] **Redo #560 Subarray Sum Equals K cold** (20 min)
- [ ] HLD: `hld_notes.md` §2 Load balancers

### Thu, Nov 19 (2h)
- [ ] DSA: #209 Minimum Size Subarray Sum
- [ ] Read `dsa_notes.md` §2 Stack (10m), then #20 Valid Parentheses ⭐
- [ ] LLD: `lld_notes.md` §2 SOLID, the **S, O, L** parts
- [ ] LLD: `lld_exercise.md` Part B, code the model classes, the enum and the 2 exceptions (they must compile)

### Fri, Nov 20 (2h)
- [ ] DSA: #155 Min Stack, #150 Evaluate Reverse Polish Notation
- [ ] HLD: `hld_notes.md` §3 Stateless services + the "1 → 1M users" walkthrough. Then draw the final diagram from memory

### Sat, Nov 21 (5h)
- [ ] Read `dsa_notes.md` §3 Monotonic Stack (15m)
- [ ] DSA block (2.5h): #739 Daily Temperatures ⭐, #496 Next Greater Element I, #503 Next Greater Element II, #901 Online Stock Span
- [ ] LLD: `lld_notes.md` §2 SOLID, the **I, D** parts
- [ ] LLD block (2.5h): `lld_exercise.md` Part C, the service, `FeeCalculator` and `Main` demo. **It must compile and run.** Then answer the review questions in `library/review.md`

### Sun, Nov 22 (5h)
- [ ] DSA: #84 Largest Rectangle in Histogram ⭐ (H)
- [ ] Stretch (only if you have energy): #239 Sliding Window Maximum (H)
- [ ] **Week 1 `test.md`**, closed book (45m). Self-grade
- [ ] **Week 2 `test.md`**, closed book (45m). Self-grade
- [ ] Fill the Weekly Log row in `Prep_Tracker.xlsx` + copy `templates/weekly_review.md` → `review.md` here
- [ ] Tell Claude: **"Week 2 done, review my tracker"**

### Job search this week (~1.5h, any day)
- [ ] **Finish the resume fixes** (`Downloads/resume_todo.md`), carried from Week 1. Do this first: everything below needs the resume
- [ ] Update Naukri + LinkedIn: headline "Backend Engineer, Java / .NET", open to work (recruiters only)
- [ ] List 15–20 referral contacts, then **send the first 3–5 requests** (short message: role, your 2-line pitch, resume link, job link)

---

**Redo queue this week:** #75 (Mon), #560 (Wed). Mark `Re-solved = Y` in the tracker when you solve them cleanly.
