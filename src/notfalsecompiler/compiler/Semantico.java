package notfalsecompiler.compiler;

import java.util.EmptyStackException;
import java.util.Iterator;
import notfalsecompiler.compiler.Constants;
import notfalsecompiler.compiler.SemanticError;
import notfalsecompiler.compiler.Token;
import notfalsecompiler.controller.SemanticoController;
import notfalsecompiler.flowcontroll.ScopeStack;
import notfalsecompiler.flowcontroll.SintaticResolver;
import notfalsecompiler.symbolTable.Symbol;
import notfalsecompiler.controller.Bipide;
import notfalsecompiler.flowcontroll.Expression;

public class Semantico extends SemanticoController implements Constants {

    public Bipide code = new Bipide();
    String newScopeName = null;

    public void executeAction(int action, Token token) throws SemanticError, Exception {
        String lexeme = token.getLexeme();
        Symbol sym;

        System.out.println("Ação #" + action + ", Token: " + token.getId() + " Lexema: " + token.getLexeme());

        try {
            switch (action) {
                case 1:
                    if (this.lastAction == 2) {
                        this.firstTokenAfterEqual = token.getLexeme();
                    }
                    break;
                case 3: //<TYPES>
                    System.out.println("Tipo: " + lexeme);
                    this.type = lexeme;
                    break;
                case 10: //<ID>
                    this.name = lexeme;
                    if (this.isAssignment) {
                        checkIsInitialized(this.name, this.scopeName);
                    }
                    if (this.type != null && this.pos == -1) {
                        insertSymbolTable(this.name, this.type, this.scopeName);
                    } else if (this.type != null) {
                        this.insertSymbolTableFuncVar(this.name, this.type, this.scopeName, this.pos);
                    }
                    if (this.isForLoopDecl) {
                        this.forLoopVars.push(lexeme);
//                        this.isForLoopDecl = false;
                    }
                    if (this.isExp) {
                        this.expression.peek().pushExp(getTypeFromLexeme(this.name));
                        if (this.expression.peek().isSysinOp && !this.isVet(lexeme)) {
                            this.code.LD("$in_port");
                            this.code.STO(lexeme);
                            break;
                        }
                        if (!this.flagOp) {
                            if (!this.isVet(lexeme)) {
                                this.code.LD(token.getLexeme());
                            } else {
                                this.expression.peek().vetPos++;
                            }
                        } else {
                            System.out.println("----------> Operator: " + this.expression.peek().peekOperator());
                            if (!this.isVet(lexeme)) {
                                if (!this.expression.peek().isRelationalOp) {
                                    //Sum
                                    if (0 == this.expression.peek().peekOperator()) {
                                        this.code.ADD(token.getLexeme());
                                    }
                                    //Sub
                                    if (1 == this.expression.peek().peekOperator()) {
                                        this.code.SUB(token.getLexeme());
                                    }
                                } else {
                                    this.genNormalRelationExp(lexeme, false);
                                }
                            } else {
                                if (!this.expression.peek().isLastOperandVet && !this.expression.peek().isRelationalOp) {
                                    System.out.println("---------------> Here");
                                    this.expression.peek().vetPos++;
                                    this.code.STO("100" + this.expression.peek().vetPos);
                                } else if (!this.expression.peek().isRelationalOp) {
                                    this.expression.peek().vetPos++;
                                }
                            }
                            System.out.println("--------------->Vet Pos: " + this.expression.peek().vetPos);
                            this.flagOp = false;
                        }
                    }
                    //else{                     if (this.isFuncCall && !this.isVet(lexeme)) {
                    //                        this.code.LD(lexeme);
                    //                        this.code.STO(this.funcCallName + "_" + this.getFuncParamLexem(this.funcCallName, this.funcCallParamIndex));
                    //                        this.funcCallParamIndex++;
                    //                    }
                    //}
                    break;
                case 11: //SEMICOLON
                    this.solveDoubleVetOpering();
                    if (this.isExp && !this.isRelationalResolved) {
                        if (this.expression.peek().validateExpression(this.varTypeToAttribute) == -1) {
                            throw new Exception("Semantic error, conversion of type not permitted");
                        }
                    }
                    if (this.isExp) {
                        if (!this.isVetAttribution && !this.expression.peek().isSysinOp && !this.isFuncCall) {
                            this.code.STO(this.expression.peek().varToAttr);
                        } else if (!this.expression.peek().isSysinOp) {
                            this.code.STO("1000");
                            this.code.LD("1002");
                            this.code.STO("$indr");
                            this.code.LD("1000");
                            this.code.STOV(this.expression.peek().varToAttr);
                        }
                    }
                    if (this.isForLoopDecl) {
                        this.code.addLabel("FOR_" + scopeStack.scopeNumber + "DECL");
                        this.isForLoopDecl = false;
                    }
//                    this.isExpression = false;
                    this.type = null;
                    this.isAssignment = false;
                    this.pos = -1;
                    this.isExp = false;
                    this.actualVetVar = null;
                    this.isVetAttribution = false;
                    if (this.expression.size() > 0) {
//                        this.expression.peek().isLastOperandVet = false;
                        this.expression.pop();
                    }
//                    this.varToAttribute = -1;
                    break;
                case 20: // INTEGER
                    System.out.println("Action 20 INTEGER: " + token.getLexeme());
                    if (this.isExp) {
                        this.expression.peek().isLastOperandVet = false;
                        if (this.expression.peek().isRelationalOp) {
                            this.genNormalRelationExp(lexeme, true);
                        }
                        if (!this.flagOp) {
                            if (this.isAssignment || this.type == null) {
                                this.code.LDI(token.getLexeme());
                            }
                            if (this.type != null && this.actualVetVar != null) {
                                this.symbols.get(this.symbols.size() - 1).setVetLength(Integer.parseInt(lexeme));
                            }
                        } else {
                            this.isRelationalResolved = false;
                            //Sum
                            if (0 == this.expression.peek().peekOperator()) {
                                this.code.ADDI(token.getLexeme());
                            }
                            //Sub
                            if (1 == this.expression.peek().peekOperator()) {
                                this.code.textInsert("SUBI", token.getLexeme());
                            }
                            this.flagOp = false;
                            System.out.println("--------------->Vet Pos: " + this.expression.peek().vetPos);
                        }
                    }
//                    if (this.isFuncCall && this.actualVetVar == null) {
//                        this.code.LD(lexeme);
//                        this.code.STO(this.funcCallName + "_" + this.getFuncParamLexem(this.funcCallName, this.funcCallParamIndex));
//                        this.funcCallParamIndex++;
//                    }
                case 21: // REAL
                case 22: // CARACTER
                case 23: // STRING
                case 28: // BOOL_TRUE
                    if (this.lastAction == 1) {
                        if (this.isExp) {
                            this.expression.peek().pushExp(SintaticResolver.getTypeNumber(SintaticResolver.symbolToAttrType(action)));
                        }
                    }
                    break;
//                case 29: 
//                    this.code.textInsert("STO", this.name);             
                case 25: // Start of relational link (while...).
                    if (lexeme.equals(";")) {
                        lexeme = "for";
                    }
                    newScopeName = lexeme + "_" + ScopeStack.scopeNumber;
                    if (lexeme.toUpperCase().equals("WHILE")) {
                        this.code.addLabel(newScopeName + "DECL");
                    }
                    if (this.lastAction == 13) {
                        this.code.removeLastLabel(this.lastScopeName);
                        this.code.JMP(newScopeName);
                        this.code.replaceJMPS(lastScopeName, newScopeName);
                        this.code.addLabel(this.lastScopeName);
                    }
                    this.isDoWhile = false;
                    this.scopeName = newScopeName;
                    ScopeStack.scopeNumber++;
                    this.isExp = true;
                    this.expression.push(new Expression());
                    this.expression.peek().vetPos = -1;
                    break;
                case 26: // End of relational link expressions.
                    if (!this.expression.peek().validateBooleanExpression()) {
                        throw new Exception("Semantic error: result of relational expression is not relational.");
                    } else {
                        this.expression.pop();
                        this.isRelationalResolved = true;
                    }
                    this.isExp = false;
                    this.isDoWhile = false;
                    this.isVetAttribution = false;
                    this.isAssignment = false;
                    this.actualVetVar = null;
                    this.scopeStack.push(this.scopeName);
                    break;
                case 32:
                    this.scopeStack.push(this.scopeName);
                    break;
                case 33:
                    newScopeName = lexeme + "_" + ScopeStack.scopeNumber;
                    this.isDoWhile = true;
                    this.scopeName = newScopeName;
                    this.scopeStack.push(this.scopeName);
                    this.code.addLabel(this.scopeName);
//                    this.expression.peek().vetPos = -1;
                    ScopeStack.scopeNumber++;
                    break;
                case 34:
                    this.isExp = true;
                    this.expression.push(new Expression());
                    this.expression.peek().vetPos = -1;
                    break;
                case 35:
                    this.isForLoopDecl = true;
                    break;
                case 36:
                    if (this.isExp) {
                        this.code.removeLastLine();
                    }
                    this.isFuncCall = true;
                    this.funcCallName = this.lastLexeme;
                    this.isExp = true;
                    this.expression.push(new Expression());
                    break;
                case 37:

                    this.solveDoubleVetOpering();
                    if (this.funcCallParamIndex == 1) {
                        if (this.isExp) {
                            if (this.expression.peek().validateExpression(this.getFuncParamType(this.funcCallName, this.funcCallParamIndex)) == -1) {
                                throw new Exception("Semantic error, conversion of type not permitted");
                            }
                            this.expression.pop();
                        }
                    }
                    if (this.expression.size() == 0) {
                        this.isExp = false;
                    }
                    this.code.STO(this.funcCallName + "_" + this.getFuncParamLexem(this.funcCallName, this.funcCallParamIndex));
                    this.code.CALL(this.funcCallName);
                    this.funcCallParamIndex = 1;
                    this.isFuncCall = false;
                    this.funcCallName = null;
                    break;
                case 38:

                    this.solveDoubleVetOpering();
                    if (this.isExp) {
                        if (this.expression.peek().validateExpression(this.getFuncParamType(this.funcCallName, this.funcCallParamIndex)) == -1) {
                            throw new Exception("Semantic error, conversion of type not permitted");
                        }
                        this.expression.pop();
                    }

                    this.code.STO(this.funcCallName + "_" + this.getFuncParamLexem(this.funcCallName, this.funcCallParamIndex));
                    this.funcCallParamIndex++;
                    this.actualVetVar = null;
                    this.expression.push(new Expression());
                case 27: // Vector
                    if (this.type != null) {
                        this.setVetLastVar();
                    }
                    if (this.expression.size() > 0 && this.expression.peek().isRelationalOp) {
                        this.code.STO("1002");
                    }
                    this.actualVetVar = this.lastLexeme;
                    this.expression.push(new Expression());
                    this.isExp = true;
                    this.expression.peek().vetPos++;
                    break;
                case 29:
                    Expression vetExp = this.expression.pop();
                    int vetExpResult = vetExp.validateExpression(this.getTypeFromLexeme(this.actualVetVar));

                    if (vetExpResult != -1) {
                        if (this.expression.size() == 0) {
                            if (this.isFuncCall) {
                                this.code.STO("$indr");
                                this.code.LDV(this.actualVetVar);
                                this.code.STOV(this.funcCallName + "_" + this.getFuncParamLexem(this.funcCallName, this.funcCallParamIndex));
                                this.funcCallParamIndex++;
                            }
                            this.isExp = false;
                        } else {
                            this.code.STO("$indr");
                            if (!this.expression.peek().isSysinOp) {
                                this.code.LDV(this.actualVetVar);
                                if (!this.expression.peek().isRelationalOp) {
                                    this.code.STO("100" + this.expression.peek().vetPos);
                                }
                            } else {
                                this.code.LD("$in_port");
                                this.code.STOV(this.actualVetVar);
                            }
//                            vetExp.saveLastVetOperation("STO", "$indr");
//                            vetExp.saveLastVetOperation("LDV", this.actualVetVar);
//                            vetExp.saveLastVetOperation("STO", "100" + this.vetPos);
                            if (this.expression.peek().isRelationalOp) {
                                this.code.STO("1001");
                                this.code.LD("1002");
                                this.code.SUB("1001");
                                this.genRelationExp(this.expression.peek().actualRelationalOp, this.scopeName.toUpperCase());
                                this.expression.peek().isRelationalOp = false;
                                this.expression.peek().actualRelationalOp = null;
                            }
                        }
//                        if (this.vetPos == 1) {
//                            this.vetPos = -1;
//                        }

                        if (this.expression.size() > 0) {
                            this.expression.peek().isLastOperandVet = true;
//                            this.code.concatBipideText(expression.peek().getSolvedOperations());
                        }
//                        this.code.concatBipideText(vetExp.getSolvedOperations());
                    } else {
                        throw new Exception("Semantic error: result of relational expression is not relational.");
                    }
                    break;
                case 30:
                    this.expression.push(new Expression());
                    this.expression.peek().isSysinOp = true;
                    this.varTypeToAttribute = SintaticResolver.getTypeNumber(lexeme);
                    this.isExp = true;
                    break;
                case 31:
                    this.expression.push(new Expression());
                    this.expression.peek().varToAttr = "$out_port";
                    this.varTypeToAttribute = SintaticResolver.getTypeNumber(lexeme);
                    this.isExp = true;
                    break;
                case 12: //OP_KEY
                    this.isRelationalResolved = false;
                    this.type = null;
                    this.pos = -1;
                    this.isExp = false;
                    break;
                case 13: //CL_KEY
                    try {
                        this.lastScopeName = this.scopeStack.pop();
                        if (this.lastScopeName.toUpperCase().contains("FOR")) {
                            this.forLoopVar = this.forLoopVars.pop();
                            this.code.LD(this.forLoopVar);
                            this.code.ADDI("1");
                            this.code.STO(this.forLoopVar);
                            this.code.JMP(lastScopeName + "DECL");
                            this.forLoopVar = null;
                        }
                        if (this.lastScopeName.toUpperCase().contains("WHILE") && !this.isDoWhile) {
                            this.code.JMP(lastScopeName + "DECL");
                        }
                        if (!this.isDoWhile && !this.isFunc(lastScopeName)) {
                            this.code.addLabel(this.lastScopeName);
                        } else if (this.isFunc(lastScopeName)) {
                            this.code.return0();
                        }
                        this.scopeName = this.scopeStack.peek();
                        this.isDoWhile = this.scopeName.toUpperCase().contains("DO");
                    } catch (EmptyStackException e) {
                        System.out.println("Pilha Vazia");
                        this.scopeName = "global0";
                    }
                    break;
                case 14: //<FUNC_BODY> ID

                    sym = symbols.get(symbols.size() - 1);
                    this.scopeName = this.name + "_" + ScopeStack.scopeNumber;

                    ScopeStack.scopeNumber++;
                    this.pos = 0;
                    sym.setFunc(true);
                    sym.setPos(this.pos);
                    this.pos++;
                    this.scopeStack.push(this.scopeName);
                    this.code.addLabel("_" + this.name);
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
                    this.isExp = true;
                    this.isAssignment = true;
                    this.expression.push(new Expression());
//                    this.expression.peek().vetPos = -1;
                    this.expression.peek().isLastOperandVet = false;
                    System.out.println("Actual Vet Var: " + actualVetVar);
                    if (this.actualVetVar == null) {
                        this.varTypeToAttribute = this.getTypeFromLexeme(this.lastLexeme);
                        this.expression.peek().varToAttr = this.lastLexeme;
                    } else {
                        this.isVetAttribution = true;
                        this.code.STO("1002");
                        this.varTypeToAttribute = this.getTypeFromLexeme(this.actualVetVar);
                        this.expression.peek().varToAttr = this.actualVetVar;
                    }
                    System.out.println("--------> Var to Attr: " + this.varTypeToAttribute);
                    break;
                case 4: //OP_REL
                    this.expression.peek().isRelationalOp = true;
                    this.expression.peek().firstRelationalOperand = this.lastLexeme;
                    if (lexeme.equals("==")) {
                        this.expression.peek().actualRelationalOp = "BNE";
                    } else if (lexeme.equals("<=")) {
                        this.expression.peek().actualRelationalOp = "BGT";
                    } else if (lexeme.equals(">=")) {
                        this.expression.peek().actualRelationalOp = "BLT";
                    } else if (lexeme.equals("<")) {
                        this.expression.peek().actualRelationalOp = "BGE";
                    } else if (lexeme.equals(">")) {
                        this.expression.peek().actualRelationalOp = "BLE";
                    }
                case 5: //OP_NEG
                case 7: //OP_ARIT_BAIXA
                    this.flagOp = true;
                    this.solveDoubleVetOpering();
                /*System.out.println("AKI" + this.name);
                 if (this.isExp) {
                 switch (token.getLexeme()) {
                 case "+":
                 //this.bipede += "\n\tADDI";
                 break;
                 case "-":
                                
                 }
                 }*/
                case 8: //OP_ARIT_ALTA
                case 15: //OR
                case 16: //AND
                case 17: //BIT_OR
                case 18: //BIT_XOR
                case 19: //BIT_AND
                    try {
//                        // Case is ID or a Primitive type.
//                        if (lastAction == 10 || lastAction == 3) {
//                            this.expStack.pushExp(getTypeFromLexeme(this.lastLexeme));
//                        }
                        this.expression.peek().pushOperator(SintaticResolver.getOperatorNumber(lexeme));
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
            throw e;
        }
    }

    private void solveDoubleVetOpering() {
        if (this.isExp && this.expression.peek().vetPos == 1) {
            this.code.LD("1000");
            if (0 == this.expression.peek().peekOperator()) {
                this.code.ADD("1001");
            } else if (1 == this.expression.peek().peekOperator()) {
                this.code.SUB("1001");
            }

            this.isDoubleVetOpering = false;
            this.expression.peek().vetPos = -1;
        }
    }

    private void setVetLastVar() {
        this.symbols.get(this.symbols.size() - 1).setVet(true);
    }

    private boolean isVet(String varLexem) throws Exception {
        Symbol next;
        String scope;

        if (varLexem.equals("")) {
            return false;
        }

        try {
            scope = this.scopeStack.peek();
        } catch (EmptyStackException e) {
            scope = "global0";
        }
        for (Iterator<Symbol> iterator = this.symbols.iterator(); iterator.hasNext();) {
            next = iterator.next();
//            System.out.println("Is vet:" + next.isVet());
//            System.out.println("Is vet Scope:" + next.getEscopo());
//            System.out.println("Is vet Id:" + next.getId());
//            
//            System.out.println("Scope:" + scope);
//            System.out.println("Id:" + varLexem);
            if (scope.equals(next.getEscopo()) && next.getId().equals(varLexem)) {
                return next.isVet();
            }
        }
//        throw new Exception("Is vet not found var with that signature");
        return false;
    }

    public static String getScopeName(String name) {
        int lastLocation = name.lastIndexOf("_");
        String toRemove = name.subSequence(lastLocation, name.length()).toString();
        name = name.replace(toRemove, "");

        return name;
    }

    private boolean isFunc(String name) throws Exception {
        Symbol next;

        name = Semantico.getScopeName(name);
        for (Iterator<Symbol> iterator = this.symbols.iterator(); iterator.hasNext();) {
            next = iterator.next();
//            System.out.println("Is vet:" + next.isVet());
//            System.out.println("Is vet Scope:" + next.getEscopo());
//            System.out.println("Is vet Id:" + next.getId());
//            
//            System.out.println("Scope:" + scope);
//            System.out.println("Id:" + varLexem);
            if (next.getId().contentEquals(name)) {
                return next.isFunc();
            }
        }
//        throw new Exception("Is vet not found var with that signature");
        return false;
    }

    private String getFuncParamLexem(String funcName, int index) {
        Symbol next;
        String scopeName;
        for (Iterator<Symbol> iterator = this.symbols.iterator(); iterator.hasNext();) {
            next = iterator.next();
            if (!next.getEscopo().toUpperCase().equals("GLOBAL0") && !next.isFunc()) {
                scopeName = Semantico.getScopeName(next.getEscopo());
            } else {
                scopeName = next.getEscopo();
            }

            if (scopeName.contentEquals(funcName) && next.getPos() == index) {
                return next.getId();
            }
        }
        return null;
    }

    private int getFuncParamType(String funcName, int index) {
        Symbol next;
        String varType;
        String scopeName;

        for (Iterator<Symbol> iterator = this.symbols.iterator(); iterator.hasNext();) {
            next = iterator.next();
            if (!next.getEscopo().toUpperCase().equals("GLOBAL0") && !next.isFunc()) {
                scopeName = Semantico.getScopeName(next.getEscopo());
            } else {
                scopeName = next.getEscopo();
            }

            if (scopeName.contentEquals(funcName) && next.getPos() == index) {
                return SintaticResolver.getTypeNumber(next.getTipo());
            }
        }
        return -1;
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
        throw new Exception("Error trying to get type, variable not declared");
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
                    System.out.println(this.warnings.get(0));
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

    private void genNormalRelationExp(String lexeme, boolean isConst) {
        this.code.STO("1000");
        if (isConst) {
            this.code.LDI(lexeme);
        } else {
            this.code.LD(lexeme);
        }
        this.code.STO("1001");
        this.code.LD("1000");
        this.code.SUB("1001");
        if (!this.isDoWhile) {
            this.genRelationExp(this.expression.peek().actualRelationalOp, this.scopeName.toUpperCase());
        } else {
            this.genRelationExp(this.expression.peek().actualRelationalOp, this.lastScopeName.toUpperCase());
        }
        this.expression.peek().isRelationalOp = false;
        this.expression.peek().actualRelationalOp = null;
    }

    private void genRelationExp(String mnemonico, String lexem) {
        switch (mnemonico.toUpperCase()) {
            case "BEQ":
                this.code.BEQ(lexem);
                break;
            case "BNE":
                this.code.BNE(lexem);
                break;
            case "BGT":
                this.code.BGT(lexem);
                break;
            case "BGE":
                this.code.BGE(lexem);
                break;
            case "BLT":
                this.code.BLT(lexem);
                break;
            case "BLE":
                this.code.BLE(lexem);
                break;
        }
    }
}
