package expressivo;

public class Number implements Expression {
    // Abstraction Function:
    //   represents a non-negative number
    // Rep Invariant:
    //   num >= 0
    // Rep exposure:
    //   num is final and private, safe from rep exposure

    private final double num;

    private void checkRep() {
        assert num >= 0;
    }

    public Number(double num) {
        this.num = num;
        checkRep();
    }

    @Override
    public String toString() {
        return num + "";
    }

    @Override
    public boolean equals(Object that) {
        // there is a more OOP approach to implement sth. like this: Double-dispatch
        if (that instanceof Number) {
            return this.num == ((Number) that).num;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(num);
    }

    @Override
    public Expression differentiation(String varName) {
        // Constant Rule
        return new Number(0);
    }

}
