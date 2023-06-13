
public class Function implements Expression {

    public Variable var;
    public Expression exp;

    public Function(Variable var, Expression exp) {
        this.var = var;
        this.exp = exp;
    }

    public Function deepCopy() {
        return new Function(var.deepCopy(), exp.deepCopy());
    }

    public Expression substitute(Variable originalVar, Expression newExp) {

        if (this.var.equals(originalVar)) {
            return this;
        }

        else {
            Function func = this.deepCopy();
            if (newExp instanceof Variable && Runner.freeVarNames.contains(((Variable) newExp).varName)) {
                Variable var = (Variable) newExp;
                String newName = this.var.varName;
                int count = 0;
                
                while (Runner.freeVarNames.contains(newName) == true) {
                    ++count;
                    newName = this.var.varName + count;
                }
                
                func.var.varName = newName;
                func.exp = func.exp.substitute(this.var, new Variable(newName));
            }
            func.exp = func.exp.substitute(originalVar, newExp);
            return func;
        }
    }

    public boolean equals(Expression other) {
        if (other instanceof Function) {
            Function func = (Function) other;
            return this.var.equals(func.var) && this.exp.equals(func.exp);
        }
        return false;
    }

    public String toString() {
        return "(λ" + var + "." + exp + ")";
    }
    
}
