package com.example.bertfeatureextractor.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

public class LoadDataset {
    private static final String TAG = "LoadDataset";
    private static final String JSON_DIR = "data/test_list_100.json";

    private String[] phrases;
    private int[] sentiments;
    private boolean empty;

    // Default constructor
    public LoadDataset(){
        phrases = null;
        sentiments = null;
        empty = true;
    }

    // Getters
    public String[] getPhrases() {
        return phrases;
    }

    public int[] getSentiments() {
        return sentiments;
    }

    public int getPhrasesNumber(){ return phrases.length; }

    public boolean isEmpty(){ return empty; }

    // Setters
    public void setPhrases(String[] phrases) {
        this.phrases = phrases;
    }

    public void setSentiments(int[] sentiments) {
        this.sentiments = sentiments;
    }

    public void loadJson(Context context) {
        try {
            InputStream is = context.getAssets().open(JSON_DIR);
            JsonReader reader = new JsonReader(new InputStreamReader(is));

            HashMap<String, List<String>> map = new Gson().fromJson(reader, HashMap.class);
            List<String> jsonPhrases = map.get("phrases");
            List<String> jsonClasses = map.get("sentiments");

            this.phrases = listToArray(jsonPhrases);
            this.sentiments = toIntegerArray(listToArray(jsonClasses));
            Log.v(TAG, "Dataset loaded.");
            this.empty = false;
        } catch (IOException ex) {
            Log.e(TAG, ex.toString());
        }
    }

    private String[] listToArray(List<String> list) {
        String[] answer = new String[list.size()];
        int index = 0;
        for (String item : list) {
            answer[index++] = item;
        }
        return answer;
    }

    private int[] toIntegerArray(String[] list){
        int[] answer = new int[list.length];
        int index = 0;
        for (String item : list) {
            answer[index++] = Integer.parseInt(item);
        }
        return answer;
    }

    public void close(){
        if (this.phrases != null){
            this.phrases = null;
        }
        if (this.sentiments != null){
            this.sentiments = null;
        }
        this.empty = true;
    }
}
