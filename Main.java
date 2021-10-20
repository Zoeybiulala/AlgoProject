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
                        finalCon = Math.max(surplus, sumOfConflict(room[roomID], temp[i][m]));
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

    public int sumOfConflict(Rooms r, Courses c){
        int conflict = 0;
        TimeSlots [] t = r.getTime();
        for(int i = 0; i < t.length; i++){
            if(t[i].isAssigned(r)){
                conflict += c.getCConflict(t[i].getClass(r));
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

    public void readFile(String constraints, String prefs){

    }

    




}
