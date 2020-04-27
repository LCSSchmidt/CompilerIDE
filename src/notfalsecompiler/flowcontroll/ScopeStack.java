package notfalsecompiler.flowcontroll;

import java.util.EmptyStackException;
import java.util.Stack;

public class ScopeStack {

    private Stack<String> scopeStack;
    public static int scopeNumber;

    public ScopeStack() {
        scopeStack = new Stack();
    }

    public ScopeStack(Stack<String> scopeStack) {
        this.scopeStack = scopeStack;
    }

    public void push(String scopeName) {
        this.scopeStack.push(scopeName);
    }

    public String pop() throws EmptyStackException {
        try {
            return this.scopeStack.pop();
        } catch (EmptyStackException e) {
            throw e;
        }
    }

    public String peek() throws EmptyStackException {
        try {
            return this.scopeStack.peek();
        } catch (EmptyStackException e) {
            throw e;
        }
    }
}
