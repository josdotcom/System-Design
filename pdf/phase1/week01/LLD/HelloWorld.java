import java.util.ArrayList;
import java.util.List;

// Interface: a contract. Anything that is a Greeter must be able to greet().
interface Greeter {
    String greet(String name);
}

// Two classes implementing the same interface in different ways.
class EnglishGreeter implements Greeter {
    @Override
    public String greet(String name) { return "Hello, " + name + "!"; }
}

class HindiGreeter implements Greeter {
    @Override
    public String greet(String name) { return "Namaste, " + name + "!"; }
}

public class HelloWorld {
    public static void main(String[] args) {
        // Declare with the interface type, create with the implementation.
        List<Greeter> greeters = new ArrayList<>();
        greeters.add(new EnglishGreeter());
        greeters.add(new HindiGreeter());

        // Same call, different behaviour at runtime (polymorphism).
        for (Greeter g : greeters) {
            System.out.println(g.greet("World"));
        }

        // The traps from lld_notes §1:
        String a = new String("hi"), b = new String("hi");
        System.out.println("a == b       : " + (a == b));        // false (different objects)
        System.out.println("a.equals(b)  : " + a.equals(b));     // true  (same content)

        Integer x = 127, y = 127, p = 128, q = 128;
        System.out.println("127 == 127   : " + (x == y));        // true  (cached -128..127)
        System.out.println("128 == 128   : " + (p == q));        // false (new objects)
        System.out.println("128 equals   : " + p.equals(q));     // true  (always use equals)
    }
}
