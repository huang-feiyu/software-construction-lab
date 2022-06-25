package expressivo;

public class Sum implements Expression {
    // Abstraction Function:
    //   left + right
    // Rep Invariant: -
    // Rep exposure:
    //   all fields are immutable and private, safe

    private final Expression left;
    private final Expression right;

    public Sum(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " + " + right.toString() + ") ";
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof Sum) {
            return left.equals(((Sum) that).getLeft()) && right.equals(((Sum) that).getRight());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + left.hashCode();
        hash = 37 * hash + right.hashCode();
        return hash;
    }

    @Override
    public Expression getLeft() {
        return left;
    }

    @Override
    public Expression getRight() {
        return right;
    }

}
