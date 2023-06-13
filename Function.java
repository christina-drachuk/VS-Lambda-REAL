
public class Function implements Expression {

    public Variable var;
    public Expression exp;

    public Function(Variable var, Expression exp) {
        this.var = var;
        this.exp = exp;
    }

    public Function copy() {
        return new Function(var.copy(), exp.copy());
    }

    public Expression sub(Variable originalVar, Expression newExp) {

        if (this.var.equals(originalVar)) {
            return this;
        }

        else {
            Function func = this.copy();
            // bounded variable found that matches name of freevar, needs to be swapped
            if (newExp instanceof Variable && Runner.freeVarNames.contains(((Variable) newExp).varName)) {
                Variable var = (Variable) newExp;
                String newName = this.var.varName;
                int count = 0;
                
                while (Runner.freeVarNames.contains(newName) == true) {
                    ++count;
                    newName = this.var.varName + count;
                }
                
                func.var.varName = newName;
                func.exp = func.exp.sub(this.var, new Variable(newName));
            }
            func.exp = func.exp.sub(originalVar, newExp);
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
