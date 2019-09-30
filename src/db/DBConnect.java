import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * Will be updated to a connection pool when i got the time
 */

public class DBConnect {
    private static  String databasedrviver = "com.mysql.cj.jdbc.Driver";


    public static Connection getConnection(){
        Connection con = null;
        try{
            Class.forName(databasedrviver);
            con = DriverManager.getConnection(PropertiesCache.getInstance().getProperty("url"));
        }catch (ClassNotFoundException e){
            e.printStackTrace();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return con;
    }
}
