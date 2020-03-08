package com.algo.offer;

import java.util.Stack;

public class StackTest {
    class ExpressionParseException extends RuntimeException {
    }

    enum Operator {
        PLUS(0), MINUS(0), MULTIPLY(1), DIVIDE(1),
        ;
        int priority;

        Operator(int priority) {
            this.priority = priority;
        }

        public static Operator of(char c) {
            switch (c) {
                case '+':
                    return PLUS;
                case '-':
                    return MINUS;
                case '*':
                    return MULTIPLY;
                case '/':
                    return DIVIDE;
            }
            return null;
        }
    }

    public double expressionEval(String expr) {
        if (expr == null) {
            throw new NullPointerException();
        }
        Stack<String> operandStack = new Stack<>();
        Stack<Operator> operatorStack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c >= '0' && c <= '9' || c == '.') {
                sb.append(c);
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                operandStack.push(sb.toString());
                sb.delete(0, sb.length());
                Operator cur = Operator.of(c);
                if (operatorStack.isEmpty() || operatorStack.peek().priority < cur.priority) {
                    operatorStack.push(cur);
                } else {
                    operandStack.push(eval(operandStack.pop(), operandStack.pop(), operatorStack.pop()));
                    operatorStack.push(cur);
                }
            } else {
                throw new ExpressionParseException();
            }
        }
        operandStack.push(sb.toString());
        while (!operatorStack.isEmpty()) {
            operandStack.push(eval(operandStack.pop(), operandStack.pop(), operatorStack.pop()));
        }
        return Double.valueOf(operandStack.pop());
    }

    private String eval(String op1, String op2, Operator pre) {
        switch (pre) {
            case PLUS:
                return String.valueOf((Double.valueOf(op1) + Double.valueOf(op2)));
            case MINUS:
                return String.valueOf((Double.valueOf(op2) - Double.valueOf(op1)));
            case MULTIPLY:
                return String.valueOf((Double.valueOf(op2) * Double.valueOf(op1)));
            case DIVIDE:
                return String.valueOf((Double.valueOf(op2) / Double.valueOf(op1)));
        }
        return null;
    }

    public static void main(String[] args) {
        StackTest test = new StackTest();
    }
}
