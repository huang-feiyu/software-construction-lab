# Expressivo

> [Expressivo](https://ocw.mit.edu/ans7870/6/6.005/s16/psets/ps3/)

## Problem 1

> Representing Expressions

Define an immutable, recursive abstract data type(ADT, aka `interface`) to represent expressions as abstract syntax trees.
Then create the variants implementing the interface.

Representing Invariants:

```antlrv4
Expression = Number(num: double)
           + Variable(str: string)
           + Sum(left: Expression, right: Expression)
           + Product(left: Expression, right: Expression)
```

---

| Class      | Abstraction Function                         | Rep Invariant           | Rep exposure |
|------------|----------------------------------------------|-------------------------|--------------|
| `Number`   | represents a non-negative num                | num >= 0                | safe         |
| `Variable` | case-sensitive nonempty sequences of letters | name.match("[a-zA-Z]+") | safe         |
| `Sum`      | left:expression + right:expression           | \-                      | safe         |
| `Product`  | left:expression * right:expression           | \-                      | safe         |

Because I am too busy recently, so I have **not** implemented tests.
