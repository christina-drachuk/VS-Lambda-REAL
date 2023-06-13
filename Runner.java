
import java.util.HashSet;

public class Runner {
    public static final HashSet<String> freeVarNames = new HashSet<>();

    public static Expression run(Expression exp) {
        freeVarNames.clear();
        Expression subExp = helper(exp);

        while (subExp != null) {
            exp = subExp;
            subExp = helper(exp);
        }

        for (String key : Console.vars.keySet()) {

            if (exp.equals(Console.vars.get(key))) {
                return new Variable(key);
            }
        }
        return exp;
    }

    private static Expression helper(Expression exp) {
        if (exp instanceof Application) {
            Application app = (Application) exp;

            if (app.leftExp instanceof Function) {
                Function func = (Function) app.leftExp;
                Variable var = func.var;
                Expression funcExp = func.exp;
                Expression subExpression = app.rightExp.deepCopy();

                if (subExpression instanceof Variable) {
                    Variable freeVar = (Variable) subExpression;
                    freeVarNames.add(freeVar.varName);
                }

                return funcExp.substitute(var, subExpression);
            }

            else {
                Expression temp = helper(app.leftExp);
                if (temp != null) {
                    app.leftExp = temp;
                    return app;
                }
                temp = helper(app.rightExp);

                if (temp != null) {
                    app.rightExp = temp;
                    return app;
                }
            }
        }

        else if (exp instanceof Function) {
            Function func = (Function) exp;
            Expression temp = helper(func.exp);
            if (temp != null) {
                func.exp = temp;
                return func;
            }
        }
        return null;
    }

}
