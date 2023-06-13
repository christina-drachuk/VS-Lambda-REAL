
public class Variable implements Expression {
    
    public String varName;

    public Variable(String varName) {
        this.varName = varName;
    }

    public Expression substitute(Variable var, Expression exp) {
        if (this.equals(var)) {
            return exp;
        }
        return this;
    }

    public boolean equals(Expression other) {
        if (other instanceof Variable) {
            Variable var = (Variable) other;
            return this.varName.equals(var.varName);
        }
        return false;
    }

    public String toString() {
        return varName;
    }

    public Variable deepCopy() {
        return new Variable(varName);
    }
}
