package notfalsecompiler.compiler;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import notfalsecompiler.compiler.Constants;
import notfalsecompiler.compiler.SemanticError;
import notfalsecompiler.compiler.Token;
import notfalsecompiler.controller.SemanticoController;
import notfalsecompiler.symbolTable.Symbol;

public class Semantico extends SemanticoController implements Constants
{
       
    public void executeAction(int action, Token token)	throws SemanticError
    {
        String lexeme = token.getLexeme();
        
        System.out.println("Ação #"+action+", Token: "+token.getId()+" Lexema: " + token.getLexeme());
        switch (action) {
            case 3: //<TYPES>
                System.out.println("Tipo: " + lexeme);
                this.type = lexeme;
                break;
            case 10: //<ID>
                System.out.println("Nome: " + lexeme);
                this.name = lexeme;
                if(this.isAssignment){
                    checkIsInitialized(this.name, this.scopeName);
                }
                if(this.type != null && this.pos == -1){
                    insertSymbolTable(this.name, this.type, this.scopeName);
                }else if(this.type != null){
                    this.insertSymbolTableFuncVar(this.name, this.type, this.scopeName, this.pos);
                }                
                break;
            case 11: // SEMICOLON
                this.type = null;
                this.isAssignment = false;
                this.pos = -1;
                break;
            case 12: // OP_KEY
                this.type = null;
                this.pos = -1;
                break;
            case 13: //<CL_KEY>
                try {
                    this.scopeStack.pop();
                    this.scopeName = this.scopeStack.peek();
                } catch (EmptyStackException e) {
                    System.out.println("Pilha Vazia");
                    this.scopeName = "global";
                }
                break;
            case 14: // <ID> PARAMS FUNCTION
                Symbol sym = symbols.get(symbols.size() - 1);
                this.scopeName = this.name;
                
                this.pos = 0;
                sym.setFunc(true);
                sym.setPos(this.pos);
                this.pos++;
                this.scopeStack.push(this.scopeName);
                break;
            case 2: // EQUAL - ASSIGN
                Symbol next;
                String escopo;
                for (Iterator<Symbol> iterator = this.symbols.iterator(); iterator.hasNext();) {
                    next = iterator.next();
                    try {
                        escopo = this.scopeStack.peek();
                    } catch (EmptyStackException e) {
                        escopo = "global";
                    }
                    if(escopo.equals(next.getEscopo()) && next.getId().equals(this.name)){
                        next.setIni(true);
                    }
                }
                
                this.isAssignment = true;
                
                break;
        }
    }	
    
    private void checkIsInitialized(String name, String scope){
        Symbol next;
        String escopo;
        for (Iterator<Symbol> iterator = this.symbols.iterator(); iterator.hasNext();) {
            next = iterator.next();
            try {
                escopo = this.scopeStack.peek();
            } catch (EmptyStackException e) {
                escopo = "global";
            }
            if(escopo.equals(next.getEscopo()) && next.getId().equals(this.name)){
                if(!next.isIni()){
                    this.warnings.add("A variável " + name + " é usada e não foi inicializada");
                }
            }
        }
    }
    
    private void insertSymbolTable(String name, String type, String scope){
        Symbol next;
        String escopo;
        for (Iterator<Symbol> iterator = this.symbols.iterator(); iterator.hasNext();) {
            next = iterator.next();
            try {
                escopo = this.scopeStack.peek();
            } catch (EmptyStackException e) {
                escopo = "global";
            }
            System.out.println("ID: " + next.getId() + " Escopo: " + next.getEscopo());
            if(escopo.equals(next.getEscopo()) && next.getId().equals(this.name)){
                this.warnings.add("A variável " + name + " já foi declarada");
                return;
            }
        }
        Symbol sym = new Symbol(name, type, scope);
        symbols.add(sym);
    }
    
    private void insertSymbolTableFuncVar(String name, String type, String scopeName, int pos){
        Symbol sym = new Symbol(name, type, scopeName, pos);
        symbols.add(sym);
        this.pos++;
    }
}
