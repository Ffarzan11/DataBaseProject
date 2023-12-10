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
            Librarian librarianTwo = new Librarian(295101336, "Checkout Librarian");

            //book dummy data
            Book bookOne = new Book(1234567831234L,"To Kill a Mockingbird","Race, justice, and morality", "Harper Lee", 1 );
            Book bookTwo = new Book(1234567831234L,"To Kill a Mockingbird","Race, justice, and morality", "Harper Lee", 2 );
            Book bookThree = new Book(1234507890034L, "Harry Potter", "Fantasy", "J.K.Rowling", 1);

            Book newBook = new Book(1204567531234L, "The Catcher in the Rye", "Coming-of-age novel", "J.D. Salinger", 1);
            Book newbook1 = new Book(1204567531234L, "The Catcher in the Rye", "Coming-of-age novel", "J.D. Salinger",2);
            Book newbook2 = new Book(1204567531234L, "The Catcher in the Rye", "Coming-of-age novel", "J.D. Salinger",3);

            Book book6 = new Book(3000000006789L, "The Lord of the Rings: The Fellowship of the Ring", "Epic quest to destroy ring", "J.R.R. Tolkien", 1);
            Book book8 = new Book(3000000008901L, "The Da Vinci Code", "Mystery of religion and art", "Dan Brown", 1);
            Book book9 = new Book(3000000009012L, "The Alchemist", "Shepherd's journey to self-discovery", "Paulo Coelho", 1);
            Book book10 = new Book(3000000010123L, "Life of Pi", "Survival tale with a tiger", "Yann Martel", 1);
            Book book11 = new Book(3000000010123L, "Life of Pi", "Survival tale with a tiger", "Yann Martel", 2);


            //creating date object since card stores SQL date
            java.sql.Date sqlDate = new java.sql.Date(2020 - 1900, 11, 1);
            java.sql.Date sqlDateTwo = new java.sql.Date(2021 - 1900, 12, 15);

            //card dummy data
            Card cardOne = new Card(123456789653L, sqlDate, personOne.getSsn());
            Card cardTwo = new Card(123400019653L, sqlDateTwo,personThree.getSsn());
            Card cardThree = new Card(123411119653L,sqlDateTwo,personTwo.getSsn());

            // //creating date object since transaction stores SQL date
            java.sql.Date tranOneSqlDate = new java.sql.Date(2020 - 1900, 11, 15);
            java.sql.Date tranOneReturnSqlDate = new java.sql.Date(2021 - 1900, 1, 15);

            java.sql.Date tranNewSqlDate = new java.sql.Date(2024-1900,11,15);
            java.sql.Date tranNewreturnSQLDate = new java.sql.Date(2024-1900,5,3);


            //creating dummy transaction data
            Transaction transactionOne = new Transaction(101200214,tranOneSqlDate, bookOne.getCopy_number(), bookOne.getISBN(), tranOneReturnSqlDate, librarianOne.getLibrarian_ID(), cardOne.getCardNumber() );
            Transaction transactionTwo = new Transaction(103312012, tranOneSqlDate, bookThree.getCopy_number(), bookThree.getISBN(), tranOneReturnSqlDate, librarianTwo.getLibrarian_ID(), cardTwo.getCardNumber());
            Transaction transactionThree = new Transaction(105800217,tranOneSqlDate, bookTwo.getCopy_number(), bookTwo.getISBN(), tranOneReturnSqlDate, librarianOne.getLibrarian_ID(), cardTwo.getCardNumber() );

            // creating dummy transaction for only checked_out books

            Transaction transactionFour,transactionFive,transactionSix;

            transactionFour = new Transaction(123456789,tranNewSqlDate, newBook.getCopy_number(), newBook.getISBN(), tranNewreturnSQLDate, librarianOne.getLibrarian_ID(), cardThree.getCardNumber());
            transactionFive = new Transaction(987654321,tranNewSqlDate, newbook1.getCopy_number(), newbook1.getISBN(), tranNewreturnSQLDate, librarianOne.getLibrarian_ID(), cardOne.getCardNumber());
            transactionSix = new Transaction(234567890,tranNewSqlDate, newbook2.getCopy_number(), newbook2.getISBN(), tranNewreturnSQLDate, librarianOne.getLibrarian_ID(), cardThree.getCardNumber());


            // Dummy data for a new book


            // Insert the new book and transaction into the database
            db.insertBook(connection, newBook);
            db.insertBook(connection,newbook1);
            db.insertBook(connection,newbook2);
            db.insertBook(connection,book6);
            db.insertBook(connection,book8);
            db.insertBook(connection,book9);
            db.insertBook(connection,book10);
            db.insertBook(connection,book11);

            // Create and insert a new person
            db.insertPerson(connection, personOne);
            db.insertPerson(connection, personTwo);
            db.insertPerson(connection, personThree);
            db.insertFaculty(connection, facultyOne);
            db.insertFaculty(connection, facultyTwo);
            db.insertLibrarian(connection, librarianOne);
            db.insertLibrarian(connection, librarianTwo);
            db.insertBook(connection, bookOne);
            db.insertBook(connection, bookTwo);
            db.insertBook(connection, bookThree);
            db.insertCard(connection, cardOne);
            db.insertCard(connection, cardTwo);
            db.insertCard(connection,cardThree);
            db.insertTransaction(connection, transactionOne);
            db.insertTransaction(connection, transactionTwo);
            db.insertTransaction(connection,transactionThree);
            db.insertTransaction(connection, transactionFour);
            db.insertTransaction(connection, transactionFive);
            db.insertTransaction(connection,transactionSix);



            // Show the contents of the "Person" table
            List<Person> persons = db.getAllPersons(connection);
            for (Person person : persons) {
                System.out.println(person);
            }

            personOne.setPhone( 999222000);
            personOne.setAddress("345 Broadway Apartment 3C");
            librarianOne.setLibrarian_title("Janitor");
            bookThree.setTitle("Of mice and men");

            java.sql.Date sqlDateNew = new java.sql.Date(2021 - 1900, 2, 2);
            cardOne.setExpirationDate(sqlDateNew);
            transactionTwo.setReturn_date(sqlDateNew);
            transactionOne.setReturn_date(sqlDateNew);

            // Modify information
            db.modifyPerson(connection, personOne);
            db.modifyLibrarian(connection, librarianOne);
            db.modifyBook(connection, bookThree);
            db.modifyCard(connection, cardOne);
            db.modifyTransaction(connection,transactionTwo);
            db.modifyTransaction(connection, transactionOne);

            // Show the updated contents of the "Person" table
            List<Person> updatedPersons = db.getAllPersons(connection);
            for (Person person : updatedPersons) {
                System.out.println("updated person " + person);
            }

             // Delete the Person with ssn 007220130
             db.deletePerson(connection, personTwo);
            //Delete the Faculty with faculty_id 342000122
             db.deleteFaculty(connection, facultyTwo);
             //Delete the Librarian with librarian_id 295101336
             db.deleteLibrarian(connection, librarianTwo);
            //deletes Book copy with ISBN 1234567831234 and copy_number 1
             db.deleteBookCopy(connection, bookOne);
             //deletes the Book based on ISBN 1234567831234
             db.deleteBook(connection, bookTwo);
             //deletes the Card with card_number 123456789653
             db.deleteCard(connection, cardOne);
             //deletes the Transaction with transaction_id 101200214
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
            System.out.println("book with multiple copies: ");
            for(Book book: bookWithMultipleCopies) {
                System.out.println(book.getTitle());

            }

            List<Book> allbooksfromdb = db.getEveryDistinctBook(connection);
            System.out.println("All the books in the db:");
            for(Book book:allbooksfromdb) {
                System.out.println(book.getTitle());
            }

            List<Person> NonfacultyCardHolder = db.getAllNonFacultyCardHolder(connection);
            System.out.println("Non Faculty Card Holder In the library: ");
            for(Person person:NonfacultyCardHolder)
             {
                 System.out.println(person.getName());
            }

            List<Transaction> allTransactions = db.getEveryTransactionReport(connection);

            System.out.println("All Transactions:");
            for (Transaction transaction : allTransactions) {
                System.out.println(transaction);
            }

            List<Book> overdueBooks= db.getOverdueBooks(connection);
            System.out.println("All overduebooks:");
            for(Book overdueBook :overdueBooks ){
                System.out.println(overdueBook);
            }

            List<Book> allCheckedOutBooksFromDb = db.getALLCheckedOutBooks(connection);
            System.out.println("All Checked_Out books in db:");
            for(Book book:allCheckedOutBooksFromDb) {
                System.out.println(book.getTitle() + ",Copy #: " + book.getCopy_number());
            }


            List<String> booksUsedMoreThanOnce = db.getBooksUsedMoreThanOnce(connection);
            System.out.println("Most Popular Checked_Out Books:");
            for (String bookTitle : booksUsedMoreThanOnce) {
                System.out.println(bookTitle);
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}