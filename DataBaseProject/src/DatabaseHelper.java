import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class DatabaseHelper {

    //Strings to create table
    private static final String PersonTable =  "CREATE TABLE IF NOT EXISTS Person ("
            + "ssn INT(9),"
            + "phone BIGINT(10),"
            + "name VARCHAR(40),"
            + "address VARCHAR(100),"
            + "PRIMARY KEY (ssn)"
            + ")";
    private static final String FacultyTable = "CREATE TABLE IF NOT EXISTS Faculty ("
            + "faculty_id INT(9),"
            + "ssn INT(9),"
            + "PRIMARY KEY (faculty_id),"
            + "FOREIGN KEY (ssn) REFERENCES Person(ssn)"
            + ")";
    private static final String LibrarianTable = "CREATE TABLE IF NOT EXISTS Librarian ("
            + "librarian_id INT(9),"
            + "librarian_title VARCHAR(25),"
            + "PRIMARY KEY (librarian_id)"
            + ")";
    private static final String BookTable =  "CREATE TABLE IF NOT EXISTS Book ("
            + "ISBN BIGINT(13),"
            + "book_title VARCHAR(100),"
            + "book_description VARCHAR(1500),"
            + "author VARCHAR(50),"
            + "copy_number INT(3),"
            + "PRIMARY KEY (ISBN, copy_number)"
            + ")";

    private static final String IndexExistsQuery = "SELECT COUNT(*) AS index_count FROM information_schema.statistics WHERE table_name = 'Book' AND index_name = 'idx_copy_number'";
    private static final String CreateIndexCopyNumber = "CREATE INDEX idx_copy_number ON Book(copy_number)";

    private static final String CardTable = "CREATE TABLE IF NOT EXISTS Card ("
            + "card_number BIGINT(12),"
            + "expiration_date DATE,"
            + "ssn INT(9),"
            + "PRIMARY KEY (card_number),"
            + "FOREIGN KEY (ssn) REFERENCES Person(ssn)"
            + ")";
    private static final String TransactionTable = "CREATE TABLE IF NOT EXISTS Transaction ("
            + "transaction_id INT(9),"
            + "date_time DATE,"
            + "copy_number INT(3),"
            + "ISBN BIGINT(13),"
            + "return_date DATE,"
            + "librarian_id INT(9),"
            + "card_number BIGINT(12),"
            + "PRIMARY KEY (transaction_id),"
            + "FOREIGN KEY (ISBN, copy_number) REFERENCES Book(ISBN, copy_number),"
            //"FOREIGN KEY (ISBN) REFERENCES Book(ISBN),"
            + "FOREIGN KEY (librarian_id) REFERENCES Librarian(librarian_id),"
            + "FOREIGN KEY (card_number) REFERENCES Card(card_number)"
            + ")";


    //create the tables
    public void createPersonTable(Connection connection) throws SQLException {

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(PersonTable);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void createFacultyTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(FacultyTable);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCardTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CardTable);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createBookTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(BookTable);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(IndexExistsQuery)) {
            if (resultSet.next() && resultSet.getInt("index_count") == 0) {
                // Index does not exist, create it
                try (Statement createIndexStatement = connection.createStatement()) {
                    createIndexStatement.executeUpdate(CreateIndexCopyNumber);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createTransactionTable(Connection connection) throws SQLException {

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(TransactionTable);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createLibrarianTable(Connection connection) throws SQLException {

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(LibrarianTable);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param connection connection with the database
     * @param person Person to be inserted
     * @throws SQLException exception that will be thrown if the insert causes error
     */
    public void insertPerson(Connection connection,Person person) throws SQLException {
        createPersonTable(connection);

        String insertSQL = "INSERT INTO  Person (ssn, phone, name, address) VALUES (?, ?, ?, ?)";

        //Insert the person data to the database
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, person.getSsn());
            preparedStatement.setLong(2, person.getPhone());
            preparedStatement.setString(3, person.getName());
            preparedStatement.setString(4, person.getAddress());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param connection connection to database
     * @param faculty Faculty to be inserted
     * @throws SQLException Exception that will be thrown in there is an error while inserting
     */
    public void insertFaculty(Connection connection,Faculty faculty) throws SQLException {
        createFacultyTable(connection);

        String insertSQL = "INSERT INTO  Faculty (faculty_id, ssn) VALUES (?, ?)";

        //inserting the values to table
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, faculty.getFacultyID());
            preparedStatement.setInt(2, faculty.getSsn());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param connection connection to the database
     * @param librarian the librarian to be inserted
     * @throws SQLException throws SQL exception if there is an error inserting
     */
    public  void insertLibrarian(Connection connection, Librarian librarian) throws SQLException {
        createLibrarianTable(connection);

        String insertSQL = "INSERT INTO  Librarian (librarian_id, librarian_title) VALUES (?, ?)";
        //inserting librarian data to the table
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, librarian.getLibrarian_ID());
            preparedStatement.setString(2, librarian.getLibratian_title());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param connection connection to the database
     * @param book the book to be inserted
     * @throws SQLException throws sql exception if there is an error inserting book
     */
    public void insertBook(Connection connection, Book book) throws SQLException {
        createBookTable(connection);
        String insertSQL = "INSERT INTO Book (ISBN, book_title, book_description, author,copy_number) VALUES (?, ?, ?, ?, ?)";

        //inserting book data to the table
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setLong(1, book.getISBN());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getDescription());
            preparedStatement.setString(4, book.getAuthor_name());
            preparedStatement.setInt(5, book.getCopy_number());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param connection connection to database
     * @param card Card to be inserted
     * @throws SQLException throws SQL exception if there is an issue inserting the card
     */
    public  void insertCard(Connection connection, Card card) throws SQLException {
        createCardTable(connection);
        String insertSQL = "INSERT INTO  Card (card_number, expiration_date, ssn) VALUES (?, ?, ?)";
        //inserting card values to the table
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setLong(1, card.getCardNumber());
            preparedStatement.setDate(2, card.getExpirationDate());
            preparedStatement.setInt(3, card.getSSN());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param connection connection to the database
     * @param transaction transaction that is to be added in the database
     * @throws SQLException throws exception if there is an issue inserting the transaction
     */
    public void insertTransaction(Connection connection, Transaction transaction) throws SQLException {
        createTransactionTable(connection);
        String insertSQL = "INSERT INTO  Transaction (transaction_id," +
                " date_time, " +
                "copy_number," +
                "ISBN, " +
                "return_date, " +
                "librarian_id, " +
                "card_number) " +
                "VALUES (?, ?, ?, ?,?,?,?)";
        //inserting the transaction to db
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, transaction.getTransaction_ID());
            preparedStatement.setDate(2,transaction.getDate());
            preparedStatement.setInt(3, transaction.getCopy_number());
            preparedStatement.setLong(4, transaction.getISBN());
            preparedStatement.setDate(5, transaction.getReturn_date());
            preparedStatement.setInt(6, transaction.getLibrarian_ID());
            preparedStatement.setLong(7, transaction.getCard_num());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param connection connection to the database
     * @param person the person to be modified
     * @throws SQLException throws exception if the database fails to modify
     */
    public void modifyPerson(Connection connection, Person person) throws SQLException {
        //update the person whose ssn matches the ssn of the person given in parameter
        String modifySQL = "UPDATE Person SET phone = ?, name = ?, address = ? WHERE ssn = ?";

        //modifying person
        try (PreparedStatement preparedStatement = connection.prepareStatement(modifySQL)) {
            preparedStatement.setLong(1, person.getPhone());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, person.getAddress());
            preparedStatement.setInt(4, person.getSsn());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) modified.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param connection connection to the database
     * @param card Card to be modified
     * @throws SQLException throws SQL exception if there is an error modifying the  card
     */
    public  void modifyCard(Connection connection, Card card) throws  SQLException {
        //modify the card whose card number matches the card number of the card given in parameter
        String modifySQL = "UPDATE Card SET expiration_date = ?  WHERE card_number = ?";
        //modify card
        try(PreparedStatement preparedStatement = connection.prepareStatement(modifySQL)) {
            preparedStatement.setDate(1, card.getExpirationDate());
            preparedStatement.setLong(2, card.getCardNumber());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) modified.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param connection connection to the database
     * @param book the book to be modified
     * @throws SQLException throws exception if there is an error modifying the book
     */
    public  void  modifyBook(Connection connection, Book book) throws  SQLException {
        //modify the book whose ISBN matches the ISBN of the book given in param
        String modifySQL = "UPDATE Book SET book_title=?, book_description = ?, author=? WHERE ISBN = ? AND copy_number=?";
        //modifying the book
        try(PreparedStatement preparedStatement = connection.prepareStatement(modifySQL)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getDescription());
            preparedStatement.setString(3, book.getAuthor_name());
            preparedStatement.setLong(4, book.getISBN());
            preparedStatement.setInt(5, book.getCopy_number());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) modified.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void  modifyTransaction(Connection connection, Transaction transaction) throws  SQLException {
        //modify the transaction date and return date
        String modifySQL = "UPDATE Transaction SET date_time = ?, return_date =?  WHERE transaction_id = ?";
        //modifying the book
        try(PreparedStatement preparedStatement = connection.prepareStatement(modifySQL)) {
            preparedStatement.setDate(1, transaction.getDate());
            preparedStatement.setDate(2, transaction.getReturn_date());
            preparedStatement.setInt(3, transaction.getTransaction_ID());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) modified.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void  modifyLibrarian(Connection connection, Librarian librarian) throws  SQLException {
        //modify the librarian title
        String modifySQL = "UPDATE Librarian SET librarian_title= ? WHERE librarian_id = ?";
        //modifying the book
        try(PreparedStatement preparedStatement = connection.prepareStatement(modifySQL)) {
            preparedStatement.setString(1, librarian.getLibratian_title());
            preparedStatement.setInt(2, librarian.getLibrarian_ID());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) modified.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * @param connection database connection
     * @param person the person to be deleted
     * @throws SQLException throws SQL exception if there is an issue deleting person
     */
    public void deletePerson(Connection connection, Person person) throws SQLException {
        try {
            // Step 1: Select card_numbers that match with the person's ssn from the Card table
            String selectCardNumbersSQL = "SELECT card_number FROM Card WHERE ssn = ?";
            try (PreparedStatement selectCardNumbersStatement = connection.prepareStatement(selectCardNumbersSQL)) {
                selectCardNumbersStatement.setInt(1, person.getSsn());
                // Execute the query to get card_numbers
                try (var resultSet = selectCardNumbersStatement.executeQuery()) {
                    while (resultSet.next()) {
                        long cardNumber = resultSet.getLong("card_number");

                        // Step 2: Delete transactions with the selected card_number from the Transaction table
                        String deleteTransactionsSQL = "DELETE FROM Transaction WHERE card_number = ?";
                        try (PreparedStatement deleteTransactionsStatement = connection.prepareStatement(deleteTransactionsSQL)) {
                            deleteTransactionsStatement.setLong(1, cardNumber);
                            deleteTransactionsStatement.executeUpdate();
                        }

                        // Step 3: Delete records with the selected card_number from the Card table
                        String deleteCardSQL = "DELETE FROM Card WHERE card_number = ?";
                        try (PreparedStatement deleteCardStatement = connection.prepareStatement(deleteCardSQL)) {
                            deleteCardStatement.setLong(1, cardNumber);
                            deleteCardStatement.executeUpdate();
                        }
                    }
                }
            }

            // Step 4: Check if a faculty exists for the person's ssn and delete it from the Faculty table
            String deleteFacultySQL = "DELETE FROM Faculty WHERE ssn = ?";
            try (PreparedStatement deleteFacultyStatement = connection.prepareStatement(deleteFacultySQL)) {
                deleteFacultyStatement.setInt(1, person.getSsn());
                deleteFacultyStatement.executeUpdate();
            }

            // Step 5: Delete the person from the Person table
            String deletePersonSQL = "DELETE FROM Person WHERE ssn = ?";
            try (PreparedStatement deletePersonStatement = connection.prepareStatement(deletePersonSQL)) {
                deletePersonStatement.setInt(1, person.getSsn());
                int rowsAffected = deletePersonStatement.executeUpdate();
                System.out.println(rowsAffected + " row(s) deleted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception after printing the stack trace
        }
    }


    /**
     *
     * @param connection database connection
     * @param faculty the faculty to be deleted
     * @throws SQLException throws SQL exception if there is an issue deleting person
     */
    public void deleteFaculty(Connection connection,Faculty faculty) throws SQLException {
        //delete the faculty whose faculty_id matches the faculty_id of the faculty given on param
        String deleteSQL = "DELETE FROM Faculty WHERE faculty_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, faculty.getFacultyID());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }
    }
    /**
     *
     * @param connection database connection
     * @param librarian the librarian to be deleted
     * @throws SQLException throws SQL exception if there is an issue deleting person
     */
    public void deleteLibrarian(Connection connection,Librarian librarian) throws SQLException {
        //delete the transaction that has librarian_id that matches with the librarian_id given on param
        String deleteSQL1 = "DELETE FROM Transaction WHERE librarian_id = ?";
        //delete the librarian whose librarian_id matches the librarian_id of the librarian given on param
        String deleteSQL2 = "DELETE FROM Librarian WHERE librarian_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL1)) {
            preparedStatement.setInt(1, librarian.getLibrarian_ID());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
            try (PreparedStatement preparedStatement2 = connection.prepareStatement(deleteSQL2)) {
                preparedStatement2.setInt(1, librarian.getLibrarian_ID());

                int rowsAffected2 = preparedStatement2.executeUpdate();
                System.out.println(rowsAffected2 + " row(s) deleted.");
            }
        }
    }

    /**
     * Delete a book copy and related transactions from the database.
     *
     * @param connection the database connection
     * @param book       the book copy to be deleted
     * @throws SQLException if there is an issue deleting the book
     */
    public void deleteBookCopy(Connection connection, Book book) throws SQLException {
        try {
            // Step 1: Delete transactions with matching ISBN and copy_number from the Transaction table
            String deleteTransactionsSQL = "DELETE FROM Transaction WHERE ISBN = ? AND copy_number = ?";
            try (PreparedStatement deleteTransactionsStatement = connection.prepareStatement(deleteTransactionsSQL)) {
                deleteTransactionsStatement.setLong(1, book.getISBN());
                deleteTransactionsStatement.setInt(2, book.getCopy_number());
                int transactionsDeleted = deleteTransactionsStatement.executeUpdate();
                System.out.println(transactionsDeleted + " transaction(s) deleted.");
            }

            // Step 2: Delete the book with matching ISBN and copy_number from the Book table
            String deleteBookSQL = "DELETE FROM Book WHERE ISBN = ? AND copy_number = ?";
            try (PreparedStatement deleteBookStatement = connection.prepareStatement(deleteBookSQL)) {
                deleteBookStatement.setLong(1, book.getISBN());
                deleteBookStatement.setInt(2, book.getCopy_number());
                int booksDeleted = deleteBookStatement.executeUpdate();
                System.out.println(booksDeleted + " book(s) deleted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception after printing the stack trace
        }
    }

    /**
     * Delete a book and related transactions from the database.
     *
     * @param connection database connection
     * @param book the book to be deleted
     * @throws SQLException throws SQL exception if there is an issue deleting person
     */
    public void deleteBook(Connection connection, Book book) throws SQLException {
        try {
            // Step 1: Delete transactions if it matches the ISBN of the book in the param
            String deleteTransactionsSQL = "DELETE FROM Transaction WHERE ISBN = ?";
            try (PreparedStatement deleteTransactionsStatement = connection.prepareStatement(deleteTransactionsSQL)) {
                deleteTransactionsStatement.setLong(1, book.getISBN());
                int transactionsDeleted = deleteTransactionsStatement.executeUpdate();
                System.out.println(transactionsDeleted + " transaction(s) deleted.");
            }

            // Step 2: Delete the book if it matches the ISBN of the book in the param
            String deleteBookSQL = "DELETE FROM Book WHERE ISBN = ?";
            try (PreparedStatement deleteBookStatement = connection.prepareStatement(deleteBookSQL)) {
                deleteBookStatement.setLong(1, book.getISBN());
                int booksDeleted = deleteBookStatement.executeUpdate();
                System.out.println(booksDeleted + " book(s) deleted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception after printing the stack trace
        }
    }



    /**
     *
     * @param connection database connection
     * @param card the card to be deleted
     * @throws SQLException throws SQL exception if there is an issue deleting person
     */
    public void deleteCard(Connection connection,Card card) throws SQLException {
        //delete the transaction whose card_number matches the card_number of the card given on param
        String deleteSQL1 = "DELETE FROM Transaction WHERE card_number = ?";
        //delete the card whose card_number matches the card_number of the card given on param
        String deleteSQL2 = "DELETE FROM Card WHERE card_number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL1)) {
            preparedStatement.setLong(1, card.getCardNumber());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
            try (PreparedStatement preparedStatement2 = connection.prepareStatement(deleteSQL2)) {
                preparedStatement2.setLong(1, card.getCardNumber());

                int rowsAffected2 = preparedStatement2.executeUpdate();
                System.out.println(rowsAffected2 + " row(s) deleted.");
            }
        }

    }



    /**
     *
     * @param connection database connection
     * @param transaction the transaction to be deleted
     * @throws SQLException throws SQL exception if there is an issue deleting person
     */
    public void deleteTransaction(Connection connection,Transaction transaction) throws SQLException {
        //delete the transaction whose transaction_id matches the transaction_id of the transaction given on param
        String deleteSQL = "DELETE FROM Transaction WHERE transaction_id = ?";


        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setLong(1, transaction.getTransaction_ID());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }
    }

    public List<Person> getAllPersons(Connection connection) throws SQLException {
        List<Person> persons = new ArrayList<>();
        String showTableSQL = "SELECT * FROM Person";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(showTableSQL)) {

            while (resultSet.next()) {
                Person person = new Person();
                person.setSsn(resultSet.getInt("ssn"));
                person.setPhone(resultSet.getLong("phone"));
                person.setName(resultSet.getString("name"));
                person.setAddress(resultSet.getString("address"));
                persons.add(person);
            }
        }

        return persons;
    }



    //advanced methods
    public List<Faculty> getAllFacultyWhoCheckedOutBook(Connection connection) throws SQLException {
        List<Faculty> facultyList = new ArrayList<>();
        String showFacultyTableSQL = "SELECT DISTINCT person.*, faculty.faculty_id " +
                "FROM person " +
                "JOIN faculty ON person.ssn = faculty.ssn " +
                "JOIN card ON person.ssn = card.ssn " +
                "JOIN transaction ON card.card_number = transaction.card_number";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(showFacultyTableSQL)) {

            while (resultSet.next()) {
                Faculty faculty = new Faculty();
                faculty.setFacultyID(resultSet.getInt("faculty_id"));
                faculty.setSsn(resultSet.getInt("ssn"));
                faculty.setName(resultSet.getString("name"));
                faculty.setAddress(resultSet.getString("address"));
                faculty.setPhone(resultSet.getLong("phone"));
                facultyList.add(faculty);
            }
        }
        return facultyList;
    }



    public List <Book> getAllBooksWithMultipleCopies(Connection connection) throws SQLException {
        List<Book> books = new ArrayList<>();
        String showBookTable = "SELECT * FROM Book WHERE copy_number > 1";
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(showBookTable)) {
            while (resultSet.next()) {
                Book book = new Book();
                book.setISBN(resultSet.getLong("ISBN"));
                book.setCopy_number(resultSet.getInt("copy_number"));
                book.setDescription(resultSet.getString("book_description"));
                book.setAuthor_name(resultSet.getString("author"));
                book.setTitle(resultSet.getString("book_title"));
                books.add(book);
            }
        }
        return  books;
    }

    public List<Book> getEveryDistinctBook(Connection connection) throws SQLException {
        List<Book> distinctBooks = new ArrayList<>();

        String showDistinctBooks = "SELECT DISTINCT ISBN, book_title, author, book_description FROM Book";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(showDistinctBooks)) {
            while (resultSet.next()) {
                Book book = new Book();
                book.setISBN(resultSet.getLong("ISBN"));
                book.setDescription(resultSet.getString("book_description"));
                book.setAuthor_name(resultSet.getString("author"));
                book.setTitle(resultSet.getString("book_title"));
                distinctBooks.add(book);
            }
        }
        return distinctBooks;
    }

    public List<Transaction> getEveryTransactionReport(Connection connection) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT * FROM Transaction";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                // Create Transaction object and add it to the list
                Transaction transaction = new Transaction();

                transaction.setTransaction_ID(resultSet.getInt("Transaction_ID"));
                transaction.setDate(resultSet.getDate("return_date"));
                transaction.setCopy_number(resultSet.getInt("copy_number"));
                transaction.setISBN(resultSet.getLong("ISBN"));
                transaction.setReturn_date(resultSet.getDate("return_date"));
                transaction.setLibrarian_ID(resultSet.getInt("Librarian_ID"));
                transaction.setCard_num(resultSet.getLong("card_number"));
                transactions.add(transaction);
            }
        }

        return transactions;
    }


    public List <Person> getAllNonFacultyCardHolder(Connection connection) throws SQLException {
        List<Person> persons = new ArrayList<>();

        String showAllNonfacultyCardHolder = "SELECT * FROM Person " +
                "LEFT JOIN Faculty ON Person.ssn = Faculty.ssn " +
                "LEFT JOIN CARD ON Person.ssn = Card.ssn " +
                "WHERE Faculty.ssn IS NULL AND CARD.ssn IS NOT NULL";

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(showAllNonfacultyCardHolder)) {

            while(resultSet.next()) {

                Person person = new Person();
                person.setSsn(resultSet.getInt("ssn"));
                person.setName(resultSet.getString("name"));
                person.setAddress(resultSet.getString("address"));
                person.setPhone(resultSet.getLong("phone"));
                persons.add(person);

            }
        }
        return persons;
    }

    // Get overdue books
    public List<Book> getOverdueBooks(Connection connection) {
        List<Book> overdueBooks = new ArrayList<>();

        // Step 1: Select records from Transaction table where return_date is less than the current date
        String selectOverdueBooksQuery = "SELECT t.ISBN, t.copy_number " +
                "FROM Transaction t " +
                "WHERE t.return_date < CURDATE()";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectOverdueBooksQuery)) {

            while (resultSet.next()) {
                long isbn = resultSet.getLong("ISBN");
                int copyNumber = resultSet.getInt("copy_number");

                // Step 2: Match ISBN and copy_number with Book table to get Book details
                Book overdueBook = getBookDetails(connection, isbn, copyNumber);

                if (overdueBook != null) {
                    overdueBooks.add(overdueBook);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return overdueBooks;
    }

    // Helper method to get Book details based on ISBN and copy_number
    private Book getBookDetails(Connection connection, long isbn, int copyNumber) {
        String selectBookQuery = "SELECT * FROM Book WHERE ISBN = ? AND copy_number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectBookQuery)) {
            preparedStatement.setLong(1, isbn);
            preparedStatement.setInt(2, copyNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Book(
                            resultSet.getLong("ISBN"),
                            resultSet.getString("book_title"),
                            resultSet.getString("book_description"),
                            resultSet.getString("author"),
                            resultSet.getInt("copy_number")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



}

