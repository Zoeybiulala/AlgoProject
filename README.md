# AlgoProject
This folder is the original one reading the real data, with no location constraint, splitted timeslots
In order to run this project, you should run **run_main.sh** first. 
beforehand, you need to ensure there is a record.txt in your code sources file and which is empty
The source file in this situation is no-location-constraint-split-time

i.e.: 
1. check bmc_data to find the record.txt file and ensure it is empty
2. ensure you are in the right directory, i,e, no-location-constraint-split-time, 
3. type sh run_timeslot_modified.sh

To get a overall report, you will need to run readRecord.py. 
The average percentage fit and average time taken will be in record.txt

modifydata_timeslots.py reads all file from bmc_data and gives a file with all timeslots splitted by 
only one single day.