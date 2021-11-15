/**
 * Description: Main class to read file, store information from the file as different instances
 *              Schedule classes into classrooms and time slots and enroll students in
 * 
 * Author: Tianbo Yang, Yitian Cao, Xinran Liu
 * Date Updated: Nov.9, 2021
 */
import java.io.*;
import java.util.*;

public class Main {
    /**
     * global instances to store all variables
     */
    public static Students [] stu;
    public static Courses [] classes;
    public static HashMap<String,Professors> professors;
    public static TimeSlots [] time;
    public static Rooms [] room;
    public static Rooms [][] lab;

    /**
     * For each Time slot, checking all other times slots and record
     * those time slots have schedule conflitcs with it. 
     */
    public static void setConflictTimes(){
        for(TimeSlots t: time){
            for(TimeSlots tt:time) {
                if(t.isOverlapping(tt) && !t.equals(tt)){
                    t.addConflictTime(tt);
                    tt.addConflictTime(t);
                }
            }
        }
        //sort the array by number of conflicts
        Arrays.sort(time, new Comparator<TimeSlots>() {
            @Override
                 //arguments to this method represent the arrays to be sorted   
                 public int compare(TimeSlots t1, TimeSlots t2){
                    int aa = t1.getConflictTime().size();
                    int bb = t2.getConflictTime().size();;
                    if(aa > bb)
                        return 1;
                    if(aa < bb)
                        return -1;
                    return 0;
                }
            });
    }

    /**
     * Calcualte the popularity of each courses by going through the 
     *  preference list of each student
     * Count the number for each course who have the same student that 
     *  want to enroll into the course
     */
    public static int getPopandCon(){
        //let the array of time slots be sorted by those has 
        // less conflicts with other time slot s to be assigned first
        setConflictTimes(); 
        int count = 0;
        ArrayList<Courses> temp = new ArrayList<Courses>(); 
        for(Students s: stu){
            temp = s.getPref(); //get the preference list for each student 
            count += temp.size();
            for(int i=0; i<temp.size(); i++){
                //since the student want to enrool in the class, the popularity for
                //such course will increase
                temp.get(i).incrPop(); 
                // record the number of conflicts by checking if some student
                // want to take both courses at the same time, if so, we increase 
                // the number of conflicts
                for (int j = i+1; j<temp.size(); j++){
                    //if the two courses share the professor, we set the conflict number 
                    //to infinity to ensure they will not be assigned into the same time slots
                    if(temp.get(i).getPro().equals(temp.get(j).getPro())){
                        temp.get(i).setCon(temp.get(j), Integer.MAX_VALUE);
                        temp.get(j).setCon(temp.get(i), Integer.MAX_VALUE);
                    } else { 
                        temp.get(i).incrCon(temp.get(j));
                        temp.get(j).incrCon(temp.get(i));
                    }
                }
            }
        }
        //setting the conflict number of some course with some other course into infinity if
        //they share the same professor
        for(Professors p :professors.values()){
            ArrayList<Courses> teach = p.getCourse();
            for(int i=0;i<teach.size();i++) {
                for(int j=i+1; j<teach.size();j++){
                    teach.get(i).setCon(teach.get(j),Integer.MAX_VALUE);
                    teach.get(j).setCon(teach.get(i),Integer.MAX_VALUE);
                }
            }
        }
        return count;
    }

    /**
     * Pairing each course
     * Store into a 2D array with index 0 and index 1 refer to 
     *  two courses that are paired
     * Sort the 2D array by decreasing number of conflicts,
     *  i.e., number of shared students
     * 
     * @return 2D array to store each pairing, sorted
     */

    public static Courses[][] pairing(){
        int total = classes.length * (classes.length-1) / 2; //total number of pairings
        //return array is 2D with index 1 storing some course and index 2 
        // storing it pairing courses
        Courses [][] returnArr = new Courses[total][2];
        int count = 0;
        for (int i = 0; i < classes.length-1; i++){
            for (int j = i+1; j< classes.length; j++){
                returnArr[count][0]=classes[i];
                returnArr[count][1]=classes[j];
                count++; //use count to record the first index of the 2d array
            }
        }
        //sort the 2D array by conflict numbers
        Arrays.sort(returnArr, new Comparator<Courses[]>() {
            @Override
                 //arguments to this method represent the arrays to be sorted   
                public int compare(Courses [] a, Courses [] b){
                    int aa = a[0].getCConflict(a[1]);
                    int bb = b[0].getCConflict(b[1]);
                    if(aa > bb)
                        return -1;
                    if(aa < bb)
                        return 1;
                    return 0;
                }
        });
        return returnArr;
    }

