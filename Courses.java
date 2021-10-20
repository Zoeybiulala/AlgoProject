import java.util.ArrayList;

public class Courses{
    private int ID;
    private ArrayList<Students> registrationList = new ArrayList<Students>();
    private Professors prof;
    private int popularity;
    private Rooms room;
    private TimeSlots time;
    private int [] classConflict;
    private int [] finalConflict;

    public Courses (int i, int cap, int t){
        ID = i;
        //registration list already initialized
        classConflict = new int[cap];
        finalConflict = new int[t];
        prof = null;
        popularity = 0;
        room = null;
        time = null;
    }

    //getters and setters
    public int getID(){ 
        return  ID;
    }

    public ArrayList<Students> getReg(){
        return registrationList;
    }

    public int getPop(){
        return popularity;
    }

    public Rooms getRoom(){
        return room;
    }

    public Professors getPro(){
        return prof;
    }

    public TimeSlots getTime(){
        return time;
    }

    public void incrConflict(Courses c){
        classConflict[c.ID]++;
    }

    public void incrConflict(int c){
        classConflict[c]++;
    }

    public int getCConflict(Courses c){
        return classConflict[c.ID];
    }

    public int getCConflict(int c){
        return classConflict[c];
    }

    public int getFConflict(Courses c){
        return finalConflict[c.ID];
    }

    public int getFConlcit(int c){
        return finalConflict[c];
    }

    public void setFConflict(Courses c, int i){
        finalConflict[c.ID] = i;
    }

    public void setFConflict(int c, int i){
        finalConflict[c] = i;
    }

    public void setProf(Professors p){
        prof = p;
    }

    public void setRoom(Rooms r){
        room = r;
    }

    public void setTime(TimeSlots t){
        time = t;
    }

    public void incrPop(){
        popularity++;
    }

    public void incrCon(Courses c){
        classConflict[c.ID]++;
    }

    public void incrCon(int c){
        classConflict[c]++;
    }

    public void setCon(Courses c, int i){
        classConflict[c.ID] = i;
    }

    public void setCon(int c, int i){
        classConflict[c] = i;
    }

    public void addStu(Students s){
        registrationList.add(s);
    }

    public boolean notScheduled(){
        return (time==null)&&(room==null);
    }

}