import os
import re

def modifyFile(rPath, wPath):
    f = open(rPath)
    fw = open(wPath, 'w')
    isRoom = False
    isClass = False
    line = f.readline()
    count = 1
    while line != '':
        if re.match('Rooms', line):
            isRoom = True
            fw.write(line)
            line = f.readline()
            continue
        if isRoom:
            fw.write(line)
            line = f.readline()
            continue
        if re.match('Class Times', line):
            fw.write(line)
            isClass = True
            line = f.readline()
            continue
        if not isRoom and isClass:
            tmp = line.split()
            if tmp[5].find('-') == -1:
                if tmp[5].find('M'):
                    string = ('{0:<4d}{1:>5s} ' + tmp[2] + ' {2:>5s} '+ tmp[4] + ' ').format(count, tmp[1], tmp[3])
                    fw.write(string + 'M\n')
                    count = count + 1
                if tmp[5].find('TH') and not tmp[5].find('TTH'):
                    string = ('{0:<4d}{1:>5s} ' + tmp[2] + ' {2:>5s} '+ tmp[4] + ' ').format(count, tmp[1], tmp[3])
                    fw.write(string + 'TH\n')
                    count = count + 1
                if tmp[5].find('T') and not tmp[5].find('TH'):
                    string = ('{0:<4d}{1:>5s} ' + tmp[2] + ' {2:>5s} '+ tmp[4] + ' ').format(count, tmp[1], tmp[3])
                    fw.write(string + 'T\n')
                    count = count + 1
                if tmp[5].find('TTH'):
                    string = ('{0:<4d}{1:>5s} ' + tmp[2] + ' {2:>5s} '+ tmp[4] + ' ').format(count, tmp[1], tmp[3])
                    fw.write(string + 'T\n')
                    count = count + 1
                    string = ('{0:<4d}{1:>5s} ' + tmp[2] + ' {2:>5s} '+ tmp[4] + ' ').format(count, tmp[1], tmp[3])
                    fw.write(string + 'TH\n')
                    count = count + 1
                if tmp[5].find('W'):
                    string = ('{0:<4d}{1:>5s} ' + tmp[2] + ' {2:>5s} '+ tmp[4] + ' ').format(count, tmp[1], tmp[3])
                    fw.write(string + 'W\n')
                    count = count + 1
                if tmp[5].find('F'):
                    string = ('{0:<4d}{1:>5s} ' + tmp[2] + ' {2:>5s} '+ tmp[4] + ' ').format(count, tmp[1], tmp[3])
                    fw.write(string + 'F\n')
                    count = count + 1
            else: #when they have -
                start = tmp[5].split('-')[0]
                end = tmp[5].split('-')[1]
                if re.match(start, 'M') and re.match(end, 'TH'):
                    string = ('{0:<4d}{1:>5s} ' + tmp[2] + ' {2:>5s} '+ tmp[4] + ' ').format(count, tmp[1], tmp[3])
                    fw.write(string + 'M\n')
                    count = count + 1
                    string = ('{0:<4d}{1:>5s} ' + tmp[2] + ' {2:>5s} '+ tmp[4] + ' ').format(count, tmp[1], tmp[3])
                    fw.write(string + 'T\n')
                    count = count + 1
                    string = ('{0:<4d}{1:>5s} ' + tmp[2] + ' {2:>5s} '+ tmp[4] + ' ').format(count, tmp[1], tmp[3])
                    fw.write(string + 'W\n')
                    count = count + 1
                    string = ('{0:<4d}{1:>5s} ' + tmp[2] + ' {2:>5s} '+ tmp[4] + ' ').format(count, tmp[1], tmp[3])
                    fw.write(string + 'TH\n')
                    count = count + 1
                if re.match(start, 'M') and re.match(end, 'F'):
                    string = ('{0:<4d}{1:>5s} ' + tmp[2] + ' {2:>5s} '+ tmp[4] + ' ').format(count, tmp[1], tmp[3])
                    fw.write(string + 'M\n')
                    count = count + 1
                    string = ('{0:<4d}{1:>5s} ' + tmp[2] + ' {2:>5s} '+ tmp[4] + ' ').format(count, tmp[1], tmp[3])
                    fw.write(string + 'T\n')
                    count = count + 1
                    string = ('{0:<4d}{1:>5s} ' + tmp[2] + ' {2:>5s} '+ tmp[4] + ' ').format(count, tmp[1], tmp[3])
                    fw.write(string + 'W\n')
                    count = count + 1
                    string = ('{0:<4d}{1:>5s} ' + tmp[2] + ' {2:>5s} '+ tmp[4] + ' ').format(count, tmp[1], tmp[3])
                    fw.write(string + 'TH\n')
                    count = count + 1
                    string = ('{0:<4d}{1:>5s} ' + tmp[2] + ' {2:>5s} '+ tmp[4] + ' ').format(count, tmp[1], tmp[3])
                    fw.write(string + 'F\n')
                    count = count + 1
            line = f.readline()
    f.close()
    fw.close()

readDir = 'bmc_data'
storeDir = 'bmc_data_timeslot_splitted'
if not os.path.isdir(storeDir):
    os.mkdir(storeDir)
# modified data stored here
files = os.listdir(readDir)
for file in files:
    readPath = os.path.join(readDir, file)
    storePath = os.path.join(storeDir, file)
    if file.startswith('c'):
        modifyFile(readPath, storePath)

