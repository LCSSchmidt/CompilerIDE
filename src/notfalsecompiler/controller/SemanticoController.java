package notfalsecompiler.controller;

import java.util.ArrayList;
import java.util.List;
import notfalsecompiler.flowcontroll.ExpressionStack;
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
    public ScopeStack scopeStack;
    public ExpressionStack expStack;
    public List<Symbol> symbols;
    public List<String> warnings;
    public List<String> errors;
    public int varToAttribute;
    public int lastAction;
    public String lastLexeme;
    public String firstTokenAfterEqual;
    public boolean flagOp;
    public String oper;
    
    public SemanticoController() {
        ScopeStack.scopeNumber = 0;
        this.scopeName = "global" + ScopeStack.scopeNumber;
        ScopeStack.scopeNumber++;
        
        this.pos = -1;
        this.scopeStack = new ScopeStack();
        this.expStack = new ExpressionStack();
        this.symbols = new ArrayList<>();
        this.warnings = new ArrayList<>();
        this.errors =  new ArrayList<>();
        
        this.varToAttribute = -1;
        this.lastAction = -1;
        this.lastLexeme = "";
        this.firstTokenAfterEqual = "";
        this.isExp = false;
        this.isRelationalResolved = false;
    }

}
