public class Faculty extends Person {

    private int Faculty_ID;

    //default constructor
    public Faculty() {
        // Default constructor
    }

    public Faculty (int SSN, String name, long phone, String address, int Faculty_ID) {
        super(SSN, name, phone, address); // Calling constructor of superclass Person
        this.Faculty_ID = Faculty_ID;
    }
    
    public int getFacultyID() {
        return Faculty_ID;
    }

    public void setFacultyID (int Faculty_ID) {
        this.Faculty_ID = Faculty_ID;
    }

    public String toString() {
        return "Faculty{" +
                "ssn=" + getSsn() +
                ", phone=" + getPhone() +
                ", name='" + getName() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", facultyID=" + Faculty_ID +
                '}';
    }
}