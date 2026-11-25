# LLD Exercise 1: Library Management System (Saturday, 2.5h)

Not timed yet. This one is about clean OOP in Java. Timed 90-minute rounds start in Week 5.

## Requirements
1. Add books. A book (ISBN, title, author) can have **multiple copies**, each with its own copy id.
2. Register members. Types: `STUDENT` (max 3 books at a time), `FACULTY` (max 10).
3. **Borrow** a copy: fails if the member is at their limit or no copy is available.
4. **Return** a copy. Late fee: ₹10/day after 14 days.
5. Search books by title or author (case-insensitive, partial match).
6. Show the books a member currently holds.

## Constraints
- Everything in memory (Maps/Lists). No DB.
- A `Main` class that runs this demo and prints results:
  - add 2 books (2 copies each), add 1 student and 1 faculty member
  - student borrows 3 books → the 4th borrow fails with a clear message
  - return one copy 20 days late → prints a fee of ₹60
  - search "harry" → finds the book
- Use **custom exceptions** (`BookNotAvailableException`, `BorrowLimitExceededException`).
- Make "today" injectable (pass a `Clock` or a `LocalDate`) so you can test late returns. This small detail impresses graders.

## Suggested structure (try your own first, then compare)
<details><summary>Show structure</summary>

```
library/
  model/     Book (record), BookCopy, Member, MemberType (enum with maxBooks), Loan
  repo/      BookRepository, MemberRepository, LoanRepository   (in-memory Maps)
  service/   LibraryService (borrow/return/search), FeeCalculator (interface) + DailyLateFeeCalculator
  exception/ BookNotAvailableException, BorrowLimitExceededException
  Main.java
```
Why is `FeeCalculator` an interface? Because the follow-up question is always "faculty pays no fee" or "weekends are free". With the interface, that's a new class, not an edit.
</details>

## After you finish, answer these (write the answers in `review.md`)
1. If tomorrow we add `DVD` items that can also be borrowed, what changes?
2. Where would a "reserve a book when it becomes available" feature go? (Hint: the Observer pattern, coming in Week 3.)
3. What happens if two members try to borrow the last copy at the same instant? (Just think about it. Concurrency is Week 5.)
