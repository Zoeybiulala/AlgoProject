/*class of Rooms */

public class Rooms {
    //instances field
    private int ID; //unique id for a room
    private int capacity; //the maximum number of students could take class in this room
    private Courses [] course; 
    private TimeSlots [] time; 

    public Rooms (int i, int c, TimeSlots [] t){
        ID = i;
        capacity = c;
        time= t;
        course = new Courses[1];
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

    public void setTime(TimeSlots [] t){
        time = t;
    }

    public void addCourse(Courses c, TimeSlots t){
        int tID = t.getID();
        course[tID] = c;
    }

    public boolean isAssigned(TimeSlots t){
        int tID = t.getID();
        return (course[tID] !=null);
    }

}
