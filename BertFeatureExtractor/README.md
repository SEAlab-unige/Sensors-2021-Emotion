# Feature extractor application
## Requirements
Currently, the minimum SDK Version is 26. This indicates that smartphones with an Android version higher (or equal) than Android 8.0 (Oreo) can run the application. 

## Description
The purpose of this Android application is to show the feasibility of the inference phase in the case of the feature extractor described in the **paper**.

The user can select one out of three quantization formats of Tiny-Bert: *INT8*, *FLOAT16*, *FLOAT32*. Each model was converted leveraging post-training quantization process, through *TFLite* framework.

The app performs Tiny-Bert inference phase over 100 samples of the test set and it outputs the time expressed in average and standard deviation.

The most important directories are:
* `assets`: collection of 100 test phrases, Tiny-Bert models and vocaulary file.
* `ml`: holds java classes concerning Bert feature extractor model and input feature converter. Modified version of [Bert Question and Answer](https://www.tensorflow.org/lite/examples/bert_qa/overview).
* `tokenization`: holds classes to perform tokenization. This folder is taken from [Bert Question and Answer](https://www.tensorflow.org/lite/examples/bert_qa/overview).
* `ui`: holds MainActivity, which is the only activity of the application.
* `utils`: holds file and dataset load utilities.
