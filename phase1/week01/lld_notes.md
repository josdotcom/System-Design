# Week 1 LLD Notes: C# → Java Bridge + OOP

You already know C#. Java is ~80% the same, and the other 20% is where interview bugs come from.

---

## 1. Setup
- **JDK 21 or 25** (both LTS). Check with `java -version`.
- **IntelliJ IDEA Community**: New Project → Java → no build system is fine for now (Maven comes in Phase 2 with Spring Boot).
- Run a single file without a project: `java Main.java` (works since JDK 11).
- Machine coding rounds usually let you use your own IDE. Get fast with IntelliJ shortcuts: `Alt+Enter` (quick fix), `Ctrl+Alt+L` (format), `Alt+Insert` (generate constructor/getters), `Shift+F10` (run).

## 2. C# → Java: the differences that bite

| C# | Java | ⚠️ Trap |
|---|---|---|
| `namespace X` | `package x;` (lowercase, must match the folder) | one **public** class per file, and the file name must match it |
| `string` | `String` | **`==` compares references!** Always use `a.equals(b)` |
| Properties `{ get; set; }` | private field + `getX()`/`setX()`, or a **`record`** for immutable data | IntelliJ `Alt+Insert` generates them |
| `List<T>` / `Dictionary<K,V>` / `HashSet<T>` | `List<T> l = new ArrayList<>()` / `Map<K,V> m = new HashMap<>()` / `Set<T> s = new HashSet<>()` | declare with the **interface** type, create with the implementation |
| `int` in generics just works | generics need wrapper types: `List<Integer>` | `Integer a = 1000, b = 1000; a == b` is **false**, but with `127` it's **true**: autoboxing calls `Integer.valueOf()`, which returns a *cached* object for -128..127 and a new object outside that range. Never use `==` on wrappers. Use `.equals` |
| `virtual` / `override` | every non-final method is virtual. Mark overrides with `@Override` | `final` method = C# non-virtual / `sealed` |
| `sealed class` | `final class` (or `sealed ... permits` in Java 17+) | |
| `const` / `readonly` | `static final` / `final` | |
| LINQ | Streams: `list.stream().filter(...).map(...).toList()` | `.toList()` is Java 16+. Older code uses `collect(Collectors.toList())` |
| `Func<T,R>` / `Action<T>` / delegates | `Function<T,R>`, `Consumer<T>`, `Supplier<T>`, `Predicate<T>`, or your own `@FunctionalInterface` | lambdas look the same: `x -> x * 2` |
| `async/await`, `Task` | `CompletableFuture`, `ExecutorService`, **virtual threads** (Java 21) | concurrency comes in Week 5 |
| `lock (obj)` | `synchronized (obj)` or `ReentrantLock` | |
| `T?` nullable | `Optional<T>` for return values | don't use Optional for fields or parameters |
| exceptions are unchecked | **checked exceptions** must be declared (`throws IOException`) or caught | prefer custom unchecked ones: `extends RuntimeException` |
| `enum` = named ints | `enum` = full classes (fields, constructors, methods) | very useful in LLD (e.g. `VehicleType` with a rate) |
| `struct` | none. Use classes/records | |
| `var` | `var` (local variables only) | |

```java
// Records: Java's answer to a C# record / DTO. Immutable; equals/hashCode/toString are free.
public record Book(String isbn, String title, String author) {}

// Enum with behavior
public enum MemberType {
    STUDENT(3), FACULTY(10);
    private final int maxBooks;
    MemberType(int maxBooks) { this.maxBooks = maxBooks; }
    public int maxBooks() { return maxBooks; }
}
```

## 3. The 4 OOP pillars (and how interviewers actually test them)

| Pillar | What it means | How it's tested in LLD |
|---|---|---|
| **Encapsulation** | Hide state; expose behavior. Fields are `private`; change state only through methods that enforce the rules | "Why is `balance` public?" → it should be `withdraw(amount)`, which validates |
| **Abstraction** | Callers depend on *what*, not *how* | Interfaces like `PaymentProcessor`, `NotificationSender` |
| **Inheritance** | "is-a" reuse | Use it sparingly. Interviewers like to see that you know when **not** to use it |
| **Polymorphism** | One interface, many implementations, picked at runtime | Replacing `if (type == CAR) ... else if (type == BIKE)` with `vehicle.getFee()` |

```java
// Polymorphism instead of if-else chains: the #1 thing machine-coding graders look for
interface FeeStrategy { double fee(long hours); }
class CarFee  implements FeeStrategy { public double fee(long h) { return 20 * h; } }
class BikeFee implements FeeStrategy { public double fee(long h) { return 10 * h; } }
// Adding a Truck = a new class. Existing code doesn't change (Open/Closed, coming in Week 2).
```

## 4. Interface vs abstract class, and composition over inheritance

| | Interface | Abstract class |
|---|---|---|
| State (fields) | no (constants only) | yes |
| Multiple | a class can implement many | only one parent |
| Use for | a **capability/contract** ("can be paid", "can notify") | **shared base code** among closely related types |

Default rule: **start with an interface**. Use an abstract class only when you have real shared state or code.

**Composition over inheritance.** `class Car extends Engine` is wrong: a car *has* an engine.
```java
class Car {
    private final Engine engine;              // has-a
    Car(Engine engine) { this.engine = engine; } // injected, so it's easy to swap or test
    void start() { engine.start(); }
}
```
Inheritance locks the relationship at compile time and exposes parent internals. Composition can be swapped at runtime (this becomes the Strategy pattern in Week 3).

## 5. How machine coding is graded (keep this in mind from day 1)
1. **It runs** and handles the demo flows (this alone filters ~50% of candidates).
2. **Separation:** models / services / repositories (in-memory) / a driver `Main`.
3. **Extensibility:** "now add X". You should only need to *add* classes, not edit 10 if-else chains.
4. **Validation & exceptions:** custom exceptions, no silent failures.
5. **Naming and readability.**
6. **Patterns used where they fit**, and *not* forced in.
