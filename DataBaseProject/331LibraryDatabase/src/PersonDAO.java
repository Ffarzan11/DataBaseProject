import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    public void createAndInsertPerson(Connection connection, int ssn, int phone, String name, String address) throws SQLException {
        createPersonTable(connection);

        String insertSQL = "INSERT INTO Person (ssn, phone, name, address) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, ssn);
            preparedStatement.setInt(2, phone);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, address);

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

    private void createPersonTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Person (" +
                "ssn INT PRIMARY KEY," +
                "phone INT," +
                "name VARCHAR(255)," +
                "address VARCHAR(255))";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
        }
    }
}