
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.imageio.plugins.tiff.ExifParentTIFFTagSet;


public class Parser {
	private ArrayList<String> tokens = new ArrayList<>();
	
	/*
	 * Turns a set of tokens into an expression.  Comment this back in when you're ready.
	 */

	// public Expression parse(ArrayList<String> tokens) throws ParseException {
	// 	tokens = preParser(tokens);


	// 	Variable var = new Variable(tokens.get(0));
    //     Application app = new Application(null, null);
    //     int paren = 0;
    //     int[] depthMap = new int[tokens.size()];
    //     int numTopLevels = 0;
    //     int level = 0;
    //     int count = 0;
    //     boolean end = false;
    //     Expression exp = null;

    //     Variable varF = null;
    //     Expression expF = null;




    //     // set the depthMap and number of levels
    //     for (int i = 0; i < tokens.size(); i++) {
    //         if (tokens.get(i).equals("(")) {
    //             depthMap[i] = level;
    //             if (level == 0) numTopLevels += 1;
    //             level += 1;
                
    //         }

    //         else if (tokens.get(i).equals(")")) {
    //             depthMap[i] = level;
    //             level -= 1;
    //         }

    //         else {
    //             depthMap[i] = level;
    //             if (level == 0) numTopLevels += 1;

    //         }


    //     }
    //     // System.out.println(tokens);
    //     // for (int x: depthMap) {
    //     //     System.out.print(x);
    //     // }
    //     // System.out.println();




    //     if (tokens.size() == 1) {


    //         exp = Console.getVars().get(tokens.get(0));




    //         if (!(exp == null)) {
    //             return Console.getVars().get(tokens.get(0));
    //         }
    //         return var;
    //     }

    //     // if there's only one top level, return only a Var
    //     else if (numTopLevels == 1 && tokens.get(0).equals("(") && tokens.get(tokens.size() - 1).equals(")")) {


            
        
    //         if (tokens.get(1).equals("\\")) {
    //             varF = new Variable(tokens.get(2));
    //             expF = parse(new ArrayList<>(tokens.subList(4, tokens.size() - 1)));
    //             // System.out.println(expF);
    //             return new Function(varF, expF);
    //         } 

    //         return parse(new ArrayList<>(tokens.subList(1, tokens.size() - 1)));
            

    //     } 

        



    //     else {



    //         for (int i = 0; i < tokens.size(); i++) {

                

    //             if (depthMap[i] == 0) {
    //                 for (int j = i + 1; j < tokens.size(); j++) {

                        
                        
    //                     if (depthMap[j] == 0) {


    //                         if (count == 0) {
    //                             exp = parse(new ArrayList<>(tokens.subList(i, j)));

    //                             count++;
    //                         }

    //                         else if (count == 1) {
    //                             app = new Application(exp, parse(new ArrayList<>(tokens.subList(i, j))));

    //                             count++;
    //                         }

    //                         else {
    //                             app = new Application(app, parse(new ArrayList<>(tokens.subList(i, j))));

    //                         }

    //                         i = j;

                            

                            
    //                     }

    //                     else if (tokens.get(j).equals(")") && j == tokens.size() - 1) {
    //                         if (count == 1) {
    //                             app = new Application(exp, parse(new ArrayList<>(tokens.subList(i, tokens.size()))));

    //                             count++;
    //                         }
        
    //                         else {
    //                             app = new Application(app, parse(new ArrayList<>(tokens.subList(i, tokens.size()))));

    //                         }

    //                         end = true;
    //                     }
    //                 }

    //                 if (!end) {
    //                     if (count == 0) {
    //                         exp = parse(new ArrayList<>(tokens.subList(i, tokens.size())));

    //                         count++;
    //                     }
    
    //                     else if (count == 1) {
    //                         app = new Application(exp, parse(new ArrayList<>(tokens.subList(i, tokens.size()))));

    //                         count++;
    //                     }
    
    //                     else {
    //                         app = new Application(app, parse(new ArrayList<>(tokens.subList(i, tokens.size()))));

    //                     }
    //                 }
    //             }
    //         }

    //     }

        

        
		

		
	// 	// This is nonsense code, just to show you how to thrown an Exception.
	// 	// To throw it, type "error" at the console.
	// 	if (var.toString().equals("error")) {
	// 		throw new ParseException("User typed \"Error\" as the input!", 0);
	// 	}

		

	// 	return app;
	// }

