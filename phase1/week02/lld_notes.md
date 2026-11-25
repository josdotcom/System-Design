# Week 2 LLD Notes: How to Approach an LLD + SOLID

---

## 1. How to approach an LLD problem (the 4-step method)

In an LLD round you get a few lines of requirements ("design a library / parking lot / Splitwise") and must turn them into **classes**: what each class *knows* (fields) and what it *does* (methods). The interviewer checks three things:
- each class has **one clear job**
- data lives in the **right place**
- a **new requirement** can be added without rewriting everything

Running example (a different domain from your exercise): **food delivery**. *"A customer places an order with items from one restaurant. An order can be cancelled only before it's picked up. Delivery fee: ₹30, free above ₹500."*

### Step 1: nouns → candidate classes
Underline the *things*: customer, order, item, restaurant → `Customer`, `Order`, `OrderItem`, `Restaurant`.
- Watch for **"one thing vs many copies"**: a menu `Item` (Paneer Tikka, ₹250) is not the same as an `OrderItem` (2 × Paneer Tikka in *this* order). Same as `Book` vs `BookCopy`.
- Watch for **events that need a record**: when something *happens* and you need to remember when and to whom (an order is placed, a book is borrowed), that event becomes a class (`Order`, `Loan`).

### Step 2: fields (what does each thing know?)
```
Order:  id, customer (Customer), restaurant (Restaurant), items (List<OrderItem>), status (OrderStatus), createdAt (LocalDateTime)
```
- Every object that gets **looked up** needs an `id`.
- A fixed set of values → **enum** (`OrderStatus { PLACED, PICKED_UP, DELIVERED, CANCELLED }`).
- Data that depends only on a type → put it **in the enum** (like `STUDENT(3)`).
- **Only model what the requirements need.** No `age`, no `email`, unless a requirement uses them.
- Link to other objects (`Customer customer`), don't copy their fields (`String customerName`).

### Step 3: verbs → methods, and who owns each one
Underline the *actions*: places, cancels, calculates the fee.

| Action touches… | Put it in |
|---|---|
| only one object's own data (`order.getTotal()`) | that class |
| several objects (placing an order: check restaurant, create the order, save it) | a **service** class: `OrderService.placeOrder(customerId, restaurantId, items)` |

Rule of thumb: **model classes hold data plus small rules about themselves; services coordinate.**

### Step 4: rules and failures
- Every "**fails if** …" → a check + a **custom exception** (`OrderNotCancellableException`).
- Every rule that's **likely to change** (fees, discounts, limits) → an **interface** with one implementation now (`DeliveryFeeCalculator`). Then "free delivery on weekends" is a new class, not an edit.

### Then code, in this order
enums → model classes → exceptions → repositories (in-memory `Map`s) → service → `Main` demo. Compile after each group, don't write everything and compile once at the end.

---

## 2. SOLID

Five rules for code that's easy to change. For each one: **what it says → code that breaks it → the fix → how to say it in an interview.**

### S: Single Responsibility
**A class should have one reason to change.**

```java
// ✗ Three reasons to change: invoice math, PDF layout, email provider
class Invoice {
    double total() { ... }
    void printPdf() { ... }
    void email(String to) { ... }
}
```
```java
// ✓ One job each
class Invoice        { double total() { ... } }
class InvoicePrinter { void printPdf(Invoice inv) { ... } }
class InvoiceMailer  { void email(Invoice inv, String to) { ... } }
```
> "If the email provider changes, I shouldn't have to touch the class that calculates totals."

### O: Open/Closed
**Open for extension, closed for modification.** Add new behaviour by adding a class, not by editing a working one.

```java
// ✗ Every new customer type = edit this method (and risk breaking the old ones)
double discount(String type, double amount) {
    if (type.equals("REGULAR")) return 0;
    else if (type.equals("GOLD")) return amount * 0.10;
    else if (type.equals("PLATINUM")) return amount * 0.20;
    return 0;
}
```
```java
// ✓ New type = new class. Nothing existing changes.
interface DiscountPolicy { double discount(double amount); }
class NoDiscount       implements DiscountPolicy { public double discount(double a) { return 0; } }
class GoldDiscount     implements DiscountPolicy { public double discount(double a) { return a * 0.10; } }
class PlatinumDiscount implements DiscountPolicy { public double discount(double a) { return a * 0.20; } }
```
> "A chain of `if (type == …)` is the smell. I replace it with an interface, which is the Strategy pattern (Week 3)."

### L: Liskov Substitution
**A subclass must work anywhere its parent works**, without surprising the caller.

```java
// ✗ Code that expects a Bird calls fly(). A Penguin breaks it.
class Bird    { void fly() { ... } }
class Penguin extends Bird { void fly() { throw new UnsupportedOperationException(); } }
```
```java
// ✓ Only birds that can fly promise to fly
class Bird { void eat() { ... } }
interface Flyer { void fly(); }
class Sparrow extends Bird implements Flyer { public void fly() { ... } }
class Penguin extends Bird { }
```
> "If a subclass has to throw 'not supported' for a parent method, the hierarchy is wrong."

### I: Interface Segregation
**Don't force a class to implement methods it doesn't need.** Many small interfaces beat one big one.

```java
// ✗ A basic printer must pretend it can scan and fax
interface Machine { void print(Doc d); void scan(Doc d); void fax(Doc d); }
```
```java
// ✓
interface Printer { void print(Doc d); }
interface Scanner { void scan(Doc d); }
class BasicPrinter implements Printer { ... }
class OfficeMachine implements Printer, Scanner { ... }
```
> "If I see empty or throwing method bodies, the interface is too big. I split it."

### D: Dependency Inversion
**High-level code depends on an interface, not on a concrete class.** The concrete class is passed in from outside (usually through the constructor), which is called **dependency injection**.

```java
// ✗ OrderService is welded to MySQL. You can't test it without a database.
class OrderService {
    private MySqlOrderRepository repo = new MySqlOrderRepository();
}
```
```java
// ✓ OrderService only knows the interface. Main (or Spring, later) decides which one.
interface OrderRepository { void save(Order o); Optional<Order> findById(int id); }
class InMemoryOrderRepository implements OrderRepository { ... }

class OrderService {
    private final OrderRepository repo;
    OrderService(OrderRepository repo) { this.repo = repo; }   // injected
}
// Main:  new OrderService(new InMemoryOrderRepository());
```
> "Constructor injection lets me swap the in-memory repo for a real DB, or a fake in tests, without touching the service. That's how Spring works."

**Same idea for time:** don't call `LocalDate.now()` inside your service. Pass in a `Clock` (or the date), so a test can pretend it's 20 days later. Your Library exercise needs this.

### SOLID cheat sheet

| Letter | Smell that breaks it | Fix |
|---|---|---|
| S | a class with "and" in its description | split it |
| O | `if/else` or `switch` on a type | interface + one class per type |
| L | subclass throws "not supported" | fix the hierarchy (smaller interface) |
| I | empty or throwing method bodies | split the interface |
| D | `new ConcreteThing()` inside a service | depend on an interface, inject it |
