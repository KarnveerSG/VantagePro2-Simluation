##VantagePro2-Simulation

#Description

This program simulates the VantagePro2's integrated sensor suite and GUI. It allows for the switching between imperial and metric
measurement as well as the ability to disable sensor threads. Sensor data is updated at intervals determined by the documentation
(eg Anemometer updates every 3 seconds).

Data transfer of data values between sensors, ISS, and GUI is done via serialization files (named in the convention of "[Name]_S.txt").

#GUI

There are 3 components to the GUI: Compass, Rainfall chart, and the Main GUI. The compass and rainfall chart are not interactable, 
but Main GUI does. The Main GUI allows for conversion between metric and imperial and allows for individual sensors to be disabled. 


