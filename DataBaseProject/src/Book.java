public class Book {
    
    private long ISBN;
    private String title;
    private String description;
    private String author_name;
    private int copy_number;


    public Book(){
        //default constructor
    }

    public Book(long ISBN, String title, String description, String author_name, int copy_number){
        this.ISBN = ISBN;
        this.title = title;
        this.description = description;
        this.author_name = author_name;
        this.copy_number = copy_number;
    }

    public long getISBN() {
        return ISBN;
    }

    public void setISBN(long ISBN) {
        this.ISBN = ISBN;
    } 
    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    } 
    
    

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    } 
    
    
    public int getCopy_number() {
        return copy_number;
    }

    public void setCopy_number(int copy_number) {
        this.copy_number = copy_number;
    }


    @Override
    public String toString() {
        return "Book{" +
                "ISBN = " + ISBN + 
                ", title = '" + title + '\'' +
                ", description = '" + description + '\'' +
                ", author_name = '" + author_name + '\'' +
                ", copy_number = " + copy_number + 
                '}';
    }
}
