
public class Function implements Expression {
    private Variable var;
    private Expression exp;

    public Function(Variable var, Expression exp) {
        this.var = var;
        this.exp = exp;
    }

    public String toString() {
		return "(\\" + var + "." + exp + ")";
	}

    public Variable getVar() {
        return var;
    }

    public Expression getExp() {
        return exp;
    }
}
