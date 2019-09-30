import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static String url = "jdbc:mysql://localhost:3306/mariaTest?user=root&password=Kvj900I23dDf&useSSL=FALSE&allowPublicKeyRetrieval=true";
    private static  String databasedrviver = "com.mysql.cj.jdbc.Driver";


    public static Connection getConnection(){
        Connection con = null;
        try{
            Class.forName(databasedrviver);
            con = DriverManager.getConnection(url);
        }catch (ClassNotFoundException e){
            e.printStackTrace();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return con;
    }
}
