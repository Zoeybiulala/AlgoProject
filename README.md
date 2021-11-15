# AlgoProject
This folder is the original one reading the real data, with no location constraint, splitted timeslots
In order to run this project, you should run **run_timeslot_splitted.sh** first. 
beforehand, you need to ensure there is a record.txt in your code sources file and which is empty
The source file in this situation is with-lab-with-no-location-constraint

i.e.: 
1. check bmc_data_timeslot_splitted to find the record.txt file and ensure it is empty
2. ensure you are in the right directory, i,e, with-lab-with-no-location-constraint,
3. type sh run_timeslot_modified.sh

To get a overall report, you will need to run readRecord.py. 
The average percentage fit and average time taken will be in record.txt

modifydata_timeslots.py reads all file from bmc_data and gives a file with all timeslots splitted by 
only one single day.