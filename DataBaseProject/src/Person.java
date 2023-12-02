public class Person {

    private int ssn;
    private long phone;
    private String name;
    private String address;

    public Person() {
        // Default constructor
    }

    public Person(int ssn, String name,  long phone, String address) {
        this.ssn = ssn;
        this.phone = phone;
        this.name = name;
        this.address = address;
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "ssn=" + ssn +
                ", phone=" + phone +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