    /**
     * Sort the room by it capacity
     */
    public static void sortRoom(){
        Arrays.sort(room, new Comparator<Rooms>() {
            @Override
                //arguments to this method represent the arrays to be sorted   
                public int compare(Rooms a, Rooms b){
                    int aa = a.getCap();
                    int bb = b.getCap();
                    if(aa > bb)
                        return -1;
                    if(aa < bb)
                        return 1;
                    return 0;
                }
        });
    }

    /* 
    * Initializing 2D array of lab rooms 
    * 7 subjects require labs
    * each subject has 2 lab rooms 
    */
    public static void setLabRooms(int numTimeSlots){
        int labCap=room[0].getCap();
        lab[0][0]=new Rooms(labCap, new TimeSlots[numTimeSlots], "MATHLAB1");
        lab[0][1]=new Rooms(labCap, new TimeSlots[numTimeSlots], "MATHLAB2");
        lab[1][0]=new Rooms(labCap, new TimeSlots[numTimeSlots], "CHEMLAB1");
        lab[1][1]=new Rooms(labCap, new TimeSlots[numTimeSlots], "CHEMLAB2");
        lab[2][0]=new Rooms(labCap, new TimeSlots[numTimeSlots], "PSYCLAB1");
        lab[2][1]=new Rooms(labCap, new TimeSlots[numTimeSlots], "PSYCLAB2");
        lab[3][0]=new Rooms(labCap, new TimeSlots[numTimeSlots], "BIOLLAB1");
        lab[3][1]=new Rooms(labCap, new TimeSlots[numTimeSlots], "BIOLLAB2");
        lab[4][0]=new Rooms(labCap, new TimeSlots[numTimeSlots], "ECONLAB1");
        lab[4][1]=new Rooms(labCap, new TimeSlots[numTimeSlots], "ECONLAB2");
        lab[5][0]=new Rooms(labCap, new TimeSlots[numTimeSlots], "PHYSLAB1");
        lab[5][1]=new Rooms(labCap, new TimeSlots[numTimeSlots], "PHYSLAB2");
        lab[6][0]=new Rooms(labCap, new TimeSlots[numTimeSlots], "CMSCLAB1");
        lab[6][1]=new Rooms(labCap, new TimeSlots[numTimeSlots], "CMSCLAB2");
    }

    /*

    */
    public static int labKey(String s){
        if(s.equals("MATH")){
            return 0;
        } else if(s.equals("CHEM")){
            return 1;
        } else if(s.equals("PSYC")){
            return 2;
        } else if(s.equals("BIOL")){
            return 3;
        } else if(s.equals("ECON")){
            return 4;
        } else if(s.equals("PHYS")){
            return 5;
        } else {
            return 6;
        }
    }

