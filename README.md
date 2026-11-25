# Interview Prep: DSA + System Design (Nov 2026 → May 2027)

Your personal course. Built for: **0–2 yrs exp → SDE-1 / SDE-2**, targeting **FAANG/Big Tech, Indian product unicorns, AI-first startups**.

| Thing | Choice |
|---|---|
| DSA language | **C++** |
| LLD / machine coding language | **Java** (you're moving over from .NET, and Java is what Indian product companies expect in LLD rounds) |
| Time budget | **~20 hrs/week**: Mon–Fri 2 hrs, Sat–Sun 5 hrs |
| Midterm | **Week 9: Jan 4–10, 2027**. Start interviewing after it. |
| Final | **Week 26: May 3–9, 2027**. This is the hard deadline. |

## How the folder works

```
README.md            ← you are here (rules + how we work together)
00_SYLLABUS.md       ← full 26-week plan (Phase 1 + Phase 2)
01_MARKET.md         ← what the SDE market looks like and why the syllabus is shaped this way
Prep_Tracker.xlsx    ← ★ your tracker: daily checklist, problem status, weekly log, dashboard
TRACKER.md           ← what the tracker tracks (reference only; use the Excel file)
pdf\                 ← a PDF for every .md file (same folder layout) + pdf\books\ (combined)
templates/           ← C++ DSA template, HLD interview framework, weekly review form
phase1/week01/       ← this week's material (notes, problems, daily checklist, test)
phase1/week02/ ...   ← generated one week at a time, based on how your last week went
```

## PDFs (for reading)

Every `.md` file has a PDF at the same path under `pdf\` (e.g. `pdf\00_SYLLABUS.pdf`, `pdf\phase1\week01\dsa_notes.pdf`). `pdf\books\` has combined versions: `00_Course_Overview.pdf` and one per week (`Phase1_Week01.pdf`). They have rendered diagrams, highlighted code, and a TOC for the longer files. The test answer key is on its own last page so you don't see it by accident.
Rebuild after any change (or after I add a new week):

```powershell
powershell -ExecutionPolicy Bypass -File D:\sysDes\tools\build_pdf.ps1          # all
powershell -ExecutionPolicy Bypass -File D:\sysDes\tools\build_pdf.ps1 week02   # just one week
```

**Read** the PDFs. **Track** in `Prep_Tracker.xlsx`. The `.md` files are the source the PDFs are built from.

## The interactive loop (how we design it together)

The full syllabus is fixed at the **topic level**. The **weekly material** is written one week ahead, so it can adapt to you:

1. Do the week using `phase1/weekNN/README.md` (the daily checklist).
2. Mark the daily Checklist and problem Status in `Prep_Tracker.xlsx`. On Sunday, take `test.md`, then fill the Weekly Log row and the weekly review (`templates/weekly_review.md`).
3. Open Claude Code in this folder and say: **"Week N done, review my tracker"**.
4. I'll read your tracker and review, then write next week's folder: more practice on your weak spots, less on what you already handle well.

If you fall behind, **don't skip ahead**. Tell me and I'll compress the plan. The midterm can slip by up to 2 weeks without breaking Phase 2.

## The 6 rules

1. **Time-box every problem.** Easy 15 min, Medium 25 min, Hard 40 min. If you're stuck at the limit, read the solution, then **re-solve it from scratch the next day** and set its Status to **Solution** in the tracker.
2. **Say it out loud.** Before you code, say the approach and its complexity out loud, like an interviewer is listening. In the room, talking through your thinking is about half the score.
3. **LLD means working code.** Notes alone don't count. Every LLD exercise ends with a Java program that compiles and runs.
4. **HLD means a drawn diagram.** For every design, draw boxes and arrows (paper or excalidraw.com) and write down the numbers you estimated.
5. **Spaced revision.** Every Sunday, re-solve 3 problems with Status = Solution (filter the DSA Problems sheet).
6. **Sundays are sacred.** Do the contest (or a mock) + the test + the tracker. The feedback loop only works if you do this.
