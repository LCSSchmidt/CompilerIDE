package notfalsecompiler.compiler;

import java.util.EmptyStackException;
import java.util.Iterator;
import notfalsecompiler.compiler.Constants;
import notfalsecompiler.compiler.SemanticError;
import notfalsecompiler.compiler.Token;
import notfalsecompiler.controller.SemanticoController;
import notfalsecompiler.flowcontroll.ExpressionStack;
import notfalsecompiler.flowcontroll.ScopeStack;
import notfalsecompiler.flowcontroll.SintaticResolver;
import notfalsecompiler.symbolTable.Symbol;

public class Semantico extends SemanticoController implements Constants {

    public void executeAction(int action, Token token) throws SemanticError {
        String lexeme = token.getLexeme();

        System.out.println("Ação #" + action + ", Token: " + token.getId() + " Lexema: " + token.getLexeme());
        try {
            switch (action) {
                case 3: //<TYPES>
                    System.out.println("Tipo: " + lexeme);
                    this.type = lexeme;
                    break;
                case 10: //<ID>
                    System.out.println("Nome: " + lexeme);
                    this.name = lexeme;
                    if (this.isAssignment) {
                        checkIsInitialized(this.name, this.scopeName);
                    }
                    if (this.type != null && this.pos == -1) {
                        insertSymbolTable(this.name, this.type, this.scopeName);
                    } else if (this.type != null) {
                        this.insertSymbolTableFuncVar(this.name, this.type, this.scopeName, this.pos);
                    }
                    if (this.isExp) {
                        this.expStack.pushExp(getTypeFromLexeme(this.name));
                    }
                    break;
                case 11: //SEMICOLON
                    if (this.isExp && !this.isRelationalResolved) {
                        System.out.println("On Semicolon, result of expression is: " + this.expStack.validateExpression(this.varToAttribute));
                    }

                    this.type = null;
                    this.isAssignment = false;
                    this.pos = -1;
                    this.isExp = false;
                    break;
                case 20: // INTEGER
                case 21: // REAL
                case 22: // CARACTER
                case 23: // STRING
                case 24: // BOOL
                    if (this.lastAction == 1) {
                        if (this.isExp) {
                            this.expStack.pushExp(SintaticResolver.getTypeNumber(SintaticResolver.symbolToAttrType(action)));
                        }
                    }
                    break;
                case 25: // Start of relational link (while...).
                    this.isExp = true;
                    break;
                case 26: // End of relational link expressions.
                    if (!this.expStack.validateBooleanExpression()) {
                        throw new Exception("Semantic error: result of relational expression is not relational.");
                    } else {
                        this.isRelationalResolved = true;
                    }
                    break;
                case 12: //OP_KEY
                    this.isRelationalResolved = false;
                    this.type = null;
                    this.pos = -1;
                    break;
                case 13: //CL_KEY
                    try {
                        this.scopeStack.pop();
                        this.scopeName = this.scopeStack.peek();
                    } catch (EmptyStackException e) {
                        System.out.println("Pilha Vazia");
                        this.scopeName = "global0";
                    }
                    break;
                case 14: //<FUNC_BODY> ID

                    Symbol sym = symbols.get(symbols.size() - 1);
                    this.scopeName = this.name + ScopeStack.scopeNumber;

                    ScopeStack.scopeNumber++;
                    this.pos = 0;
                    sym.setFunc(true);
                    sym.setPos(this.pos);
                    this.pos++;
                    this.scopeStack.push(this.scopeName);
                    break;
                case 2: //<ASSIGN_TYPE>
                    Symbol next;
                    String escopo;
                    for (Iterator<Symbol> iterator = this.symbols.iterator(); iterator.hasNext();) {
                        next = iterator.next();
                        try {
                            escopo = this.scopeStack.peek();
                        } catch (EmptyStackException e) {
                            escopo = "global0";
                        }
                        if (escopo.equals(next.getEscopo()) && next.getId().equals(this.name)) {
                            next.setIni(true);
                        }
                    }

                    this.isAssignment = true;
                    this.varToAttribute = this.getTypeFromLexeme(this.lastLexeme);
                    break;
                case 4: //OP_REL
                case 5: //OP_NEG
                case 7: //OP_ARIT_BAIXA
                case 8: //OP_ARIT_ALTA
                case 15: //OR
                case 16: //AND
                case 17: //BIT_OR
                case 18: //BIT_XOR
                case 19: //BIT_AND
                    try {

                        // Case is ID or a Primitive type.
                        if (lastAction == 10 || lastAction == 3) {
                            this.expStack.pushExp(getTypeFromLexeme(this.lastLexeme));
                        }
                        this.expStack.pushOperator(SintaticResolver.getOperatorNumber(lexeme));
                        this.isExp = true;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
            }
            this.lastAction = action;
            this.lastLexeme = lexeme;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private int getTypeFromLexeme(String lexem) throws Exception {
        Symbol actualSymbol = null;

        try {
            for (int i = symbols.size() - 1; i >= 0; i--) {
                actualSymbol = symbols.get(i);

                if (actualSymbol.getId().equals(lexem)) {
                    return SintaticResolver.getTypeNumber(actualSymbol.getTipo());
                }
            }
        } catch (Exception e) {
            System.out.println("Error on getTypeFromLexem: " + e.getMessage());
        }
        throw new Exception("Lexem not found in symbol table");
    }

    private void checkIsInitialized(String name, String scope) {
        Symbol next;
        String escopo;

        try {
            escopo = this.scopeStack.peek();
        } catch (EmptyStackException e) {
            escopo = "global0";
        }
        for (Iterator<Symbol> iterator = this.symbols.iterator(); iterator.hasNext();) {
            next = iterator.next();
            if (escopo.equals(next.getEscopo()) && next.getId().equals(this.name)) {
                if (!next.isIni()) {
                    this.warnings.add("A variável " + name + " é usada e não foi inicializada");
                }
            }
        }
    }

    private void insertSymbolTable(String name, String type, String scope) {
        Symbol next;
        String escopo;
        for (Iterator<Symbol> iterator = this.symbols.iterator(); iterator.hasNext();) {
            next = iterator.next();
            try {
                escopo = this.scopeStack.peek();
            } catch (EmptyStackException e) {
                escopo = "global0";
            }
            System.out.println("ID: " + next.getId() + " Escopo: " + next.getEscopo());
            if (escopo.equals(next.getEscopo()) && next.getId().equals(this.name)) {
                this.warnings.add("A variável " + name + " já foi declarada");
                return;
            }
        }
        Symbol sym = new Symbol(name, type, scope);
        symbols.add(sym);
    }

    private void insertSymbolTableFuncVar(String name, String type, String scopeName, int pos) {
        Symbol sym = new Symbol(name, type, scopeName, pos);
        symbols.add(sym);
        this.pos++;
    }
}
