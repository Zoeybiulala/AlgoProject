import java.util.ArrayList;

public class Students {
    private int ID;
    private Courses [] pref;
    private ArrayList<Courses> reg = new ArrayList<Courses>();

    public Students(int i){
        ID = i;
        pref = new Courses[4];
        pref[0] = null;
        //reg is already initialized
    }

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

    public void addReg(Courses c){
        reg.add(c);
    }

    public void addPref(Courses [] c){
        pref = c; 
    }
}
