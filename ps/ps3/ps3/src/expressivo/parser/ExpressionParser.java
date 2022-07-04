// Generated from Expression.g4 by ANTLR 4.5.1

package expressivo.parser;
// Do not edit this .java file! Edit the grammar in Expression.g4 and re-run Antlr.

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExpressionParser extends Parser {
  static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

  protected static final DFA[] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache =
    new PredictionContextCache();
  public static final int
    T__0=1, T__1=2, NUM=3, VAR=4, TIMES=5, PLUS=6, SPACES=7;
  public static final int
    RULE_root = 0, RULE_expr = 1;
  public static final String[] ruleNames = {
    "root", "expr"
  };

  private static final String[] _LITERAL_NAMES = {
    null, "'('", "')'", null, null, "'*'", "'+'"
  };
  private static final String[] _SYMBOLIC_NAMES = {
    null, null, null, "NUM", "VAR", "TIMES", "PLUS", "SPACES"
  };
  public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

  /**
   * @deprecated Use {@link #VOCABULARY} instead.
   */
  @Deprecated
  public static final String[] tokenNames;
  static {
    tokenNames = new String[_SYMBOLIC_NAMES.length];
    for (int i = 0; i < tokenNames.length; i++) {
      tokenNames[i] = VOCABULARY.getLiteralName(i);
      if (tokenNames[i] == null) {
        tokenNames[i] = VOCABULARY.getSymbolicName(i);
      }

      if (tokenNames[i] == null) {
        tokenNames[i] = "<INVALID>";
      }
    }
  }

  @Override
  @Deprecated
  public String[] getTokenNames() {
    return tokenNames;
  }

  @Override

  public Vocabulary getVocabulary() {
    return VOCABULARY;
  }

  @Override
  public String getGrammarFileName() { return "Expression.g4"; }

  @Override
  public String[] getRuleNames() { return ruleNames; }

  @Override
  public String getSerializedATN() { return _serializedATN; }

  @Override
  public ATN getATN() { return _ATN; }


      // This method makes the lexer or parser stop running if it encounters
      // invalid input and throw a ParseCancellationException.
      public void reportErrorsAsExceptions() {
          // To prevent any reports to standard error, add this line:
          //removeErrorListeners();
          
          addErrorListener(new BaseErrorListener() {
              public void syntaxError(Recognizer<?, ?> recognizer,
                                      Object offendingSymbol,
                                      int line, int charPositionInLine,
                                      String msg, RecognitionException e) {
                  throw new ParseCancellationException(msg, e);
              }
          });
      }

  public ExpressionParser(TokenStream input) {
    super(input);
    _interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
  }
  public static class RootContext extends ParserRuleContext {
    public ExprContext expr() {
      return getRuleContext(ExprContext.class,0);
    }
    public TerminalNode EOF() { return getToken(ExpressionParser.EOF, 0); }
    public RootContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }
    @Override public int getRuleIndex() { return RULE_root; }
    @Override
    public void enterRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterRoot(this);
    }
    @Override
    public void exitRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitRoot(this);
    }
  }

  public final RootContext root() throws RecognitionException {
    RootContext _localctx = new RootContext(_ctx, getState());
    enterRule(_localctx, 0, RULE_root);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(4);
      expr(0);
      setState(5);
      match(EOF);
      }
    }
    catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    }
    finally {
      exitRule();
    }
    return _localctx;
  }

  public static class ExprContext extends ParserRuleContext {
    public TerminalNode NUM() { return getToken(ExpressionParser.NUM, 0); }
    public TerminalNode VAR() { return getToken(ExpressionParser.VAR, 0); }
    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }
    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class,i);
    }
    public TerminalNode TIMES() { return getToken(ExpressionParser.TIMES, 0); }
    public TerminalNode PLUS() { return getToken(ExpressionParser.PLUS, 0); }
    public ExprContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }
    @Override public int getRuleIndex() { return RULE_expr; }
    @Override
    public void enterRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterExpr(this);
    }
    @Override
    public void exitRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitExpr(this);
    }
  }

  public final ExprContext expr() throws RecognitionException {
    return expr(0);
  }

  private ExprContext expr(int _p) throws RecognitionException {
    ParserRuleContext _parentctx = _ctx;
    int _parentState = getState();
    ExprContext _localctx = new ExprContext(_ctx, _parentState);
    ExprContext _prevctx = _localctx;
    int _startState = 2;
    enterRecursionRule(_localctx, 2, RULE_expr, _p);
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
      setState(14);
      switch (_input.LA(1)) {
      case NUM:
        {
        setState(8);
        match(NUM);
        }
        break;
      case VAR:
        {
        setState(9);
        match(VAR);
        }
        break;
      case T__0:
        {
        setState(10);
        match(T__0);
        setState(11);
        expr(0);
        setState(12);
        match(T__1);
        }
        break;
      default:
        throw new NoViableAltException(this);
      }
      _ctx.stop = _input.LT(-1);
      setState(24);
      _errHandler.sync(this);
      _alt = getInterpreter().adaptivePredict(_input,2,_ctx);
      while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
        if ( _alt==1 ) {
          if ( _parseListeners!=null ) triggerExitRuleEvent();
          _prevctx = _localctx;
          {
          setState(22);
          switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
          case 1:
            {
            _localctx = new ExprContext(_parentctx, _parentState);
            pushNewRecursionContext(_localctx, _startState, RULE_expr);
            setState(16);
            if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
            setState(17);
            match(TIMES);
            setState(18);
            expr(6);
            }
            break;
          case 2:
            {
            _localctx = new ExprContext(_parentctx, _parentState);
            pushNewRecursionContext(_localctx, _startState, RULE_expr);
            setState(19);
            if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
            setState(20);
            match(PLUS);
            setState(21);
            expr(5);
            }
            break;
          }
          } 
        }
        setState(26);
        _errHandler.sync(this);
        _alt = getInterpreter().adaptivePredict(_input,2,_ctx);
      }
      }
    }
    catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    }
    finally {
      unrollRecursionContexts(_parentctx);
    }
    return _localctx;
  }

  public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
    switch (ruleIndex) {
    case 1:
      return expr_sempred((ExprContext)_localctx, predIndex);
    }
    return true;
  }
  private boolean expr_sempred(ExprContext _localctx, int predIndex) {
    switch (predIndex) {
    case 0:
      return precpred(_ctx, 5);
    case 1:
      return precpred(_ctx, 4);
    }
    return true;
  }

  public static final String _serializedATN =
    "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\t\36\4\2\t\2\4"+
      "\3\t\3\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\21\n\3\3\3\3\3"+
      "\3\3\3\3\3\3\3\3\7\3\31\n\3\f\3\16\3\34\13\3\3\3\2\3\4\4\2\4\2\2\37"+
      "\2\6\3\2\2\2\4\20\3\2\2\2\6\7\5\4\3\2\7\b\7\2\2\3\b\3\3\2\2\2\t\n"+
      "\b\3\1\2\n\21\7\5\2\2\13\21\7\6\2\2\f\r\7\3\2\2\r\16\5\4\3\2\16\17"+
      "\7\4\2\2\17\21\3\2\2\2\20\t\3\2\2\2\20\13\3\2\2\2\20\f\3\2\2\2\21"+
      "\32\3\2\2\2\22\23\f\7\2\2\23\24\7\7\2\2\24\31\5\4\3\b\25\26\f\6\2"+
      "\2\26\27\7\b\2\2\27\31\5\4\3\7\30\22\3\2\2\2\30\25\3\2\2\2\31\34\3"+
      "\2\2\2\32\30\3\2\2\2\32\33\3\2\2\2\33\5\3\2\2\2\34\32\3\2\2\2\5\20"+
      "\30\32";
  public static final ATN _ATN =
    new ATNDeserializer().deserialize(_serializedATN.toCharArray());
  static {
    _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }
}