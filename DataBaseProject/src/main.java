import java.sql.*;
import java.util.*;

public class main{
	public static void main(String args[]) {
      try (Connection connection = DatabaseConnection.getConnection()) {
         System.out.println("Connected to the database!");

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