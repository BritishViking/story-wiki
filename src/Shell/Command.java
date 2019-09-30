package Shell;

import java.io.Serializable;
import java.sql.Connection;

public class Command implements Serializable {
    private String name;
    private CommandsInterface command;
    private String[] argsTypes;
    private String description;
    public static final String INTEGER = "java.lang.Integer";
    public static final String STRING = "java.lang.String";

    public Command(String name, CommandsInterface cmd , String[] types, String description){
        argsTypes = types;
        command = cmd;
        this.name = name;
        this.description = description;
    }


    public String getName(){
        return name;
    }
    public String getDescription(){return description;}
    public String run(Object[] args){
        if(args.length != argsTypes.length){
            throw new IllegalArgumentException("Illegal number of arguments Passed To Command Object");
        }





        for(int i = 0; i<args.length; i++){
            if(!(args[i] == null)){
                if(!argsTypes[i].equals(args[i].getClass().getName())){
                    throw new IllegalArgumentException("Illegal Argument Type Passed To Command Object:");
                }
            }
        }


        return command.run(args);
    }


}
