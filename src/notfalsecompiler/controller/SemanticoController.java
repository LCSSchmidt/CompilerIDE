package notfalsecompiler.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import notfalsecompiler.flowcontroll.Expression;
import notfalsecompiler.flowcontroll.ScopeStack;
import notfalsecompiler.symbolTable.Symbol;

public class SemanticoController {

    public String name;
    public String type;
    public String scopeName;
    public int pos;
    public boolean isAssignment;
    public boolean isRelationalResolved;
    public boolean isExp;
    public boolean isDoubleVetOpering;
    public boolean isVetAttribution;
    public boolean isFuncCall;
    public boolean passedMultiplesParams;
    public String funcCallName;
    public ScopeStack scopeStack;
    public Stack<Expression> expression;
    public Stack<String> forLoopVars;
    public List<Symbol> symbols;
    public List<String> warnings;
    public List<String> errors;
    public int varTypeToAttribute;
    public int lastAction;
    public int funcCallParamIndex;
    public String actualVetVar;
    public String lastLexeme;
    public String lastScopeName;
    public String firstTokenAfterEqual;
    public String forLoopVar;
    public boolean flagOp;
    public boolean isDoWhile;
    public boolean isForLoopDecl;
//    public boolean isRelationalOp;
//    public boolean isExpression;
    
    // ################################ Vector Handler
//    public int vetPos;
    
    public SemanticoController() {
        ScopeStack.scopeNumber = 0;
        this.scopeName = "global" + ScopeStack.scopeNumber;
        ScopeStack.scopeNumber++;
        
        this.pos = -1;
        this.scopeStack = new ScopeStack();
        this.expression = new Stack<>();
        this.forLoopVars = new Stack<>();
        this.symbols = new ArrayList<>();
        this.warnings = new ArrayList<>();
        this.errors =  new ArrayList<>();
        
        this.varTypeToAttribute = -1;
        this.lastAction = -1;
        this.funcCallParamIndex = 1;
//        this.vetPos = -1;
        this.lastLexeme = "";
        this.lastScopeName = null;
        this.actualVetVar = null;
        this.funcCallName = null;
        this.forLoopVar = null;
        this.firstTokenAfterEqual = "";
        this.isExp = false;
        this.isRelationalResolved = false;
        this.isDoubleVetOpering = false;
        this.isVetAttribution = false;
        this.isForLoopDecl = false;
        this.isFuncCall = false;
//        this.isParamExp = false;
    }

}
