package com.custom_nlp.tokenizer;

import com.custom_nlp.entity.TokenInfo;

import java.io.IOException;
import java.util.*;

public class WordTokenizer {

    private WordTokenizerUtils wordTokenizerUtils = new WordTokenizerUtils();
    private WordClassifier classifier = new WordClassifier();

    public List<TokenInfo> tokenize(String text) throws IOException {
        text = cleanUp(text);
        List<TokenInfo> list = wordSplitter(text);
        classifier.process(list);
        return list;
    }

    private List<TokenInfo> wordSplitter(String text) throws IOException {
        List<TokenInfo> list = new ArrayList<>();

        //date marking...
        Map<String, String> dateTimeMap = new HashMap<>();
        text = wordTokenizerUtils.markDateAndTime(text, dateTimeMap);

        // Step 2: Tokenize the modified text
        String[] tokens = text.split("\\s+");

        for (String token : tokens) {
            TokenInfo tokenInfo = new TokenInfo();

            // Step 3: Restore and tag datetime
            if (dateTimeMap.containsKey(token)) {
                tokenInfo.setToken(dateTimeMap.get(token));
                tokenInfo.setTokenize(true);
                tokenInfo.addType(TokenInfo.Type.DATETIME);
                list.add(tokenInfo);
                continue;
            }

            // Step 4: Matching known patterns
            wordTokenizerUtils.ruleBasedWordMatching(token, tokenInfo);

            if(!tokenInfo.isTokenize()){
                List<String> splitedWord = wordTokenizerUtils.seperatePrefixSuffixSpecialChar(token);

                if (splitedWord.size() > 0) {
                    for (String word : splitedWord){
                        list.add(new TokenInfo(word, false, false,true));
                    }
                    continue; // skip the current token since it's split
                }

                // Step 6: Fallback for unrecognized tokens
                tokenInfo.setToken(token);
                tokenInfo.setTokenize(true);
                tokenInfo.addType(TokenInfo.Type.UN_RECOGNIZED);
            }

            list.add(tokenInfo);
        }

        //re-iterate because special char seperator modified words
        for(TokenInfo token: list){
            wordTokenizerUtils.ruleBasedWordMatching(token.getToken(), token);
        }

        return list;
    }

    private String cleanUp(String text){
        text = wordTokenizerUtils.normalizeWhitespace(text);
        text = wordTokenizerUtils.normalize(text);
        text = wordTokenizerUtils.removeEmoji(text);
        text = wordTokenizerUtils.decodeHtmlEntities(text);
        return text;
    }
}

