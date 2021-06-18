# Linear separator application
## Requirements
Currently, the minimum SDK Version is 26. This indicates that smartphones with an Android version higher (or equal) than Android 8.0 (Oreo) can run the application.

## Description
This application is written in Kotlin.

The purpose of this Android application is to show the feasibility of the training process in the case of the linear separator described in the **paper**, considering the time to solve the optimization problem and retrieve the parameters.

The app generates randomly a data matrix and a labels vector (with the correct range of classes number) to recreate the conditions of a complex problem.
Throught the algorithm of mini-batch gradient descent the linear separator computes weights and bias of the solving hyperplane.

The user can select different parameters: number of features (columns of data matrix), number of samples (rows of data matrix), number of classes (depends on the addressed problem), number of epochs for gradient descent and finally the batch size. The eventual output of the application is the time, expressed in average and standard deviation, to solve the problem over 30 trials.

The main classes are:
* `MainActivity.kt`: this is the main activity of the application. In each one of the 30 trials it creates randomly the data matrix and labels vector, it instantiates the linear separator it retrieves the time to perform mini-batch gradient descent to get the hyperplane parameters. Finally it computes the average and the standard deviation of the time array.
* `Separator.kt`:
* `MathOps.kt`:

