# Emotion Recognition on Edge Devices: Training and Deployment
This repository concerns the deployment on smartphone of the paper **"Emotion Recognition on Edge Devices: Training and Deployment"** by ... et al., [DOI](...)

## Authors
* Vlad Pandelea: vlad.pandelea@ntu.edu.sg
* Edoardo Ragusa: edoardo.ragusa@edu.unige.it
* Tommaso Apicella: tommaso.apicella@edu.unige.it
* Paolo Gastaldo: paolo.gastaldo@unige.it
* Erik Cambria: cambria@ntu.edu.sg

## Table of contents
* [Android application](#android-application)
* [Reference](#reference)

## Android application
### Requirements
Currently, the app settings in `build.gradle` file are:
* compileSdkVersion 29
* minSdkVersion 19
* targetSdkVersion 29

The minimum SDK 19 indicates that smartphones with an Android version higher (or equal) than Android 4.4 (KitKat) can run the application. 

### Description
This demo enables to run the algorithm described in the **paper** on Android smartphone.\
User can select an image from the asset or from storage.\
The app can run inference using only the polarity classifier or using the saliency detector in combination with polarity classifier.\
For each performed inference phase the app displays:
* *Inference time*: time spent to perform forward phase.
* *Inference result*: eventual polarity output, "Positive" or "Negative".
* *Inference confidence*: confidence associated with the prediction. A number in [0, 1] range if the final polarity class is established by the entire image classifier, "---" otherwise. The rationale behind this is that the algorithm's operations can change the range and the meaning of the confidence value. For more details, please refer to the **paper**.

Each model of the **pythonProject** was converted to *TFLite* exploiting post-training quantization process for *FP32*, *FP16* and *INT8* formats. Models are available in the asset folder.\
The most important directories are:
* `assets`: collection of test image, saliency and polarity models, labels file.
* `utils`: holds java classes of algorithm, file and general utilities.
* `activity`: holds MainActivity, which is the only activity of the application.
* `classes`: holds java classes concerning the prediction, the polarity classifier and saliency detector.

## Reference
If you find the code or pre-trained models useful, please cite the following paper:

**Emotion Recognition on Edge Devices: Training and Deployment** ... [DOI](...)

    @article{,
      title={},
      author={},
      journal={},
      pages={},
      year={},
      publisher={}
    }
