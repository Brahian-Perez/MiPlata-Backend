package persistence.database;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";

    private static Connection connection = null;


    private DatabaseConnection(){

    }

    public static Connection getConnection() throws SQLException{
        if(connection != null && !connection.isClosed()){
            connection.close();
        }

        return connection;
    }

    public static void closeConnection() throws SQLException{
        if(connection != null && !connection.isClosed()){
            connection.close();
        }
    }


}
