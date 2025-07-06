package com.custom_nlp.vocab.creator;

import com.custom_nlp.entity.Alphabet;
import com.custom_nlp.entity.Verb;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class VocabCreator {

    public static void main(String[] args) throws IOException, CsvValidationException {
        VocabCreator cv = new VocabCreator();
        String filepath = "/home/billion/Downloads/names/americanNames.txt";
        String binfilepath = "/home/billion/Downloads/names/americanNames.bin";
        cv.indianName(filepath, binfilepath);
    }

    public void indianName(String vocabFilePath, String binFilePath){
        try {
            // Read all text from the file
            String content = new String(Files.readAllBytes(Paths.get(vocabFilePath)), "UTF-8");

            // Convert string to bytes
            byte[] bytes = content.getBytes("UTF-8");

            // Write bytes to binary file
            try (FileOutputStream fos = new FileOutputStream(binFilePath)) {
                fos.write(bytes);
            }

            System.out.println("Binary file created successfully: " + binFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void americanName(String vocabFilePath, String binFilePath){

    }

    public void verbVocab(String vocabFilePath, String binFilePath) throws IOException, CsvValidationException {
        try {

            // Read the entire file into String
            String json = new String(Files.readAllBytes(Paths.get(vocabFilePath)));

            // Replace NaN with null
            json = json.replace("NaN", "null");

            ObjectMapper mapper = new ObjectMapper();
            List<Verb> entries = mapper.readValue(
                    json,
                    new TypeReference<List<Verb>>() {}
            );

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(binFilePath))) {
                oos.writeObject(entries);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void contractionVocab(String vocabFilePath, String binFilePath) throws IOException, CsvValidationException {
        try {

            // Read the entire file into String
            String json = new String(Files.readAllBytes(Paths.get(vocabFilePath)));

            // Replace NaN with null
            json = json.replace("NaN", "null");

            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, String> entries = mapper.readValue(
                    json,
                    new TypeReference<HashMap<String, String>>() {}
            );

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(binFilePath))) {
                oos.writeObject(entries);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void alphabetVocab(String vocabFilePath, String binFilePath) throws IOException, CsvValidationException {
        try {

            // Read the entire file into String
            String json = new String(Files.readAllBytes(Paths.get(vocabFilePath)));

            // Replace NaN with null
            json = json.replace("NaN", "null");

            ObjectMapper mapper = new ObjectMapper();
            List<Alphabet> entries = mapper.readValue(
                    json,
                    new TypeReference<List<Alphabet>>() {}
            );

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(binFilePath))) {
                oos.writeObject(entries);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
