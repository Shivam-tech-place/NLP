package com.custom_nlp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Alphabet implements Serializable {
    private String word;
    @JsonProperty("wn_pos")
    private String pos;
    @JsonProperty("definitions")
    private String defination;
    @JsonProperty("examples")
    private String example;
    @JsonProperty("lemmas")
    private String lemma;
}
