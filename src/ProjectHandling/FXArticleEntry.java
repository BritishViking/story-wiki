import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class FXArticleEntry extends VBox {
    private TextField headField = new TextField();
    private TextArea bodyField = new TextArea();
    private Text head;
    private Text body;
    private ArticleEntry entry;
    private int index;
    private Button delete = new Button("Delete");
    private Button unDelete = new Button("Un Delete");
    private HBox header = new HBox();
    private boolean deleted = false;



    public FXArticleEntry(ArticleEntry entry, int index){
        super();
        this.entry = entry;
        this.index = index;
        head = new Text(entry.getHead());
        body = new Text(entry.getBody());

        body.setWrappingWidth(600);
        body.setTextAlignment(TextAlignment.JUSTIFY);
        headField = new TextField(entry.getHead());
        bodyField = new TextArea(entry.getBody());
        bodyField.setWrapText(true);
        unDelete.setOnMouseClicked(e ->{
            setDelete(false);
        });

        header.getChildren().addAll(headField, delete);
        header.setSpacing(30);
        delete.setOnMouseClicked(e ->{
            // delete gui entry
                //set it unselected
                setSelected(false);
                setDelete(true);

            // delete application entry
           //  System.out.println("This button does nothing yet");
        });
        this.getChildren().addAll(head, body);
        this.setOnMouseClicked(e -> {
            this.setSelected(true);
        });

        this.setSpacing(10);

    }

    public void setSelected(boolean status){
        if(!deleted) {
            if (status) {

                this.getChildren().remove(0, 2);
                this.getChildren().addAll(header, bodyField);
            } else {
                entry.setBody(bodyField.getText());
                entry.setHead(headField.getText());
                body.setText(entry.getBody());
                head.setText(entry.getHead());
                this.getChildren().remove(0, 2);
                this.getChildren().addAll(head, body);

            }
        }
    }

    public void setDelete(boolean status){
        if(status) {
            deleted = true;
            this.getChildren().remove(0, 2);
            header.getChildren().remove(0, 2);

            head.setFill(Color.GRAY);
            body.setFill(Color.GRAY);
            header.getChildren().addAll(head, unDelete);
            this.getChildren().addAll(header, body);
            entry.setDeleted(true);
        }else{
            head.setFill(Color.BLACK);
            body.setFill(Color.BLACK);
            deleted = false;
            header.getChildren().remove(0,2);
            this.getChildren().remove(0,2);

            header.getChildren().addAll(headField, delete);
            this.getChildren().addAll(header, bodyField);
            entry.setDeleted(false);
        }
    }

    public  int getIndex(){
        return  index;
    }

    public ArticleEntry getArticleEntry(){
        return entry;
    }

    public void save(){
        entry.setBody(bodyField.getText());
        entry.setHead(headField.getText());
    }


}
