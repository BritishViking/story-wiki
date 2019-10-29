import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CleanUp {
    public static void closeCon(Connection con){
        try{
            if(con != null){
                con.close();
                System.out.println("Closed Connection");}
        }catch (SQLException f){
            f.printStackTrace();
            System.out.println("Did not close Connection");

        }

    }
    public static void closePre(PreparedStatement stmt){
        try{
            if(stmt != null) {
                stmt.close();
                System.out.println("Closed Statment");
            }
        }catch (SQLException f){
            System.out.println("Did not close Statment");

            f.printStackTrace();

        }
    }

    public static void closeRes(ResultSet res){
        try{
            if(res != null){
                res.close();
                System.out.println("Closed Resultset");}
        }catch (SQLException f){
            f.printStackTrace();
            System.out.println("Did not close Connection");
        }
    }

    public static void handleException(Exception e){
        if (e instanceof  SQLException){
            System.out.println("SQL Exception");

            e.printStackTrace();
        }else if( e instanceof java.lang.RuntimeException){
            System.out.println("runtime exception");
            throw (java.lang.RuntimeException) e;
        }else{
            e.printStackTrace();
        }
    }
}
