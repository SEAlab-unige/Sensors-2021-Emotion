# Emotion Recognition on Edge Devices: Training and Deployment
This repository concerns the deployment on smartphone of the paper **"Emotion Recognition on Edge Devices: Training and Deployment"** by ... et al., [DOI](...)

## Authors
* Vlad Pandelea: vlad.pandelea@ntu.edu.sg
* Edoardo Ragusa: edoardo.ragusa@edu.unige.it
* Tommaso Apicella: tommaso.apicella@edu.unige.it
* Paolo Gastaldo: paolo.gastaldo@unige.it
* Erik Cambria: cambria@ntu.edu.sg

## Table of contents
* [Linear separator application](#linear-separator-application)
* [Reference](#reference)

## Linear separator application
### Requirements
Currently, the minimum SDK Version is 26. This indicates that smartphones with an Android version higher (or equal) than Android 8.0 (Oreo) can run the application. 

### Description
The purpose of this Android application is to show the feasibility of the training process in the case of the linear separator described in the **paper**, considering the time to solve the optimization problem and retrieve the parameters.

The app generates randomly a data matrix and a labels vector (with the correct range of classes number) to recreate the conditions of a complex problem
Throught the algorithm of mini-batch gradient descent the linear separator computes weights and bias of the solving hyperplane.

The user can select different parameters: number of features (columns of data matrix), number of samples (rows of data matrix), number of classes (depends on the addressed problem), number of epochs for gradient descent and finally the batch size. The eventual output of the application is the time, expressed in average ms and standard deviation, to solve the problem over 30 trials.
 
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
