# Expressivo

> [Expressivo](https://ocw.mit.edu/ans7870/6/6.005/s16/psets/ps3/)

## Problem 1

> Representing Expressions

Define an immutable, recursive abstract data type(ADT, aka `interface`) to represent
expressions as abstract syntax trees. Then create the variants implementing the interface.

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

<s>TODO: Use knowledge in [little-language-2](https://web.mit.edu/6.031/www/sp21/classes/28-little-languages-2/)
to refactor the code.</s>

## Problem 2

> Parsing Expressions

<strong>*</strong> According to
[Parser Generators](https://web.mit.edu/6.005/www/sp16/classes/18-parser-generators/),
edit `Expression.g4`.

```antlrv4
root : expr EOF;
expr : expr TIMES expr #Product
       | expr PLUS expr #Sum
       | NUM #Num
       | VAR #Var
       | '(' expr ')' #Parens
       ;

NUM : [0-9]+('.'[0-9]*)? | '.'[0-9]+;
VAR : [a-zA-Z]+;

TIMES : '*';
PLUS : '+';

SPACES : [ \t\r\n]+ -> skip;
```

<strong>*</strong> According to [Parser Generators](https://github.com/mit6005/sp16-ex18-parser-generators),
implement `Expression.parse()`

Before implementing the problem, I think it is very difficult...
I should be more confident in myself.
