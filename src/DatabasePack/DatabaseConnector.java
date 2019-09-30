package DatabasePack;
import java.sql.*;

public class DatabaseConnector {
    private static  String url;
    private static final String databasedrviver = "com.mysql.cj.jdbc.Driver";
    private static Connection con;
    private static Statement stat;
    private static ResultSet res;


    public static void setDatabaseInfo(String server, String database, String username, String password) {
        url = "jdbc:mysql://"+server+":3306/"+database+"?user="+username+"&password="+password+"&useSSL=FALSE&allowPublicKeyRetrieval=true";
    }


    public static void skriv(Object o){
        System.out.println(o);
    }

    /*
        If variable is false, the connect method closes on its own
     */
    private static boolean connect(){

        try {
            Class.forName(databasedrviver);
            con = DriverManager.getConnection(url);
            stat = con.createStatement();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            conClose();

            return false;
        }catch (SQLException e){
            e.printStackTrace();
            conClose();
            return false;
        }catch (Exception e){
            e.printStackTrace();
            conClose();
            return false;
        }


        return true;
    }

    private static void conClose(){
        try{
            if(stat != null){
            stat.close();
            System.out.println("Closed Statment");
            stat = null;}
        }catch (SQLException f){
            System.out.println("Did not close Statment");
            stat = null;
            f.printStackTrace();

        }

        try{
            if(con != null){
            con.close();
            con = null;
            System.out.println("Closed Connection");}
        }catch (SQLException f){
            f.printStackTrace();
            System.out.println("Did not close Connection");
            con = null;
        }
    }

    private static void close(){
        if(res!= null){
            try{
                res.close();
                System.out.println("Closed ResultSet");
                res = null;
            }catch (SQLException f){
                System.out.println("Did not close ResultSet");
                res = null;
                f.printStackTrace();
            }
        }

        conClose();
    }



    public static Table exec(String sql) throws SQLSyntaxErrorException {
        ResultSet set = null;
        Table test = null;

        if(sql == null){
            throw new NullPointerException("SQL sentence can not be null");
        }

        if (connect()) {
            try {
                if (stat.execute(sql)) {

                    res = stat.getResultSet();
                    test = tableCreator();

                }

            } catch (SQLSyntaxErrorException e) {
                throw new SQLSyntaxErrorException();
            }catch (SQLIntegrityConstraintViolationException e){
                throw new SQLSyntaxErrorException();
            } catch(Exception e) {
                e.printStackTrace();



            }
        }

        close();
        return test;
    }

    private static Table tableCreator(){
        Table tab = null;
        String[][] s = null;
        if(res != null) {

            try {
                ResultSetMetaData resMeta = res.getMetaData();
                int colCount = resMeta.getColumnCount();

                TableHead tb = tableHeadCreator(resMeta);

                tab = new Table(tb);

                Object val;
                Row r = new Row(tb);
                res.last();
                int total = res.getRow();

                res.beforeFirst();
                while (res.next()) {
                    r = new Row(tb);
                    for (int i = 1; i <= colCount; i++) {

                        val = res.getObject(i);
                        if(val != null) {

                           r.addValue(resMeta.getColumnName(i), val);
                        }else{

                           r.addValue(resMeta.getColumnName(i), null);
                        }

                    }
                    tab.addRow(r);

                }

                res.beforeFirst();
            } catch (SQLException e) {
                e.printStackTrace();
                tab = null;
            }catch (Exception e){
                e.printStackTrace();
                tab = null;
            }
        }

        return tab;
    }

    public static boolean createNewUser(String username, String passwordHash){
        connect();
        String sql = "INSERT INTO user(username, passwordHashvalue) VALUES('"+username+"', '"+passwordHash+"');";

        try{
            stat.execute(sql);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }finally {
            close();
        }
    }

    private static TableHead tableHeadCreator(ResultSetMetaData resM) throws SQLException{
        TableHead tab = new TableHead("Deafault", resM.getColumnName(1), Values.getValueFromSQLA(resM.getColumnTypeName(1)));
        for (int i = 2; i<=resM.getColumnCount(); i++){

            tab.addColumn(resM.getColumnName(i), Values.getValueFromSQLA(resM.getColumnTypeName(i)), !Values.sqlIsNullable(resM.isNullable(i)));

        }


        return tab;

    }

    /*public static boolean checkPassword(String username, String passwordaHash){
        String sql = "SELECT "
    }*/

}
/*

 */