import javafx.scene.text.Text;

public abstract class TagRunner {
    public abstract Text run(String[][] arguments, String content, Project pro)  throws TextFormattingException ;
}
