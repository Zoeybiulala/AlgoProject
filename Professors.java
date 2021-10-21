/**
 * class of Professors
 */
public class Professors {
    //instances field
    private int ID; //unique id for the professor
    private Courses [] teach; //the courses this professor will teach

    /**
     * constructor of Professors
     * @param i is the unique id for the professor
     */
    public Professors(int i){
        ID = i;
        teach = new Courses[1];
    }

    public Professors(int i, Courses [] t){
        ID = i;
        teach = t;
    }

    public int getID(){
        return ID;
    }

    public void addCourse(Courses c){
        int size = teach.length;
        if (teach[size - 1] == null){
            teach[size - 1] = c;
            return;
        }
        Courses [] temp = new Courses[size+1];
        temp[size] = c;
        teach = temp; 
    }

    public void addCourse(Courses [] c){
        teach = c; 
    }

    //check if this professor and another professor is the same professor
    public boolean equal(Professors a){
        return (a.ID == ID);
    }
}
