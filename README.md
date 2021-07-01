# Emotion Recognition on Edge Devices: Training and Deployment
This repository concerns the deployment on smartphone of the techniques described in **"Emotion Recognition on Edge Devices: Training and Deployment"** by Pandelea et al., [DOI](https://doi.org/10.3390/s21134496)

## Authors
* Vlad Pandelea: vlad.pandelea@ntu.edu.sg
* Edoardo Ragusa: edoardo.ragusa@edu.unige.it
* Tommaso Apicella: tommaso.apicella@edu.unige.it
* Paolo Gastaldo: paolo.gastaldo@unige.it
* Erik Cambria: cambria@ntu.edu.sg

## Table of contents
* [Feature extractor application](#feature-extractor-application)
* [Linear separator application](#linear-separator-application)
* [Reference](#reference)

## Feature extractor application
### Requirements
Currently, the minimum SDK Version is 26. This indicates that smartphones with an Android version higher (or equal) than Android 8.0 (Oreo) can run the application. 

### Description
The purpose of this Android application is to show the feasibility of the inference phase in the case of the feature extractor described in the **paper**.

The user can select one out of three quantization formats of Tiny-Bert: *INT8*, *FLOAT16*, *FLOAT32*. Each model was converted leveraging post-training quantization process, through *TFLite* framework.

The app performs Tiny-Bert inference phase over 100 samples of the test set and it outputs the time expressed in average and standard deviation.


## Linear separator application
### Requirements
Currently, the minimum SDK Version is 26. This indicates that smartphones with an Android version higher (or equal) than Android 8.0 (Oreo) can run the application. 

### Description
The purpose of this Android application is to show the feasibility of the training process in the case of the linear separator described in the **paper**, considering the time to solve the optimization problem and retrieve the parameters.

The app generates randomly a data matrix and a labels vector (with the correct range of classes number) to recreate the conditions of a complex problem.
Throught the algorithm of mini-batch gradient descent the linear separator computes weights and bias of the solving hyperplane.

The user can select different parameters: number of features (columns of data matrix), number of samples (rows of data matrix), number of classes (depends on the addressed problem), number of epochs for gradient descent and finally the batch size. The eventual output of the application is the time, expressed in average and standard deviation, to solve the problem over 30 trials.
 
## Reference
If you find the code or pre-trained models useful, please cite the following paper:

**Emotion Recognition on Edge Devices: Training and Deployment.** V. Pandelea, E. Ragusa, T. Apicella, P. Gastaldo and E. Cambria. Sensors, 2021. [DOI](https://doi.org/10.3390/s21134496)

    @Article{s21134496,
     AUTHOR = {Pandelea, Vlad and Ragusa, Edoardo and Apicella, Tommaso and Gastaldo, Paolo and Cambria, Erik},
     TITLE = {Emotion Recognition on Edge Devices: Training and Deployment},
     JOURNAL = {Sensors},
     VOLUME = {21},
     YEAR = {2021},
     NUMBER = {13},
     ARTICLE-NUMBER = {4496},
     URL = {https://www.mdpi.com/1424-8220/21/13/4496},
     ISSN = {1424-8220},
     ABSTRACT = {Emotion recognition, among other natural language processing tasks, has greatly benefited from the use of large transformer models. Deploying these models on resource-constrained devices, however, is a major challenge due to their computational cost. In this paper, we show that the combination of large transformers, as high-quality feature extractors, and simple hardware-friendly classifiers based on linear separators can achieve competitive performance while allowing real-time inference and fast training. Various solutions including batch and Online Sequential Learning are analyzed. Additionally, our experiments show that latency and performance can be further improved via dimensionality reduction and pre-training, respectively. The resulting system is implemented on two types of edge device, namely an edge accelerator and two smartphones.},
     DOI = {10.3390/s21134496}
    }
