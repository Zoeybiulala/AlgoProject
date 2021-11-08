import java.util.ArrayList;
import java.util.*;
/** class of Rooms
 */
public class TimeSlots {
    //instances field
    private int ID; //unique id for Time Slots
    private Rooms [] room; //the rooms that are avaiable for this time slot
    private ArrayList<Courses> course = new ArrayList<Courses>(); //assigned courses at this time slot
    private int start;
    private int end;
    private boolean[] weekdays;
    private ArrayList<TimeSlots> conflictTimes = new ArrayList<TimeSlots>();
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

    public TimeSlots(int i, int s, int e, String day){
        ID = i;
        start = s;
        end = e;
        weekdays = new boolean[5];
        for(int j =0; j< day.length();j++) {
            if(day.charAt(j) == 'M') {
                weekdays[0] = true;
            } 
            if(day.charAt(j) == 'T' && j<day.length()-1) {
                if(day.charAt(j+1)!= 'H'){
                    weekdays[1] = true;
                } else {
                    weekdays[3] = true;
                }
            } 
            if(day.charAt(j) == 'T' && j == day.length()-1) {
                weekdays[1] = true;
            }
            if(day.charAt(j) == 'W') {
                weekdays[2] = true;
            }
            if(day.charAt(j) == 'F') {
                weekdays[4] = true;
            }
        }
    }

    public boolean isOverlapping(TimeSlots t1) {
        boolean  sameDay = false;
        for(int i=0; i< 5;i ++) {
            if(weekdays[i] && t1.weekdays[i]) {
                sameDay = true;
                break;
            }
        }
        if(sameDay == false) return false;
        if((t1.start<=start && start <=t1.end)|| (t1.start<=end  && end <=t1.end)
         || (start<=t1.start && end >= t1.end) || (start>=t1.start && end<=t1.end)) {
            return true;
        }
        return false;

    }
    public void addConflictTime(TimeSlots t){
        conflictTimes.add(t);
    }

    public ArrayList<TimeSlots> getConflictTime(){
        return conflictTimes;
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

    public void addClass(Courses c){
        course.add(c);
    }
}
