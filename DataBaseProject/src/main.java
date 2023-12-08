import java.sql.*;
import java.sql.Date;
import java.util.*;

public class main{
    public static void main(String args[]) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Connected to the database!");
            DatabaseHelper db = new DatabaseHelper();

            //creating the tables for the database
            db.createPersonTable(connection);
            db.createFacultyTable(connection);
            db.createCardTable(connection);
            db.createBookTable(connection);
            db.createLibrarianTable(connection);
            db.createTransactionTable(connection);

            //person dummy data
            Person personOne = new Person(100100100, "Alice Smith", 2221114453L, "746 Broadway Apartment 1F");
            Person personTwo = new Person(007220130, "Mary Austin", 4349670982L, "5 Corona, Apartment 2E");
            Person personThree = new Person(111220321, "John Smith", 2120349874L, "Ridgewood apartment#2C");

            //faculty dummy data
            Faculty facultyOne = new Faculty(personOne.getSsn(), personOne.getName(), personOne.getPhone(), personOne.getAddress(), 333444222);
            Faculty facultyTwo = new Faculty(personThree.getSsn(), personThree.getName(), personThree.getPhone(), personThree.getAddress(), 342000122);

            //librarian dummy data
            Librarian librarianOne = new Librarian(222111333, "Chief Librarian");

            //book dummy data
            Book bookOne = new Book(1234567831234L,"To Kill a Mockingbird","Race, justice, and morality", "Harper Lee", 1 );
            Book bookTwo = new Book(1234567831234L,"To Kill a Mockingbird","Race, justice, and morality", "Harper Lee", 2 );
            Book bookThree = new Book(1234507890034L, "Harry Potter", "Fantasy", "J.K.Rowling", 1);
            //creating date object since card stores SQL date
            java.sql.Date sqlDate = new java.sql.Date(2023 - 1900, 11, 1);
            java.sql.Date sqlDateTwo = new java.sql.Date(2023 - 1900, 12, 15);

            //card dummy data
            Card cardOne = new Card(123456789653L, sqlDate, personOne.getSsn());
            Card cardTwo = new Card(123400019653L, sqlDateTwo,personThree.getSsn());

            // //creating date object since transaction stores SQL date
            java.sql.Date tranOneSqlDate = new java.sql.Date(2023 - 1900, 12, 15);
            java.sql.Date tranOneReturnSqlDate = new java.sql.Date(2024 - 1900, 1, 15);

            //creating dummy transaction data
            Transaction transactionOne = new Transaction(001200214,tranOneSqlDate, bookOne.getCopy_number(), bookOne.getISBN(), tranOneReturnSqlDate, librarianOne.getLibrarian_ID(), cardOne.getCardNumber() );
            Transaction transactionTwo = new Transaction(003312012, tranOneSqlDate, bookThree.getCopy_number(), bookThree.getISBN(), tranOneReturnSqlDate, librarianOne.getLibrarian_ID(), cardTwo.getCardNumber());

            // Create and insert a new person
            db.insertPerson(connection, personOne);
            db.insertPerson(connection, personTwo);
            db.insertPerson(connection, personThree);
            db.insertFaculty(connection, facultyOne);
            db.insertFaculty(connection, facultyTwo);
            db.insertLibrarian(connection, librarianOne);
            db.insertBook(connection, bookOne);
            db.insertBook(connection, bookTwo);
            db.insertBook(connection, bookThree);
            db.insertCard(connection, cardOne);
            db.insertCard(connection, cardTwo);
            db.insertTransaction(connection, transactionOne);
            db.insertTransaction(connection, transactionTwo);



            // Show the contents of the "Person" table
            List<Person> persons = db.getAllPersons(connection);
            for (Person person : persons) {
                System.out.println(person);
            }

            personOne.setPhone( 999222000);
            personOne.setAddress("345 Broadway Apartment 3C");
            librarianOne.setLibrarian_title("Janitor");
            bookOne.setTitle("Of mice and men");

            java.sql.Date sqlDateNew = new java.sql.Date(2024 - 1900, 2, 2);
            cardOne.setExpirationDate(sqlDateNew);
            transactionOne.setReturn_date(sqlDateNew);

            // Modify information
            db.modifyPerson(connection, personOne);
            db.modifyLibrarian(connection, librarianOne);
            db.modifyBook(connection, bookOne);
            db.modifyCard(connection, cardOne);
            db.modifyTransaction(connection, transactionOne);

            // Show the updated contents of the "Person" table
            List<Person> updatedPersons = db.getAllPersons(connection);
            for (Person person : updatedPersons) {
                System.out.println("updated person " + person);
            }

            // Delete
            db.deletePerson(connection, personTwo);
            db.deleteFaculty(connection, facultyTwo);
            db.deleteLibrarian(connection, librarianOne);
            //deletes book copy
            db.deleteBook(connection, bookOne);
            db.deleteCard(connection, cardOne);
            db.deleteTransaction(connection, transactionOne);

            // Show the final contents of the "Person" table
            List<Person> finalPersons = db.getAllPersons(connection);
            for (Person person : finalPersons) {
                System.out.println("all person " + person);
            }

            List<Faculty> facultyWithBorrowedBook = db.getAllFacultyWhoCheckedOutBook(connection);
            for(Faculty faculty: facultyWithBorrowedBook) {
                System.out.println("faculty: " + faculty);
            }

            List<Book> bookWithMultipleCopies = db.getAllBooksWithMultipleCopies(connection);
            for(Book book: bookWithMultipleCopies) {
                System.out.println("book with multiple copies " + book.getTitle());
            }

            List<Book> allbooksfromdb = db.getEveryBook(connection);
            System.out.println("All the books in the db:");
            for(Book book:allbooksfromdb) {
                System.out.println(book.getTitle());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}