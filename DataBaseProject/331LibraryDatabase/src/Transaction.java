
public class Transaction {
    
    private int Transaction_ID;
    private String date;
    private int copy_number;
    private int ISBN;
    private String return_date;
    private int Librarian_ID;

    public Transaction(){
        //Default constructor
    }

    public Transaction(int Transaction_ID, String date, int copy_number, int ISBN, String return_date, int Librarian_ID){
        this.Transaction_ID = Transaction_ID;
        this.date = date;
        this.copy_number = copy_number;
        this.ISBN = ISBN;
        this.return_date = return_date;
        this.Librarian_ID = Librarian_ID;
    }


    public int getTransaction_ID() {
        return Transaction_ID;
    }

    public void setTransaction_ID(int Transaction_ID) {
        this.Transaction_ID = Transaction_ID;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public int getCopy_number() {
        return copy_number;
    }

    public void setCopy_number(int copy_number) {
        this.copy_number = copy_number;
    }
    

    
    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    } 
    
    
    
    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }



    public int getLibrarian_ID() {
        return Librarian_ID;
    }

    public void setLibrarian_ID(int Librarian_ID) {
        this.Librarian_ID = Librarian_ID;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                " Transaction_ID = " + Transaction_ID +
                ", date = '" + date + '\'' +
                ", copy_number = " + copy_number + 
                ", ISBN = " + ISBN + 
                ", return_date = '" + return_date + '\'' +
                ", Librarian_ID = " + Librarian_ID + 
                '}';
    }

}
