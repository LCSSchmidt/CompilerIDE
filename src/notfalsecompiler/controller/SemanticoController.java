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
    public ScopeStack scopeStack;
    public Stack<Expression> expression;
    public List<Symbol> symbols;
    public List<String> warnings;
    public List<String> errors;
    public int varTypeToAttribute;
    public int lastAction;
    public String actualVetVar;
    public String lastLexeme;
    public String firstTokenAfterEqual;
    public boolean flagOp;
//    public boolean isRelationalOp;
//    public boolean isExpression;
    
    // ################################ Vector Handler
    public int vetPos;
    
    public SemanticoController() {
        ScopeStack.scopeNumber = 0;
        this.scopeName = "global" + ScopeStack.scopeNumber;
        ScopeStack.scopeNumber++;
        
        this.pos = -1;
        this.scopeStack = new ScopeStack();
        this.expression = new Stack<>();
        this.symbols = new ArrayList<>();
        this.warnings = new ArrayList<>();
        this.errors =  new ArrayList<>();
        
        this.varTypeToAttribute = -1;
        this.lastAction = -1;
        this.vetPos = -1;
        this.lastLexeme = "";
        this.actualVetVar = "";
        this.firstTokenAfterEqual = "";
        this.isExp = false;
        this.isRelationalResolved = false;
        this.isDoubleVetOpering = false;
        this.isVetAttribution = false;
//        this.isParamExp = false;
    }

}
