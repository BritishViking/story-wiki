import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class FXCategoryAttribute extends HBox {
    private boolean isSelected;

    private int index;

    private Text text =  new Text();
    private TextField textField = new TextField();
    private Button deleteButton = new Button("Delete");
    private boolean isEdited;
    private boolean isDeleted;

    FXCategoryAttribute(String stringText, int index){

        this.index = index;


        deleteButton.setOnAction(e->{

            if(isDeleted){
                setDeleted(false);
            }else{
                setDeleted(true);
            }
        });

        text.setWrappingWidth(400);
        text.setText(stringText);
        text.setFill(Color.GRAY);
        this.setSpacing(50);

        textField.setPrefWidth(400);
        textField.setText(text.getText());

        this.setOnMouseEntered(e->{
            if(!isDeleted) {
                text.setFill(Color.BLACK);
                ((Node) e.getSource()).getScene().setCursor(Cursor.HAND);
            }
        });

        this.setOnMouseExited(e->{
            if(!isDeleted) {
                text.setFill(Color.GRAY);
                ((Node) e.getSource()).getScene().setCursor(Cursor.DEFAULT);
            }else{

                ((Node) e.getSource()).getScene().setCursor(Cursor.DEFAULT);
            }
        });

        this.getChildren().addAll(text);

    }

    public boolean isDeleted(){
        return isDeleted;
    }

    public void setDeleted(boolean status){
        if(status && !isDeleted){ // delete
            text.setFill(Color.LIGHTGRAY);
            setSelected(false);
            deleteButton.setText("Undelete");
            this.getChildren().add(deleteButton);
        }else if(!status && isDeleted){ // undelete

            deleteButton.setText("Delete");
            this.getChildren().clear();
            this.getChildren().addAll(text);
        }

        isDeleted = status;
    }

    public boolean isSelected(){
        return isSelected;
    }
    public void setSelected(boolean status){
        if(!isDeleted) {
            if (status && !isSelected) { // select
                this.getChildren().clear();
                this.getChildren().addAll(textField, deleteButton);
            } else if (!status && isSelected) { // unselect
                if(!textField.getText().equals(text.getText())){
                    text.setText(textField.getText());
                    isEdited = true;
                }

                this.getChildren().clear();
                this.getChildren().addAll(text);
            }
            isSelected = status;
        }


    }

  /*  public void (String txt){
       if(!txt.equals(text.getText())){
           System.out.println("Setting edited");
           text.setText(txt);
           isEdited = true;
        }
    }*/

    public void setEdited(boolean status) {
        isEdited = status;
    }

    public boolean isEdited(){
        return isEdited;
    }

    public String getText(){
        return text.getText();
    }

    public int getIndex(){
        return index;
    }


}