    /**
     * Scheduling the classes into classrooms and timeslots
     * 
     * Our definition of conflict numbers is 
     */
    public static void scheduling(){
        Courses [][] temp = pairing();
        int size = temp.length; 
        int finalCon, roomID, surplus, finalRoomID, finalT;
        finalCon = 0; 
        roomID = 0; 
        surplus = Integer.MIN_VALUE;
        finalRoomID = -1;
        for(int i = 0; i < size; i++){
            for (int m = 0; m <2; m++){
                if(temp[i][m].notScheduled() || temp[i][m].getPro().getName()==null){
                    // if(temp[i][m].getName().equals("002151") || temp[i][m].getName().equals("011826")){
                    //     System.out.println(i+" "+m);
                    // }

                    // storing the final conflict some courses in all time slots
                    int [] finalConflict = new int[time.length]; 
                    for(int j = 0; j < time.length; j++){
                        // find the largest available room
                        // we only need to find the first available room since the array is 
                        // alreay sorted by its capacity
                        for (int k = 0; k < room.length; k++){ 
                            if (!room[k].isAssigned(time[j])
                                && temp[i][m].getValidRooms().contains(room[k])){ 
                                roomID = k; //record the index for the largest availble room
                                k = room.length; //break
                            }
                        }
                        //surplus is positive only happens when some class have more student want to enroll
                        // than the room's capacity
                        surplus = temp[i][m].getPop() - room[roomID].getCap();
                        if(temp[i][m].hasLab()){
                            //find the number of lab conflicts for each time slot
                            int[] labConflicts;
                            for(int k=0; k<time.length; k++){
                                labConflicts[k]=sumOfLabConflict(time[k], temp[i][j]);
                            }
                            int minLabCon = Integer.MAX_VALUE;
                            for(int l=0; l<labConflicts.length; l++){
                                if(time[l] != time[j]){
                                    if(labConflicts[l]<minLabCon){
                                        minLabCon=labConflicts[l];
                                    }
                                }
                            }
                            // record the larger from conflict number or the shortage of classroom capacity
                            finalCon = Math.max(surplus, sumOfLectureConflict(time[j], temp[i][m]) + minLabCon);
                        } else{
                        // record the larger from conflict number or the shortage of classroom capacity
                        finalCon = Math.max(surplus, sumOfLectureConflict(time[j], temp[i][m]));
                        }
                        finalConflict[j] = finalCon;
                    }
                    //find the timeslots with minimum conflict numbers 
                    int [][] arr = findMinCon(finalConflict);
                    int first = arr[0][0];
                    int second = -1;
                    int third = -1;
                    time[first].addClass(temp[i][m]);
                    temp[i][m].addTime(time[first]); 
                    if(time[first].getDuration() < 200){
                        //System.out.println("aaa");
                        time[first].addClass(temp[i][m]);
                        for(int k =  1; k < arr.length; k++){
                            if(!time[arr[k][0]].isOverlapping(time[first]) && time[arr[k][0]].getDuration()<200){
                                second = arr[k][0];
                                //System.out.println(time[second].getDuration());
                                temp[i][m].addTime(time[arr[k][0]]); 
                                time[arr[k][0]].addClass(temp[i][m]);
                                break;
                            }
                        }
                    }
                    if(temp[i][m].hasLab()==true) {
                        //time[first].addClass(temp[i][m]);
                        for(int l =  1; l < arr.length; l++){
                            if(!time[arr[l][0]].isOverlapping(time[first]) && !time[arr[l][0]].isOverlapping(time[second])){
                                third = arr[l][0];
                                //System.out.println(time[second].getDuration());
                                temp[i][m].addTime(time[arr[l][0]]); 
                                time[arr[l][0]].addClass(temp[i][m]);
                                break;
                            }
                        }
                    }
                    //finding the room to be assigned
                    finalRoomID = roomID; //initialize it first into the largest available room
                    //finding the smallest room can fit all students inside
                    for(int h = room.length-1; h >= 0; h--){
                        if(temp[i][m].getTime().size()==1){
                            if(room[h].getCap() >= temp[i][m].getPop() 
                                && !room[h].isAssigned(time[first])){ 
                                finalRoomID = h;
                                break;
                            }
                        } else {
                            if(room[h].getCap() >= temp[i][m].getPop() 
                                && !room[h].isAssigned(time[first]) 
                                    && !room[h].isAssigned(time[second])
                                        && temp[i][m].getValidRooms().contains(room[h])){ 
                                finalRoomID = h;
                                break;
                            }
                        }
                        finalRoomID = h;
                    }
                    //setting & scheduling
                    temp[i][m].setRoom(room[finalRoomID]);
                    if(second == -1){
                        room[finalRoomID].setTime(time[first],first);
                        room[finalRoomID].addCourse(temp[i][m], time[first]);
                    } else {
                        room[finalRoomID].setTime(time[first],first);
                        room[finalRoomID].addCourse(temp[i][m], time[first]);
                        room[finalRoomID].setTime(time[second],second);
                        room[finalRoomID].addCourse(temp[i][m], time[second]);
                    }
                    if(third != -1){
                        int key = labKey(temp[i][m].getSubject());
                        if(!lab[key][0].isAssigned(time[third])){
                            lab[key][0].setTime(time[third], third);
                            lab[key][0].addCourse(temp[i][m], time[third]);
                        } else{
                            lab[key][1].setTime(time[third], third);
                            lab[key][1].addCourse(temp[i][m], time[third]);
                        }
                    }
                }
            }
        }
    }

    /**
     * helper function for scheduling
     * @param arr array storing the conflict numbers of some course with all time slots
     * @return the index of the array with the least conflict number
     */
    public static int[][] findMinCon(int [] arr){
        int [][] tmp = new int[arr.length][2];
        for(int i = 0; i < arr.length; i++){
            tmp[i][0] = i;
            tmp[i][1] = arr[i];
        }
        Arrays.sort(tmp, new Comparator<int[]>() {
            @Override
                 //arguments to this method represent the arrays to be sorted   
                public int compare(int [] a, int [] b){
                    return Integer.compare(a[1], b[1]);
                }
        });
        return tmp;
    }

