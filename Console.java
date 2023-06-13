
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





public class Console {
	private static Scanner in;
	public static Hashtable<String, Expression> vars = new Hashtable<String, Expression>();
	public static String latestPrint;

	
	public static void main(String[] args) throws ParseException {
		String keyDic = null;
		Expression expDic = null;

		in = new Scanner (System.in);
		
		Lexer lexer = new Lexer();
		Parser parser = new Parser();
		
		String input = cleanConsoleInput();  // see comment
		
		while (! input.equalsIgnoreCase("exit")) {
			latestPrint = "";
			ArrayList<String> tokens = lexer.tokenize(input);

			if (tokens.size() == 0) {
				input = cleanConsoleInput();
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
					System.out.println("Added " + expDic + " as " + keyDic);
				}

				

				input = cleanConsoleInput();
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

						latestPrint = setVar.getKey();
						System.out.println(setVar.getKey());

						replaced = true; 

						break;
					}
				}
				

				latestPrint = output.toString();
				if (!replaced) System.out.println(output);
				// Runner.run(parser.parse(new ArrayList<>(tokens.subList(1, tokens.size()))));

				input = cleanConsoleInput();
			}
			
			else {
				



				String output = "";
				
				try {

					if (tokens.size() == 0) {
						break;
					}

					Expression exp = parser.parse(tokens);
					output = exp.toString();
				} catch (Exception e) {
					System.out.println("Unparsable expression, input was: \"" + input + "\"");
					input = cleanConsoleInput();
					continue;
				}
				
				latestPrint = output;
				System.out.println(output);
				
				input = cleanConsoleInput();
			}
		}
		System.out.println("Goodbye!");
	}

	

	
	
	/*
	 * Collects user input, and ...
	 * ... does a bit of raw string processing to (1) strip away comments,  
	 * (2) remove the BOM character that appears in unicode strings in Windows,
	 * (3) turn all weird whitespace characters into spaces,
	 * and (4) replace all λs with backslashes.
	 */
	

	// Getter for dic
	public static Hashtable<String, Expression> getVars() {
		return vars;
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
