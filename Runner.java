import java.applet.AppletContext;
import java.text.ParseException;
import java.util.ArrayList;

public class Runner {
    public static Expression run(Expression exp) throws ParseException {


        // if (exp instanceof Function) {
        //     System.out.print("FuncVar: " + ((Function) exp).getVar() + " -- FuncExp: ");
        //     System.out.println(((Function) exp).getExp());
        // }

        // else if (exp instanceof Application) {
        //     System.out.print("Left: " + ((Application) exp).getLeft() + " -- Right: ");
        //     System.out.println(((Application) exp).getRight());
        // }

        // else if (exp instanceof Variable) {
        //     System.out.println("Variable: " + exp);
        // }
        
        // else System.out.println("Expression: " +  exp);

        Lexer lexer = new Lexer();
		Parser parser = new Parser();

        if (exp instanceof Function) {
            Function func = (Function) exp;
            Variable funcVar = func.getVar();
            Expression funcExp = func.getExp();
            return new Function(funcVar, run(funcExp));
        }

        else if (exp instanceof Application) {
            Application app = ((Application) exp);
            if (app.getLeft() instanceof Function) {


                
                // Left
                Function func = (Function) app.getLeft();
                Variable funcVar = func.getVar();
                Expression funcExp = func.getExp();
                ArrayList<String> funcExpTokens = lexer.tokenize(funcExp.toString());

                // Right
                Expression replace = run(app.getRight());
                ArrayList<String> replaceTokens = lexer.tokenize(replace.toString());


                
                // Check if IN subFunction
                boolean inside = false;
                int paren = 0;

                ArrayList<String> freeVars = new ArrayList<>();
                ArrayList<String> boundVars = new ArrayList<>();

                for (int i = 0; i < funcExpTokens.size(); i++) { 

                    String var = funcExpTokens.get(i).toString();

                    

                    boolean insideFree = false;
                    int parenFree = 0;

                    for (int k = 0; k < funcExpTokens.size(); k++) {
                        if (funcExpTokens.get(i).equals("\\")) {
                            if (!boundVars.contains(funcExpTokens.get(i + 1))) {
                                boundVars.add(funcExpTokens.get(i + 1));
                            }
                            
                        }
                    }

                    for (int k = 0; k < replaceTokens.size(); k++) {

                        if (!insideFree && replaceTokens.get(k).equals("\\")) {
                            insideFree = true;
                            parenFree = 1; 
                        }
    
                        else if (insideFree && replaceTokens.get(k).equals("(")) {
                            parenFree++;
                        }
    
                        else if (insideFree && replaceTokens.get(k).equals(")")) {
                            parenFree--;
                            if (parenFree == 0) {
                                insideFree = false;
                            }
                        }
    
    
                        if (!insideFree && "()\\.=".indexOf(var) == -1) {
                            if (!freeVars.contains(replaceTokens.get(k))) {
                                freeVars.add(replaceTokens.get(k));
                            }
                            
                        }
                    }
                    
                    // System.out.println("Free: " + freeVars + "--> Bound: " + boundVars);

                    if ("()\\.=".indexOf(var) == -1) {
                        // If var is a free variable
                        // \x1.x1
                        if (freeVars.contains(var) && boundVars.contains(var)) {
                            String newVar = "WRONG";

                            for (int j = 0; j < var.length(); j++) {
                                char character = var.charAt(j);
                                

                                // Check if 0-9
                                if ((int) character >= 48 && (int) character <= 57) {

                                    newVar = var.substring(0, j) + (Integer.parseInt(var.substring(j, var.length())) + 1);
                                    j = var.length() - 1;
                                }

                                else if (j == var.length() - 1) {
                                    newVar = var + 1;
                                }


                            }

                            funcExpTokens.set(i, newVar);
                        }
                    }

                }


                for (String x : funcExpTokens) {
                    System.out.print(x + " ");
                }
                System.out.println();

                for (int i = 0; i < funcExpTokens.size(); i++) {

                    if (!inside && funcExpTokens.get(i).equals("\\") && funcExpTokens.get(i + 1).equals(funcVar.toString())) {
                        inside = true;
                        paren = 1; 
                    }

                    else if (inside && funcExpTokens.get(i).equals("(")) {
                        paren++;
                    }

                    else if (inside && funcExpTokens.get(i).equals(")")) {
                        paren--;
                        if (paren == 0) {
                            inside = false;
                        }
                    }


                    if (!inside && funcExpTokens.get(i).equals(funcVar.toString())) {
                        funcExpTokens.remove(i);
                        funcExpTokens.addAll(i, lexer.tokenize(replace.toString()));

                        i = i + lexer.tokenize(replace.toString()).size();
                    }
                }
                // System.out.println(funcExpTokens);
                for (String x : funcExpTokens) {
                    System.out.print(x + " ");
                }
                System.out.println();

                return run(parser.parse(funcExpTokens));

            }

            else {
                app = new Application(run(app.getLeft()), run(app.getRight()));
                if (app.getLeft() instanceof Function) {
                    return run(app);
                }


                
                return app;
            }
        }

        else if (exp instanceof Variable || exp instanceof Expression) {
            // System.out.println("end");
            return exp;
        }

        
       

        return null;
    }
}
