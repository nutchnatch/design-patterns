package com.activemesa.creational.flyweight;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 * We want to capitalise particular part of this text
 */
public class FormattedText {

    private String plainText;

    /**
     * Thi approach is problematic because we are saving a boolean array for each text.
     * We will refactor this implementation with a more modular approach
     */
    private boolean[] capitalise;

    public FormattedText(String plainText) {
        this.plainText = plainText;
        this.capitalise = new boolean[plainText.length()];
    }

    public void capitalise(int start, int end) {
        for(int i = start; i <= end;  ++i) {
            capitalise[i] = true;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for(int i = 0; i < plainText.length(); i ++) {
            char c = plainText.charAt(i);
            sb.append(capitalise[i] ? Character.toUpperCase(c) : c);
        }
        return sb.toString();
    }
}

/**
 * This approach does not have an array of boolean which wastes memory, but have an elegant api for text formatting
 * using Flyweight pattern.
 */
class BetterFormattedText {
    private String plainText;
    private List<TextRange> formatting = new ArrayList<>();

    public BetterFormattedText(String plainText) {
        this.plainText = plainText;
    }

    public TextRange getRange(int start, int end) {
        final TextRange range = new TextRange(start, end);
        formatting.add(range);
        return range;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            for (TextRange range : formatting)
                if (range.covers(i) && range.capitalize) {
                    c = Character.toUpperCase(c);
                }
            sb.append(c);
        }
        return sb.toString();
    }

    public class TextRange {
        public int start, end;
        public boolean capitalize, bold, italic;

        public TextRange(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public boolean covers(int position) {
            return position >= start && position <= end;
        }
    }

}

class Demo2 {
    public static void main(String[] args) {
        final FormattedText ft = new FormattedText("This is a brave new world");
        ft.capitalise(10, 15);
        System.out.println(ft);

        final BetterFormattedText bft = new BetterFormattedText("Makes America great again.");
        bft.getRange(13, 18).capitalize = true;
        System.out.println(bft);
    }
}