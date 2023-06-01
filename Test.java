import java.applet.AppletContext;
import java.text.ParseException;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws ParseException {
        ArrayList<String> test1 = new ArrayList<>();
        test1.add("a");
        test1.add("b");
        test1.add("c");

        ArrayList<String> test2 = new ArrayList<>();
        test2.add("d");
        test2.add("e");
        test2.add("f");

        System.out.println(test1);

        test1.remove(1);
        test1.addAll(1, test2);

        
        System.out.println(test1);

        // Lexer lexer = new Lexer();
		// Parser parser = new Parser();


        // Expression exp = parser.parse(lexer.tokenize("(((λx.x) y) z)"));

        // if (exp instanceof Variable) {
        //     System.out.println("var");
        // }

        // else if (exp instanceof Application) {
        //     System.out.println("app");
        // }

        // else if (exp instanceof Function) {
        //     System.out.println("func");
        // }

        // else if (exp instanceof Expression) {
        //     System.out.println("exp");
        // }
    }
}
