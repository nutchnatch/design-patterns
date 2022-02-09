package com.activemesa.behavioral.visitor.intrusive;

/**
 * With this interface we can extend the hierarchy and add more classes that can be visited
 */
interface ExpressionVisitor {
    void visit(DoubleExpression e);
    void visit(AdditionExpression e);
}


public abstract class Expression {
    /**
     * Any element in the hierarchy has to accept this visitor
     * @param visitor
     */
    public abstract void accept(ExpressionVisitor visitor);
}


class DoubleExpression extends Expression {
    public double value;

    public DoubleExpression(double value) {
        this.value = value;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Have to implements this method or others that are added to interface breaks the Open-Close principle
     * @param sb
     */
//    @Override
//    public void print(StringBuilder sb)
//    {
//        sb.append(value);
//    }
}

/**
 * So let's suppose I want to print this.
 * Another question is how do we go about adding functionality to every single element in the hierarchy because
 * here we have two elements.
 * Double expression and in addition expression we might have ten elements inheriting from expression.
 * And the question is we want to add the printing.
 * How do we do this.
 *
 * Now the first approach and the most simple approach is what's called an intrusive visitor and by intrusive
 * we mean that we're going to jump into classes we've already written and already tested maybe and we're
 * going to modify the entire hierarchy.
 *
 * Now this is something you typically want to avoid because it violates the open closed principle and
 * because it also violates the Single Responsibility Principle remember if we have a concern such as printing
 * for example we should probably be in a separate class not spread throughout the 10 20 however many classes
 * in the hierarchy.
 */
class AdditionExpression extends Expression {
    public Expression left, right;

    public AdditionExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

//    @Override
//    public void print(StringBuilder sb)
//    {
//        sb.append("(");
//        left.print(sb);
//        sb.append("+");
//        right.print(sb);
//        sb.append(")");
//    }
}

/**
 * Responsible for all printing
 */
class ExpressionPrinter implements ExpressionVisitor{
    private StringBuilder sb = new StringBuilder();

    /**
     * Reflective visitor
     * We will use reflection to identify which kind of expression we actually got
     * @param e
     * @param sb
     */
    public static void print(Expression e, StringBuilder sb) {
        //Here we are using reflection
        if(e.getClass() == DoubleExpression.class) {
            sb.append(((DoubleExpression)e).value);
        } else if(e.getClass() == AdditionExpression.class) {
            AdditionExpression ae = (AdditionExpression) e;
            sb.append("(");
            print(ae.left, sb);
            sb.append("+");
            print(ae.right, sb);
            sb.append(")");
        }
    }

    @Override
    public void visit(DoubleExpression e) {
        sb.append(e.value);
    }

    @Override
    public void visit(AdditionExpression e) {
        sb.append("(");
        e.left.accept(this);
        sb.append("+");
        e.right.accept(this);
        sb.append(")");
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}

class ExpressionCalculator implements ExpressionVisitor {

    public double result;

    @Override
    public void visit(DoubleExpression e) {
        result = e.value;
    }

    @Override
    public void visit(AdditionExpression e) {
        e.left.accept(this);
        double a = result;
        e.right .accept(this);
        double b = result;
        result =  a + b;
    }
}

class Demo2 {
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

        final ExpressionCalculator ec = new ExpressionCalculator();
        ec.visit(e);
        System.out.println(ep + " = " + ec.result);
    }
}