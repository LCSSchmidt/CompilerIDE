package notfalsecompiler.controller;

import java.util.ArrayList;
import java.util.List;
import notfalsecompiler.flowcontroll.ScopeStack;
import notfalsecompiler.symbolTable.Symbol;

public class SemanticoController {
    public String name;
    public String type;
    public String scopeName;
    public int pos;
    public boolean isAssignment;
    public ScopeStack scopeStack;
    public List<Symbol> symbols = new ArrayList<>();
    public List<String> warnings = new ArrayList<>();
    public List<String> errors = new ArrayList<>();

    public SemanticoController() {
        this.scopeName = "global";
        scopeStack = new ScopeStack();
    }
    
}
