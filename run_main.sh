#!/bin/bash

set -o errexit

for ((i = 0 ; i < 9 ; i++)); do
    if [ $i == 0 ]
    then
        java Main.java "bmc_data/c_F200$i.txt" "bmc_data/sf_F200$i.txt" "bmc_data/output_F200$i.txt"
    else
        java Main.java "bmc_data/c_F200$i.txt" "bmc_data/sf_F200$i.txt" "bmc_data/output_F200$i.txt"
        java Main.java "bmc_data/c_S200$i.txt" "bmc_data/sf_S200$i.txt" "bmc_data/output_S200$i.txt"
    fi
done

for ((i = 0 ; i < 5 ; i++)); do
    if [ $i == 4 ]
    then
        java Main.java "bmc_data/c_S201$i.txt" "bmc_data/sf_S201$i.txt" "bmc_data/output_S201$i.txt"
    else
        java Main.java "bmc_data/c_F201$i.txt" "bmc_data/sf_F201$i.txt" "bmc_data/output_F201$i.txt"
        java Main.java "bmc_data/c_S201$i.txt" "bmc_data/sf_S201$i.txt" "bmc_data/output_S201$i.txt"
    fi
done