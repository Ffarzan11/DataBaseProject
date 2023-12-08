import java.sql.*;

//code to connect to database
public class DatabaseConnection {
	private static final String JDBC_URL ="jdbc:mysql://localhost:3306/librarydatabase";
	private static final String USERNAME ="root";

	private static final String PASSWORD ="KDgtHaglwXa6kNQ";

	public static Connection getConnection() throws SQLException {
      return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
  }

  public static void closeConnection(Connection connection) {
      if (connection != null) {
          try {
              connection.close();
          } catch (SQLException e) {
              e.printStackTrace();
          }
      }
  }
}
