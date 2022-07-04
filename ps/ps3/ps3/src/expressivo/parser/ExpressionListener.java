// Generated from Expression.g4 by ANTLR 4.5.1

package expressivo.parser;
// Do not edit this .java file! Edit the grammar in Expression.g4 and re-run Antlr.

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExpressionParser}.
 */
public interface ExpressionListener extends ParseTreeListener {
  /**
   * Enter a parse tree produced by {@link ExpressionParser#root}.
   * @param ctx the parse tree
   */
  void enterRoot(ExpressionParser.RootContext ctx);
  /**
   * Exit a parse tree produced by {@link ExpressionParser#root}.
   * @param ctx the parse tree
   */
  void exitRoot(ExpressionParser.RootContext ctx);
  /**
   * Enter a parse tree produced by the {@code Var}
   * labeled alternative in {@link ExpressionParser#expr}.
   * @param ctx the parse tree
   */
  void enterVar(ExpressionParser.VarContext ctx);
  /**
   * Exit a parse tree produced by the {@code Var}
   * labeled alternative in {@link ExpressionParser#expr}.
   * @param ctx the parse tree
   */
  void exitVar(ExpressionParser.VarContext ctx);
  /**
   * Enter a parse tree produced by the {@code Parens}
   * labeled alternative in {@link ExpressionParser#expr}.
   * @param ctx the parse tree
   */
  void enterParens(ExpressionParser.ParensContext ctx);
  /**
   * Exit a parse tree produced by the {@code Parens}
   * labeled alternative in {@link ExpressionParser#expr}.
   * @param ctx the parse tree
   */
  void exitParens(ExpressionParser.ParensContext ctx);
  /**
   * Enter a parse tree produced by the {@code Num}
   * labeled alternative in {@link ExpressionParser#expr}.
   * @param ctx the parse tree
   */
  void enterNum(ExpressionParser.NumContext ctx);
  /**
   * Exit a parse tree produced by the {@code Num}
   * labeled alternative in {@link ExpressionParser#expr}.
   * @param ctx the parse tree
   */
  void exitNum(ExpressionParser.NumContext ctx);
  /**
   * Enter a parse tree produced by the {@code Product}
   * labeled alternative in {@link ExpressionParser#expr}.
   * @param ctx the parse tree
   */
  void enterProduct(ExpressionParser.ProductContext ctx);
  /**
   * Exit a parse tree produced by the {@code Product}
   * labeled alternative in {@link ExpressionParser#expr}.
   * @param ctx the parse tree
   */
  void exitProduct(ExpressionParser.ProductContext ctx);
  /**
   * Enter a parse tree produced by the {@code Sum}
   * labeled alternative in {@link ExpressionParser#expr}.
   * @param ctx the parse tree
   */
  void enterSum(ExpressionParser.SumContext ctx);
  /**
   * Exit a parse tree produced by the {@code Sum}
   * labeled alternative in {@link ExpressionParser#expr}.
   * @param ctx the parse tree
   */
  void exitSum(ExpressionParser.SumContext ctx);
}