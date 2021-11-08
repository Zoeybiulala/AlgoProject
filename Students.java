import java.util.ArrayList;
/* Students class represent a student and its preference list and final registration
 * 
 */
public class Students {
    //instances field
    private int ID; //unique id for each student
    private ArrayList<Courses> pref = new ArrayList<Courses>(); 
                            //a list of final classes the student would take
    private ArrayList<Courses> reg = new ArrayList<Courses>(); 
                            //a list of final classes the student would take
    private String name;
    /* constructor
     * @param i, the unique id of the student
     */
    public Students(int i, String n){
        ID = i;
        name = n;
        //reg is already initialized
    }
    
    //getters and setters
    public int getID(){
        return ID;
    }

    public int getRegNum(){
        return reg.size();
    }

    public ArrayList<Courses> getReg(){
        return reg;
    }

    public ArrayList<Courses> getPref(){
        return pref;
    }

    public String getName() {
        return name;
    }

    //different ways to construct a student's preference list (as an array)
    public void addPref(Courses c){
        pref.add(c);
    }

    /* addReg will add the course into the student's registration list
     * @param c, the course that the student will take
     */
    public void addReg(Courses c){
        reg.add(c);
    }

    
}
