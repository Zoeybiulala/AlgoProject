public class Courses{
    private int ID;
    private Students [] registrationList;
    private Professors prof;
    private int popularity;
    private Rooms room;
    private TimeSlots time;
    private int [] finalConflict;

    public Courses (int i){
        ID = i;
        registrationList = new Students[1];
        finalConflict = new int[1];
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
        finalConflict[c.ID]++;
    }

    public void incrConflict(int c){
        finalConflict[c]++;
    }

    public int getConflict(Courses c){
        return finalConflict[c.ID];
    }

    public int getConflict(int c){
        return finalConflict[c];
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
        finalConflict[c.ID]++;
    }

    public void incrCon(int c){
        finalConflict[c]++;
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

}