# TCSS360-PRJ02
TCSS 360 PRJ#02- Continuing the VantagePro2 development

This program simulates the VantagePro2's integrated sensor suite and GUI. It allows for the switching between imperial and metric
measurement as well as the abilitiy to disable sensor threads. Sensor data is updated at intervals determined by the documentation
(eg Anemometer updates every 3 seconds).

Data transfer of data values between sensors, ISS, and GUI is done via serialization files (named in the convention of "[Name]_S.txt").

Note: Due to an earlier push there is an accidental duplicate nesting scheme where the project folder TCSS360-PRJ02
is nested in a folder of the same name. The innermost folder named TCSS360-PRJ02 in Github should contain the functional project.
