package com.custom_nlp.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Verb implements Serializable {
    private String word;
    private String vb;
    private String vbd;
    private String vbg;
    private String vbn;
    private String vbp;
    private String vbz;
}
