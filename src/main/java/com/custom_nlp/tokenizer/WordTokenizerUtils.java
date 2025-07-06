package com.custom_nlp.tokenizer;

import com.custom_nlp.entity.TokenInfo;
import com.custom_nlp.vocab.loader.VocabLoader;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordTokenizerUtils {
    private static final String onlyAlpahbetsMatcher = "^[a-zA-Z]+$";
    private static final Pattern urlPattern = Pattern.compile("\\b(?:https?://)?(?:www\\.)?[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}\\b");
    private static final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,}$");
    private static final Pattern hashtagPattern = Pattern.compile("#\\w+");
    private static final Pattern numberPattern = Pattern.compile("^\\d+(\\.\\d+)?$");
    private static final Pattern specialCharPrefixSuffixPattern = Pattern.compile("\\s+");
    private static final Pattern dateTimePattern = Pattern.compile("\\b(?:\\d{1,2}[-/]\\d{1,2}[-/]\\d{2,4}|\\d{4}[-/]\\d{1,2}[-/]\\d{1,2}|(?:\\d{1,2}\\s)?(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)[a-z]*[.,]?\\s\\d{2,4})(?:\\s+[01]?\\d:[0-5]\\d(?::[0-5]\\d)?(?:\\s?[APap][Mm])?)?\\b");

    private List<Pattern> customRules = new ArrayList<>();



    private VocabLoader vocabLoader = null;

    public WordTokenizerUtils(){
        vocabLoader = new VocabLoader();
    }

    String markDateAndTime(String text, Map<String, String> dateTimeMap){
        Matcher dateTimeMatcher = dateTimePattern.matcher(text);
        int dtCounter = 0;

        // Step 1: Replace date/time with placeholders
        while (dateTimeMatcher.find()) {
            String match = dateTimeMatcher.group();
            String placeholder = "__DATETIME" + dtCounter++ + "__";
            dateTimeMap.put(placeholder, match);
            text = text.replace(match, placeholder);
        }
        return text;
    }

    void ruleBasedWordMatching(String token, TokenInfo tokenInfo) throws IOException {
        if (token.matches("^[a-zA-Z]+$")) {
            tokenInfo.setToken(token);
            tokenInfo.setTokenize(true);
            tokenInfo.addType(TokenInfo.Type.ALPHABET);
        } else if (numberPattern.matcher(token).matches()) {
            tokenInfo.setToken(token);
            tokenInfo.setTokenize(true);
            tokenInfo.addType(TokenInfo.Type.NUMBER);
        } else if (emailPattern.matcher(token).matches()) {
            tokenInfo.setToken(token);
            tokenInfo.setTokenize(true);
            tokenInfo.addType(TokenInfo.Type.EMAIL);
        } else if (urlPattern.matcher(token).matches()) {
            tokenInfo.setToken(token);
            tokenInfo.setTokenize(true);
            tokenInfo.addType(TokenInfo.Type.URL);
        } else if (hashtagPattern.matcher(token).matches()) {
            tokenInfo.setToken(token);
            tokenInfo.setTokenize(true);
            tokenInfo.addType(TokenInfo.Type.HASHTAG);
        } else if (checkContractionAndAbbr(token)) {
            tokenInfo.setToken(token);
            tokenInfo.setTokenize(true);
            tokenInfo.setAbbr(true);
            tokenInfo.addType(TokenInfo.Type.CONTRACTION_ABBR);
        }
    }


    String removeEmoji(String text){
        return EmojiParser.removeAllEmojis(text);
    }
    
    boolean isPunctuation(char ch) {
        return "!.,;:()[]{}\"'?".indexOf(ch) >= 0;
    }

    String normalizeWhitespace(String input) {
        return input.replaceAll("[\\s\\u00A0\\u1680\\u180E\\u2000-\\u200A\\u202F\\u205F\\u3000]+", " ").trim().toLowerCase();
    }

    String normalize(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFKC);
    }

    String decodeHtmlEntities(String input) {
        return StringEscapeUtils.unescapeHtml4(input);
    }

    boolean checkContractionAndAbbr(String token) throws IOException {
        HashMap<String, String> contractionData = vocabLoader.loadContractionDataSet();
        if(contractionData.containsKey(token)){
            return true;
        }else{
            return false;
        }
    }

    //bugs are there -- <p>the -- < , p>this
    public List<String> seperatePrefixSuffixSpecialChar(String input) {
        List<String> tokens = new ArrayList<>();
        String[] rawTokens = input.split("\\s+");

        for (String token : rawTokens) {
            int start = 0;
            int end = token.length() - 1;

            // Extract prefix special chars
            while (start <= end && !Character.isLetterOrDigit(token.charAt(start))) {
                tokens.add(Character.toString(token.charAt(start)));
                start++;
            }

            // Extract suffix special chars
            while (end >= start && !Character.isLetterOrDigit(token.charAt(end))) {
                end--;
            }

            // Add core word (only if there's something left)
            if (start <= end) {
                tokens.add(token.substring(start, end + 1));
            }

            // Extract suffix (in reverse to preserve order)
            for (int i = end + 1; i < token.length(); i++) {
                tokens.add(Character.toString(token.charAt(i)));
            }
        }

        return tokens;
    }

}
