import java.util.ArrayList;
import java.util.List;

interface Animal {
	String Sound(String name);
}

class Dog implements Animal{
	@Override
	public String Sound(String name) {
		return name + " " + "barks woof woof";
	}
}

class Cat implements Animal{
	@Override
	public String Sound(String name) {
		return name + " " + "Meows";
	}
}

public class AnimalSounds {
	public static void main(String args[]) {
		List<Animal> animals = new ArrayList<>();
		animals.add(new Dog());
		animals.add(new Cat());
		
		System.out.println(animals.get(0).Sound("Bruno"));
		System.out.println(animals.get(1).Sound("Puss"));
	}
}