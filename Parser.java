
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
        int closeParenIndex = 0;
        int i = 2;

        if (tokens.size() > 1) {

            app = new Application(var, new Variable(tokens.get(1)));


            // If first token == "("
            if (tokens.get(0).equals("(")) {
                i = 0;
                    
                paren = 1;
                for (int j = 1; j < tokens.size(); j++) {
                    if (tokens.get(j).equals("(")) {
                        paren++;
                    }

                    else if (tokens.get(j).equals(")")) {
                        paren--; 
                        if (paren == 0) {

                            closeParenIndex = j;
                            while (tokens.get(i+1).equals("(") && tokens.get(j - 1).equals(")")) {
                                i++;
                                j--;
                            }

                            System.out.println(tokens.subList(i+1, j));

                            if (closeParenIndex == tokens.size() - 1) {
                                return parse(new ArrayList<String>(tokens.subList(i + 1, j)));
                            }

                            else {
                                app = new Application(parse(new ArrayList<String>(tokens.subList(i + 1, j))), parse(new ArrayList<String>(tokens.subList(closeParenIndex + 1, tokens.size()))));
                            }
                            
                        }
                    }

                    
                    
                }

                paren = 0;
                i = tokens.size();

            }

            // if index 1 == "("
            else if (tokens.get(1).equals("(")) {
                i = 1;
                    
                paren = 1;
                for (int j = 2; j < tokens.size(); j++) {
                    if (tokens.get(j).equals("(")) {
                        paren++;
                    }

                    else if (tokens.get(j).equals(")")) {
                        paren--; 
                        if (paren == 0) {

                            closeParenIndex = j;
                            while (tokens.get(i+1).equals("(") && tokens.get(j - 1).equals(")")) {
                                i++;
                                j--;
                            }

                            System.out.println(tokens.subList(i+1, j));
                            app = new Application(var, parse(new ArrayList<String>(tokens.subList(i + 1, j))));
                        }
                    }

                    
                    
                }

                paren = 0;
                i = closeParenIndex + 1;
                

            }

            else {

            }
            
            

            
            for (i = i; i < tokens.size(); i++) {

                if (tokens.get(i).equals("(")) {
                    
                    paren = 1;
                    for (int j = i + 1; j < tokens.size(); j++) {
                        if (tokens.get(j).equals("(")) {
                            paren++;
                        }
    
                        else if (tokens.get(j).equals(")")) {
                            paren--; 
                            if (paren == 0) {

                                closeParenIndex = j;


                                boolean cont = true;

                                for (int k = i + 1; k < tokens.size(); k++) {
                                    if (k != tokens.size() && tokens.get(k).equals(")")) {
                                        System.out.println("RAAAAAA");
                                        app = new Application(app, parse(new ArrayList<String>(tokens.subList(i + 1, j))));
                                        cont = false;
                                    }
                                }

                                if (cont) {
                                    while (tokens.get(i+1).equals("(") && tokens.get(j - 1).equals(")")) {
                                    i++;
                                    j--;
                                }


                                System.out.println(tokens.subList(i+1, j));
                                System.out.println(closeParenIndex);
                                app = new Application(app, parse(new ArrayList<String>(tokens.subList(i + 1, j))));
                                }

                                
                            }
                        }

                        
                        
                    }

                    paren = 0;
                    i = closeParenIndex + 1;
                    // System.out.println(i);
                    // ((a b) (c d))
                    // (((a)) (c d)) --> ((a)) (c d)

                }

                else {
                    app = new Application(app, new Variable(tokens.get(i)));
                }
                
            }
        }

        else {
            return var;
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
