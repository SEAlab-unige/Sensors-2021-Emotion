package com.example.bertapp.ml;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.CompatibilityList;
import org.tensorflow.lite.gpu.GpuDelegate;
import org.tensorflow.lite.nnapi.NnApiDelegate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

public class BertSentiment {
    // Constants
    private static final String TAG = "BertSentiment";
    private static String DIC_PATH = "vocab/vocab.txt";
    private static final int MAX_SEQ_LEN = 32;
    private static final boolean DO_LOWER_CASE = true;

    // Private variables
    private final Context context;
    private final Map<String, Integer> dic = new HashMap<>();
    private final FeatureConverter featureConverter;
    private Interpreter tflite;
    private boolean empty;
    private int H;
    private String modelName;

    // Constructor
    public BertSentiment(Context context) {
        this.context = context;
        this.featureConverter = new FeatureConverter(dic, DO_LOWER_CASE, MAX_SEQ_LEN);
        this.empty = true;
        this.H = 0;
        this.modelName = "";
    }

    // Getters
    public int[] getInputShape() {
        int index = 0;
        if (tflite != null) {
            return tflite.getInputTensor(index).shape();
        }
        return null;
    }

    public int[] getOutputShape() {
        int index = 0;
        if (tflite != null) {
            return tflite.getOutputTensor(index).shape();
        }
        return null;
    }

    public boolean isEmpty() {
        return empty;
    }

    // Methods
    public void loadModel(String modelPath){ //, boolean useGPU) {
        try {
            ByteBuffer buffer = loadModelFile(this.context.getAssets(),  modelPath);
            loadDictionary();
//            // Initialize interpreter with GPU delegate
//            Interpreter.Options options = new Interpreter.Options();
//            CompatibilityList compatList = new CompatibilityList();
//            NnApiDelegate nnApiDelegate = null;
//            // Initialize interpreter with NNAPI delegate for Android Pie or above
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && useGPU) {
//                nnApiDelegate = new NnApiDelegate();
//                options.addDelegate(nnApiDelegate);
//            }
//            tflite = new Interpreter(buffer, options);
            tflite = new Interpreter(buffer);
            DataType inputDataType = tflite.getInputTensor(0).dataType();
            DataType outputDataType = tflite.getOutputTensor(0).dataType();
            int[] inputShape = getInputShape();
            int[] outputShape = getOutputShape();
            this.H = getOutputShape()[2];
            this.modelName = modelPath;
            Log.v(TAG, "TFLite model loaded.");
            this.empty = false;
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage());
        }
    }


    public void loadDictionary() {
        try {
            loadDictionaryFile(this.context.getAssets().open(DIC_PATH));
            Log.v(TAG, "Dictionary loaded.");
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    public void close() {
        if (tflite != null) {
            tflite.close();
            tflite = null;
        }
        dic.clear();
        this.empty = true;
        this.modelName = "";
    }

    /**
     * Load tflite model from assets.
     */
    public MappedByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        try (AssetFileDescriptor fileDescriptor = assetManager.openFd(modelPath);
             FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor())) {
            FileChannel fileChannel = inputStream.getChannel();
            long startOffset = fileDescriptor.getStartOffset();
            long declaredLength = fileDescriptor.getDeclaredLength();
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
        }
    }

    /**
     * Load dictionary from assets.
     */
    public void loadDictionaryFile(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            int index = 0;
            while (reader.ready()) {
                String key = reader.readLine();
                dic.put(key, index++);
            }
        }
    }

    /**
     * Input: Original phrase. Later converted to Feature by
     * FeatureConverter. Output: A float array containing the logits.
     */
    public float[] predict(String phrase) {
        Log.v(TAG, "TFLite model: running...");
        Log.v(TAG, "Convert Feature...");
        Feature feature = featureConverter.convert(phrase);

        Log.v(TAG, "Set inputs...");
        int[][] inputIds = new int[1][MAX_SEQ_LEN];
        float[][][] endLogits = new float[1][MAX_SEQ_LEN][H];

        for (int j = 0; j < MAX_SEQ_LEN; j++) {
            inputIds[0][j] = feature.inputIds[j];
        }

        Log.v(TAG, "Run inference...");
        tflite.run(inputIds, endLogits);

        Log.v(TAG, "Finish.");

        return retrievePrediction(endLogits[0]);
    }

    private float[] retrievePrediction(float[][] modelOutput) {
        return modelOutput[0];
    }
}

