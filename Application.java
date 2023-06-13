public class Application implements Expression {
    
    public Expression leftExp;
    public Expression rightExp;

    public Application(Expression leftExp, Expression rightExp) {
        this.leftExp = leftExp;
        this.rightExp = rightExp;
    }

    public Expression substitute(Variable var, Expression exp) {
        return new Application(this.leftExp.substitute(var, exp), this.rightExp.substitute(var, exp));
    }

    public Application deepCopy() {
        return new Application(leftExp.deepCopy(), rightExp.deepCopy());
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
