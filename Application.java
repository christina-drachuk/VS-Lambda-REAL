

public class Application implements Expression {
    public Expression left;
    public Expression right;

    public Application(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Application copy() {
        return new Application(left.copy(), right.copy());
    }

    public Expression sub(Variable v, Expression e) {
        return new Application(this.left.sub(v, e), this.right.sub(v, e));
    }

    public String toString() {
        return "(" + left + " " + right + ")";
    }

    public boolean equals(Expression other) {
        if (other instanceof Application) {
            Application app = (Application) other;
            return this.left.equals(app.left) && this.right.equals(app.right);
        }
        return false;
    }
}
