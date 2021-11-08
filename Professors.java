import java.util.ArrayList;
/**
 * class of Professors
 */
public class Professors {
    //instances field
    private int ID; //unique id for the professor
    private String name;
    private ArrayList<Courses> teach = new ArrayList<Courses>(); //the courses this professor will teach

    /**
     * constructor of Professors
     * @param i is the unique id for the professor
     */
    public Professors(int i, String n){
        ID = i;
        name = n;
    }

    public Professors(String n) {
        name = n;
    }


    public int getID(){
        return ID;
    }

    public String getName(){
        return name;
    }

    public void addCourse(Courses c){
        teach.add(c);
    }

    public ArrayList<Courses> getCourse(){
        return teach;
    }


    //check if this professor and another professor is the same professor
    public boolean equals(Professors a){
        return (a.getName().equals(name));
    }
}
