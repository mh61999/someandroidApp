package il.ac.tcb.st.finalassignment.Assists;

import java.util.List;

public class Games {
    public Games(){}
    public Games(List<Game> array) {
        Array = array;
    }

    public List<Game> getArray() {
        return Array;
    }

    public void addtoArray(Game d){Array.add(d);}
    public void setArray(List<Game> array) {
        Array = array;
    }

    List<Game> Array;
}
