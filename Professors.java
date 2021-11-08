import java.util.ArrayList;
/**
 * class of Professors
 * Professor with have a name, stored in String and a list of courses it teach, stored in ArrayList
 */
public class Professors {
    //instances field
    private String name;
    private ArrayList<Courses> teach = new ArrayList<Courses>(); //the courses this professor will teach

    /**
     * constructor of Professors
     */

    public Professors(String n) {
        name = n;
    }

    /** getters */

    public String getName(){
        return name;
    }

    public ArrayList<Courses> getCourse(){
        return teach;
    }

    /** setters */
    public void setName(String n){
        name = n;
    }

    public void setCourse(ArrayList<Courses> c){
        teach = c;
    }

    /**
     * adding a course that the prof will teach
     * @param c
     */
    public void addCourse(Courses c){
        teach.add(c);
    }

    //check if this professor and another professor is the same professor
    public boolean equals(Professors a){
        return (a.getName().equals(name));
    }
}
