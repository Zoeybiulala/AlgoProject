import java.sql.Time;
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
            temp = s.getReg();
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
        int [] finalConlict;
        int finalCon, roomID, surplus;
        finalCon = 0;
        roomID=-1;
        surplus = Integer.MIN_VALUE;
        for(int i = 0; i < size; i++){
            if(temp[i][0].notScheduled()){
                for(int j = 0; j < time.length; j++){
                    for (int k = 0; k < room.length; k++){
                        if (!room[k].isAssigned(time[k])){
                            roomID = k;
                            break;
                        }
                    }
                    surplus = temp[i][0].getPop() - room[roomID].getCap();
                    finalCon = Math.max(surplus, sumOfConflict(room[roomID], temp[i][0]));

                }
            }
        }
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

    




}
