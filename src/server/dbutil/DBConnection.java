package server.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    private static DBConnection instance;
    private Connection conn;
    
    public static DBConnection getInstance() {
        if(instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
    
    private DBConnection () {
        
    }
    
    public void connectToDatabase() throws ClassNotFoundException , SQLException {
        
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chitchat" , "root" , "Hypercity@10269");
            System.out.println("Connection established");
        
    }
    
    public void closeConnection() throws SQLException {
        if(conn != null) {
            conn.close();            
        }
    }
    
    public Connection getConnection() {
        return conn;
    }
}
