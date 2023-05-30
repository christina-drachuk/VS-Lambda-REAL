import java.applet.AppletContext;
import java.text.ParseException;
import java.util.ArrayList;

public class Runner {
    public static Expression run(Expression exp) throws ParseException {

        Lexer lexer = new Lexer();
		Parser parser = new Parser();
        boolean inside = false;

        if (exp instanceof Application && ((Application) exp).getLeft() instanceof Function) {
            Application app = (Application) exp;

            ArrayList<String> tokens = lexer.tokenize(((Function) app.getLeft()).getExp().toString());

            for (int i = 0; i < tokens.size(); i++) {
                if (tokens.get(i).equals("(") && tokens.get(i + 1).equals("\\")) {
                    inside = true;
                }

                else if (tokens.get(i).equals(")")) inside = false;

                else if (tokens.get(i).equals(((Function) app.getLeft()).getVar().toString()) && !inside) {

                    

                    tokens.remove(i);
                    tokens.addAll(i, lexer.tokenize(app.getRight().toString()));
                }
            }

            exp = parser.parse(tokens);
            run(exp);

            

        }
        
        return exp;
    }
}
