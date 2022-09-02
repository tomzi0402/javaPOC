package home.tommy;

public class SuperClassTest {
	public static void main(String[] str) throws Exception {
		Parent c = new Child();
		c.print();
	}
}

//filename Main.java
class Grandparent {
	protected String a = "a";

	public void print() {
		System.out.println("Grandparent's Print()");
	}
}

class Parent extends Grandparent {
	protected String a = "b";

	public void print() {
		System.out.println("Parent's Print()");
	}

}

class Child extends Parent {
	@Override
	public void print() {
		System.out.println("Child's Print()");
	}
}