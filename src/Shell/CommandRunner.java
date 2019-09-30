package Shell;
import DatabasePack.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLSyntaxErrorException;

public class CommandRunner {
   private static Command[] com;
   public static String[] modes = {"default", "sql"};
   private static final String DBPASS = "Kvj900I23dDf";
   private static int mode = 0;

   /* Basically how the script interpereter works:
      a script will be splitt into three gruops:
      1. word is the main command
      2. word is entity
      3. the rest of the string (after the first letter which is not " " is the identifier

      ! if the script consist only of 2 words, the entity becomes null and the second word becomes the identifier

      ! if the script consist only of 1  word, the entire script is the main command (minus any spaces aka " ")
      
    */

    public static String[] scriptInterpereter(String script) throws IllegalArgumentException {
        String main = null;
        String entity = null;
        String identifier = null ;

        if(mode != 0 && script.length()!=0) {
            String temp = script.replace(" ", "");
            char bit = temp.charAt(0);
            if( bit == '*'){
                script = script.substring(script.indexOf("*") + 1);
            }else if (mode == 1 ){
                main = "execSql";
                identifier = script.substring(script.indexOf(bit));

                if( identifier.charAt(identifier.length() -1) != ';'){
                    identifier += ";";
                }
                entity = null;
            }
        }


            char currentCharacter;

        if(main == null) {
            for (int i = 0; i < script.length(); i++) {
                currentCharacter = script.charAt(i);
                if (script.charAt(i) != ' ') {
                    if (main == null) {
                        main = "" + currentCharacter;
                    } else if (entity == null) {
                        main += currentCharacter;
                    } else if (identifier == null) {
                        entity += currentCharacter;
                    } else {
                        identifier += currentCharacter;
                    }
                } else {
                    if (main == null) {
                        main = "";
                    } else if (entity == null) {
                        entity = "";
                    } else if (identifier == null) {
                        identifier = "";
                    } else {
                        identifier += currentCharacter;
                    }
                }
            }

        }

        if(main == null){
            throw new IllegalArgumentException("NO MAIN COMMAND");
        }

        if(identifier == null && entity != null){
            identifier = entity;
            entity = null;
        }

        String[] kol = {main, entity, identifier};

        return kol;
    }


    public static String runCommand(String commandstring){

        String[] comands = scriptInterpereter(commandstring);
        String[] args = {comands[1],comands[2]};

        for(int i = 0; i<com.length; i++){
            if(comands[0].equals(com[i].getName())){

                return com[i].run(args);
            }
        }

        return "UNKNOWN MAIN COMMAND";
    }

    private static void addCommand(Command comand){
        if(com!=null){
            Command[] temp = new Command[com.length + 1];

            for(int i = 0; i<com.length; i++){
                temp[i] = com[i];
            }

            temp[com.length] = comand;

            com = temp;
        }else{

            com = new Command[1];
            com[0] = comand;
        }
    }



    static{
        String[] types = {Command.STRING, Command.STRING};
        DatabaseConnector.setDatabaseInfo("localhost", "book1", "root",DBPASS );


        CommandsInterface testIdentifier = new CommandsInterface(){
            @Override
            public String run(Object[] o) {
                return "." + o[1] + ".";
            }
        };
        addCommand(new Command("showIdentifier", testIdentifier, types, "Displayes the identifier"));

        CommandsInterface listCommands = new CommandsInterface(){
            @Override
            public String run(Object[] o) {
                String coms = "\n>" + com[0].getName() + ": " + com[0].getDescription();

                for(int i = 1; i<com.length; i++){
                    coms += "\n>" + com[i].getName() + ": " + com[i].getDescription();
                }

                return coms;
            }
        };
        addCommand(new Command("listcom", listCommands, types, "Lists all commands"));

        CommandsInterface pingadmin = new CommandsInterface(){
            @Override
            public String run(Object[] o) {
                return Admin.ping();
            }
        };
        addCommand(new Command("pingadmin", pingadmin, types, "calls method Admin.ping() which return success! if seccuessfull"));


        CommandsInterface exceptionExample = new CommandsInterface(){
            @Override
            public String run(Object[] o) {
                for(int i = 0; i<modes.length; i++){
                    if(o[1].equals(modes[i])){
                        mode = i;
                        return "Mode set to: " + o[1];
                    }
                }

                return "UNKNOWN MODE";
            }
        };
        addCommand(new Command("setmode", exceptionExample, types, "Sets the mode of the specified identifier"));

        CommandsInterface execSql = new CommandsInterface(){
            @Override
            public String run(Object[] o) {
                try {
                    String url = (String) o[1];
                    System.out.println(url);
                    if(url == null){
                        throw new NullPointerException("The identifier can not be null");
                    }
                    Table tb = Admin.execSQL(url);
                    return tb.toString();
                }catch (SQLSyntaxErrorException e){
                   return ExceptionHandler.exToString(e);
                }

            }
        };
        addCommand(new Command("execSql", execSql, types, "Executes the sql in the identifier"));

        CommandsInterface pingDB = new CommandsInterface(){
            @Override
            public String run(Object[] o) {
                try {
                    return Admin.pingDb();
                }catch (SQLSyntaxErrorException e){
                    return ExceptionHandler.exToString(e);
                }
            }
        };
    addCommand(new Command("pingdb", pingDB, types, "Pings the database"));
        /*
        CommandsInterface execSql = new CommandsInterface(){
            @Override
            public String run(Object[] o) {


            }
        };
        addCommand(new Command("execSql", execSql, types, ""));

         */


    }


}
