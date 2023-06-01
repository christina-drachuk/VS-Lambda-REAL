import java.applet.AppletContext;
import java.text.ParseException;
import java.util.ArrayList;

public class Runner {
    public static Expression run(Expression exp) throws ParseException {

        Lexer lexer = new Lexer();
		Parser parser = new Parser();

        if (exp instanceof Application) {
            Application app = ((Application) exp);
            if (app.getLeft() instanceof Function) {


                
                // Left
                Function func = (Function) app.getLeft();
                Variable funcVar = func.getVar();
                Expression funcExp = func.getExp();
                ArrayList<String> funcExpTokens = lexer.tokenize(funcExp.toString());

                // Right
                Expression replace = app.getRight();

                
                // Check if IN subFunction
                boolean inside = false;
                int paren = 0;

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

                return run(parser.parse(funcExpTokens));

            }

            else {
                return new Application(run(app.getLeft()), run(app.getRight()));
            }
        }

        else if (exp instanceof Variable || exp instanceof Expression) {
            return exp;
        }

        
       

        return null;
    }
}
