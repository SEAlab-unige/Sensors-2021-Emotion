package com.example.bertapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bertapp.R;
import com.example.bertapp.ml.BertSentiment;
import com.example.bertapp.ml.LoadDataset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.bertapp.utils.FileUtils.retrieveFileNames;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    // Constants
    private static final String EMPTY_TEXT = "                      ";
    private static final String MODEL_DIR = "model";

    // Private variables
    private BertSentiment bertSentiment;
    private LoadDataset loadDataset;

    // Widget
    private Spinner spn_modelName;
    private TextView txtv_mean;
    private TextView txtv_stdDev;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link widgets to XML
        spn_modelName = (Spinner) findViewById(R.id.spinner_model_name);
        txtv_mean = (TextView) findViewById(R.id.text_view_mean);
        txtv_stdDev = (TextView) findViewById(R.id.text_view_standard_deviation);

        // Load models name in spinner
        setUpModelSpinner(spn_modelName, MODEL_DIR);

        // Create bert model
        bertSentiment = new BertSentiment(this);

        // Load dataset
        loadDataset = new LoadDataset();
        loadDataset.loadJson(this);
        displayMessage("Dataset loaded: " + loadDataset.getPhrasesNumber() + " phrases");
    }

    private void setUpModelSpinner(Spinner spn_sel, String model_path) {
        // Spinner drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add(EMPTY_TEXT); // load at least empty category
        // Retrieve files in model_path
        try {
            String[] files_names = retrieveFileNames(getApplicationContext(), model_path);
            // Update categories
            for (String s : files_names) {
                if (s.contains(".tflite")) {
                    String spin_choice = s.substring(0, s.lastIndexOf("."));
                    categories.add(spin_choice);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Attaching data adapter to spinner
        spn_sel.setAdapter(dataAdapter);
        // Attaching listener
        spn_sel.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        if (!item.equals(EMPTY_TEXT)) {
            if (parent == spn_modelName) {
                bertSentiment.loadModel(selectModel(MODEL_DIR, item));
            }
            // Display TOAST
            String text = "Selected: " + item;
            displayMessage(text);
        }else{
            bertSentiment.close();
        }
        resetResults();
    }

    private String selectModel(String modelsDir, String modelName) {
        String filePath = null;
        try {
            String[] files_names = retrieveFileNames(getApplicationContext(), modelsDir);
            for (String f : files_names) {
                if (f.contains(modelName)) {
                    filePath = modelsDir + "/" + f;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bertSentiment != null){
            bertSentiment.close();
        }
        if (loadDataset != null){
            loadDataset.close();
        }
    }

    public void predictionLoop(View v){
        if (!(loadDataset.isEmpty()) && !(bertSentiment.isEmpty())){
            // Create timeList
            ArrayList<Long> timesList = new ArrayList<>();
            // Retrieve phrases
            String[] phrasesArray = loadDataset.getPhrases();
            // Inference loop
            for (int i = 0; i < loadDataset.getPhrasesNumber(); i++) {
                long beforeTime = SystemClock.elapsedRealtime();
                float[] pred = bertSentiment.predict(phrasesArray[i]);
                long afterTime = SystemClock.elapsedRealtime();
                timesList.add(afterTime - beforeTime);
            }
            // Display mean inference time
            txtv_mean.setText(String.format("%.3f ms", computeMean(timesList)));
            // Display standard deviation
            txtv_stdDev.setText(String.format("%.3f ms", computeStdDev(timesList)));
        }else{
            // Display message
            displayMessage("Dataset or model absent!");
        }
    }

    private void resetResults() {
        // Reset results widgets
        txtv_mean.setText(R.string.result_txtv);
        txtv_stdDev.setText(R.string.result_txtv);
    }
    private void resetSpinner() {
        spn_modelName.setSelection(0);
    }

    private void displayMessage(String text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    private float computeMean(ArrayList<Long> timesList) {
        float sum = 0.0f;
        int offset = 20;
        for (int i = offset; i < timesList.size(); i++) {
            sum += timesList.get(i);
        }
        return sum/(timesList.size()-offset);
    }

    private float computeStdDev(ArrayList<Long> timesList){
        float mean = computeMean(timesList);
        float std_dev = 0.0f;
        int offset = 20;

        for (int i = offset; i < timesList.size(); i++) {
            std_dev +=  Math.pow(timesList.get(i)-mean,2);
        }

        return (float)Math.sqrt(std_dev/(timesList.size()-offset));
    }
}
