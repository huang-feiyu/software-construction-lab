/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

/**
 * An immutable data type representing a polynomial expression of:
 * + and *
 * non-negative integers and floating-point numbers
 * variables (case-sensitive nonempty strings of letters)
 *
 * <p>PS3 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {

    // Datatype definition
    //   Expression = Number(num: double)
    //              + Variant(str: string)
    //              + Sum(left: Expression, right: Expression)
    //              + Product(left: Expression, right: Expression)

    /**
     * Parse an expression.
     *
     * @param input expression to parse, as defined in the PS3 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    static Expression parse(String input) {
        return Parser.parse(input);
    }

    /**
     * @return a parse-able representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override
    String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS3 handout.
     */
    @Override
    boolean equals(Object thatObject);

    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     * e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    int hashCode();

    default String getName() {
        throw new UnsupportedOperationException(this.getClass().toString() + ": do not support getName()");
    }

    default Expression getLeft() {
        throw new UnsupportedOperationException(this.getClass().toString() + ": do not support getLeft()");
    }

    default Expression getRight() {
        throw new UnsupportedOperationException(this.getClass().toString() + ": do not support getRight()");
    }

    static Expression times(Expression left, Expression right) {
        return new Product(left, right);
    }

    static Expression plus(Expression left, Expression right) {
        return new Sum(left, right);
    }

}
