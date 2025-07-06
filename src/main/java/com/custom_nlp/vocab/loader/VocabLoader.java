package com.custom_nlp.vocab.loader;

import com.custom_nlp.data_structure.MapArray;
import com.custom_nlp.entity.Alphabet;
import com.custom_nlp.entity.Verb;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class VocabLoader {
    private static HashMap<String, String> contractionDataSet = null;
    private static HashSet<String> names = null;
    private static MapArray<Verb> verbs = null;
    private static MapArray<Alphabet> words = null;

    public HashMap<String, String> loadContractionDataSet() throws IOException {
        if(contractionDataSet == null || contractionDataSet.isEmpty()){
            contractionDataSet = loadContractionVocab();
            return contractionDataSet;
        }else{
            return contractionDataSet;
        }
    }

    public HashSet<String> loadNameDataSet() throws IOException {
        if(names == null || names.isEmpty()){
            names = loadAmericanNames();
            names.addAll(loadIndianNames());
            return names;
        }else{
            return names;
        }
    }

    public MapArray<Verb> loadVerbDataSet() throws IOException {
        if(verbs == null){
            verbs = loadVerbVocab();
            return verbs;
        }else{
            return verbs;
        }
    }

    public MapArray<Alphabet> loadAlphabetDataSet() throws IOException {
        if(words == null){
            words = loadAplhabetVocab();
            return words;
        }else{
            return words;
        }
    }



    public HashSet<String> loadAmericanNames() throws IOException {
        HashSet<String> names = new HashSet<>();

        // Try to get the resource as InputStream
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("americanNames.bin")) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: IndianNames.bin");
            }

            // Read all bytes from the InputStream
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int nRead;
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();

            byte[] bytes = buffer.toByteArray();

            // Convert bytes back to String
            String content = new String(bytes, StandardCharsets.UTF_8);

            // Split the content into lines and add to HashSet
            String[] lines = content.split("\\R"); // \R matches any line break
            for (String line : lines) {
                String[] parts = line.trim().split("\\s+"); // split by any whitespace
                for(String part : parts){
                    names.add(part.toLowerCase());
                }
            }
        }
        return names;
    }

    public HashSet<String> loadIndianNames() throws IOException {
        HashSet<String> names = new HashSet<>();

        // Try to get the resource as InputStream
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("IndianNames.bin")) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: IndianNames.bin");
            }

            // Read all bytes from the InputStream
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int nRead;
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();

            byte[] bytes = buffer.toByteArray();

            // Convert bytes back to String
            String content = new String(bytes, StandardCharsets.UTF_8);

            // Split the content into lines and add to HashSet
            String[] lines = content.split("\\R"); // \R matches any line break
            for (String line : lines) {
                String[] parts = line.trim().split("\\s+"); // split by any whitespace
                for(String part : parts){
                    names.add(part.toLowerCase());
                }
            }
        }
        return names;
    }

    public HashMap<String, String> loadContractionVocab() throws IOException {
        HashMap<String, String> contration = new HashMap<>();
//        List<Verb> contractions = new ArrayList<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("contractions.bin")) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: alphabet.bin");
            }

            try (ObjectInputStream ois = new ObjectInputStream(is)) {
                contration = (HashMap<String, String>) ois.readObject();
            } catch (ClassNotFoundException e) {
                throw new IOException("Class not found while reading binary file", e);
            }
        }
        return contration;
    }

    public MapArray<Verb> loadVerbVocab() throws IOException {
        MapArray<Verb> map = new MapArray<>(1024);
        List<Verb> verbs = new ArrayList<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("verb.bin")) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: alphabet.bin");
            }

            try (ObjectInputStream ois = new ObjectInputStream(is)) {
                verbs = (List<Verb>) ois.readObject();
            } catch (ClassNotFoundException e) {
                throw new IOException("Class not found while reading binary file", e);
            }

            for (Verb verb : verbs){
                map.put(verb.getWord(), verb);
            }
        }
        return map;
    }

    public MapArray<Alphabet> loadAplhabetVocab() throws IOException {
        MapArray<Alphabet> map = new MapArray<>(1024);
        List<Alphabet> alphabets = new ArrayList<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("alphabet.bin")) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: alphabet.bin");
            }

            try (ObjectInputStream ois = new ObjectInputStream(is)) {
                alphabets = (List<Alphabet>) ois.readObject();
            } catch (ClassNotFoundException e) {
                throw new IOException("Class not found while reading binary file", e);
            }

            for (Alphabet alphabet : alphabets){
                map.put(alphabet.getWord(), alphabet);
            }
        }
        return map;
    }
}
