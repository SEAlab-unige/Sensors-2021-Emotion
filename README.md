# Emotion Recognition on Edge Devices: Training and Deployment
This repository concerns the deployment on smartphone of the paper **"Emotion Recognition on Edge Devices: Training and Deployment"** by Pandelea et al., [DOI](...)

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

The user can select one out of three quantization formats of Tiny-Bert: INT8, FLOAT16, FLOAT32.

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

**Emotion Recognition on Edge Devices: Training and Deployment.** V. Pandelea, E. Ragusa, T. Apicella, P. Gastaldo and E. Cambria. Sensors, 2021. [DOI](...)

    @article{,
      title={},
      author={},
      journal={},
      pages={},
      year={},
      publisher={}
    }
