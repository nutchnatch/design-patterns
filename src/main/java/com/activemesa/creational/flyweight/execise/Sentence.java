package com.activemesa.creational.flyweight.execise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sentence {

    private String[] words;
    private Map<Integer, WordToken> tokens = new HashMap<>();

    public Sentence(String plainText)
    {
        words = plainText.split(" ");
    }

    public WordToken getWord(int index)
    {
        WordToken wt = new WordToken();
        tokens.put(index, wt);
        return tokens.get(index);
    }

    @Override
    public String toString()
    {
        List<String> wList = new ArrayList<>();
        for(int i = 0; i < words.length; i ++) {
            String w = words[i];
            if(tokens.containsKey(i) && tokens.get(i).capitalize) {
                w = w.toUpperCase();
            }
            wList.add(w);
        }
        return String.join(" ",  wList);
    }

    class WordToken
    {
        public boolean capitalize;
    }
}
