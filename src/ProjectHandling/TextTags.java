import javafx.scene.text.Text;

public class TextTags {
    private String name;
    private String[] attributes;
    private TagRunner runner;
    public TextTags(String name, String[] attributes){
        this.name = name;
        this.attributes = attributes;
    }
    public String getName(){
        return name;
    }



    public void setTagrunner(TagRunner runner){
        this.runner = runner;
    }

    public Text runTag(String content, String[][] attributes, Project pro) throws TextFormattingException {
        return runner.run(attributes, content, pro);
    }

}
