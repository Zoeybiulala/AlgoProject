import java.util.*;

public class Main {
    public Students [] stu;
    public Courses [] classes;
    public Professors [] prof;
    public TimeSlots [] time;

    public void getPopandCon(){
        Courses [] temp = new Courses[4];
        for(Students s: stu){
            temp = s.getReg();
            for(int i=0; i<4; i++){
                temp[i].incrPop();
                for (int j = i+1; j<4; j++){
                    if(temp[i].getPro().equal(temp[j].getPro())){

                    } else {
                        temp[i].incrCon(temp[j]);
                        temp[j].incrCon(temp[i]);
                    }
                }
            }
        }
    }

 
    
}
