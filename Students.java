import java.util.ArrayList;
/* Students class represent a student and its preference list and final registration
 * 
 */
public class Students {
    //instances field
    private int ID; //unique id for each student
    private Courses [] pref; // 4 classes in preference list
    private ArrayList<Courses> reg = new ArrayList<Courses>(); 
                            //a list of final classes the student would take

    /* constructor
     * @param i, the unique id of the student
     */
    public Students(int i){
        ID = i;
        pref = new Courses[4];
        pref[0] = null;
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

    public Courses [] getPref(){
        return pref;
    }

    //different ways to construct a student's preference list (as an array)
    public void addPref(Courses c){
        for (int i = 0; i < 4; i++){
            if(pref[i] == null ){
                pref[i] = c;
            }
        }
    }

    public void addPref(Courses c1, Courses c2){
        boolean add1 = true;
        boolean add2 = true;
        for (int i = 0; i < 4; i++){
            if(pref[i] == null && add1){
                pref[i] = c1;
                add1 = false;
            }
            else if(pref[i] == null && add2){
                pref[i] = c2;
                add2 = false;
            } else {

            }
        }
    }

    public void addPref(Courses c1, Courses c2, Courses c3){
        boolean add1 = true;
        boolean add2 = true;
        boolean add3 = true;
        for (int i = 0; i < 4; i++){
            if(pref[i] == null && add1){
                pref[i] = c1;
                add1 = false;
            }
            else if(pref[i] == null && add2){
                pref[i] = c2;
                add2 = false;
            }
            else if(pref[i] == null && add3){
                pref[i] = c3;
                add3 = false;
            } else {

            }
        }
    }

    public void addPref(Courses c1, Courses c2, Courses c3, Courses c4){
        boolean add1, add2, add3, add4;
        add1 = true;
        add2 = true;
        add3 = true;
        add4 = true;
        for (int i = 0; i < 4; i++){
            if(add1){
                pref[i] = c1;
                add1 = false;
            } 
            else if(add2){
                pref[i] = c2;
                add2 = false;
            }
            else if(add3){
                pref[i] = c3;
                add3 = false;
            } else if(add4){
                pref[i] = c4;
                add4 = false;
            }else{

            }
        }
    }

    public void addPref(Courses [] c){
        pref = c; 
    }

    /* addReg will add the course into the student's registration list
     * @param c, the course that the student will take
     */
    public void addReg(Courses c){
        reg.add(c);
    }

    
}
