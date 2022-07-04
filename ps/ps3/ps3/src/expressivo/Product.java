package expressivo;

public class Product implements Expression {
    // Abstraction Function:
    //   left * right
    // Rep Invariant: -
    // Rep exposure:
    //   all fields are immutable and private, safe

    private final Expression left;
    private final Expression right;

    public Product(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " * " + right.toString() + ")";
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof Product) {
            return left.equals(((Product) that).getLeft()) && right.equals(((Product) that).getRight());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 11;
        hash = 41 * hash + left.hashCode();
        hash = 41 * hash + right.hashCode();
        return hash;
    }

    @Override
    public Expression differentiation(String varName) {
        // Product Rule
        Product left = new Product(getLeft().differentiation(varName), getRight());
        Product right = new Product(getRight().differentiation(varName), getLeft());
        return new Sum(left, right);
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
