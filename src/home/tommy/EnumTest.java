package home.tommy;

enum Level {
	LOW, MEDIUM, HIGH
}

public class EnumTest {

	public static void main(String[] args) throws Exception {
		Level myLvl = Level.LOW;
		switch (myLvl) {
		case LOW:
			System.out.println("1" + myLvl);
			break;
		case MEDIUM:
			System.out.println("2" + myLvl);
			break;
		case HIGH:
			System.out.println("3" + myLvl);
			break;
		}
	}

}
