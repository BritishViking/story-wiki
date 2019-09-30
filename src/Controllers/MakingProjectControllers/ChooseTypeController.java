

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;




public class ChooseTypeController extends Controller {

    @FXML RadioButton twoDim;
    @FXML RadioButton threeDim;

    public void goToHome(ActionEvent event){
        directTo("firstScreen.fxml", getEventStage(event));
    }

    public void createGame(ActionEvent event){
        directTo("createGame.fxml", getEventStage(event));
    }
    public void initialize(){

    }
}
