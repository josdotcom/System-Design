# LLD Exercise: Stock Price Alert System (Tue → Thu → Sat → Sun)

A small version of what Zerodha, Groww or TradingView do. Code goes in `D:\sysDes\phase1\week03\alerts\`. Java.

Use the 4-step method from **Week 2** `lld_notes.md` §1: nouns → fields → verbs and owners → rules. If you haven't read it yet (it's in the Week 2 backlog), read it before Part A, because Part A depends on it.

## Requirements
1. The system tracks **stocks**: a symbol (`"TCS"`) and a current price.
2. A price feed calls `updatePrice(symbol, newPrice)` whenever a price changes.
3. A **user** (id, name, email, phone) can create **alerts** on a stock. There are 3 kinds:
   - `ABOVE x`: fires when the price goes **≥ x**
   - `BELOW x`: fires when the price goes **≤ x**
   - `PERCENT_CHANGE y`: fires when the price has moved **≥ y%** (up or down) from the price at the moment the alert was created
4. When an alert fires, the user is notified through the **channel they chose for that alert**: `EMAIL`, `SMS` or `PUSH`. For now, "notify" means printing a line to the console.
5. An alert fires **once**, then becomes inactive.
6. A user can list their active alerts and delete one.

Required:
- custom exceptions `StockNotFoundException` and `AlertNotFoundException`
- the 5 patterns from this week's notes each appear **once, where they actually solve a problem**. Part B is where you decide where.

---

## Part A (Tue): classes and fields, on paper. No code yet.

1. **Nouns → classes.** Underline the nouns in the requirements. Which are classes, which are enums, and which are just fields?
2. **Fields.** For each class, list its fields with types. Watch for:
   - An alert needs to know *something* about the price at the moment it was created. Which kind of alert needs this?
   - Is "active" a field or something you work out?
   - Every object that's looked up by id needs an `id`.

Send me your list. I'll review it before Thursday.

## Part B (Thu): who owns each action, and where the patterns go. Still on paper.

1. **Verbs → methods.** For each action (create alert, update price, check alerts, notify, list alerts, delete alert), which class owns it, and what does it take and return?
2. **Patterns.** Answer each question in one line:
   - The **rule** for whether an alert fires differs per alert kind, and more kinds will come. Which pattern?
   - The **way** a user is notified differs per channel, and the right one has to be **created** from the channel the user picked. Which two patterns, together?
   - When a stock's price changes, the alerts on that stock must react, and the stock shouldn't know what an alert is. Which pattern? Who is the subject, and who is the observer?
   - An alert has required fields (user, stock, kind, threshold) and optional ones (a note, an expiry date). Which pattern?
   - Alert ids must be unique across the whole program. Which pattern? Would you **inject** it instead? Why or why not?
3. **Rules.** For each "fails if" (unknown symbol, deleting an alert that doesn't exist or belongs to someone else), write the check and the exception.

Send it to me. I'll review it like an interviewer, before you code.

## Part C (Sat): code it. It must compile and run.

Order (compile after each step): enums → model classes → exceptions → strategies → notifiers + factory → service → `Main`.

**`Main` demo.** It must print exactly these 3 notifications (the text after `]` can be worded your own way):

```
Setup:   stocks TCS = 4000.00, INFY = 1500.00
         Alice: TCS ABOVE 4100           via EMAIL
         Bob:   INFY BELOW 1450          via SMS
         Alice: TCS PERCENT_CHANGE 5%    via PUSH     (base price 4000.00)

Updates: TCS 4050 → TCS 4120 → TCS 4130 → INFY 1440 → TCS 4210

Expected output:
[EMAIL → alice@example.com] TCS at 4120.00: crossed above 4100.00
[SMS → +91-90000-00002] INFY at 1440.00: dropped below 1450.00
[PUSH → Alice] TCS at 4210.00: moved +5.25% since 4000.00
Alice's active alerts: none
Bob's active alerts: none
```

Check by hand **why** TCS 4130 prints nothing.

## Part D (Sun): two new requirements. **Don't edit** any existing class except the factory.

1. Add a `WHATSAPP` channel.
2. Add a `BETWEEN low high` alert kind: fires when the price enters the range `[low, high]`.

Then count the existing lines you changed. If your patterns are in the right places, it's 1 line in the factory plus the new enum value (and whatever parses the new alert kind), and everything else is new classes.

## Review questions (Sun, after it runs). Write the answers in `alerts/review.md`
1. TCS has 100,000 active `ABOVE` alerts. Every price update loops over all of them. How could you find only the alerts that fire, faster? (Hint: this week's DSA. What if the thresholds were kept sorted?)
2. The SMS provider is down for 2 minutes. What happens to alerts that fire during that time in your code? What would a real system do? (Week 6 preview: queues and retries.)
3. The price feed calls `updatePrice` from one thread while users create and delete alerts from other threads. Name one thing that can go wrong. (Just think about it. Concurrency is Week 5.)
4. If you made the ID generator a Singleton, how would you write a test that expects the first alert's id to be 1, when other tests already created alerts?
5. Point to the line in your code that would have to change if you had **not** used Strategy for the alert rules.
