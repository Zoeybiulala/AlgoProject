public class Courses{
    private int ID;
    private Students [] registrationList;
    private Professors prof;
    private int popularity;
    private Rooms room;
    private TimeSlots time;
    private int [] classConflict;

    public Courses (int i){
        ID = i;
        registrationList = new Students[1];
        classConflict = new int[1];
        prof = null;
        popularity = 0;
        room = null;
        time = null;
    }

    //getters and setters
    public int getID(){ 
        return  ID;
    }

    public Students [] getReg(){
        return registrationList;
    }

    public int getPpo(){
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

    public int getConflict(Courses c){
        return classConflict[c.ID];
    }

    public int getConflict(int c){
        return classConflict[c];
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
        int size = registrationList.length;
        if (registrationList[size - 1] == null){
            registrationList[size - 1] = s;
            return;
        }
        Students [] temp = new Students[size+1];
        temp[size] = s;
        registrationList = temp; 
    }

    public boolean notScheduled(){
        return (time==null)&&(room==null);
    }

}