    public Expression parse(ArrayList<String> tokens) throws ParseException {
        tokens = preParser(tokens);
        // System.out.println(tokens);


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


        if (tokens.size() == 1) {

            // If vars contains the var, replace it with its value
            exp = Console.getVars().get(tokens.get(0));
            if (!(exp == null)) {
                return Console.getVars().get(tokens.get(0));
            }
            return var;
        }

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

        int elementCount = 0;

        for (String x : tokens) {
            System.out.print(x + " ");
        }
        System.out.println();

        for (int x : depthMap) {
            System.out.print(x + " ");
            if (x == 0) {
                elementCount++;
            }
        }
        System.out.println();
        System.out.println("ELEMENT COUNT : " + elementCount);

        if (elementCount == 1) {

            if (tokens.get(0).equals("(") && tokens.get(tokens.size() - 1).equals(")")) {
                if (tokens.get(1).equals("\\")) {
                    varF = new Variable(tokens.get(2));
                    expF = parse(new ArrayList<String>(tokens.subList(4, tokens.size() - 1)));

                    return new Function(varF, expF);
                }

                //System.out.println(new ArrayList<String>(tokens.subList(1, tokens.size() - 1)));
                return parse(new ArrayList<String>(tokens.subList(1, tokens.size() - 1)));
                


            }
            

        }

        else {
            count = 0; 
            for (int i = 0; i < tokens.size(); i++) {
                if (depthMap[i] == 0) {
                    if (i == tokens.size() - 1) {
                        if (count == 0) exp = parse(new ArrayList<>(tokens.subList(i, tokens.size())));

                        else if (count == 1) app = new Application(exp, parse(new ArrayList<>(tokens.subList(i, tokens.size()))));

                        else app = new Application(app, parse(new ArrayList<>(tokens.subList(i, tokens.size()))));

                        count++;

                    } 

                    for (int j = i+1; j < tokens.size(); j++) {
                        if (depthMap[j] == 0) {
                            if (count == 0) exp = parse(new ArrayList<>(tokens.subList(i, j)));

                            else if (count == 1) app = new Application(exp, parse(new ArrayList<>(tokens.subList(i, j))));

                            else app = new Application(app, parse(new ArrayList<>(tokens.subList(i, j))));

                            count++;
                            i = j - 1;
                            j = tokens.size();
                        }
                         
                        else if (j == tokens.size() - 1) {
                            if (count == 0) exp = parse(new ArrayList<>(tokens.subList(i, tokens.size())));

                            else if (count == 1) app = new Application(exp, parse(new ArrayList<>(tokens.subList(i, tokens.size()))));

                            else app = new Application(app, parse(new ArrayList<>(tokens.subList(i, tokens.size()))));

                            count++;
                        }
                    }
                    
                }
            }
        }



        if (var.toString().equals("error")) {
			throw new ParseException("User typed \"Error\" as the input!", 0);
		}

        if (count == 1) return exp;
        return app;
    }

	// public static ArrayList<String> preParser(ArrayList<String> tokens) {
    //     ArrayList<String> newTokens = new ArrayList<>();
    //     String current = "";
    //     boolean isRight = false;
    //     boolean endParen = false;

    //     for (int i = 0; i < tokens.size(); i++) {
    //         current = tokens.get(i);

    //         if (current.equals("\\")) {
    //             if (i > 0) {
    //                 if (tokens.get(i - 1).equals("(")) {
    //                     // Do nothing
    //                     newTokens.add("\\");
    //                 }

    //                 else {
    //                     newTokens.add("(");
    //                     newTokens.add("\\");
    //                     i++;
    //                     isRight = false;

    //                     while (isRight == false && i < tokens.size()) {
    //                         if (tokens.get(i).equals(")")) {
    //                             newTokens.add(")");
    //                             isRight = true;
    //                         }

    //                         else {
    //                             newTokens.add(tokens.get(i));
    //                             i++;
    //                         }
                            
    //                     }
    //                     newTokens.add(")");
    //                 }
    //             }

    //             else {
    //                 newTokens.add("(");
    //                 newTokens.add("\\");
    //                 endParen = true;
    //             }
    //         }

    //         else {
    //             newTokens.add(current);
    //         }

            
    //     }

    //     if (endParen) newTokens.add(")");

        


    //     return newTokens;
    // }

    public static ArrayList<String> preParser(ArrayList<String> tokens) {
        ArrayList<String> newTokens = new ArrayList<>();
        String current = "";
        int paren = 0;
        int lambdaParen = 0;
        boolean inside = false;
        boolean lambdaInside = false;

        for (int i = 0; i < tokens.size(); i++) {
            current = tokens.get(i);
            
            if (current.equals("(")) {
                paren++;
                inside = true;
            }

            else if (current.equals("\\")) {
                newTokens.add("(");
                newTokens.add(tokens.get(i)); // lambda
                newTokens.add(tokens.get(++i)); // var
                newTokens.add(tokens.get(++i)); // period

                // System.out.println(newTokens);

                lambdaInside = true;
                lambdaParen++;
            }

            else {
                newTokens.add(current);
            }

            int startParen = i;
            boolean first = true;
            boolean includeParen = true;
            while (inside) {
                
                i++;
                if (first && tokens.get(i).equals("\\")) {first = false; includeParen = false;}

                if (i != tokens.size()) current = tokens.get(i);

                if (current.equals("(")) paren ++;

                else if (current.equals(")")) {
                    paren--;
                    if (paren == 0) {
                        inside = false;
                        if (includeParen) newTokens.add("(");
                        newTokens.addAll(preParser(new ArrayList<>(tokens.subList(startParen + 1, i))));
                        if (includeParen) newTokens.add(")");
                        includeParen = true;
                    }

                    
                }
            }

            while (lambdaInside) {
                i++;
                if (i != tokens.size()) current = tokens.get(i);


                if (i == tokens.size()) {
                    // System.out.println(preParser(new ArrayList<>(tokens.subList(startParen + 1, tokens.size()))));
                    newTokens.addAll(preParser(new ArrayList<>(tokens.subList(startParen + 1, tokens.size()))));
                    newTokens.add(")");


                    lambdaInside = false;
                    
                    
                    
                }

                else {
                    if (current.equals("(")) lambdaParen ++;

                    else if (current.equals(")")) {
                        lambdaParen--;
                        if (lambdaParen == 0) {
                            lambdaInside = false;
                            newTokens.addAll(preParser(new ArrayList<>(tokens.subList(startParen, i + 1))));
                            newTokens.add(")");
                        }

                        
                    }
                }


            }

            
        }



        return newTokens;

    }

    
}