    /**
     * calculate the number of conflicts between the lectures of course c 
     * and all other courses assigned
     * into the timeslot t and all other timeslots overlapping with t
     * @param t some timeslot
     * @param c some course
     * @return the number of conflicts 
     */
    public static int sumOfLectureConflict(TimeSlots t, Courses c){
        int conflict = 0;
        //caculating the number of total conflicts 
        for (int i =0; i< t.getCourse().size();i++) {
            //when the professor teaching c is already teaching some other courses
            //thus will not be available
            if(t.getCourse().get(i).getPro().equals(c.getPro())){
                conflict = Integer.MAX_VALUE;
                break;
            }
            if(conflict != Integer.MAX_VALUE){
                conflict += t.getCourse().get(i).getCConflict(c);
            }
        }
        //checking all timeslots overlapping with t and redo the process with them
        for(TimeSlots tt : t.getConflictTime()){
            for (int i =0; i< tt.getCourse().size();i++) {
                if(tt.getCourse().get(i).getPro().equals(c.getPro())){
                    conflict = Integer.MAX_VALUE;
                    break;
                }
                if(conflict != Integer.MAX_VALUE){
                    conflict += tt.getCourse().get(i).getCConflict(c);
                }
                
            }
        }
        return conflict;
    }

    /**
     * calculate the number of conflicts between the lab section of course c 
     * and all other courses assigned
     * into the timeslot t and all other timeslots overlapping with t
     * @param t some timeslot
     * @param c some course
     * @return the number of conflicts 
     */
    public static int sumOfLabConflict(TimeSlots t, Courses c){
        int key = labKey(c.getSubject());
        int conflict1 = 0;
        //caculating the number of total conflicts 
        for (int i =0; i< t.getCourse().size();i++) {
            //Check if the course c uses the same lab room as  
            //another course scheduled for the same timeslot t
            if(t.getCourse().get(i).getLabRoom().equals(lab[key][0])){
                conflict1 = Integer.MAX_VALUE;
                break;
            }
            if(conflict1 != Integer.MAX_VALUE){
                conflict1 += t.getCourse().get(i).getCConflict(c);
            }
        }
        //checking all timeslots overlapping with t and redo the process with them
        for(TimeSlots tt : t.getConflictTime()){
            for (int i =0; i< tt.getCourse().size();i++) {
            if(t.getCourse().get(i).getLabRoom().equals(lab[key][0])){
                conflict1 = Integer.MAX_VALUE;
                break;
            }
                if(conflict1 != Integer.MAX_VALUE){
                    conflict1 += tt.getCourse().get(i).getCConflict(c);
                }
                
            }
        }
        int conflict2 = 0;
        //caculating the number of total conflicts 
        for (int i =0; i< t.getCourse().size();i++) {
            //Check if the course c uses the same lab room as  
            //another course scheduled for the same timeslot t
            if(t.getCourse().get(i).getLabRoom().equals(lab[key][1])){
                conflict2 = Integer.MAX_VALUE;
                break;
            }
            if(conflict2 != Integer.MAX_VALUE){
                conflict2 += t.getCourse().get(i).getCConflict(c);
            }
        }
        //checking all timeslots overlapping with t and redo the process with them
        for(TimeSlots tt : t.getConflictTime()){
            for (int i =0; i< tt.getCourse().size();i++) {
            if(t.getCourse().get(i).getLabRoom().equals(lab[key][1])){
                conflict2 = Integer.MAX_VALUE;
                break;
            }
                if(conflict2 != Integer.MAX_VALUE){
                    conflict2 += tt.getCourse().get(i).getCConflict(c);
                }
                
            }
        }
        if(conflict1>=conflict2){
            return conflict1;
        } else{
            return conflict2;
        }
    }


