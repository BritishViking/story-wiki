package Shell;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Shell extends MainFrame {
    public Shell (String window){
        super(window);
    }

    
    public static void main(String[]args)throws Exception{

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new Shell("Wolrdbuilding Shell");
                frame.setSize(1000, 700);
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

                frame.setVisible(true);
            }
        });
    }

    @Override
    public String runCommand(String commandString) {
        try {
            return CommandRunner.runCommand(commandString);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (Exception e) {
           return ExceptionHandler.exToString(e);
        }
    }
}
