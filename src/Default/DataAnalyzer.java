package Default;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This program performs Def-Use Analysis for a Java source code file. It can
 * identify a def-use for multiple variables on the same line. This program uses
 * String Operation, Java Collections to analyze, store and perform the analysis
 * data.
 * 
 * @author Chaitanya Hapase
 *
 */
public class DataAnalyzer {

	public static List<String> variableList = new ArrayList<String>();
	public static Map<String, Set<Integer>> variableDefMap = new HashMap<String, Set<Integer>>();
	public static Map<String, Set<Integer>> variableUseMap = new HashMap<String, Set<Integer>>();

	public static void main(String[] args) {

		FileInputStream fstream;
		try {
			fstream = new FileInputStream("src/testers/DefUseExample.java");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			List<String> variableDefsOnEachLine = new ArrayList<String>();
			int lineCounter = 1;
			String strLine;

			// Reading every single line of the source code for analysis.
			while ((strLine = br.readLine()) != null) {

				// Calculates the variable definitions
				variableDefsOnEachLine = calculateVariableDef(variableDefsOnEachLine, strLine);

				// For displaying the Defs of variable and populating the variableDefMap which
				// stores the variable and their def data
				for (String eachVariable : variableDefsOnEachLine) {
					if (!eachVariable.trim().isEmpty()) {
						populateVariableDefMap(eachVariable.trim(), lineCounter);
					}

				}

				// For displaying the Uses of variable and populating the variableUseMap
				calculateVariableUseAndPopulateUseMap(strLine, lineCounter);

				lineCounter++;

				variableDefsOnEachLine.clear();

			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		displayDefUseTable();
	}

	/**
	 * Function for displaying the def-use analysis in a sophisticated format.
	 */
	private static void displayDefUseTable() {

		for (String eachVariable : variableList) {

			System.out.println("Variable : " + eachVariable);

			if (variableDefMap.containsKey(eachVariable)) {
				System.out.println("Def : " + sortOutputSet(variableDefMap.get(eachVariable)));
			}

			if (variableUseMap.containsKey(eachVariable)) {
				System.out.println("Use : " + sortOutputSet(variableUseMap.get(eachVariable)));
			}
			System.out.println();
		}
	}

	/**
	 * Function for sorting the output set of line numbers prior to displaying.
	 * 
	 * @param set
	 * @return List<Integer>
	 */
	private static List<Integer> sortOutputSet(Set<Integer> set) {

		List<Integer> sortedList = new ArrayList<Integer>(set);
		Collections.sort(sortedList);
		return sortedList;
	}

	/**
	 * Function calculates the Variable usage. It can handle multiple variables at
	 * the same statement and can identify each variable for it's def or use
	 * behavior.
	 * 
	 * @param strLine
	 * @param lineCounter
	 */
	private static void calculateVariableUseAndPopulateUseMap(String strLine, int lineCounter) {

		String[] tempArray = null;
		Set<Integer> setOfLineNumbers = new HashSet<Integer>();
		String variable = null;

		// checking for declaration statements
		if (!(strLine.contains("boolean ") || strLine.contains("byte ") || strLine.contains("short ")
				|| strLine.contains("int ") || strLine.contains("long ") || strLine.contains("char ")
				|| strLine.contains("float ") || strLine.contains("double ") || strLine.contains("String "))) {

			// checking for multiple variables def-use scenario
			if (strLine.contains("=")) {
				tempArray = strLine.split("=");
				variable = tempArray[1].replaceAll(";", "");

				// Populating the variable def map if encountered.
				populateVariableDefMap(tempArray[0].trim(), lineCounter);

			} else {
				// Every variable should be declared and defined prior to its use. If there is
				// no record of the variable's definition, it won't be added to the def-use
				// analysis
				if (!variableList.isEmpty()) {
					for (String everyStr : variableList) {
						if (strLine.contains(everyStr)) {
							variable = everyStr;
						}
					}
				}

			}

			if (variable != null) {
				variable = variable.replace(";", "");
			}

			if (!variableDefMap.isEmpty()) {
				if (variable != null) {

					String[] tempStringArray = variable.split(" ");

					// Iterating for every variable found in the multiple variable scenario and
					// analyzing it for the def-use
					for (String eachTempString : tempStringArray) {

						eachTempString = eachTempString.trim();

						if (variableDefMap.containsKey(eachTempString)) {
							if (variableUseMap.containsKey(eachTempString)) {
								setOfLineNumbers = variableUseMap.get(eachTempString);
								setOfLineNumbers.add(lineCounter);
								variableUseMap.put(eachTempString, setOfLineNumbers);
							} else {
								setOfLineNumbers.add(lineCounter);
								variableUseMap.put(eachTempString, setOfLineNumbers);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Function which runs initially on every line of code which checks for
	 * declaration statements. Further, the function populates the variable def map
	 * based on the analysis.
	 * 
	 * @param variable
	 * @param lineCounter
	 */
	private static void populateVariableDefMap(String variable, int lineCounter) {

		Set<Integer> setOfLineNumbers = new HashSet<Integer>();

		if (variable.contains("=")) {
			variable = variable.split("=")[0].trim();
		}

		if (variableDefMap.containsKey(variable)) {
			setOfLineNumbers = variableDefMap.get(variable);
			setOfLineNumbers.add(lineCounter);
			variableDefMap.put(variable, setOfLineNumbers);
		} else {
			setOfLineNumbers.add(lineCounter);
			variableDefMap.put(variable, setOfLineNumbers);
		}

		if (!variableList.contains(variable)) {
			variableList.add(variable);
		}

	}

	/**
	 * Function checks every line for primitive and non-primitive data types in Java
	 * and forwards them to the extraction method accordingly.
	 * 
	 * @param variablesOnEachLine
	 * @param strLine
	 * @return
	 */
	private static List<String> calculateVariableDef(List<String> variablesOnEachLine, String strLine) {

		if (strLine.contains("boolean ")) {
			variablesOnEachLine = extractVariableDef("boolean", strLine);

		} else if (strLine.contains("byte ")) {
			variablesOnEachLine = extractVariableDef("byte", strLine);

		} else if (strLine.contains("short ")) {
			variablesOnEachLine = extractVariableDef("short", strLine);

		} else if (strLine.contains("int ")) {
			variablesOnEachLine = extractVariableDef("int", strLine);

		} else if (strLine.contains("long ")) {
			variablesOnEachLine = extractVariableDef("long", strLine);

		} else if (strLine.contains("char ")) {
			variablesOnEachLine = extractVariableDef("char", strLine);

		} else if (strLine.contains("float ")) {
			variablesOnEachLine = extractVariableDef("float", strLine);

		} else if (strLine.contains("double ")) {
			variablesOnEachLine = extractVariableDef("double", strLine);

		} else if (strLine.contains("String ")) {
			variablesOnEachLine = extractVariableDef("String", strLine);

		}

		return variablesOnEachLine;
	}

	/**
	 * Function extracts the variable name from the line of code using String
	 * manipulation functions.
	 * 
	 * @param string
	 * @param strLine
	 * @return
	 */
	private static List<String> extractVariableDef(String string, String strLine) {
		List<String> tempListOfVariables = new ArrayList<String>();

		String[] tempList = strLine.split(string);

		tempList[1] = tempList[1].replaceAll(";", " ");

		if (tempList[1].trim().contains(",")) {
			String[] variables = (tempList[1].trim().split(","));

			for (String eachVariable : variables) {

				tempListOfVariables.add(eachVariable);
			}
		} else {
			tempListOfVariables.add(tempList[1].trim());
		}

		return tempListOfVariables;
	}

}