    /**
     * enroll students into courses
     */
    public static void enrollment() {
        boolean available = true;
        // int sCou =0;
        for(Students s :stu) {
            ArrayList<Courses> temp = new ArrayList<Courses>(); 
            temp = s.getPref(); //get each student's preference list
            for(int i=0; i<temp.size(); i++) {
                //checking if the course students registered has no schedule
                //conlict with the next course the student want to enroll
                for(int j=0; j<s.getRegNum(); j++) {
                    // if(temp.get(i).getName().equals("002855") || s.getReg().get(j).getName().equals("002855)")){
                    //     System.out.println(sCou+" "+i+" "+j);
                    // }
                    ArrayList<TimeSlots> a = temp.get(i).getTime();
                    ArrayList<TimeSlots> b = s.getReg().get(j).getTime();
                    for(TimeSlots aa: a){
                        if(!available)
                            break;
                        for(TimeSlots bb: b){
                            if(aa.isOverlapping(bb)){
                                available = false;
                                break;
                            }
                        }
                    }
                }
                //at temp.get(i) class, it is available
                //students can enroll in temp.get(i) class when they are available
                //and the classroom has enough room to fit them in
                if((temp.get(i).getRoom().getCap() > temp.get(i).getReg().size()) && available){
                    temp.get(i).addStu(s);
                    s.addReg(temp.get(i));
                }
                available = true;
            }
            // sCou ++;
        }
    }

    /**
     * Read the input files and store all instances into several arrays
    */
    public static int readFile(String constraints, String prefs) throws FileNotFoundException, IOException{
        BufferedReader con; //constraints file
        BufferedReader pre; //students' preference list
        con = new BufferedReader(new FileReader(constraints)); //read the constaint file first
        String tmp;
        String [] info;
        boolean isRoom, isTeachers, isTime;
        isRoom = false; isTeachers = false; isTime = false;
        int tsize, rsize, csize, capacity;
        int roomCount =0;
        int classCount = 0;
        int timeCount  =0;
        //int psize = 0;
        //int pIndex = 0;
        tsize = 0; rsize = 0; csize = 0;  capacity = 0;
        while((tmp = con.readLine())!=null){
            info = tmp.split("\\s+"); //split each line with space
            if(info[0].equals("Class")){
                tsize = Integer.parseInt(info[2]);
                time = new TimeSlots[tsize];
                isTime = true;
            } else if (info[0].equals("Rooms")) {
                rsize = Integer.parseInt(info[1]);
                room = new Rooms[rsize];
                isRoom = true;
            } else if (info[0].equals("Classes")){
                csize = Integer.parseInt(info[1]);
                classes = new Courses[csize];
  
            } else if (info[0].equals("Teachers")){
                professors = new HashMap<>();
                isTeachers = true;
            } else {
                if(isTime && (!isRoom)&&(!isTeachers)){ //we need to store the timeslots
                    int start = Integer.parseInt(info[1].replaceAll(":",""));
                    int end = Integer.parseInt(info[3].replaceAll(":",""));
                    //we used military time for start and end time 
                    //e.g.use 2100 to represent 9:00PM
                    //12PM is just 1200
                    if(info[2].equals("PM") && start != 1200){
                        start +=1200;
                    }
                    if(info[4].equals("PM") && end != 1200){
                        end += 1200;
                    }
                    time[timeCount] = new TimeSlots(timeCount,start,end,info[5]);   
                    timeCount ++;  
                }
                if(isTime && isRoom && (!isTeachers)){
                    capacity = Integer.parseInt(info[1]);
                    TimeSlots[] emptyTimes = new TimeSlots[tsize];
                    room[roomCount] = new Rooms(capacity, emptyTimes,info[0]);
                    ++ roomCount;
                }
                if(isTime && isRoom && isTeachers){
                    
                    if(info.length>1){
                        classes[classCount] = new Courses(classCount, csize, tsize, info[0]);
                        Professors p = new Professors(info[1]);
                        if(!professors.containsKey(info[1])){
                            professors.put(info[1],p);
                        }
                        classes[classCount].setProf(p);
                        professors.get(info[1]).addCourse(classes[classCount]);
                        classes[classCount].setSubject(info[2]);
                        ArrayList<Rooms> validRooms=new ArrayList<Rooms>();
                        for(int k=3; k<info.length; k++){
                            for(int j=0; j<room.length; j++){
                                if(info[k].equals(room[j].getName())){
                                    validRooms.add(room[j]);
                                    break;
                                }
                            }
                        }
                        classes[classCount].setValidRooms(validRooms);
                        classCount++;
                    } else {
                        classes[classCount] = new Courses(classCount, csize, tsize, info[0]);
                        Professors p = new Professors(null);
                        classes[classCount].setProf(p);
                        classCount++;
                    }
                    
                }
            }
        }
        pre = new BufferedReader(new FileReader(prefs));
        tmp = pre.readLine();
        info = tmp.split("\\s+");
        int stuSize = Integer.parseInt(info[1]);
        stu = new Students[stuSize];
        for(int i = 0; i < stuSize; i++){
            stu[i] = new Students(i, "");
        }
        int stuID;
        stuID = 0;
        while((tmp = pre.readLine())!=null){
            info = tmp.split("\\s+");
            stu[stuID] = new Students(stuID, info[0]);
            boolean hasClass = false;
            for(int i = 1; i < info.length; i++){
                for(Courses c : stu[stuID].getPref()) {
                    if(c.getName().equals(info[i])){ //we have this class already
                        hasClass = true; // we might want to use this class again by 
                                        //naming it as a new class since it's a lab or something
                    }
                }
                if(hasClass == false) {
                    for(int j = 0; j < classes.length; j++){
                        if(classes[j].getName().equals(info[i])){
                            stu[stuID].addPref(classes[j]);
                            break;
                        }
                    }
                }
                
            }
            stuID++;
        }
        pre.close();
        con.close();
        return tsize;
    }

