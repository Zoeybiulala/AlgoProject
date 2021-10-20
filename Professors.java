public class Professors {
    private int ID;
    private Courses [] canTeach;
    private Courses [] toTeach;

    public Professors(int i){
        ID = i;
        canTeach = new Courses[1];
        canTeach[0] = null;
        toTeach = new Courses[1];
        toTeach[0] = null;
    }

    public Professors(int i, Courses [] canT){
        ID = i;
        canTeach = canT;
        toTeach = new Courses[1];
        toTeach[0] = null;
    }

    public int getID(){
        return ID;
    }

    public void addPref(Courses c){
        int size = canTeach.length;
        if (canTeach[size - 1] == null){
            canTeach[size - 1] = c;
            return;
        }
        Courses [] temp = new Courses[size+1];
        temp[size] = c;
        canTeach = temp; 
    }

    public void addPref(Courses [] c){
        canTeach = c; 
    }

    public void addTeach(Courses c){
        int size = canTeach.length;
        if (toTeach[size - 1] == null){
            toTeach[size - 1] = c;
            return;
        }
        Courses [] temp = new Courses[size+1];
        temp[size] = c;
        toTeach = temp; 
    }

    public void addTeach(Courses [] c){
        toTeach = c; 
    }

    public boolean equal(Professors a, Professors b){
        return (a.ID == b.ID);
    }
}
