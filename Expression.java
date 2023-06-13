public interface Expression {
    Expression substitute(Variable var, Expression exp);
    
    Expression deepCopy();

    boolean equals(Expression other);
}
