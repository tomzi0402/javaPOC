package home.tommy;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class LambdaTest {

	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		try {
			final int number = 1_000_000;

			List<Integer> numbers = Arrays.asList(new Integer[] { 1, 2, 3, number });
			numbers.forEach((n) -> {
				System.out.println(n);
			});

			System.out.println("===================");

			numbers.stream().map((n) -> n * 2).sorted((x, y) -> y.compareTo(x)).forEach(System.out::println);

			System.out.println("===================");

			Consumer<Integer> method = (n) -> System.out.println(++n);
			numbers.forEach(method);

			System.out.println("===================");

			StrFn fn1 = (s) -> {
				System.out.println("Good Morning: " + s);
			};
			StrFn fn2 = (s) -> {
				System.out.println("Good Night: " + s);
			};

			fn1.print("Tommy");
			fn2.print("Theo");

			List<Person> people = Arrays.asList(new Person("Tommy", 42), new Person("Theo", 3));
//			List<Person> people = new ArrayList<>();
			int computedAges = people.stream().reduce(0, (partialAgeResult, user) -> {
				try {
					return partialAgeResult + user.getAge();
				} catch (Exception e) {
					e.printStackTrace();
					return 111;
				}
			}, Integer::sum);
			fn1.print(computedAges + "");

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

//	@Test
//	public void assertTest() {
//		
//	}

}

class Person {
	String name;
	int age;

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}

@FunctionalInterface
interface StrFn {
	void print(String str);

}
