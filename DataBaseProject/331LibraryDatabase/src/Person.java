public class Person {

    private int ssn;
    private int phone;
    private String name;
    private String address;

    public Person() {
        // Default constructor
    }

    public Person(int ssn, int phone, String name, String address) {
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
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
