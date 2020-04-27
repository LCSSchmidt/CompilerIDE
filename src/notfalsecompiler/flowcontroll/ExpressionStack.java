package notfalsecompiler.flowcontroll;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import notfalsecompiler.symbolTable.Symbol;

public class ExpressionStack {

    Stack<Integer> expStack;
    Stack<Integer> operatorStack;

    public ExpressionStack() {
        this.expStack = new Stack<>();
        this.operatorStack = new Stack<>();
    }

    public void pushExp(int lexem) {
        this.expStack.push(lexem);
    }

    public int popExp() throws EmptyStackException {
        try {
            return this.expStack.pop();
        } catch (EmptyStackException e) {
            throw e;
        }
    }

    public int peekExp() throws EmptyStackException {
        try {
            return this.expStack.peek();
        } catch (EmptyStackException e) {
            throw e;
        }
    }

    public void pushOperator(int lexem) {
        this.operatorStack.push(lexem);
    }

    public int popOperator() throws Exception {
        try {
            return this.operatorStack.pop();
        } catch (EmptyStackException e) {
            throw e;
        }
    }

    public int peekOperator() {
        try {
            return this.operatorStack.peek();
        } catch (EmptyStackException e) {
            throw e;
        }
    }

    public static int getTypeNumber(String lexem) {
        switch (lexem) {
            case "int":
                return SemanticTable.INT;
            case "float":
                return SemanticTable.FLO;
            case "char":
                return SemanticTable.CHA;
            case "bool":
                return SemanticTable.BOO;
            case "string":
                return SemanticTable.STR;
        }
        return -1;
    }

    public static int getOperatorNumber(String operator) {
        switch (operator) {
            case "+":
                return SemanticTable.SUM;
            case "-":
                return SemanticTable.SUB;
            case "/":
                return SemanticTable.DIV;
            case "*":
                return SemanticTable.MUL;
            case "&&":
            case "||":
            case "!":
            case ">":
            case "<":
            case ">=":
            case "<=":
            case "==":
            case "!=":
                return SemanticTable.REL;
        }
        return -1;
    }

    public int validateExpression(int varToAttribute) throws Exception {
        int operated_1 = -1;
        int operated_2 = -1;
        int operator = -1;

        try {
            while (expStack.size() != 1) {
                operated_1 = expStack.pop();
                operated_2 = expStack.pop();

                operator = operatorStack.pop();
                expStack.push(SemanticTable.resultType(operated_1, operated_2, operator));
            }

            return SemanticTable.atribType(varToAttribute, expStack.pop());
        } catch (Exception e) {
            throw e;
        }
    }
}
