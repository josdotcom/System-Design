# System Design + DSA Interview Prep: a 26-Week Course

A structured self-study course that takes a software engineer with **0–2 years of experience** to interview-ready for **SDE-1 / SDE-2** roles at Big Tech, product companies and startups.

It covers all four areas of a typical loop:

| Area | What's covered | Language |
|---|---|---|
| **DSA** | patterns in a building order: arrays and hashing → sliding window → binary search → trees → graphs → DP | C++ |
| **HLD** (high-level design) | scaling, load balancers, caching, databases, queues, then full designs (URL shortener, chat, news feed, …) | — |
| **LLD** (low-level design / machine coding) | OOP, SOLID, design patterns, then timed builds (Parking Lot, Splitwise, BookMyShow, LRU cache, …) | Java |
| **Behavioral + AI-era topics** | STAR stories, AI system design (RAG, LLM gateway), AI-assisted coding rounds | — |

## Schedule at a glance

| | Weeks | Goal |
|---|---|---|
| **Phase 1** | 1–8 | Interview-ready: DSA mediums, machine coding, basic HLD |
| 🎯 **Midterm** | 9 | 2 DSA papers, a 90-min machine coding round, an HLD round, behavioral |
| **Phase 2** | 10–25 | FAANG level: advanced DSA, classic HLD designs, distributed systems, AI system design, a Java project |
| 🏁 **Final** | 26 | A full mock loop |

Time budget: **~20 hours/week** (2 h on weekdays, 5 h on Saturday and Sunday). The full week-by-week plan is in [`00_SYLLABUS.md`](00_SYLLABUS.md).

## What's in this repo

```
README.md          ← this file
00_SYLLABUS.md     ← the 26-week plan, exam formats and pass bars
01_MARKET.md       ← what the SDE interview market looks like and why the syllabus is shaped this way
phase1/weekNN/     ← one folder per week
pdf/               ← a PDF of every .md file, same folder layout
```

Each week folder has the same files:

| File | What it is |
|---|---|
| `README.md` | the daily checklist for the week: what to read and solve each day |
| `dsa_notes.md` | the week's patterns, with templates, the reasoning behind them, and common traps |
| `dsa_problems.md` | the LeetCode problem list with difficulty, pattern hints and the day to do each one |
| `hld_notes.md` | system design concepts explained from first principles, each ending in a short "interview answer" |
| `lld_notes.md` | OOP / SOLID / design patterns, each with code that breaks the rule and the fix |
| `lld_exercise.md` | a small design to build in Java, in parts, ending with working code |
| `test.md` | a 45-min closed-book test (20 marks) with an answer key hidden at the bottom |

New weeks are added one at a time, so the material can be adjusted to the weak spots from the week before.

**Reading:** on GitHub the `.md` files render directly, diagrams included. For offline reading, use the PDFs in `pdf/`.

## How to use it

1. Follow `phase1/weekNN/README.md` day by day.
2. Track every problem: solved alone, needed a hint, or read the solution.
3. On Sunday, take that week's `test.md` closed book and self-grade it.
4. Review the week: what was slow, what kept going wrong. Let that decide what the next week focuses on.

Fallen behind? Don't skip ahead and don't cut topics. Keep the unfinished items as a backlog and clear it alongside the next week.

## The rules

1. **Time-box every problem.** Easy 15 min, Medium 25 min, Hard 40 min. If you're still stuck at the limit, read the solution, then **re-solve it from scratch** two days later.
2. **Talk before you code.** Clarifying questions (edge cases and output), the approach and *why* it works, and the complexity with an actual number (n = 10⁵ → n² = 10¹⁰, too slow). In an interview, this is about half the score.
3. **LLD means working code.** Notes alone don't count. Every LLD exercise ends with a Java program that compiles and runs.
4. **HLD means a drawn diagram.** For every design, draw the boxes and arrows and write down the numbers you estimated.
5. **Spaced revision.** Every week, re-solve problems you previously needed the solution for.
6. **Protect the weekly review.** The test plus an honest review is what makes the next week better.

## References

The notes are original, written in plain English. They follow the order and topics of standard material: the NeetCode / Striver DSA roadmaps, *System Design Interview* vol. 1 & 2 (Alex Xu), *Designing Data-Intensive Applications* (Kleppmann), *Head First Design Patterns*, and refactoring.guru.
