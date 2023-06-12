

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ToolTipManager;
import javax.xml.crypto.dsig.keyinfo.KeyValue;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class Test {
	public static String variableOutput = "";
	public static int totalPoints = 0;
	private static Hashtable<Integer, Integer> pointVals = new Hashtable<Integer, Integer>();


	// Point Values

	

    public static void main(String[] args) throws ParseException {
		pointVals.put(1, 1);
		pointVals.put(2, 5);

		
        
        
		totalPoints += pointCalcRun(1, testConsole(";eeijeijeje"), "");
		totalPoints += pointCalcRun(2, testConsole("Correct-Answer! ; Comment is here!"), "Correct-Answer!");

		System.out.println("Your total points are: " + totalPoints);
		
		System.out.println(" S t r i p ".strip());

    }
	
    
    public static Expression testConsole(String input) throws ParseException {
        Lexer lexer = new Lexer();
		Parser parser = new Parser();

        String keyDic = null;
		Expression expDic = null;

        Hashtable<String, Expression> vars = Console.getVars();
		


        ArrayList<String> tokens = lexer.tokenize(input);

			if (tokens.size() == 0) {
				// input = cleanConsoleInput();
			}

			else if (tokens.size() > 1 && tokens.get(1).equals("=")) {
				keyDic = tokens.get(0);

				if (vars.get(keyDic) != null) {
					System.out.println(keyDic + " is already defined. ");
				}

				else {
					if (tokens.get(2).equalsIgnoreCase("run")) {
						expDic = Runner.run(parser.parse(new ArrayList<>(tokens.subList(3, tokens.size()))));
					}

					else {
						expDic = parser.parse(new ArrayList<>(tokens.subList(2, tokens.size())));
					}
					
					vars.put(keyDic, expDic);

					System.out.println("Added " + expDic + " as " + keyDic);
					return new Variable(keyDic);
				}

				

				// input = cleanConsoleInput();
			}

			else if (tokens.size() > 1 && tokens.get(0).equalsIgnoreCase("run")) {


				// Make a parsed expression *parser.parse()

				// make the run method take two expressions, feed it the same thing twice

				

				Expression output = Runner.run(parser.parse(new ArrayList<>(tokens.subList(1, tokens.size()))));
				



				boolean replaced = false;
				for (Entry<String, Expression> setVar : vars.entrySet()) {
					System.out.print(setVar.getKey() + " : ");
					System.out.println(setVar.getValue());

					if (setVar.getValue().toString().equals(output.toString())) {

						System.out.println(setVar.getKey());

						replaced = true; 

						break;
					}
				}
				

				if (!replaced) return output;
				// Runner.run(parser.parse(new ArrayList<>(tokens.subList(1, tokens.size()))));

				// input = cleanConsoleInput();
			}
			
			else {
				



				String output = "";
                Expression exp = null;
				
				try {

					if (tokens.size() == 0) {
						System.out.println("tokens.size() is 0");;
					}

					exp = parser.parse(tokens);
					output = exp.toString();
				} catch (Exception e) {
					System.out.println("Unparsable expression, input was: \"" + input + "\"");
					// input = cleanConsoleInput();
				}
				
				return exp;
				
				// input = cleanConsoleInput();
			}

            return null;

    }

	public static int pointCalcRun(int testNum, Expression exp, String expectedResult) {

		
		if (exp != null && exp.toString().equals(expectedResult)) {
			if (pointVals.containsKey(testNum)) {
				System.out.println("Correct: " + exp + " --> " + pointVals.get(testNum) + " point(s)!\n");
				return pointVals.get(testNum);
			}
		}

		else if (exp == null && expectedResult.equals("")) {
			System.out.println("Correct: --> 1 point!\n");
			return pointVals.get(testNum);
		}

		System.out.println("Incorrect: " + exp + " --> should be: " + expectedResult);
		return 0;
	}

	// public static int pointCalcVar(int testNum, Expression exp, String expectedResult) {
	// 	if (Parser.parse(exp).toString().endsWith(expectedResult)) {
	// 		if (pointVals.containsKey(testNum)) {
	// 			return pointVals.get(testNum);
	// 		}
	// 	}

	// 	return 0;
	// }
}
