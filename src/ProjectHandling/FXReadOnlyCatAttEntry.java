import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class FXReadOnlyCatAttEntry extends Pane {
    private TextFlow attributeFlow = new TextFlow();
    private TextFlow entryFlow = new TextFlow();

    public FXReadOnlyCatAttEntry(DisplayableCatAttEntry ent , Project pro){

        String att = ent.getAtt();
        String catent = ent.getValue();
        if(catent == null){
            catent= "";
        }

        Text tAtt = new Text(att);
        Text[] tCatEnt = null;
        try {
            tCatEnt = TextWizard.textAsDisplayable(catent, pro);
            for(int i = 0; i<tCatEnt.length; i++) {
                entryFlow.getChildren().add(tCatEnt[i]);
                entryFlow.setMaxWidth(200);
            }

        }catch (TextFormattingException e){
            e.printStackTrace();
        }

        attributeFlow.getChildren().add(tAtt);
        attributeFlow.setPrefWidth(125);




        VBox metaContainer = new VBox();
        metaContainer.setSpacing(30);
        HBox container = new HBox();

        container.setSpacing(20);

        container.getChildren().addAll(attributeFlow,entryFlow);
        Separator s = new Separator();
        s.setPrefWidth(325);
        metaContainer.getChildren().addAll(container, s);
        this.getChildren().add(metaContainer);
    }
}
