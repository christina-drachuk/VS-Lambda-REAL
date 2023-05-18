
public class Application implements Expression {
    private Expression left;
    private Expression right;

    public Application(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        
    }

    public String toString() {
		return "(" + left + " " + right + ")";
	}
}
