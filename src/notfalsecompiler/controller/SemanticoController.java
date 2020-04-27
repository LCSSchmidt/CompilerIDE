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
    public ScopeStack scopeStack;
    public ExpressionStack expStack;
    public List<Symbol> symbols;
    public List<String> warnings;
    public List<String> errors;
    public int varToAttribute;
    public int lastAction;
    public String lastLexeme;
    public boolean isExp;
    
    public SemanticoController() {
        ScopeStack.scopeNumber = 0;
        this.scopeName = "global" + ScopeStack.scopeNumber;
        ScopeStack.scopeNumber++;
        
        this.pos = -1;
        scopeStack = new ScopeStack();
        expStack = new ExpressionStack();
        symbols = new ArrayList<>();
        warnings = new ArrayList<>();
        errors =  new ArrayList<>();
        
        varToAttribute = -1;
        lastAction = -1;
        lastLexeme = "";
        isExp = false;
    }

}
