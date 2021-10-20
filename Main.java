import java.io.*;
import java.util.*;


public class Main {
    public Students [] stu;
    public Courses [] classes;
    public Professors [] prof;
    public TimeSlots [] time;
    public Rooms [] room;

    public void getPopandCon(){
        Courses [] temp = new Courses[4];
        for(Students s: stu){
            temp = s.getPref();
            for(int i=0; i<4; i++){
                temp[i].incrPop();
                for (int j = i+1; j<4; j++){
                    if(temp[i].getPro().equal(temp[j].getPro())){
                        temp[i].setCon(temp[j], Integer.MAX_VALUE);
                        temp[j].setCon(temp[i], Integer.MAX_VALUE);
                    } else {
                        temp[i].incrCon(temp[j]);
                        temp[j].incrCon(temp[i]);
                    }
                }
            }
        }
    }

    public Courses[][] pairing(){
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

    public void sortRoom(){
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

    public void scheduling(){
        Courses [][] temp = pairing();
        int size = temp.length;
        int [] finalConlict = new int[time.length];
        int finalCon, roomID, surplus, finalRoomID, finalT;
        finalCon = 0;
        roomID=-1;
        surplus = Integer.MIN_VALUE;
        finalRoomID = -1;
        for(int i = 0; i < size; i++){
            for (int m = 0; m <2; m++){
                if(temp[i][m].notScheduled()){
                    for(int j = 0; j < time.length; j++){
                        for (int k = 0; k < room.length; k++){
                            if (!room[k].isAssigned(time[k])){
                                roomID = k;
                                break;
                            }
                        }
                        surplus = temp[i][m].getPop() - room[roomID].getCap();
                        finalCon = Math.max(surplus, sumOfConflict(time[j], temp[i][m]));
                        finalConlict[j] = finalCon;
                    }
                    finalT = findMinCon(finalConlict);
                    temp[i][m].setTime(time[finalT]);
                    finalRoomID = roomID;
                    for(int h = room.length-1; h >= roomID; h++){
                        if(room[h].getCap() > temp[i][m].getPop()){
                            finalRoomID = h;
                            break;
                        }
                    }
                    temp[i][m].setRoom(room[finalRoomID]);
                }
            }
        }
    }

    public int findMinCon(int [] arr){
        int id = 0;
        int min = arr[0];
        for (int i = 1; i < arr.length; i++){
            if(arr[i]<min){
                id = i;
                min = arr[i];
            }
        }
        return id;
    }

    public int sumOfConflict(TimeSlots t, Courses c){
        int conflict = 0;
        Rooms [] r = t.getRooms();
        for(int i = 0; i < r.length; i++){
            if(r[i].isAssigned(t)){
                conflict += c.getCConflict(r[i].getTCourse(t));
            }
        }
        return conflict;
    }

    public void enrollment() {
        Courses [] temp = new Courses[4];
        boolean available = true;
        for(Students s :stu) {
            temp = s.getPref();
            for(int i=0; i<4; i++) {
                for(int j=0; j<4; j++) {
                    if(j != i) {
                        if(temp[j].getTime().equals(temp[i].getTime())){
                            available = false;
                        }
                    }
                }
                if(temp[i].getRoom().getCap() > temp[i].getReg().size() && available){
                    temp[i].addStu(s);
                    s.addReg(temp[i]);
                }
            }
        }
    }

    public void readFile(String constraints, String prefs) throws FileNotFoundException, IOException{
        BufferedReader con;
        BufferedReader pre;
        con = new BufferedReader(new FileReader(constraints));
        String tmp;
        String [] info;
        boolean isRoom, isTeachers;
        isRoom = false; isTeachers = false;
        int tsize, rsize, csize, psize, index;
        index = 0;
        tsize = 0; rsize = 0; csize = 0; psize = 0;
        while((tmp = con.readLine())!=null){
            info = tmp.split("\\s+");
            if(info[0] == "Class"){
                tsize = Integer.parseInt(info[2]);
                time = new TimeSlots[tsize];
                for (int i = 0; i < tsize; i++){
                    time[i] = new TimeSlots(i);
                }
            } else if (info[0] == "Rooms") {
                rsize = Integer.parseInt(info[1]);
                room = new Rooms[rsize];
                isRoom = true;
            } else if (info[0] == "Teachers"){
                psize = Integer.parseInt(info[1]);
                prof = new Professors[psize];
            } else {
                if(isRoom && (!isTeachers)){
                    index = Integer.parseInt(info[0]) - 1;
                    room[index] = new Room()
                }
            }
        }


        pre = new BufferedReader(new FileReader(prefs));
        pre.close();
        con.close();
    }

    public static void main(String [] args){

    }

    




}
