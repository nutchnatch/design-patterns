package com.activemesa.behavioral.visitor.intrusive;

public class Demo {
    public static void main(String[] args) {
        // 1 + (2+3)
        final AdditionExpression add = new AdditionExpression(
                new DoubleExpression(1),
                new AdditionExpression(
                        new DoubleExpression(2),
                        new DoubleExpression(3)
                )
        );
        StringBuilder sb = new StringBuilder();
        ExpressionPrinter.print(add, sb);
        System.out.println(sb);
    }
}
