/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package penjualanbarangdoni;

import java.sql.*;

public class Conn {
    
    private Connection conn;
    public Connection openConnection() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/penjualan_doni","root","");
        }
        return conn;
    }
}
