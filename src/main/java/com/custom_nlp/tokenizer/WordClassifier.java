package com.custom_nlp.tokenizer;

import com.custom_nlp.entity.TokenInfo;
import com.custom_nlp.vocab.loader.VocabLoader;

import java.io.IOException;
import java.util.List;

public class WordClassifier {

    private VocabLoader vocabLoader = null;

    public WordClassifier(){
        vocabLoader = new VocabLoader();
    }

    //name marking...
    //location marking...
    //pos marking... -- verb and alphabet marking
    //stop words...
    public void process(List<TokenInfo> tokens) throws IOException {
        boolean alpha = false;
        boolean verb = false;

        for (TokenInfo token : tokens){
            if(token.getType().contains(TokenInfo.Type.ALPHABET) || token.getType().contains(TokenInfo.Type.UN_RECOGNIZED)){
                if (vocabLoader.loadAlphabetDataSet().contains(token.getToken())) {
                    //load all the details of that alphabet into those words...
                    token.setAlphabet(vocabLoader.loadAlphabetDataSet().get(token.getToken()));
                    alpha = true;
                }
                if (vocabLoader.loadVerbDataSet().contains(token.getToken())){
                    //load all the details of that verb into those words...
                    token.setVerb(vocabLoader.loadVerbDataSet().get(token.getToken()));
                    verb = true;
                }

                if(!alpha && !verb){
                    if(vocabLoader.loadNameDataSet().contains(token.getToken())){
                        token.addType(TokenInfo.Type.NAME);
                    }
                }
            }
        }
    }
}
