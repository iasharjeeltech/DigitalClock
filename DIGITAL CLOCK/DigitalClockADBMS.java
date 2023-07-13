import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DigitalClockADBMS {

    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    static final String USER = "System";
    static final String PASS = "Oracle";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute a query to create a table to store clock time
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS ClockTime " +
                         "(ID INTEGER not NULL GENERATED ALWAYS AS IDENTITY, " +
                         " Time VARCHAR2(20), " +
                         " PRIMARY KEY ( ID ))";
            stmt.executeUpdate(sql);

            // Get the current time and format it
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            // Insert the current time into the database
            sql = "INSERT INTO ClockTime (Time) VALUES ('" + formatter.format(currentTime) + "')";
            stmt.executeUpdate(sql);

            // Retrieve the latest time from the database
            sql = "SELECT Time FROM ClockTime ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String latestTime = rs.getString("Time");
                System.out.println(latestTime);
            }

            // Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // Finally block used to close resources
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
    } // end main
}
