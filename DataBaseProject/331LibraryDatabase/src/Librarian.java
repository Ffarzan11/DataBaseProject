public class Librarian {
    
    private int Librarian_ID;
    private String librarian_title;


    public Librarian(){
        //default constructor
    }

    public Librarian(int Librarian_ID, String librarian_title){
        this.Librarian_ID = Librarian_ID;
        this.librarian_title = librarian_title;
    }


    public int getLibrarian_ID() {
        return Librarian_ID;
    }

    public void setLibrarian_ID(int Librarian_ID) {
        this.Librarian_ID = Librarian_ID;
    }


    public String getLibratian_title() {
        return librarian_title;
    }

    public void setLibrarian_title(String librarian_title) {
        this.librarian_title = librarian_title;
    }
    

    @Override
    public String toString() {
        return "Librarian{" +
                " Librarian_ID = " + Librarian_ID +
                ", librarian_title = '" + librarian_title + '\'' +
                '}';
    }

}
