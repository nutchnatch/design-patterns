package com.activemesa.behavioral.interpreter.lexing;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

interface Element {
    int eval();
}

class Integer implements Element {

    private int value;

    public Integer(int value) {
        this.value = value;
    }

    @Override
    public int eval() {
        return value;
    }
}

class BinaryOperation implements Element {

    public enum Type {
        ADDITION,
        SUBBTRACTION
    }

    public Type type;
    public Element left, right;

    @Override
    public int eval() {
        switch (type) {
            case ADDITION:
                return left.eval() + right.eval();
            case SUBBTRACTION:
                return left.eval() - right.eval();
            default:
                return 0;

        }
    }
}

class Token {
    public enum Type {
        INTEGER,
        PLUS,
        MINUS,
        LPAREN,
        RPAREN
    }

    public Type type;
    public String text;

    public Token(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return "`" + text + "´";
    }
}

public class Demo {
    static Element parse(List<Token> tokens) {
        final BinaryOperation result = new BinaryOperation();
        boolean haveLHS = false;

        for(int i = 0; i < tokens.size(); i ++) {
            final Token token = tokens.get(i);

            switch (token.type) {
                case PLUS:
                    result.type = BinaryOperation.Type.ADDITION;
                case MINUS:
                    result.type = BinaryOperation.Type.SUBBTRACTION;
                case LPAREN:
                    int j = 0; // location of the right parenthesis
                    for(; j < tokens.size(); j++) {
                        if(tokens.get(j).type == Token.Type.RPAREN) {
                            break;
                        }
                    }
                    final List<Token> subExpressions = tokens.stream()
                            .skip(i + 1)
                            .limit(j - i - 1) // maximum limit until when i go
                            .collect(Collectors.toList());
                    Element element = parse(subExpressions);
                    if(!haveLHS) {
                        result.left = element;
                        haveLHS = true;
                    } else {
                        result.right = element;
                    }
                    i = j;
                    break;
                case RPAREN:
                    break;
                case INTEGER:
                    final Integer integer = new Integer(java.lang.Integer.parseInt(token.text));
                    if(!haveLHS) {
                        result.left = integer;
                        haveLHS = true;
                    } else {
                        result.right = integer;
                        break;
                    }
            }
        }
        return result;
    }
    static List<Token> lex(String input) {
        final List<Token> result = new ArrayList<>();
        for(int i = 0; i < input.length(); ++i) {
            switch (input.charAt(i)) {
                case '+':
                    result.add(new Token(Token.Type.PLUS, "+"));
                    break;
                case '-':
                    result.add(new Token(Token.Type.MINUS, "-"));
                    break;
                case '(':
                    result.add(new Token(Token.Type.LPAREN, "("));
                    break;
                case ')':
                    result.add(new Token(Token.Type.RPAREN, ")"));
                    break;
                default:
                    final StringBuilder sb = new StringBuilder("" + input.charAt(i));
                    for(int j = i + 1; j < input.length(); ++j) {
                        if(Character.isDigit(input.charAt(j))) {
                            sb.append(input.charAt(j));
                            i++;
                        } else {
                            result.add(new Token(Token.Type.INTEGER, sb.toString()));
                            break;
                        }
                    }
                    break;
            }
        }
        return result;
    }
    public static void main(String[] args) {
        final String input = "(13+4)-(12+1)";
        List<Token> tokens = lex(input);
        System.out.println(tokens.stream()
                .map(Token::toString)
                .collect(Collectors.joining("\t"))
        );

        Element parsed = parse(tokens);
        System.out.println(input + " = " + parsed.eval());
    }
}
