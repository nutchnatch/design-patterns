package com.activemesa.behavioral.visitor.acyclic;


public interface Visitor { // marker interface, does not have any members at all
}

interface ExpressionVisitor extends Visitor {
    // applied for any kind of expression
    void visit(Expression obj);
}

interface DoubleExpressionVisitor extends Visitor {
    // applied for any kind of expression
    void visit(DoubleExpression obj);
}

interface AdditionExpressionVisitor extends Visitor {
    // applied for any kind of expression
    void visit(AdditionExpression obj);
}

abstract class Expression {
    /**
     * Here we can have this implementation like  a general implementation for this abstract class which can be
     * overridden by other classes.
     * @param visitor
     */
    void accept(Visitor visitor) {
        if(visitor instanceof ExpressionVisitor) {
            ((ExpressionVisitor)visitor).visit(this);
        }
    }
}

class DoubleExpression extends Expression {
    public double value;

    public DoubleExpression(double value) {
        this.value = value;
    }

    @Override
    void accept(Visitor visitor) {
        if(visitor instanceof DoubleExpressionVisitor) {
            ((DoubleExpressionVisitor)visitor).visit(this);
        }
    }
}

class AdditionExpression extends Expression {
    public Expression left, right;

    public AdditionExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    void accept(Visitor visitor) {
        if(visitor instanceof AdditionExpressionVisitor) {
            ((AdditionExpressionVisitor)visitor).visit(this);
        }
    }
}

/**
 * Here we have the flexibility to just implement only interfaces we are interested in. If a given interface
 * does not make sense, we just remove it and code still keep working.
 */
class ExpressionPrinter implements DoubleExpressionVisitor, AdditionExpressionVisitor {

    private StringBuilder sb = new StringBuilder();

    @Override
    public void visit(DoubleExpression obj) {
        sb.append(obj.value);
    }

    @Override
    public void visit(AdditionExpression obj) {
        sb.append('(');
        obj.left.accept(this);
        sb.append('+');
        obj.right.accept(this);
        sb.append(')');
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}

class Demo {
    public static void main(String[] args) {
        // 1 + (2+3)
        final AdditionExpression e = new AdditionExpression(
                new DoubleExpression(1),
                new AdditionExpression(
                        new DoubleExpression(2),
                        new DoubleExpression(3)
                )
        );

        final ExpressionPrinter ep = new ExpressionPrinter();
        ep.visit(e);
        System.out.println(ep);
    }
}
