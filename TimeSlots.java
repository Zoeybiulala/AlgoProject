public class TimeSlots {
    private int ID;
    private Rooms [] room;
    private Courses [] course;

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

    public TimeSlots(int i, Courses [] c){
        ID = i;
        room = null;
        course = c;
    }

    public TimeSlots(int i, Rooms [] r, Courses [] c){
        ID = i;
        room = r;
        course = c;
    }

    public void setRoom(Rooms [] r){
        room = r;
    }

    public void setCourse(Courses [] c){
        course = c;
    }

    public int getID(){
        return ID;
    }

    public Rooms [] getRooms(){
        return room;
    }

    public Courses [] getCourse(){
        return course;
    }

    public Courses getClass(Rooms r){
        return course[r.getID()];
    }

    public boolean isAssigned(Rooms r){
        return course[r.getID()] != null;
    }
}
