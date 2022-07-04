package expressivo;

import expressivo.parser.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.util.Stack;

public class Parser {
    public static Expression parse(String string) {
        // Create a stream of characters from the string
        CharStream stream = new ANTLRInputStream(string);

        // Make a parser
        ExpressionParser parser = makeParser(stream);

        // Generate the parse tree using the starter rule.
        // root is the starter rule for this grammar.
        // Other grammars may have different names for the starter rule.
        ParseTree tree = parser.root();

        MakeExpression exprMaker = new MakeExpression();
        new ParseTreeWalker().walk(exprMaker, tree);
        return exprMaker.getExpression();
    }

    /**
     * Make a parser that is ready to parse a stream of characters.
     * To start parsing, the client should call a method on the returned parser
     * corresponding to the start rule of the grammar, e.g. parser.root() or
     * whatever it happens to be.
     * During parsing, if the parser encounters a syntax error, it will throw a
     * ParseCancellationException.
     *
     * @param stream stream of characters
     * @return a parser that is ready to parse the stream
     */
    private static ExpressionParser makeParser(CharStream stream) {
        // Make a lexer.  This converts the stream of characters into a
        // stream of tokens.  A token is a character group, like "<i>"
        // or "</i>".  Note that this doesn't start reading the character stream yet,
        // it just sets up the lexer to read it.
        ExpressionLexer lexer = new ExpressionLexer(stream);
        lexer.reportErrorsAsExceptions();
        TokenStream tokens = new CommonTokenStream(lexer);

        // Make a parser whose input comes from the token stream produced by the lexer.
        ExpressionParser parser = new ExpressionParser(tokens);
        parser.reportErrorsAsExceptions();

        return parser;
    }
}

class MakeExpression implements ExpressionListener {
    private final Stack<Expression> stack = new Stack<>();
    // Invariant: stack contains the Expression value of each parse
    // subtree that has been fully-walked so far, but whose parent has not yet
    // been exited by the walk. The stack is ordered by recency of visit, so that
    // the top of the stack is the Expression for the most recently walked
    // subtree.
    //
    // At the start of the walk, the stack is empty, because no subtrees have
    // been fully walked.
    //
    // Whenever a node is exited by the walk, the Expression values of its
    // children are on top of the stack, in order with the last child on top. To
    // preserve the invariant, we must pop those child Expression values
    // from the stack, combine them with the appropriate Expression
    // producer, and push back an Expression value representing the entire
    // subtree under the node.
    //
    // At the end of the walk, after all subtrees have been walked and the
    // root has been exited, only the entire tree satisfies the invariant's
    // "fully walked but parent not yet exited" property, so the top of the stack
    // is the Expression of the entire parse tree.

    public Expression getExpression() {
        return stack.get(0);
    }

    @Override
    public void enterRoot(ExpressionParser.RootContext ctx) {
    }

    @Override
    public void exitRoot(ExpressionParser.RootContext ctx) {
    }

    @Override
    public void enterProduct(ExpressionParser.ProductContext ctx) {
    }

    @Override
    public void exitProduct(ExpressionParser.ProductContext ctx) {
        assert ctx.expr().size() == 2;
        Expression right = stack.pop();
        Expression left = stack.pop();
        stack.add(Expression.times(left, right));
    }

    @Override
    public void enterSum(ExpressionParser.SumContext ctx) {
    }

    @Override
    public void exitSum(ExpressionParser.SumContext ctx) {
        assert ctx.expr().size() == 2;
        Expression right = stack.pop();
        Expression left = stack.pop();
        stack.add(Expression.plus(left, right));
    }

    @Override
    public void enterVar(ExpressionParser.VarContext ctx) {
    }

    @Override
    public void exitVar(ExpressionParser.VarContext ctx) {
        stack.push(new Variable(ctx.getText()));
    }

    @Override
    public void enterParens(ExpressionParser.ParensContext ctx) {
    }

    @Override
    public void exitParens(ExpressionParser.ParensContext ctx) {
    }

    @Override
    public void enterNum(ExpressionParser.NumContext ctx) {
    }

    @Override
    public void exitNum(ExpressionParser.NumContext ctx) {
        stack.push(new Number(Double.parseDouble(ctx.getText())));
    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {
    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {
    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {
    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {
    }
}
