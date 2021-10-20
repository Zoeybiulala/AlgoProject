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
                        temp[i].setCon(temp[j], 1000000000);
                        temp[j].setCon(temp[i], 1000000000);
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
                    int aa = a[0].getConflict(a[1]);
                    int bb = b[0].getConflict(b[1]);
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
        for(int i = 0; i < size; i++){
            temp[i]
        }
    }

    




}
