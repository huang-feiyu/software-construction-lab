/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import java.util.Map;

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

    /**
     * Generate an expression that takes the derivative of a variable.
     *
     * @param varName the name of the variable.
     * @return derivative of <code>this</code> expression. The derivative rules
     * are defined in <code>README</code> document.
     */
    Expression differentiate(String varName);

    /**
     * Simplify <code>this</code> expression. Simplified expression is equivalent
     * to previous.
     *
     * @return simplified expression.
     */
    Expression simplify(Map<Expression, Double> values);

    /**
     * Get name of a <code>Variable</code>.
     *
     * @return <code>Variable</code> name.
     * @throws UnsupportedOperationException if <code>Expression</code> is not a
     * <code>Variable</code>.
     */
    default String getName() {
        throw new UnsupportedOperationException(this.getClass().toString() + ": do not support getName()");
    }

    /**
     * Get left expression of a <code>Sum</code> or <code>Product</code>.
     *
     * @return left expression
     * @throws UnsupportedOperationException if <code>Expression</code> is neither
     * <code>Sum</code> nor <code>Product</code>.
     */
    default Expression getLeft() {
        throw new UnsupportedOperationException(this.getClass().toString() + ": do not support getLeft()");
    }

    /**
     * Get right expression of a <code>Sum</code> or <code>Product</code>.
     *
     * @return right expression
     * @throws UnsupportedOperationException if <code>Expression</code> is neither
     * <code>Sum</code> nor <code>Product</code>.
     */
    default Expression getRight() {
        throw new UnsupportedOperationException(this.getClass().toString() + ": do not support getRight()");
    }

    /**
     * Generate left * right.
     *
     * @param left expression
     * @param right expression
     * @return <code>Product</code> represents as left * right.
     */
    static Expression times(Expression left, Expression right) {
        Number num0 = new Number(0);
        Number num1 = new Number(1);
        if (left.equals(num0) || right.equals(num0)) {
            // rule 2
            return num0;
        } else if (left.equals(num1)) {
            // rule 1
            return right;
        } else if (right.equals(num1)) {
            // rule 1
            return left;
        }

        // rule 4
        if (left instanceof Number && right instanceof Number) {
            Number leftNum = (Number) left;
            Number rightNum = (Number) right;
            return new Number(leftNum.getNum() * rightNum.getNum());
        }
        return new Product(left, right);
    }

    /**
     * Generate left + right.
     *
     * @param left expression
     * @param right expression
     * @return <code>Sum</code> represents as left + right.
     */
    static Expression plus(Expression left, Expression right) {
        Number num0 = new Number(0);
        // rule 3
        if (left.equals(num0)) {
            return right;
        } else if (right.equals(num0)) {
            return left;
        }

        // rule 4
        if (left instanceof Number && right instanceof Number) {
            Number leftNum = (Number) left;
            Number rightNum = (Number) right;
            return new Number(leftNum.getNum() + rightNum.getNum());
        }
        return new Sum(left, right);
    }

}
