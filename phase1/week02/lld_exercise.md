# LLD Exercise: Finish the Library Management System (Tue → Thu → Sat)

Same requirements as Week 1 (`phase1/week01/lld_exercise.md`), finished in 3 parts. Code goes in `D:\sysDes\phase1\week02\library\`. Java.

## Requirements (unchanged)
1. Add books. A book (ISBN, title, author) can have **multiple copies**, each with its own copy id.
2. Register members. Types: `STUDENT` (max 3 books at a time), `FACULTY` (max 10).
3. **Borrow** a copy: fails if the member is at their limit or no copy is available.
4. **Return** a copy. Late fee: ₹10/day after 14 days.
5. Search books by title or author (case-insensitive, partial match).
6. Show the books a member currently holds.

Required: custom exceptions `BookNotAvailableException` and `BorrowLimitExceededException`, and an injectable "today" (`Clock` or `LocalDate`).

`Main` demo: add 2 books (2 copies each) + 1 student + 1 faculty member → the student borrows 3 books, the 4th fails with a clear message → return one copy 20 days late → prints a fee of ₹60 → search "harry" finds the book.

---

## Where you stopped (Sun, Nov 15)

You finished Step 1 (classes) and most of Step 2 (fields):

```
Book:        isbn (String), title (String), author (String)
BookCopy:    id (int), book (Book), available (boolean)
Member:      id (int), name (String), type (MemberType), borrowed (List<Loan>)
MemberType:  STUDENT(3), FACULTY(10)   field: maxBooks (int)
Loan:        ?
```

---

## Part A (Tue): finish the design on paper. No code yet.

Read `lld_notes.md` §1 first. Then send me each step and I'll review it before you move on.

1. **`Loan` fields.** What does it need to answer: which copy? which member? what's the fee at return? is it still active?
2. **Step 3: methods and owners.** List the actions in the requirements (add book, register member, borrow, return, search, show held books). For each one: which class owns it, and what does it take in and return? Hint: borrow touches a member, a copy and a new loan, so does it belong in `Member`?
3. **Where are things stored?** Something has to hold *all* books, members and loans so you can look them up by id. What and where? (One `Map` per kind of object is fine.)
4. **Step 4: rules.** For each "fails if" in the requirements, write the check and the exception. Which rule is **likely to change** later, and so should be an interface?

## Part B (Thu): code the model. It must compile.

After reading `lld_notes.md` §2 S, O, L:
- `MemberType` enum (with `maxBooks`), `Book`, `BookCopy`, `Member`, `Loan`
- the two exceptions (extend `RuntimeException`)
- a tiny `Main` that creates one of each object and prints it. That's enough to prove it compiles

## Part C (Sat): service, fee, demo. It must run.

After reading `lld_notes.md` §2 I, D:
- the service class with borrow, return, search, and "books held by a member"
- the late-fee rule behind an interface
- "today" passed in from outside
- the full `Main` demo from the requirements, with the expected output

Then I'll review it the way an interviewer would.

## Review questions (Sat, after it runs). Write the answers in `library/review.md`
1. Tomorrow we add `DVD`s that can also be borrowed. What changes in your code?
2. Faculty should pay no late fee. How many existing lines do you change? (If the answer is "an `if` in the service", look at SOLID **O** again.)
3. Where would "reserve a book and get notified when a copy comes back" go? (Observer pattern, Week 3.)
4. Two members try to borrow the last copy at the same instant. What can go wrong? (Just think about it. Concurrency is Week 5.)
5. Which SOLID principle does each of these follow in your code: the `MemberType` enum, the fee interface, passing in the clock?
