package Shell;

import DatabasePack.*;

import java.sql.SQLSyntaxErrorException;

public class Admin {
    DatabaseConnector db;

    public Admin(){
        DatabaseConnector.setDatabaseInfo("localhost", "book1", "root", "Kvj900I23dDf");
    }
    private static boolean loginUser;
    public static String pingDb() throws SQLSyntaxErrorException{
      Table tab = DatabaseConnector.exec("SELECT firstname FROM characters");

        return "Success!";
    }

    public static Table getTestTable() throws SQLSyntaxErrorException {
        return DatabaseConnector.exec("SELECT firstname, middlename, famname FROM characters");
    }

    public static String getInsertTestTable() throws SQLSyntaxErrorException{
        Table tab = getTestTable();

        return tab.getInsert();
    }

    public static boolean createNewCharacter(Table tab) {
        return false;
    }

    public static Table execSQL(String sqlstring) throws SQLSyntaxErrorException {


        Table tab = DatabaseConnector.exec(sqlstring);


        return tab;
    }

    public static void main(String[]args){
        Admin a  = new Admin();
/*
        String[] val = a.getFirstName();
        for (int i = 0; i<val.length; i++){
            System.out.println(val[i]);
        }*/
    }

    public static boolean addCharacter(Table tab){
        String sql;
        Row[] val = tab.getValues();

        if(val == null){
            return false;
        }

        sql ="";
        return true;
    }


    public static boolean createUser(String username,  String password)
    {
        try {
            // sjekker fær vi begynner med den stressete hashingen
            if (username.length() > 0 && username.length() <= 20 && password.length() > 6 && password.length() <= 30) {
                System.out.println("YAY");
                //hasher passorder
                String passwordHash = HashWizard.generateHash(password);

                //Database.class method, User createNewUser(String username, String displayname, String passwordHash)
                //returns null if username is taken, or something else is wrong
                if (DatabaseConnector.createNewUser(username, passwordHash)) {
                    loginUser = true;
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static String ping(){
        return "Success!";
    }


}
