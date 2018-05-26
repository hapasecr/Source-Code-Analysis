package Default;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class computes the cyclomatic complexity of a java source code based on
 * a number of various statements that occur in java source code.
 * 
 * @author Chaitanya Hapase
 *
 */
public class CCMain {

	public static void main(String[] args) {

		int ccCount = 1;
		boolean flag = false;
		List<String> returnStmts = new ArrayList<String>(Arrays.asList("return"));
		List<String> selectionStmts = new ArrayList<String>(
				Arrays.asList("if (", "else if (", "case", "default:"));
		List<String> loopStmts = new ArrayList<String>(
				Arrays.asList("for (", "while (", "do {", "break;", "continue;"));
		List<String> operatorStmts = new ArrayList<String>(Arrays.asList("&&", "||", "?", ":"));
		List<String> exceptionStmts = new ArrayList<String>(
				Arrays.asList("catch (", "finally {", "throw", "throw new"));

		try {

			FileInputStream fstream = new FileInputStream("src/testers/CCSourceCode.java");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;

			while ((strLine = br.readLine()) != null) {
				if (stringContainsItemFromlist(strLine, returnStmts)) {
					flag = true;
				} else if (stringContainsItemFromlist(strLine, selectionStmts)) {
					flag = true;
				} else if (stringContainsItemFromlist(strLine, loopStmts)) {
					flag = true;
				} else if (stringContainsItemFromlist(strLine, operatorStmts)) {
					flag = true;
				} else if (stringContainsItemFromlist(strLine, exceptionStmts)) {
					flag = true;
				}

				if (flag) {
					ccCount++;
				}

				flag = false;
			}
			System.out.println("Cyclomatic Complexity : " + ccCount);

			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static boolean stringContainsItemFromlist(String strLine, List<String> returnStmts) {

		for (String eachString : returnStmts) {
			if (strLine.contains(eachString)) {
				return true;
			}
		}
		return false;
	}

}
