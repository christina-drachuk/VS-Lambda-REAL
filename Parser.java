
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;


public class Parser {
	private ArrayList<String> tokens = new ArrayList<>();
	
	/*
	 * Turns a set of tokens into an expression.  Comment this back in when you're ready.
	 */

	public Expression parse(ArrayList<String> tokens) throws ParseException {
		this.tokens = preParser(tokens);
		Variable var = new Variable(tokens.get(0));
        Application app;
        int paren = 0;
        ArrayList<Integer> depthMap = new ArrayList<Integer>(tokens.size());
        int numTopLevels = 0;
        int level = 0;

        // set the depthMap and number of levels
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("(")) {
                depthMap.set(i, level);
                level += 1;
                if (level == 0) numTopLevels += 1;
            }

            else {
                depthMap.set(i, level);
            }
        }

        if (tokens.size() == 1) {
            return var;
        }

        // if there's only one top level, return only a Var
        else if (numTopLevels == 1 && tokens.get(0).equals("(") && tokens.get(tokens.size() - 1).equals(")")) {


            // if > size 1, parse the innards
            else {
                parse(new ArrayList<>(tokens.subList(1, tokens.size() - 2)));
            }
        } 

        for (int i = 0; i < tokens.size(); i++) {
            if (depthMap.get(i) == 0) {
                for (int j = i + 1; j < tokens.size(); j++) {
                    if (depthMap.get(j) == 0) {
                        parse(sub)
                    }
                }
            }
        }

        
		

		
		// This is nonsense code, just to show you how to thrown an Exception.
		// To throw it, type "error" at the console.
		if (var.toString().equals("error")) {
			throw new ParseException("User typed \"Error\" as the input!", 0);
		}

		
        System.out.println("completed");
		return app;
	}

	public static ArrayList<String> preParser(ArrayList<String> tokens) {
        ArrayList<String> newTokens = new ArrayList<>();
        String current = "";
        boolean isRight = false;

        for (int i = 0; i < tokens.size(); i++) {
            current = tokens.get(i);

            if (current.equals("\\")) {
                if (i > 0) {
                    if (tokens.get(i - 1).equals("(")) {
                        // Do nothing
                        newTokens.add("\\");
                    }

                    else {
                        newTokens.add("(");
                        newTokens.add("\\ ");
                        i++;
                        isRight = false;

                        while (isRight == false && i < tokens.size()) {
                            if (tokens.get(i).equals(")")) {
                                newTokens.add(")");
                                isRight = true;
                            }

                            else {
                                newTokens.add(tokens.get(i));
                                i++;
                            }
                            
                        }
                        newTokens.add(")");
                    }
                }
            }

            else {
                newTokens.add(current);
            }

            
        }

        


        return newTokens;
    }
}
