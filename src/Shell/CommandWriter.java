package Shell;

import java.io.*;
import java.util.ArrayList;

public class CommandWriter {
    public static boolean writeCommand(String urlLocation, Command command){
        try {
            FileOutputStream fos = new FileOutputStream(urlLocation);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(command);
            oos.close();

            return true;
        }catch (IOException e){
            e.printStackTrace();
            return  false;
        }
    }

    public static ArrayList<Command> loadAllCommands(String directoryLocation){
        ArrayList<Command> coms = new ArrayList<>();
        File folder = new File(directoryLocation);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                try {
                    Command com = readCommand(listOfFiles[i].getAbsolutePath());
                    if(com != null){
                        coms.add(com);
                    }
                }catch (Exception e){

                }
            }
        }

        return coms;
    }

    public static Command readCommand(String urlLocation){
        try {
            FileInputStream fis = new FileInputStream(urlLocation);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Command readCommand = (Command) ois.readObject();
            ois.close();
            return readCommand;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }



    }

    public static void main (String[]args){
       /* String location = "Commands/CalvionDinner.ser";

        CommandsInterface CalvionDinner = new CommandsInterface(){
            @Override 
            
            public String run(Object[] o){
                String s = "CalvionDinner:) \nSigned, " + o[1];
                return s;
            }
        };

        String[] types = {Command.STRING, Command.STRING};


        Command CalvionDinner2 = new Command("CalvionDinner", CalvionDinner, types, "CalvionDinner");

        writeCommand(location, CalvionDinner2);*/

        ArrayList<Command> readComms = loadAllCommands("Commands");
        Object[] argss = {"Hello", "MyLove"};

        System.out.println(readComms.size());

        for(int i = 0; i<readComms.size(); i++){
            System.out.println("\nRunning...");
            System.out.println(readComms.get(i).run(argss));
        }

    }
}
