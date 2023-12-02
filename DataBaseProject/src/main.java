import java.sql.*;
import java.util.*;

public class main{
	public static void main(String args[]) {
		// SQL commands to create tables
      String createPersonTableSQL = "CREATE TABLE IF NOT EXISTS Person ("
              + "ssn INT(9),"
              + "phone INT(9),"
              + "name VARCHAR(40),"
              + "address VARCHAR(100),"
              + "PRIMARY KEY (ssn)"
              + ")";

      String createFacultyTableSQL = "CREATE TABLE IF NOT EXISTS Faculty ("
              + "faculty_id INT(9),"
              + "ssn INT(9),"
              + "PRIMARY KEY (faculty_id),"
              + "FOREIGN KEY (ssn) REFERENCES Person(ssn)"
              + ")";

      String createCardTableSQL = "CREATE TABLE IF NOT EXISTS Card ("
              + "card_number INT(12),"
              + "expiration_date DATE,"
              + "ssn INT(9),"
              + "PRIMARY KEY (card_number),"
              + "FOREIGN KEY (ssn) REFERENCES Person(ssn)"
              + ")";

      String createBookTableSQL = "CREATE TABLE IF NOT EXISTS Book ("
              + "ISBN INT(13),"
              + "book_title VARCHAR(100),"
              + "book_description VARCHAR(1500),"
              + "author VARCHAR(50),"
              + "copy_number INT(15),"
              + "PRIMARY KEY (ISBN)"
              + ")";

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

      String createLibrarianTableSQL = "CREATE TABLE IF NOT EXISTS Librarian ("
              + "librarian_id INT(12),"
              + "librarian_title VARCHAR(25),"
              + "PRIMARY KEY (librarian_id)"
              + ")";
      try (Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement()) {
         System.out.println("Connected to the database!");
         // Execute the SQL commands to create tables
         statement.executeUpdate(createPersonTableSQL);
         statement.executeUpdate(createFacultyTableSQL);
         statement.executeUpdate(createCardTableSQL);
         statement.executeUpdate(createBookTableSQL);
         statement.executeUpdate(createTransactionTableSQL);
         statement.executeUpdate(createLibrarianTableSQL);
         
         DatabaseHelper personDAO = new DatabaseHelper();

         Person personOne = new Person(100100100, "Alice Smith", 646890359, "746 Broadway Appartment 1F");
         // Create and insert a new person
         personDAO.createAndInsertPerson(connection, personOne);

         // Show the contents of the "Person" table
         List<Person> persons = personDAO.getAllPersons(connection);
         for (Person person : persons) {
             System.out.println(person);
         }

         // Modify the person's information
         personDAO.modifyPerson(connection, 123456783, 987654321, "Jane Doe", "456 Oak St");

         // Show the updated contents of the "Person" table
         List<Person> updatedPersons = personDAO.getAllPersons(connection);
         for (Person person : updatedPersons) {
             System.out.println(person);
         }

         // Delete the person with SSN 123456783
         personDAO.deletePerson(connection, 123456783);

         // Show the final contents of the "Person" table
         List<Person> finalPersons = personDAO.getAllPersons(connection);
         for (Person person : finalPersons) {
             System.out.println(person);
         }

     } catch (SQLException e) {
         e.printStackTrace();
     }
	}
}