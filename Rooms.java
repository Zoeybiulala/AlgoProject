/*class of Rooms */

public class Rooms {
    //instances field
    private int ID; //unique id for a room
    private String name;
    private int capacity; //the maximum number of students could take class in this room
    private Courses [] course; 
    private TimeSlots [] time; 

    public Rooms (int i, int c, TimeSlots [] t,String n){
        ID = i;
        capacity = c;
        time= t;
        course = new Courses[t.length];
        name = n;
    }

    public int getID(){
        return ID;
    }

    public int getCap(){
        return capacity;
    }

    public Courses [] getCourse(){
        return course;
    }

    public TimeSlots [] getTime(){
        return time;
    }

    public Courses getTCourse(TimeSlots t){
        return course[t.getID()];
    }

    public String getName() {
        return name;
    }

    public void setTime(TimeSlots t,int timeID){
        time[timeID] = t;
    }

    public void addCourse(Courses c, TimeSlots t){
        int tID = t.getID();
        course[tID] = c;
    }

    public boolean isAssigned(TimeSlots t){//gaiwanle
        boolean isoverlap=false;
        for(int i=0; i<time.length;i++) {
            if(time[i]!= null){
                if(time[i].isOverlapping(t)) {
                    isoverlap = true;
                    break;
                }
            }
        }
        return isoverlap;
    }

}
