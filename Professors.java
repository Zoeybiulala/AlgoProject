import java.util.ArrayList;
/**
 * class of Professors
 */
public class Professors {
    //instances field
    private int ID; //unique id for the professor
    private ArrayList<Courses> teach; //the courses this professor will teach

    /**
     * constructor of Professors
     * @param i is the unique id for the professor
     */
    public Professors(int i){
        ID = i;
        teach = new ArrayList<Courses>();
    }



    public int getID(){
        return ID;
    }

    public ArrayList<Courses> getTeach() {
        return teach;
    }

    public void addCourse(Courses c){
        teach.add(c);
    }

    //check if this professor and another professor is the same professor
    public boolean equals(Professors a){
        return (a.ID == ID);
    }
}
