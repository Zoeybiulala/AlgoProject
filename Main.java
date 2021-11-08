
import java.io.*;
import java.util.*;


public class Main {
    /*
        global instances to store all variables
    */
    public static Students [] stu;
    public static Courses [] classes;
    public static HashMap<String,Professors> professors;
    public static TimeSlots [] time;
    public static Rooms [] room;

    public static void setConflictTimes(){
        for(TimeSlots t: time){
            for(TimeSlots tt:time) {
                if(t.isOverlapping(tt)){
                    t.addConflictTime(tt);
                    tt.addConflictTime(t);
                }
            }
        }
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

    public static void getPopandCon(){
        setConflictTimes();
        ArrayList<Courses> temp = new ArrayList<Courses>(); 
        for(Students s: stu){
            temp = s.getPref();
            int size = temp.size();
            for(int i=0; i<size; i++){
                temp.get(i).incrPop();
                for (int j = i+1; j<size; j++){
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
        for(Professors p :professors.values()){
            ArrayList<Courses> teach = p.getCourse();
            for(int i=0;i<teach.size();i++) {
                for(int j=i+1; j<teach.size();j++){
                    teach.get(i).setCon(teach.get(j),Integer.MAX_VALUE);
                    teach.get(j).setCon(teach.get(i),Integer.MAX_VALUE);
                }
            }
        }
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
        int total = classes.length * (classes.length-1) / 2;
        Courses [][] returnArr = new Courses[total][2];
        int count = 0;
        for (int i = 0; i < classes.length-1; i++){
            for (int j = i+1; j< classes.length; j++){
                returnArr[count][0]=classes[i];
                returnArr[count][1]=classes[j];
                count++;
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

    /**
     * Scheduling 
     */
    public static void scheduling(){
        sortRoom();
        Courses [][] temp = pairing();
        int size = temp.length;
        
        int finalCon, roomID, surplus, finalRoomID, finalT;
        finalCon = 0;
        roomID=0;
        surplus = Integer.MIN_VALUE;
        finalRoomID = -1;
        for(int i = 0; i < size; i++){
            for (int m = 0; m <2; m++){
                if(temp[i][m].notScheduled()){
                    // if(temp[i][m].getName().equals("002151") || temp[i][m].getName().equals("011826")){
                    //     System.out.println(i+" "+m);
                    // }
                    int [] finalConlict = new int[time.length];
                    for(int j = 0; j < time.length; j++){
                        for (int k = 0; k < room.length; k++){ //find the largest available room
                            if (!room[k].isAssigned(time[j])){
                                roomID = k;
                                k = room.length;
                            }
                        }
                        surplus = temp[i][m].getPop() - room[roomID].getCap();
                        finalCon = Math.max(surplus, sumOfConflict(time[j], temp[i][m]));
                        finalConlict[j] = finalCon;
                    }
                    
                    
                    finalT = findMinCon(finalConlict);
                    temp[i][m].setTime(time[finalT]); 
                    time[finalT].addClass(temp[i][m]);
                    finalRoomID = roomID;
                    for(int h = room.length-1; h >= 0; h--){
                        if(room[h].getCap() >= temp[i][m].getPop() 
                            && !room[h].isAssigned(time[finalT])){ //
                            finalRoomID = h;
                            break;
                        }
                        finalRoomID = h;
                    }
                    temp[i][m].setRoom(room[finalRoomID]);
                    room[finalRoomID].setTime(time[finalT],finalT);
                    room[finalRoomID].addCourse(temp[i][m], time[finalT]);
                }
            }
        }
    }

    public static int findMinCon(int [] arr){
        int id = 0;
        int min = arr[0];
        for (int i = 1; i < arr.length; i++){
            if(arr[i]<min){
                id = i;
                min = arr[i];
            }
        }
        if(min==Integer.MAX_VALUE) return -1;
        return id;
    }

    public static int sumOfConflict(TimeSlots t, Courses c){
        int conflict = 0;
        for(TimeSlots tt : t.getConflictTime()){
            for (int i =0; i< tt.getCourse().size();i++) {
                if(tt.getCourse().get(i).getPro().equals(c.getPro())){
                    conflict = Integer.MAX_VALUE;
                    break;
                }
                if(conflict != Integer.MAX_VALUE){
                    conflict += tt.getCourse().get(i).getCConflict(c);//
                }
                
            }
        }
        
        return conflict;
    }

    public static void enrollment() {
        boolean available = true;
        int sCou =0;
        for(Students s :stu) {
            ArrayList<Courses> temp = new ArrayList<Courses>(); //gaile
            temp = s.getPref();
            for(int i=0; i<temp.size(); i++) {
                for(int j=0; j<s.getRegNum(); j++) {
                    if(temp.get(i).getName().equals("002855") || s.getReg().get(j).getName().equals("002855)")){
                        System.out.println(sCou+" "+i+" "+j);
                    }
                    TimeSlots a = temp.get(i).getTime();
                    TimeSlots b = s.getReg().get(j).getTime();
                    if(a.isOverlapping(b)){
                        available = false;
                    }
                    
                }
                //at temp.get(i) class, it is available
                if((temp.get(i).getRoom().getCap() > temp.get(i).getReg().size()) && available){
                    temp.get(i).addStu(s);
                    s.addReg(temp.get(i));
                }
                available = true;
            }
            sCou ++;
        }
    }

    /**
     * Read the input files and store all instances into several arrays
    */
    public static void readFile(String constraints, String prefs) throws FileNotFoundException, IOException{
        BufferedReader con;
        BufferedReader pre;
        con = new BufferedReader(new FileReader(constraints));
        String tmp;
        String [] info;
        boolean isRoom, isTeachers, isTime;
        isRoom = false; isTeachers = false; isTime = false;
        int tsize, rsize, csize, psize, capacity, pIndex;
        int roomCount =0;
        int classCount = 0;
        int timeCount  =0;
        tsize = 0; rsize = 0; csize = 0; psize = 0; capacity = 0; pIndex = 0;
        while((tmp = con.readLine())!=null){
            info = tmp.split("\\s+");
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
                psize = Integer.parseInt(info[1]);
                professors = new HashMap<>();
                // prof = new Professors[psize];
                // for(int i = 0; i < psize; i++){
                //     prof[i] = new Professors(i, "");
                // }
                isTeachers = true;
            } else {
                if(isTime && (!isRoom)&&(!isTeachers)){
                  
                        int start = Integer.parseInt(info[1].replaceAll(":",""));
                        int end = Integer.parseInt(info[3].replaceAll(":",""));
                        if(info[2].equals("PM") && start != 1200)
                            start +=1200;
                      
                        if(info[4].equals("PM") && end != 1200){
                            end += 1200;
                          
                        }
                        time[timeCount] = new TimeSlots(timeCount,start,end,info[5]);   
                        timeCount ++;  
                    
                }
                if(isTime && isRoom && (!isTeachers)){
                    capacity = Integer.parseInt(info[1]);
                    TimeSlots[] emptyTimes = new TimeSlots[tsize];
                     
                    room[roomCount] = new Rooms(roomCount, capacity, emptyTimes,info[0]);
                    ++ roomCount;
                }
                if(isTime && isRoom && isTeachers){
                    classes[classCount] = new Courses(classCount, csize, tsize, info[0]);
                    Professors p = new Professors(info[1]);
                    if(!professors.containsKey(info[1])){
                        professors.put(info[1],p);
                    }
                    classes[classCount].setProf(p);
                    professors.get(info[1]).addCourse(classes[classCount]);
                    classCount++;
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
        while((tmp = pre.readLine())!=null){ //deigai
            info = tmp.split("\\s+");
            stu[stuID] = new Students(stuID, info[0]);
            boolean hasClass =false;
            for(int i = 1; i < info.length; i++){
                for(Courses c : stu[stuID].getPref()) {
                    if(c.getName().equals(info[i])){ //we have this class already
                        hasClass = true;
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
    }

    public static int outputSchedule(String file) throws IOException{
        int preferenceVal = 0;
        for (int i = 0; i < classes.length; i++){
            preferenceVal += (classes[i].getReg()).size();
        }
        String stuID;
        BufferedWriter pw = new BufferedWriter(new FileWriter(file));
        String tmp = "";
        tmp = "Course\tRoom\tTeacher\tTime\tStudents\n";
        pw.write(tmp);
        tmp = "";
        for (int i = 0; i < classes.length; i++){
            ArrayList<Students> regList = classes[i].getReg();
            tmp += classes[i].getName() + "\t" + (classes[i].getRoom().getName()) + "\t"
                 + (classes[i].getPro().getName()) + "\t" 
                    + (classes[i].getTime().getID() + 1) + "\t";
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
        String con = "constraints.txt";
        String pref = "student_prefs0.txt";
        String output = "output.txt";
        readFile(con,pref);
        getPopandCon();
        scheduling();
        enrollment();
        int preferenceVal = outputSchedule(output);
        System.out.println("Student Preference Value: " + preferenceVal);
        System.out.println("Best Case Student Value: " + 4*stu.length);
        System.out.println("Fit percentage: " + 100* ((double)preferenceVal/(4*stu.length)) + "%");
        long end = System.currentTimeMillis();
        System.out.println("Time used: " + (end-start));

        for(Courses c: classes){
            if(c.getName().equals("002151")){
                for(Courses a:classes){
                    if(a.getName().equals("011826")){
                        System.out.println(c.getPro().equals(a.getPro()));
                        System.out.println(c.getCConflict(a));
                    }
                }
            }
        }
    }

}
