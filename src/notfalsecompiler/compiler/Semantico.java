package notfalsecompiler.compiler;

import java.util.EmptyStackException;
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
                if(this.type != null && this.pos == -1){
                    insertSymbolTable(this.name, this.type, this.scopeName);
                }else if(this.type != null){
                    this.insertSymbolTableFuncVar(this.name, this.type, this.scopeName, this.pos);
                }
                break;
            case 11:
                this.type = null;
                break;
            case 12: 
                this.type = null;
                this.pos = -1;
                break;
            case 13:
                try {
                    this.scopeStack.pop();
                    this.scopeName = this.scopeStack.peek();
                } catch (EmptyStackException e) {
                    System.out.println("Pilha Vazia");
                    this.scopeName = "global";
                }
                break;
            case 14:
                Symbol sym = symbols.get(symbols.size() - 1);
                this.scopeName = this.name;
                
                this.pos = 0;
                sym.setFunc(true);
                sym.setPos(this.pos);
                this.pos++;
                this.scopeStack.push(this.scopeName);
                break;
        }
    }	
    
    private void insertSymbolTable(String name, String type, String scope){
        Symbol sym = new Symbol(name, type, scope);
        symbols.add(sym);
    }
    
    private void insertSymbolTableFuncVar(String name, String type, String scopeName, int pos){
        Symbol sym = new Symbol(name, type, scopeName, pos);
        symbols.add(sym);
        this.pos++;
    }
}
