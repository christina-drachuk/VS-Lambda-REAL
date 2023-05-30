
public class Application implements Expression {

    

    private Expression left;
    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    private Expression right;

    public Application(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        
    }

    public String toString() {
		return "(" + left + " " + right + ")";
	}
}
