
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

	String tok = "";
	
	public ArrayList<String> tokenize(String input) {
		ArrayList<String> tokens = new ArrayList<String>();
        for (int i = 0; i < input.length(); i++) {
            if ("()\\.=".indexOf(input.substring(i, i+1)) != -1) {
                if (!(tok.isEmpty())) {
                    tokens.add(tok);
                    tok = "";
                }
                tokens.add(input.substring(i, i+1));
            }

            else if (input.substring(i, i+1).equals(" ")) {
                if (!(tok.isEmpty())) {
                    tokens.add(tok);
                    tok = "";
                }
            }

            else if (input.substring(i, i+1).equals("λ")) {
                if (!(tok.isEmpty())) {
                    tokens.add(tok);
                    tok = "";
                }
                tokens.add("\\");
            }

            else if (input.substring(i, i+1).equals(";")) {
                i = input.length() - 1;
            }

            else {
                tok += input.substring(i, i+1);
            }
            
        }

        if (!(tok.isEmpty())) {
            tokens.add(tok);
            tok = "";
        }

        return tokens;
	}



}
