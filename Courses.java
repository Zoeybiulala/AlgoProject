/**
 * Description: The Courses will contain information about one course, including its location 
 *              in the array, the prof to teach this course, a list storing the room that it 
 *              be assigned in, its popularity, list of students who would like to 
 *              enroll and a list of registered students, two array storing the conflict value with
 *              each other courses, and another which we used to count fo the final conflict value
 *              and one assigned room, a name, a subject and whether it has lab.
 * 
 * Date Updated: Nov.9, 2021
 * Author: Tianbo Yang, Yitian Cao, Xinran Liu
 */
import java.util.ArrayList;

public class Courses{
    /**
     * ID: unique id for a course
     * registrationList: a list of registered students
     * prof: the professor who will teach the class
     * popularity: the number of students who want to take this class
     * room: the assigned room for this class
     * validRooms: list of valid rooms this class can be taught in
     * time: the assigned time for this class
     * classConflict: the index of the array is the each class and the value represents 
     *                  the conflict number of this class with the class at certain index
     * finalConflict: the index of the array is the time slot and the value represents
                        a value that represents the conflict score at the given time slot
     * name: name for the course
     * subject: the subject for this class determines the lab room assignment
     * lab: whether this class has a lab
     */
    private int ID; 
    private ArrayList<Students> registrationList = new ArrayList<Students>();
    private Professors prof; 
    private int popularity; 
    private Rooms room; 
    private ArrayList<Rooms> validRooms = new ArrayList<Rooms>(); 
    private ArrayList<TimeSlots> time = new ArrayList<TimeSlots>(); 
    private int [] classConflict; 
    private int [] finalConflict; 
    private String name;
    private String subject; 
    private boolean lab;

    /* Constructor
     * @param i, the id of the course
     * @param cap, the number of classes to be scheduled
     * @param t, number of timeslots
     */
    public Courses (int i, int cap, int t, String n){
        ID = i;
        //registration list already initialized
        classConflict = new int[cap];
        finalConflict = new int[t];
        prof = null;
        popularity = 0;
        room = null;
        name = n;
        subject = null;
        lab = false;
    }

    /** getters */
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

    public ArrayList<Rooms> getValidRooms(){
        return validRooms;
    }

    public Professors getPro(){
        return prof;
    }

    public ArrayList<TimeSlots> getTime(){
        return time;
    }

    public String getSubject(){
        return subject;
    }

    public boolean hasLab(){
        return lab;
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

    public String getName(){
        return name;
    }

    /** setters */

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

    public void setValidRooms(ArrayList<Rooms> vR){
        validRooms = vR;
    }

    public void setTime(ArrayList<TimeSlots> t){
        time = t;
    }

    public void setSubject(String s){
        subject = s;
    }

    public void setLab(boolean l){
        lab = l;
    }

    /**
     * increment popularity of the class
     */
    public void incrPop(){
        popularity++;
    }

    /**
     * increment the conflict number of this class with another class c
     */
    public void incrCon(Courses c){
        classConflict[c.ID]++;
    }
    /**
     * @Override
     * @param c
     */
    public void incrCon(int c){
        classConflict[c]++;
    }

    /**
     * set the conflict number of this class with another class c
     * @param c course
     * @param i conflict number
     */
    public void setCon(Courses c, int i){
        classConflict[c.ID] = i;
    }

    /**
     * @Override
     * @param c
     * @param i
     */
    public void setCon(int c, int i){
        classConflict[c] = i;
    }

    /**
     * add a student s to the registration list 
     */
    public void addStu(Students s){
        registrationList.add(s);
    }

    /** 
     * add a single timeSlots to the course
     */
    public void addTime(TimeSlots t){
        time.add(t);
    }

    /**
     * to see if the class is scheduled or not
     * @return true if it is not scheduled
     */
    public boolean notScheduled(){
        return (time.size()==0)||(room==null);
    }

    /**
     * evaluates whether the class has a lab section based on subject
     */
    public void toLab(){
        if(subject.equals("MATH") 
        || subject.equals("CHEM") 
        || subject.equals("PSYC") 
        || subject.equals("BIOL") 
        || subject.equals("ECON")
        || subject.equals("PHYS")
        || subject.equals("CMSC")){
            lab = true;
        }
    }
}