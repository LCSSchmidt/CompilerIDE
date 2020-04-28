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

    public boolean validateBooleanExpression() throws Exception {

        try {
            resolveExpressions();
            return expStack.pop() == 4;
        } catch (Exception e) {
            throw e;
        }
    }

    public int validateExpression(int varToAttribute) throws Exception {

        try {
            resolveExpressions();
            return SemanticTable.atribType(varToAttribute, expStack.pop());
        } catch (Exception e) {
            throw e;
        }
    }

    private void resolveExpressions() throws Exception {
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
        } catch (Exception e) {
            throw e;
        }
    }
}
