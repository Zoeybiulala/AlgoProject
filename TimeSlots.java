import java.util.ArrayList;
/** class of Rooms
 */
public class TimeSlots {
    //instances field
    private int ID; //unique id for Time Slots
    private Rooms [] room; //the rooms that are avaiable for this time slot
    private ArrayList<Courses> course = new ArrayList<Courses>(); //assigned courses at this time slot
    
    /**
     * constructors of TimeSlots
     * @param i is the unique id for TimeSlot
     */
    public TimeSlots(int i){
        ID = i;
        room = null;
    }

    /**
     * constructors of TimeSlots
     * @param i is the unique id for TimeSlot
     * @param r the rooms for this timeslot
     */
    public TimeSlots(int i, Rooms [] r){
        ID = i;
        room = r;
    }

    /**
     * constructors of TimeSlots
     * @param i is the unique id for TimeSlot
     * @param c the assigned classes for this time slot
     */
    public TimeSlots(int i, ArrayList<Courses> c){
        ID = i;
        room = null;
        course = c;
    }

    /**
     * constructors of TimeSlots
     * @param i is the unique id for TimeSlot
     * @param r the rooms for this timeslot
     * @param c the assigned classes for this time slot
     */
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

    //check if a room at this time slot is assigned to some class
    public boolean isAssigned(Rooms r){
        return course.get(r.getID()) != null;
    }
}
