
import java.util.HashSet;

public class Runner {
    public static final HashSet<String> freeVarNames = new HashSet<>();

    private static Expression runHelper(Expression exp) {
        if (exp instanceof Application) {
            Application app = (Application) exp;
            if (app.left instanceof Function) {
                Function func = (Function) app.left;
                Variable var = func.variable;
                Expression funcExp = func.expression;
                Expression subExp = app.right.copy();
                if (subExp instanceof Variable) {
                    Variable freeVar = (Variable) subExp;
                    freeVarNames.add(freeVar.name);
                }
                return funcExp.sub(var, subExp);
            } else {
                Expression temp = runHelper(app.left);
                if (temp != null) {
                    app.left = temp;
                    return app;
                }
                temp = runHelper(app.right);
                if (temp != null) {
                    app.right = temp;
                    return app;
                }
            }
        } else if (exp instanceof Function) {
            Function func = (Function) exp;
            Expression temp = runHelper(func.expression);
            if (temp != null) {
                func.expression = temp;
                return func;
            }
        }
        return null;
    }

    public static Expression run(Expression exp) {
        freeVarNames.clear();
        Expression subExp = runHelper(exp);
        while (subExp != null) {
            exp = subExp;
            subExp = runHelper(exp);
        }
        // compare this expression against everything in stored variables. If this
        // evaluates to an expression that is identical to a stored variable, just
        // return that variable
        for (String key : Console.vars.keySet()) {

            if (exp.equals(Console.vars.get(key))) {
                return new Variable(key);
            }
        }
        return exp;
    }
}
