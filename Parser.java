
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;


public class Parser {
	private ArrayList<String> tokens = new ArrayList<>();
	
	/*
	 * Turns a set of tokens into an expression.  Comment this back in when you're ready.
	 */

	public Expression parse(ArrayList<String> tokens) throws ParseException {
		tokens = preParser(tokens);

        System.out.println("---------------------");
        System.out.println(tokens);
        System.out.println("---------------------");

		Variable var = new Variable(tokens.get(0));
        Application app = new Application(null, null);
        int paren = 0;
        int[] depthMap = new int[tokens.size()];
        int numTopLevels = 0;
        int level = 0;
        int count = 0;
        boolean end = false;
        Expression exp = null;

        Variable varF = null;
        Expression expF = null;


        System.out.println("RAWRRRRRRR"); 
        System.out.println(tokens);


        // set the depthMap and number of levels
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("(")) {
                depthMap[i] = level;
                if (level == 0) numTopLevels += 1;
                level += 1;
                
            }

            else if (tokens.get(i).equals(")")) {
                depthMap[i] = level;
                level -= 1;
            }

            else {
                depthMap[i] = level;
                if (level == 0) numTopLevels += 1;

            }


        }

        for (int i = 0; i < depthMap.length; i++) {
            System.out.print(depthMap[i] + " ");
        }
        System.out.println(" level = " + numTopLevels);


        if (tokens.size() == 1) {

            System.out.println(Console.getVars());
            exp = Console.getVars().get(tokens.get(0));
            System.out.println(exp == null);

            System.out.println(Console.getVars().keys());

            if (!(exp == null)) {
                return Console.getVars().get(tokens.get(0));
            }
            return var;
        }

        // if there's only one top level, return only a Var
        else if (numTopLevels == 1 && tokens.get(0).equals("(") && tokens.get(tokens.size() - 1).equals(")")) {


            
            System.out.println("HERE");
            System.out.println(tokens.subList(1, tokens.size()-1));

            System.out.println(tokens.get(1));
            System.out.println(tokens.get(1).equals("\\"));

            if (tokens.get(1).equals("\\")) {
                System.out.println("functionizer");
                varF = new Variable(tokens.get(2));
                expF = parse(new ArrayList<>(tokens.subList(4, tokens.size() - 1)));
                
                return new Function(varF, expF);
            } 

            return parse(new ArrayList<>(tokens.subList(1, tokens.size() - 1)));
            

        } 

        



        else {
            System.out.println(tokens);



            for (int i = 0; i < tokens.size(); i++) {

                

                if (depthMap[i] == 0) {
                    for (int j = i + 1; j < tokens.size(); j++) {

                        
                        
                        if (depthMap[j] == 0) {
                            System.out.print(tokens.subList(i, j));
                            System.out.print(" + ");
                            System.out.print(tokens.subList(j , tokens.size()));

                            if (count == 0) {
                                exp = parse(new ArrayList<>(tokens.subList(i, j)));
                                System.out.print("EXP: ");
                                System.out.println(exp);
                                count++;
                            }

                            else if (count == 1) {
                                app = new Application(exp, parse(new ArrayList<>(tokens.subList(i, j))));
                                System.out.print("APP: ");
                                System.out.println(app);
                                count++;
                            }

                            else {
                                app = new Application(app, parse(new ArrayList<>(tokens.subList(i, j))));
                                System.out.print("APP: ");
                                System.out.println(app);
                            }

                            i = j;

                            

                            
                        }

                        else if (tokens.get(j).equals(")") && j == tokens.size() - 1) {
                            if (count == 1) {
                                app = new Application(exp, parse(new ArrayList<>(tokens.subList(i, tokens.size()))));
                                System.out.print("APP: ");
                                System.out.println(app);
                                count++;
                            }
        
                            else {
                                app = new Application(app, parse(new ArrayList<>(tokens.subList(i, tokens.size()))));
                                System.out.print("APP: ");
                                System.out.println(app);
                            }

                            end = true;
                        }
                    }

                    if (!end) {
                        if (count == 0) {
                            exp = parse(new ArrayList<>(tokens.subList(i, tokens.size())));
                            System.out.print("EXP: ");
                            System.out.println(exp);
                            count++;
                        }
    
                        else if (count == 1) {
                            app = new Application(exp, parse(new ArrayList<>(tokens.subList(i, tokens.size()))));
                            System.out.print("APP: ");
                            System.out.println(app);
                            count++;
                        }
    
                        else {
                            app = new Application(app, parse(new ArrayList<>(tokens.subList(i, tokens.size()))));
                            System.out.print("APP: ");
                            System.out.println(app);
                        }
                    }
                }
            }

        }

        

        
		

		
		// This is nonsense code, just to show you how to thrown an Exception.
		// To throw it, type "error" at the console.
		if (var.toString().equals("error")) {
			throw new ParseException("User typed \"Error\" as the input!", 0);
		}

		
        System.out.println("_________");
		return app;
	}

	public static ArrayList<String> preParser(ArrayList<String> tokens) {
        ArrayList<String> newTokens = new ArrayList<>();
        String current = "";
        boolean isRight = false;
        boolean endParen = false;

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
                        newTokens.add("\\");
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

                else {
                    newTokens.add("(");
                    newTokens.add("\\");
                    endParen = true;
                }
            }

            else {
                newTokens.add(current);
            }

            
        }

        if (endParen) newTokens.add(")");

        


        return newTokens;
    }
}
