package home.tommy;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class LambdaTest {

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

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

}

@FunctionalInterface
interface StrFn {
	void print(String str);

}
