import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

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
            + "PRIMARY KEY (ISBN)"
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
            + "FOREIGN KEY (copy_number) REFERENCES Book(copy_number),"
            + "FOREIGN KEY (ISBN) REFERENCES Book(ISBN),"
            + "FOREIGN KEY (librarian_id) REFERENCES Librarian(librarian_id),"
            + "FOREIGN KEY (card_number) REFERENCES Card(card_number)"
            + ")";

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
    public void insertPerson(Connection connection,Person person) throws SQLException {
        createPersonTable(connection);

        String insertSQL = "INSERT INTO  Person (ssn, phone, name, address) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, person.getSsn());
            preparedStatement.setLong(2, person.getPhone());
            preparedStatement.setString(3, person.getName());
            preparedStatement.setString(4, person.getAddress());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
    }
    public void insertFaculty(Connection connection,Faculty faculty) throws SQLException {
        createFacultyTable(connection);

        String insertSQL = "INSERT INTO  Faculty (faculty_id, ssn) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, faculty.getFacultyID());
            preparedStatement.setInt(2, faculty.getSsn());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
    }

    public  void insertLibrarian(Connection connection, Librarian librarian) throws SQLException {
        createLibrarianTable(connection);

        String insertSQL = "INSERT INTO  Librarian (librarian_id, librarian_title) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, librarian.getLibrarian_ID());
            preparedStatement.setString(2, librarian.getLibratian_title());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
    }
    public void insertBook(Connection connection, Book book) throws SQLException {
        createBookTable(connection);
        String insertSQL = "INSERT INTO Book (ISBN, book_title, book_description, author,copy_number) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setLong(1, book.getISBN());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getDescription());
            preparedStatement.setString(4, book.getAuthor_name());
            preparedStatement.setInt(5, book.getCopy_number());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
    }
    public  void insertCard(Connection connection, Card card) throws SQLException {
        createCardTable(connection);
        String insertSQL = "INSERT INTO  Card (card_number, expiration_date, ssn) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setLong(1, card.getCardNumber());
            preparedStatement.setDate(2, card.getExpirationDate());
            preparedStatement.setInt(3, card.getSSN());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }

    }

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
    }
    public void modifyPerson(Connection connection, Person person) throws SQLException {
        String modifySQL = "UPDATE Person SET phone = ?, name = ?, address = ? WHERE ssn = ?";

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

    public  void modifyCard(Connection connection, Card card) throws  SQLException {
        String modifySQL = "UPDATE Card SET expiration_date = ?,  WHERE card = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(modifySQL)) {
            preparedStatement.setLong(1, card.getCardNumber());
            preparedStatement.setDate(2, card.getExpirationDate());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) modified.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  void  modifyBook(Connection connection, Book book) throws  SQLException {
        String modifySQL = "UPDATE Book SET book_description = ?, copy_number WHERE ISBN = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(modifySQL)) {
            preparedStatement.setLong(1, book.getISBN());
            preparedStatement.setString(3, book.getDescription());
            preparedStatement.setInt(4, book.getCopy_number());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) modified.");
        }
        catch (SQLException e) {
            e.printStackTrace();
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
                person.setPhone(resultSet.getInt("phone"));
                person.setName(resultSet.getString("name"));
                person.setAddress(resultSet.getString("address"));
                persons.add(person);
            }
        }

        return persons;
    }

    public void deletePerson(Connection connection, int ssn) throws SQLException {
        String deleteSQL = "DELETE FROM Person WHERE ssn = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, ssn);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }
    }
}