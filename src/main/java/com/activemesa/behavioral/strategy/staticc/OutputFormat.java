package com.activemesa.behavioral.strategy.staticc;

import javax.xml.soap.Text;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public enum OutputFormat {

    MARKDOWN,
    HTML
}

interface ListStrategy {
    default void start(StringBuilder sb) {};
    void addListItem(StringBuilder sb, String item);
    default void end(StringBuilder sb) {};
}

class MarkdownListStrategy implements ListStrategy {

    @Override
    public void addListItem(StringBuilder sb, String item) {
        sb.append(" * ")
                .append(item)
                .append(System.lineSeparator());
    }
}

class HtmlListStrategy implements ListStrategy {

    @Override
    public void start(StringBuilder sb) {
        sb.append("<ul>").append(System.lineSeparator());
    }

    @Override
    public void addListItem(StringBuilder sb, String item) {
        sb.append( "<li>")
                .append(item)
                .append("</li>")
                .append(System.lineSeparator());
    }

    @Override
    public void end(StringBuilder sb) {
        sb.append("</ul>").append(System.lineSeparator());
    }
}

// In order to have the static approach, we have to transform this class in order to use generics
class TextProcessor<LS extends ListStrategy> {
    private StringBuilder sb = new StringBuilder();
    private LS listStrategy;

    public TextProcessor(Supplier<? extends LS> ctor) {
        listStrategy = ctor.get();
    }

    public void append(List<String> items) {
        listStrategy.start(sb);
        for(String item : items) {
            listStrategy.addListItem(sb, item);
        }
        listStrategy.end(sb);
    }

    public void clear() {
        sb.setLength(0);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}

class Demo {
    public static void main(String[] args) {
        TextProcessor<MarkdownListStrategy> tp = new TextProcessor<>(MarkdownListStrategy::new);
        tp.append(Arrays.asList("alpha", "beta", "gama"));
        System.out.println(tp);

        TextProcessor<HtmlListStrategy> tp2 = new TextProcessor<>(HtmlListStrategy::new);
        tp2.append(Arrays.asList("alpha", "beta", "gama"));
        System.out.println(tp2);
    }
}