# The SDE Interview Market: Jan → May 2027 (forecast)

> **This is a forecast.** It's based on hiring trends visible as of Nov 2026, from interview reports and industry write-ups. It is not official company policy. Always read recent interview experiences (LeetCode Discuss, Glassdoor, Taro, Blind) for the specific company **2–3 weeks before** your interview there.

## 1. What's changed (and what it means for you)

| Trend | Evidence | What we do about it |
|---|---|---|
| **DSA is still the gate.** Online tests and the first 1–2 rounds are still LeetCode-style. Mediums dominate, and speed matters. Pure puzzle rounds carry a little less weight at the onsite. | Commentary across 2026 reports: FAANG still uses LeetCode, but its weight is decreasing | ~50% of Phase 1 is DSA. Patterns over volume. Timed practice + weekly contests |
| **AI-assisted coding rounds exist now.** Meta replaced one onsite coding round with an AI-enabled one (late 2025, expanding through 2026). Startups adopt this faster than Big Tech. The skill being tested is **checking AI output**, not prompting. | Multiple 2026 write-ups on Meta's CoderPad + AI round | Phase 2 Week 20, plus a light drill every week from Week 5: get AI-written code, find its bugs, write the edge-case tests |
| **Debugging / code-review rounds.** "Here's a 200-line codebase, fix it / extend it." | Loops are described as 5–7 rounds incl. debugging and AI-tool fluency | Machine coding builds double as practice. Phase 2 has reading-unfamiliar-code drills |
| **Machine coding / LLD is *the* filter at Indian unicorns.** Flipkart, Swiggy and others run a 90-min "build a working system" round, often first. Many candidates who cleared DSA get rejected here. | Candidate reports for Flipkart SDE-2 (2024–2026); Swiggy prep guides | LLD in Java from Week 1, 5 timed builds by the midterm. **This is your edge from 0–2 yrs exp.** |
| **HLD at SDE-2, lighter at SDE-1.** SDE-1 loops may skip HLD or ask basics. SDE-2 expects a full 45-min design. | Standard loop structures | HLD fundamentals in Phase 1, full classic designs in Phase 2 |
| **AI system design questions.** Mostly at AI-first startups and product teams: "design a RAG chatbot over docs", "add LLM features without blowing up costs". | Growing in AI-engineer loops | Phase 2 Week 19 |
| **Junior hiring is tighter. Behavioral and projects matter more.** There are fewer entry-level slots and more candidates per slot, and cheating concerns are pushing companies toward judgment-based rounds. | 2026 market reports | Referrals from Week 6, a STAR story bank, and a real Java project (Phase 2) |

## 2. Typical loops you'll face

**Indian product unicorn (Flipkart/Swiggy/Zomato/Razorpay/CRED), SDE-1/2**
OA (2–3 DSA) → **Machine coding (90–120 min)** → DSA (2 mediums) → LLD/HLD discussion → Hiring manager.

**Big Tech (Amazon/Google/Microsoft/Meta), SDE-1/2**
OA → Phone screen (DSA) → Onsite: 2–3 DSA, 1 design (LLD at SDE-1, HLD at SDE-2), behavioral (Amazon puts LPs in *every* round). Meta may include the AI-assisted round.

**AI-first startup**
Take-home or live build (AI tools often *allowed*) → system design (often with an LLM component) → founder/culture chat.

## 3. Your positioning

- **.NET + Angular background:** you've built real backend APIs, so lead with that in HLD answers ("in my current system we…"). Moving to Java won't cost you much: C# and Java are close cousins (see `phase1/week01/lld_notes.md`).
- **Why Java for LLD:** interviewers at Indian product companies expect it, and Spring Boot opens up most backend roles.
- **Why C++ for DSA:** it's fast and the STL is strong. This is fine everywhere. Don't switch.

## Sources (Nov 2026)
- [We analyzed 2,500 tech interviews in 2026 (DEV)](https://dev.to/prachub/we-analyzed-2500-tech-interviews-in-2026-here-is-exactly-what-faang-is-asking-now-4a7l)
- [AI-Assisted Coding Interviews (2026) guide](https://pawankjha.substack.com/p/ai-assisted-coding-interviews-a-complete)
- [Meta now lets you use AI in coding interviews (DEV)](https://dev.to/klement_gunndu/meta-now-lets-you-use-ai-in-coding-interviews-most-candidates-use-it-wrong-5156)
- [Think the technical interview is dead? Think again (LeadDev)](https://zephrcf.leaddev.com/hiring/think-the-technical-interview-is-dead-think-again)
- [Coding interviews in 2026 are harder than ever](https://brianjenney.substack.com/p/coding-interviews-in-2026-are-harder)
- [Flipkart interview process 2026 (TechPrep)](https://www.techprep.app/blog/flipkart-interview-process)
- [Swiggy interview process 2026 (TechPrep)](https://www.techprep.app/blog/swiggy-interview-process)
- [Flipkart SDE-2 experience (LeetCode Discuss)](https://leetcode.com/discuss/interview-experience/5470072/)
