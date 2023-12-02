import java.sql.Date;

public class Transaction {
    
    private int Transaction_ID;
    private Date date;
    private int copy_number;
    private long ISBN;
    private Date return_date;
    private int Librarian_ID;
    private  long card_num;

    public Transaction(){
        //Default constructor
    }

    public Transaction(int Transaction_ID, Date date, int copy_number, long ISBN, Date return_date, int Librarian_ID, long card_num){
        this.Transaction_ID = Transaction_ID;
        this.date = date;
        this.copy_number = copy_number;
        this.ISBN = ISBN;
        this.return_date = return_date;
        this.Librarian_ID = Librarian_ID;
        this.card_num = card_num;
    }


    public int getTransaction_ID() {
        return Transaction_ID;
    }

    public void setTransaction_ID(int Transaction_ID) {
        this.Transaction_ID = Transaction_ID;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public int getCopy_number() {
        return copy_number;
    }

    public void setCopy_number(int copy_number) {
        this.copy_number = copy_number;
    }
    

    
    public long getISBN() {
        return ISBN;
    }

    public void setISBN(long ISBN) {
        this.ISBN = ISBN;
    } 
    
    
    
    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }



    public int getLibrarian_ID() {
        return Librarian_ID;
    }

    public void setLibrarian_ID(int Librarian_ID) {
        this.Librarian_ID = Librarian_ID;
    }

    public long getCard_num() {
        return card_num;
    }

    public void setCard_num(long card_num) {
        this.card_num = card_num;
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
