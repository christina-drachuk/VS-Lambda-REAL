public interface Expression {
    Expression sub(Variable var, Expression exp);
    
    Expression copy();

    boolean equals(Expression other);
}
