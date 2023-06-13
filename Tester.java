import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Tester {
    public static String latestPrint = "";
    public static int totalPoints = 0;
	public static int testNum = 1;
	private static Scanner in;


    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        BufferedReader reader;

        reader = new BufferedReader(new FileReader("TestScript.txt"));
        String line = reader.readLine(); 

		in = new Scanner (System.in);
       

        while (line != null) {

            String input = "";
            String correctAnswer = "";

			String result = "WRONG";

            String[] temp1;
            String[] temp2;
         
            int points = 0;
			

            temp1 = line.split(">");
            input = temp1[0].strip();
            temp2 = temp1[1].split("///");
            correctAnswer = temp2[0].strip();
            points = Integer.parseInt(temp2[1]);

			

            System.out.println(input);
			result = testConsole(input);
            System.out.println("> " + result);
            



            if (correctAnswer.strip().equals(result)) {
                if (points > 0) System.out.println(testNum	+ ": CORRECT: + " + points + " points!");
				else System.out.println(testNum + ": CORRECT!");
                totalPoints += points;
            }

            else {
                System.out.println(testNum + ": INCORRECT, the correct answer is " + correctAnswer);
            }


			// try {
			// Thread.sleep(1000);
			// } 
			
			// catch (InterruptedException e) {
			// Thread.currentThread().interrupt();
			// }

            line = reader.readLine();
			testNum++;

			input = cleanConsoleInput();
			if (input.equalsIgnoreCase("exit")) {
				break;
			}

			else if (input.equals("")) {

			}

			else {
				while (!input.equalsIgnoreCase("exit") && !input.equals("")) {
					result = testConsole(input);
					System.out.println("> " + result);
					input = cleanConsoleInput();
				}

				if (input.equalsIgnoreCase("exit")) {
					break;
				}

				else if (input.equals("")) {

				}
				
			}
        }

        System.out.println("POINTS: " + totalPoints);



        reader.close();
    }

	

    public static String testConsole(String input) throws ParseException {
        String keyDic = null;
		Expression expDic = null;
		
		Lexer lexer = new Lexer();
		Parser parser = new Parser();

        Hashtable<String, Expression> vars = Console.getVars();
		
		
		if (!input.equalsIgnoreCase("exit")) {
			latestPrint = "";
			ArrayList<String> tokens = lexer.tokenize(input);

			if (tokens.size() == 0) {
				
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

					latestPrint = "Added " + expDic + " as " + keyDic;
					// System.out.println("Added " + expDic + " as " + keyDic);
				}

				

			}

			else if (tokens.size() > 1 && tokens.get(0).equalsIgnoreCase("run")) {


				// Make a parsed expression *parser.parse()

				// make the run method take two expressions, feed it the same thing twice

				

				Expression output = Runner.run(parser.parse(new ArrayList<>(tokens.subList(1, tokens.size()))));
				



				boolean replaced = false;
				for (Entry<String, Expression> setVar : vars.entrySet()) {
					// System.out.print(setVar.getKey() + " : ");
					// System.out.println(setVar.getValue());

					if (setVar.getValue().toString().equals(output.toString())) {

						latestPrint = setVar.getKey();
						// System.out.println(setVar.getKey());

						replaced = true; 

						break;
					}
				}
				

				
				if (!replaced) latestPrint = output.toString();;
				// Runner.run(parser.parse(new ArrayList<>(tokens.subList(1, tokens.size()))));

			}

			else if (tokens.size() > 1 && tokens.get(0).equalsIgnoreCase("populate")) {
				int leftBound = Integer.parseInt(tokens.get(1));
				int rightBound = leftBound;
				if (tokens.size() > 2) rightBound = Integer.parseInt(tokens.get(2));

				if (rightBound < leftBound) {
					int temp = rightBound;
					rightBound = leftBound;
					leftBound = temp;
				}


				Expression succ = parser.parse(lexer.tokenize("(λn.(λf.(λx.(f ((n f) x)))))"));
				Expression current = parser.parse(lexer.tokenize("\\f.\\x.x"));

				for (int i = 1; i < leftBound; i++) {
					current = Runner.run(parser.parse(lexer.tokenize(succ.toString() + " " + current.toString())));

				}

				for (int i = leftBound; i < rightBound + 1; i++) {

					if (i != 0) {
						current = Runner.run(parser.parse(lexer.tokenize(succ.toString() + " " + current.toString())));
					}


					if (vars.containsKey(Integer.toString(i))) {
						System.out.println(Integer.toString(i) + " is already defined. ");
					}

					else {
						vars.put(Integer.toString(i), current);
						System.out.println("Added " + current + " as " + Integer.toString(i));
					}
					
				}

				if (leftBound == rightBound) {
					System.out.println("Populated " + leftBound + ".");
				}

				else {
					// System.out.println("Populated numbers "  + leftBound +  " to " + rightBound);
					latestPrint = "Populated numbers "  + leftBound +  " to " + rightBound;
				}

				
			}
			
			else {
				



				String output = "";
				
				try {

					if (tokens.size() == 0) {
					}

                    else {
                        Expression exp = parser.parse(tokens);
					    output = exp.toString();
                    }

					
				} catch (Exception e) {
					System.out.println("Unparsable expression, input was: \"" + input + "\"");
				}
				
				latestPrint = output;
				// System.out.println(output);
				
			}
		}

        return latestPrint;
		// System.out.println("Goodbye!");

    }

	private static String cleanConsoleInput() {
		System.out.print("> ");
		String raw = in.nextLine();
		String deBOMified = raw.replaceAll("\uFEFF", ""); // remove Byte Order Marker from UTF

		String clean = removeWeirdWhitespace(deBOMified);
		
		return clean.replaceAll("λ", "\\\\");
	}
	
	
	public static String removeWeirdWhitespace(String input) {
		String whitespace_chars = 
				""           // dummy empty string for homogeneity
				+ "\\u0009"  // CHARACTER TABULATION
				+ "\\u000A"  // LINE FEED (LF)
				+ "\\u000B"  // LINE TABULATION
				+ "\\u000C"  // FORM FEED (FF)
				+ "\\u000D"  // CARRIAGE RETURN (CR)
				+ "\\u0020"  // SPACE
				+ "\\u0085"  // NEXT LINE (NEL) 
				+ "\\u00A0"  // NO-BREAK SPACE
				+ "\\u1680"  // OGHAM SPACE MARK
				+ "\\u180E"  // MONGOLIAN VOWEL SEPARATOR
				+ "\\u2000"  // EN QUAD 
				+ "\\u2001"  // EM QUAD 
				+ "\\u2002"  // EN SPACE
				+ "\\u2003"  // EM SPACE
				+ "\\u2004"  // THREE-PER-EM SPACE
				+ "\\u2005"  // FOUR-PER-EM SPACE
				+ "\\u2006"  // SIX-PER-EM SPACE
				+ "\\u2007"  // FIGURE SPACE
				+ "\\u2008"  // PUNCTUATION SPACE
				+ "\\u2009"  // THIN SPACE
				+ "\\u200A"  // HAIR SPACE
				+ "\\u2028"  // LINE SEPARATOR
				+ "\\u2029"  // PARAGRAPH SEPARATOR
				+ "\\u202F"  // NARROW NO-BREAK SPACE
				+ "\\u205F"  // MEDIUM MATHEMATICAL SPACE
				+ "\\u3000"; // IDEOGRAPHIC SPACE 
		Pattern whitespace = Pattern.compile(whitespace_chars);
		Matcher matcher = whitespace.matcher(input);
		String result = input;
		if (matcher.find()) {
			result = matcher.replaceAll(" ");
		}

		return result;
	}
}
