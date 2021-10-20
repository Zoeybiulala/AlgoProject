public class Professors {
    private int ID;
    private Courses [] teach;

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

    public boolean equal(Professors a){
        return (a.ID == ID);
    }
}
