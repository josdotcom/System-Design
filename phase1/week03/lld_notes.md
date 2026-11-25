# Week 3 LLD Notes: Design Patterns I

A design pattern is a **named, well-known answer to a problem that keeps coming up** in object-oriented code. In an LLD round you aren't scored for using many patterns. You're scored for:
1. noticing the **problem** (the smell),
2. picking the pattern that fixes it, and
3. saying **why** in one sentence.

A pattern used where there's no problem counts *against* you (over-engineering).

Each section: **the smell → the pattern → Java code → when NOT to use it → the interview line.** The examples use different domains on purpose. Your exercise (stock alerts) is yours to design.

---

## 1. Strategy

**Smell:** an `if/else` or `switch` that picks *how* to do something, and new cases keep getting added.

```java
// ✗ Every new ride type = edit this method (breaks SOLID O)
double fare(String type, double km) {
    if (type.equals("NORMAL")) return 50 + 12 * km;
    else if (type.equals("SURGE")) return (50 + 12 * km) * 1.8;
    else if (type.equals("POOL")) return 30 + 8 * km;
    throw new IllegalArgumentException(type);
}
```

**Pattern:** put each way of doing it behind one interface. The caller holds *some* strategy and doesn't care which.

```java
interface FareStrategy {
    double fare(double km);
}
class NormalFare implements FareStrategy { public double fare(double km) { return 50 + 12 * km; } }
class SurgeFare  implements FareStrategy {
    private final double multiplier;
    SurgeFare(double multiplier) { this.multiplier = multiplier; }
    public double fare(double km) { return (50 + 12 * km) * multiplier; }
}
class PoolFare   implements FareStrategy { public double fare(double km) { return 30 + 8 * km; } }

class Ride {
    private final double km;
    private final FareStrategy fareStrategy;              // injected (SOLID D)
    Ride(double km, FareStrategy fareStrategy) { this.km = km; this.fareStrategy = fareStrategy; }
    double totalFare() { return fareStrategy.fare(km); }  // Ride never asks "which type am I?"
}
// new Ride(10, new SurgeFare(1.8)).totalFare()
```

New ride type = **one new class**. `Ride` and the existing strategies don't change.

**When not to:** 2 cases that will never grow. A plain `if` is fine.
**Strategy vs an enum with a field:** `MemberType.STUDENT(3)` holds a different *number* per type. When the *behaviour* differs (a different formula, a different check), use Strategy.

> "The fare rule changes often, so it's a Strategy interface: one class per rule, injected into Ride. Adding a rule means adding a class, not editing a working one. That's open/closed."

---

## 2. Factory

**Smell:** `new ConcreteClass()` choices scattered around your code. Every place that creates the object has its own `if (type == …) new X() else new Y()`.

**Pattern:** one place decides **which class to create**. Callers ask the factory and only see the interface.

```java
interface Exporter { String export(Report r); }
class PdfExporter  implements Exporter { public String export(Report r) { return "PDF..."; } }
class CsvExporter  implements Exporter { public String export(Report r) { return "a,b,c..."; } }
class JsonExporter implements Exporter { public String export(Report r) { return "{...}"; } }

enum Format { PDF, CSV, JSON }

class ExporterFactory {
    static Exporter create(Format format) {
        return switch (format) {               // the ONLY switch on Format in the whole codebase
            case PDF  -> new PdfExporter();
            case CSV  -> new CsvExporter();
            case JSON -> new JsonExporter();
        };
    }
}
// Exporter e = ExporterFactory.create(user.preferredFormat());
```

"Isn't that `switch` the same smell as in Strategy?" Almost: it's now in **one** place, and it only *creates* objects. Adding a format = one new class + one line here. Nothing else in the codebase changes.

**Often used together:** a Factory *creates* the right Strategy.
**When not to:** only one implementation exists and no other is planned.

> "Creation is in one factory, so the rest of the code depends only on the Exporter interface. Adding a format touches the factory and one new class."

---

## 3. Observer

**Smell:** when something happens in object A, several other objects need to react, and A shouldn't have to know who they are.

```java
// ✗ Channel knows every kind of reaction. A new reaction = edit Channel.
void upload(Video v) {
    videos.add(v);
    emailService.sendNewVideoEmails(v);
    recommendationEngine.reindex(v);
    analytics.track(v);
}
```

**Pattern:** the **subject** keeps a list of **observers** behind an interface and notifies all of them. Observers subscribe and unsubscribe themselves.

```java
interface VideoListener {                      // the observer
    void onNewVideo(Channel channel, Video video);
}

class Channel {                                // the subject
    private final String name;
    private final List<VideoListener> listeners = new ArrayList<>();
    Channel(String name) { this.name = name; }

    void subscribe(VideoListener l)   { listeners.add(l); }
    void unsubscribe(VideoListener l) { listeners.remove(l); }

    void upload(Video v) {
        // ... store the video ...
        for (VideoListener l : listeners) l.onNewVideo(this, v);   // doesn't know who they are
    }
}

class EmailNotifier implements VideoListener {
    public void onNewVideo(Channel c, Video v) { System.out.println("Email: new video " + v.title()); }
}
// channel.subscribe(new EmailNotifier()); channel.subscribe(analytics);
```

