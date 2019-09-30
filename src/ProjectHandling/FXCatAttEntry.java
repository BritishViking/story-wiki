import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class FXCatAttEntry extends Pane {
    DisplayableCatAttEntry dEntry;
    private boolean isSelected;
    private TextArea aerea = new TextArea();
    private HBox container = new HBox();
    private Text attribute;
    private Text entry;
    private TextFlow nameFlow = new TextFlow();
    private TextFlow entryFlow = new TextFlow();


    public FXCatAttEntry(DisplayableCatAttEntry ent){
        // initialize all components
        dEntry = ent;
        attribute = new Text(ent.getAtt());
        entry = new Text(ent.getValue());

        aerea.setText(dEntry.getValue());
        aerea.setPrefHeight(100);
        aerea.setMaxWidth(200);
        aerea.setWrapText(true);

        nameFlow.getChildren().add(attribute);
        nameFlow.setMaxWidth(200);
        nameFlow.setPrefWidth(200);

        entryFlow.getChildren().add(entry);
        container.getChildren().addAll(nameFlow, entryFlow);
        this.getChildren().add(container);

        // add listeners


        this.setOnMouseEntered(e->{
            ((Node) e.getSource()).getScene().setCursor(Cursor.HAND);
        });

        this.setOnMouseExited(e->{
            ((Node) e.getSource()).getScene().setCursor(Cursor.DEFAULT);
        });
    }

    public DisplayableCatAttEntry getEntry(){
        return dEntry;
    }

    public boolean isSelected(){
        return isSelected;
    }
    public void setSelected(boolean status){
        if(status && !isSelected){
            container.getChildren().clear();
            container.getChildren().addAll(nameFlow, aerea);
            isSelected = true;
        }else if(!status && isSelected){
            container.getChildren().clear();

            String newValue = aerea.getText();

            System.out.println("DEBUG entry " + entry.getText());
            System.out.println("DEBUG newValue " + newValue);
            if(newValue!= null) {
                if (!newValue.equals(entry.getText())) {
                    entry.setText(newValue);
                    dEntry.updateValue(newValue);

                }
            }else{
                entry.setText("");
                dEntry.updateValue("");
            }

            container.getChildren().addAll(nameFlow, entryFlow);
            isSelected = false;
        }
    }
}
