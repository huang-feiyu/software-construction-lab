package expressivo;

public class Variable implements Expression {
    // Abstraction Function:
    //   a case-sensitive nonempty name
    // Rep Invariant:
    //   name.match("[a-zA-Z]+")
    // Rep exposure:
    //   name is final and private, safe

    private final String name;

    private void checkRep() {
        assert name.matches("[a-zA-Z]+");
    }

    public Variable(String name) {
        this.name = name;
        checkRep();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof Variable) {
            return this.name.equals(((Variable) that).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String getName() {
        return name;
    }

}
