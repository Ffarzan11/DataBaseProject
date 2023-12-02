import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestSQL {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/LibraryDatabase";
        String username = "root";
        String password = "1998";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
           System.out.println("Connected to the database!");

           // Execute a query on the "Person" table
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery("SELECT * FROM Person");

           // Print the columns of the "Person" table
           while (resultSet.next()) {
               int ssn = resultSet.getInt("ssn");
               int phone = resultSet.getInt("phone");
               String name = resultSet.getString("name");
               String address = resultSet.getString("address");

               System.out.println("SSN: " + ssn + ", Phone: " + phone + ", Name: " + name + ", Address: " + address);
           }

       } catch (Exception e) {
           e.printStackTrace();
       }
    }
}


