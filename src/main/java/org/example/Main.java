package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {

    private static final Set<String> allWords;
    private static final List<String> nineLetterWords;

    static {
        try {
            // I use static block, because these are final and need to be initialized here.
            allWords = loadAllWords();
            nineLetterWords = allWords.stream().filter(word -> word.length() == 9).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Set<String> correctWords = new LinkedHashSet<>();
        long start = System.currentTimeMillis();
        nineLetterWords.forEach(word -> {
            if (isCorrectWord(word)){
                correctWords.add(word);
            }
        });
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Time elapsed: " + timeElapsed + " ms");

        System.out.println("correctWords.size: " + correctWords.size());
    }

    private static boolean isCorrectWord(String word){
        if (word.length() == 1){
            if ("I".equals(word) || "A".equals(word)){
                return true;
            }
        }
        StringBuilder builder = new StringBuilder(word);
        for (int i = 0; i < word.length(); i++) {
            builder.deleteCharAt(i);
            String newWord = builder.toString();
            if (allWords.contains(newWord)){
                return isCorrectWord(newWord);
            }
            builder = new StringBuilder(word);
        }
        return false;
    }

    private static Set<String> loadAllWords() throws IOException {
        URL wordsUrl = new URL("https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(wordsUrl.openConnection().getInputStream()))){
            Set<String> words = reader.lines().skip(2).filter(word -> word.toUpperCase().contains("I")
                            || word.toUpperCase().contains("A"))
                    .collect(Collectors.toCollection((Supplier<Set<String>>) HashSet::new));
            // collect to HashSet, because search is very fast in this case O(1)

            // these are missing, so add them manually
            words.add("A");
            words.add("I");
            return words;
        }
    }
}