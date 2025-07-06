package com.custom_nlp.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class TokenInfo {

    // Enum declared inside the class
    public enum Type {
        ALPHABET,
        NUMBER,
        EMAIL,
        URL,
        HASHTAG,
        CONTRACTION_ABBR,
        DATETIME,
        UN_RECOGNIZED,
        NAME
    }

    private String token;
    private int startOffset;
    private int endOffset;
    private boolean contraction;
    private String expandContraction;
    private boolean tokenize;
    private boolean abbr;
    private Alphabet alphabet = null;
    private Verb verb = null;
    private String POS;
    private String lemma;


    private Set<TokenInfo.Type> type = null;

    public TokenInfo(){
        type = new HashSet<>();
    }

    public TokenInfo(String token, boolean contraction, boolean abbr, boolean tokenize){
        type = new HashSet<>();
        this.token = token;
        this.contraction = contraction;
        this.abbr = abbr;
        this.tokenize = tokenize;
    }

    public void addType(Type type){
        this.type.add(type);
    }

    public String getPOS(){
        if(type.size() > 0){
            for(Type t : type){
                this.POS = t.toString() + ", ";
            }
            if(verb != null){
                this.POS = verb.toString();
                this.lemma = verb.getVb();
            }
        }
        return this.POS;
    }
}
