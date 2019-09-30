package Shell;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionHandler {
    public static String exToString(Exception e){

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();

    }
}
