package testers;

public class CallGraphs {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		doStuff();

	}

	private static void doStuff() {

		int additionOutput = new Operations().add(3, 4);
		System.out.println("Addition Output is : " + additionOutput);
		int subtractionOutput = new Operations().sub(4, 3);
		System.out.println("Subtraction output is : " + subtractionOutput);
		int multiplicationOutput = new Operations().mul(3, 4);
		System.out.println("Multiplication output is : " + multiplicationOutput);
	}
}

class Operations {
	public static int mul(int i, int j) {
		// TODO Auto-generated method stub
		return i * j;
	}

	public static int sub(int i, int j) {
		// TODO Auto-generated method stub
		return i - j;
	}

	public static int add(int i, int j) {
		// TODO Auto-generated method stub
		return i + j;
	}

}
