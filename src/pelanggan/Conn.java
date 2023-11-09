package pelanggan;

import java.sql.*;
public class Conn {
    private Connection conn;
    public Connection openConnection() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/","root","");
        }
        return conn;
    }
}
