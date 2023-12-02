import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

	public void createPersonTable(Connection connection) throws SQLException {
      String createPersonTableSQL = "CREATE TABLE IF NOT EXISTS Person ("
            + "ssn INT(9),"
            + "phone INT(9),"
            + "name VARCHAR(40),"
            + "address VARCHAR(100),"
            + "PRIMARY KEY (ssn)"
            + ")";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createPersonTableSQL);
        }
   }
   
   public void createFacultyTable(Connection connection) throws SQLException {
      String createFacultyTableSQL = "CREATE TABLE IF NOT EXISTS Faculty ("
            + "faculty_id INT(9),"
            + "ssn INT(9),"
            + "PRIMARY KEY (faculty_id),"
            + "FOREIGN KEY (ssn) REFERENCES Person(ssn)"
            + ")";

      try (Statement statement = connection.createStatement()) {
          statement.executeUpdate(createFacultyTableSQL);
      }
   }
   
   public void createCardTable(Connection connection) throws SQLException {
      String createCardTableSQL = "CREATE TABLE IF NOT EXISTS Card ("
            + "card_number INT(12),"
            + "expiration_date DATE,"
            + "ssn INT(9),"
            + "PRIMARY KEY (card_number),"
            + "FOREIGN KEY (ssn) REFERENCES Person(ssn)"
            + ")";

      try (Statement statement = connection.createStatement()) {
          statement.executeUpdate(createCardTableSQL);
      }
   }
   
   public void createBookTable(Connection connection) throws SQLException {
      String createBookTableSQL = "CREATE TABLE IF NOT EXISTS Book ("
            + "ISBN INT(13),"
            + "book_title VARCHAR(100),"
            + "book_description VARCHAR(1500),"
            + "author VARCHAR(50),"
            + "copy_number INT(15),"
            + "PRIMARY KEY (ISBN)"
            + ")";

      try (Statement statement = connection.createStatement()) {
          statement.executeUpdate(createBookTableSQL);
      }
   }
   public void createTransactionTable(Connection connection) throws SQLException {
      String createTransactionTableSQL = "CREATE TABLE IF NOT EXISTS Transaction ("
            + "transaction_id INT(9),"
            + "date_time DATE,"
            + "copy_number INT(15),"
            + "ISBN INT(13),"
            + "return_date DATE,"
            + "librarian_id INT(9),"
            + "card_number INT(12),"
            + "PRIMARY KEY (transaction_id),"
            + "FOREIGN KEY (copy_number) REFERENCES Book(copy_number),"
            + "FOREIGN KEY (ISBN) REFERENCES Book(ISBN),"
            + "FOREIGN KEY (librarian_id) REFERENCES Librarian(librarian_id),"
            + "FOREIGN KEY (card_number) REFERENCES Card(card_number)"
            + ")";

      try (Statement statement = connection.createStatement()) {
          statement.executeUpdate(createTransactionTableSQL);
      }
   }
   public void createLibrarianTable(Connection connection) throws SQLException {
      String createLibrarianTableSQL = "CREATE TABLE IF NOT EXISTS Librarian ("
            + "librarian_id INT(12),"
            + "librarian_title VARCHAR(25),"
            + "PRIMARY KEY (librarian_id)"
            + ")";

      try (Statement statement = connection.createStatement()) {
          statement.executeUpdate(createLibrarianTableSQL);
      }
   }
    public void createAndInsertPerson(Connection connection,Person person) throws SQLException {
        createPersonTable(connection);

        String insertSQL = "INSERT INTO Person (ssn, phone, name, address) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, person.getSsn());
            preparedStatement.setInt(2, person.getPhone());
            preparedStatement.setString(3, person.getName());
            preparedStatement.setString(4, person.getAddress());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
    }

    public void modifyPerson(Connection connection, int ssn, int newPhone, String newName, String newAddress) throws SQLException {
        String modifySQL = "UPDATE Person SET phone = ?, name = ?, address = ? WHERE ssn = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(modifySQL)) {
            preparedStatement.setInt(1, newPhone);
            preparedStatement.setString(2, newName);
            preparedStatement.setString(3, newAddress);
            preparedStatement.setInt(4, ssn);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) modified.");
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