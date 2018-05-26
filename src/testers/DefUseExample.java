package testers;

public class DefUseExample {

	public static void main(String[] args) {
		
		int num1 = 10;
		int num2 = 20;
		int exchangeNum;
		int sum;
		
		exchangeNum = num1;
		num1 = num2;
		num2 = exchangeNum;
		
		sum = num1 + num2;
		
		System.out.println(num1);
		System.out.println(num2);
		System.out.println(exchangeNum);
		System.out.println(sum);
		
	}
	
}
