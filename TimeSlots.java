import java.util.ArrayList;
public class TimeSlots {
    private int ID;
    private Rooms [] room;
    private ArrayList<Courses> course = new ArrayList<Courses>();

    public TimeSlots(int i){
        ID = i;
        room = null;
        course = null;
    }

    public TimeSlots(int i, Rooms [] r){
        ID = i;
        room = r;
        course = null;
    }

    public TimeSlots(int i, ArrayList<Courses> c){
        ID = i;
        room = null;
        course = c;
    }

    public TimeSlots(int i, Rooms [] r, ArrayList<Courses> c){
        ID = i;
        room = r;
        course = c;
    }

    public void setRoom(Rooms [] r){
        room = r;
    }

    public void setCourse(ArrayList<Courses> c){
        course = c;
    }

    public int getID(){
        return ID;
    }

    public Rooms [] getRooms(){
        return room;
    }

    public ArrayList<Courses> getCourse(){
        return course;
    }

    public Courses getClass(Rooms r){
        return course.get(r.getID());
    }

    public boolean isAssigned(Rooms r){
        return course.get(r.getID()) != null;
    }
}
