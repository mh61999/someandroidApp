package il.ac.tcb.st.finalassignment.Assists;

public class User {
    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    String FirstName;
    String LastName;

    public User(String FirstName,String LastName){
        this.FirstName = FirstName;
        this.LastName=LastName;
    }
}
