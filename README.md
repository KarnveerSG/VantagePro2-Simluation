# VantagePro2-Simulation

## Description

This program simulates the VantagePro2's integrated sensor suite and GUI. It allows for the switching between imperial and metric
measurement as well as the ability to disable sensor threads. Sensor data is updated at intervals on their own threads determined by the documentation
(eg Anemometer updates every 3 seconds).

Data transfer of data values between sensors, ISS, and GUI is done via serialization files (named in the convention of "[Name]_S.txt").

## GUI

There are 3 components to the GUI: Compass, Rainfall chart, and the Main GUI. The compass and rainfall chart are not interactable, 
but Main GUI does. The Main GUI allows for conversion between metric and imperial and allows for individual sensors to be disabled. 

## Compass
![Compass](https://user-images.githubusercontent.com/46460325/104662978-d1c3ba00-5680-11eb-88c6-23a886eade84.PNG)

## Rainfall
![Rainfall](https://user-images.githubusercontent.com/46460325/104663082-194a4600-5681-11eb-95cf-049dcfc2cd43.PNG)

## Main GUI
This is where the metric/imperial switch is located for the user. 
![Metric Converter](https://user-images.githubusercontent.com/46460325/104663137-2f580680-5681-11eb-9a1d-939d67bf02a8.PNG)

This is where the sensors can be individually diabled for the user. 
![Disabled Sensor](https://user-images.githubusercontent.com/46460325/104663139-2ff09d00-5681-11eb-91ce-832c690c443a.PNG)
