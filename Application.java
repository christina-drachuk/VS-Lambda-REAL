public class Application implements Expression {
    
    public Expression leftExp;
    public Expression rightExp;

    public Application(Expression leftExp, Expression rightExp) {
        this.leftExp = leftExp;
        this.rightExp = rightExp;
    }

    public Expression sub(Variable var, Expression exp) {
        return new Application(this.leftExp.sub(var, exp), this.rightExp.sub(var, exp));
    }

    public Application copy() {
        return new Application(leftExp.copy(), rightExp.copy());
    }

    public boolean equals(Expression other) {
        if (other instanceof Application) {
            Application app = (Application) other;
            return this.leftExp.equals(app.leftExp) && this.rightExp.equals(app.rightExp);
        }
        
        else return false;
    }

    public String toString() {
        return "(" + leftExp + " " + rightExp + ")";
    }
}