**Things interviewers ask about:**
- **One slow or failing observer** blocks the others, because they run one after another in the same thread. In a real system the subject puts an event on a **queue** (Week 6, Kafka) and the observers consume it.
- **Unsubscribing while notifying** can throw `ConcurrentModificationException`. Loop over a copy, or use `CopyOnWriteArrayList`.
- Push vs pull: pass the data in the call (push, as above) or pass only the subject and let the observer read what it needs (pull).

> "The subject only knows the listener interface. New reactions subscribe themselves, so the subject never changes. At scale I'd replace the in-memory list with a message queue so one slow consumer can't block the rest."

---

## 4. Singleton

**What it is:** a class that allows **exactly one instance**, with one global way to get it.

```java
// Simplest thread-safe version: an enum
enum IdGenerator {
    INSTANCE;
    private final AtomicLong next = new AtomicLong(1);
    long nextId() { return next.getAndIncrement(); }
}
// long id = IdGenerator.INSTANCE.nextId();
```

```java
// Lazy + thread-safe without locks: the "holder" idiom
class AppConfig {
    private AppConfig() { /* load settings */ }                 // nobody else can call new
    private static class Holder { static final AppConfig INSTANCE = new AppConfig(); }
    static AppConfig getInstance() { return Holder.INSTANCE; }  // the JVM creates it on first use, exactly once
}
```

**The trap you'll be asked about:**
```java
// ✗ Not thread-safe: two threads both see null and both create one
static AppConfig getInstance() {
    if (instance == null) instance = new AppConfig();
    return instance;
}
```
The textbook fix is "double-checked locking" with a `volatile` field. Mention it, but write the enum or holder version: they're shorter and harder to get wrong.

**Why interviewers are suspicious of Singletons:** it's a **global variable** in disguise. Classes that call `X.getInstance()` inside them hide that dependency, and tests can't swap it for a fake. That breaks SOLID **D**.
**Better in most designs:** create *one* instance in `Main` and **inject** it wherever it's needed. You still have one instance, but the dependency is visible. (Spring beans are singletons in exactly this way.)

> "I'd only make something a true Singleton when two instances would be a bug, like an ID generator. Otherwise I create one instance and inject it, so classes stay testable."

---

## 5. Builder

**Smell:** a constructor with many parameters, several of them optional.

```java
// ✗ Which boolean is which? What do the nulls mean?
new Pizza("LARGE", "THIN", true, false, null, List.of("olives"), true, null);
```

**Pattern:** a separate Builder collects the values step by step with named methods, checks them, then creates an **immutable** object.

```java
class Pizza {
    private final String size;          // required
    private final String crust;         // optional, has a default
    private final boolean extraCheese;  // optional
    private final List<String> toppings;

    private Pizza(Builder b) {          // only the builder can call this
        this.size = b.size; this.crust = b.crust;
        this.extraCheese = b.extraCheese; this.toppings = List.copyOf(b.toppings);
    }

    static class Builder {
        private final String size;
        private String crust = "REGULAR";
        private boolean extraCheese = false;
        private final List<String> toppings = new ArrayList<>();

        Builder(String size) { this.size = size; }                        // required parts in the constructor
        Builder crust(String c)        { this.crust = c; return this; }   // return this → calls can be chained
        Builder extraCheese()          { this.extraCheese = true; return this; }
        Builder topping(String t)      { this.toppings.add(t); return this; }
        Pizza build() {
            if (toppings.size() > 5) throw new IllegalStateException("max 5 toppings");   // check once, here
            return new Pizza(this);
        }
    }
}
// Pizza p = new Pizza.Builder("LARGE").crust("THIN").extraCheese().topping("olives").build();
```

**When not to:** 2–3 required fields with no optional ones. A normal constructor is clearer.
In real Java projects, Lombok's `@Builder` writes this for you. In an interview, write it by hand or say "I'd use Lombok's `@Builder`".

> "Many optional fields, so a Builder: readable call sites, validation in one place in build(), and the result is immutable, so it's safe to share between threads."

---

## Cheat sheet

| Pattern | Smell that calls for it | One-line reason | SOLID link |
|---|---|---|---|
| Strategy | `if/switch` on *how* to do something | swap behaviour without editing the caller | O, D |
| Factory | `new X()` choices repeated in many places | creation decided in one place | D (callers see the interface only) |
| Observer | "when A happens, B, C, D must react" | A doesn't know who's listening | O |
| Singleton | two instances would be a bug | exactly one, globally reachable | can break D, so prefer injecting one instance |
| Builder | long constructor, many optional parameters | readable, validated, immutable | (readability, not SOLID) |

**In machine-coding rounds you'll use these again and again:** Strategy (pricing, fees, matching rules), Factory (vehicles, payment methods, notification channels), Observer (notifications, status changes). Builder and Singleton come up less often, and usually as a question ("is this thread-safe?").
