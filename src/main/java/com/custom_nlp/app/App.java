package com.custom_nlp.app;

import com.custom_nlp.entity.TokenInfo;
import com.custom_nlp.tokenizer.WordTokenizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        List<String> list = new ArrayList<>();
        list.add("Hello, world! This is NLP's finest hour.");
        list.add("E.g., Dr. Smith arrived at 10:30 a.m. on Jan. 3rd, 2022.");
        list.add("Check this out: https://openai.com and email me at test@example.com.");
        list.add("I can't believe it's already 7 o'clock — time flies!");
        list.add("RT @elonmusk: AI > humans? 🤖🔥 #FutureTalk");
        list.add("Price dropped from $1,299.99 to $999.50 in 24hrs!");
        list.add("--Hello!! Ready-to-go? Well...maybe.");
        list.add("<p>This is bold.</p> But why so serious? 😐");
        list.add("¡Hola! Je t’aime beaucoup. こんにちは、世界！");
        list.add("Mr. & Mrs. D’Souza's 25th-anniversary bash was lit! 🎉");
        WordTokenizer wt = new WordTokenizer();
//        for(String s : list){
//            for (TokenInfo token: wt.tokenize(s)){
//                System.out.print(token.getToken().toString()+" , ");
//            }
//            System.out.println();
//        }

        for (TokenInfo token: wt.tokenize("<p>This is bold.</p> But why so serious? 😐")){
            System.out.println(token.getToken()+" -- "+ token.getPOS());
        }
    }
}
