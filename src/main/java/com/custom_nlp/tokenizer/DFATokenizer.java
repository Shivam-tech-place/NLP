package com.custom_nlp.tokenizer;

import java.util.*;

public class DFATokenizer {

    enum State {
        START, IDENTIFIER, NUMBER, INVALID
    }

    public static List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        State currentState = State.START;

        for (int i = 0; i <= input.length(); i++) {
            char c = i < input.length() ? input.charAt(i) : ' '; // Sentinel whitespace at end

            switch (currentState) {
                case START:
                    if (Character.isLetter(c) || c == '_') {
                        buffer.append(c);
                        currentState = State.IDENTIFIER;
                    } else if (Character.isDigit(c)) {
                        buffer.append(c);
                        currentState = State.NUMBER;
                    } else if (!Character.isWhitespace(c)) {
                        // Skip punctuation and invalids here, or handle separately
                        tokens.add(Character.toString(c));
                    }
                    break;

                case IDENTIFIER:
                    if (Character.isLetterOrDigit(c) || c == '_') {
                        buffer.append(c);
                    } else {
                        tokens.add(buffer.toString());
                        buffer.setLength(0);
                        currentState = State.START;
                        i--; // Re-evaluate this char in START
                    }
                    break;

                case NUMBER:
                    if (Character.isDigit(c)) {
                        buffer.append(c);
                    } else {
                        tokens.add(buffer.toString());
                        buffer.setLength(0);
                        currentState = State.START;
                        i--; // Re-evaluate this char in START
                    }
                    break;

                default:
                    break;
            }
        }

        return tokens;
    }

    public static void main(String[] args) {
        String text = "int score_1 = 98; float average = 89.5;";
        List<String> tokens = tokenize(text);
        System.out.println("Tokens:");
        for (String token : tokens) {
            System.out.print(token+" | ");
        }
    }
}