    /**
     * Read the path for some file and will output the schedule in such file
     * and calculate the student prefernce value
     * @param file
     * @return the value of total students successfully enrolled, i.e., student
     *          preference value
     * @throws IOException
     */
    public static int outputSchedule(String file) throws IOException{
        int preferenceVal = 0;
        //calculate the preference value
        for (int i = 0; i < classes.length; i++){
            preferenceVal += (classes[i].getReg()).size();
        }
        //create the file if do not exist
        File f = new File(file);
        f.createNewFile();
        String stuID;
        BufferedWriter pw = new BufferedWriter(new FileWriter(file));
        String tmp = "";
        //formatting
        tmp = "Course\tRoom\tTeacher\tTime\tStudents\n";
        pw.write(tmp);
        tmp = "";
        for (int i = 0; i < classes.length; i++){
            ArrayList<Students> regList = classes[i].getReg();
            ArrayList<TimeSlots> timeList = classes[i].getTime();
            if(timeList.size()==1){
                tmp += classes[i].getName() + "\t" + (classes[i].getRoom().getName()) + "\t"
                + (classes[i].getPro().getName()) + "\t" 
                   + (classes[i].getTime().get(0).getID() + 1) + "\t";
            } else {
                tmp += classes[i].getName() + "\t" + (classes[i].getRoom().getName()) + "\t"
                    + (classes[i].getPro().getName()) + "\t";
                for(int j = 0; j<timeList.size(); j++){
                       tmp += (classes[i].getTime().get(j).getID() + 1) + "\t";
                }
            }
            for (int j = 0; j < regList.size(); j++){
                stuID = regList.get(j).getName();
                tmp += stuID + " " ; 
            }
            pw.write(tmp + "\n");
            tmp = "";
        }
        pw.flush();
        pw.close();
        return preferenceVal;
    }

    public static void main(String [] args) throws FileNotFoundException, IOException{
        long start = System.currentTimeMillis();
        String con = args[0];
        String pref = args[1];
        String output = args[2];
        int numTimeSlots = readFile(con,pref); //reading input
        int count = getPopandCon(); //getting the popularity and conflict numbers
        sortRoom();
        setLabRooms(numTimeSlots);
        scheduling(); //output a possible schedule
        enrollment(); //enroll students in
        int preferenceVal = outputSchedule(output); //output the schedule in a file and get the preference value
        long end = System.currentTimeMillis();
        
        //output result
        String [] info = con.split("/");
        String record = info[info.length-2] + "/record.txt";
        File f = new File(record);
        f.createNewFile();
        BufferedWriter pw = new BufferedWriter(new FileWriter(f, true));
        String [] info2 = info[info.length-1].split("_");
        String tmp = info2[1].replace(".txt", "");
        pw.write(tmp + "\n");
        pw.write("Student Preference Value: " + preferenceVal+ "\n");
        pw.write("Best Case Student Value: " + count + "\n");
        pw.write("Fit percentage: " + 100* ((double)preferenceVal/(double)count) + "%"+ "\n");
        pw.write("Time used: " + (end-start)+ "\n");
        pw.flush();
        pw.close();

        //print in the terminal
        System.out.println("Student Preference Value: " + preferenceVal);
        System.out.println("Best Case Student Value: " + count);
        System.out.println("Fit percentage: " + 100* ((double)preferenceVal/(double)count) + "%");
        System.out.println("Time used: " + (end-start));
    }
}
