package notfalsecompiler.compiler;

import java.util.List;
import notfalsecompiler.compiler.Constants;
import notfalsecompiler.compiler.SemanticError;
import notfalsecompiler.compiler.Token;
import notfalsecompiler.controller.SemanticoController;
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
                if(this.type != null){
                    insertSymbolTable();
                }
            break;
            case 11:
                this.type = null;
            break;
        
        }
    }	
    
    public void insertSymbolTable(){
        Symbol sym = new Symbol(this.name, this.type);
        symbols.add(sym);
    }
    
}
