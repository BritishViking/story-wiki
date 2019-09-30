

//import DatabasePack.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class CreateGameController extends Controller {
/*
    @FXML RadioButton twoDim;
    @FXML RadioButton threeDim;
    @FXML RadioButton noPlatform;
    @FXML RadioButton choosePlatform;
    @FXML TextField platformTextField;
    @FXML Label error;
    @FXML TextField name;
    @FXML TextArea discription;

    private ToggleGroup graphics = new ToggleGroup();
    private ToggleGroup platform =  new ToggleGroup();;


    public void goToHome(ActionEvent event){
        directTo("firstScreen.fxml", getEventStage(event));
    }

    public void checkSelectedPlatform(ActionEvent event){
        System.out.println("Method checkSelectedPlatform() triggered");
        if (choosePlatform.isSelected()){
            platformTextField.setVisible(true);
        }else{
            platformTextField.setVisible(false);
        }
    }

    public void createGame(ActionEvent event){
        System.out.println("Method createGame is called");
       if(name.getText().equals("")){
           error.setVisible(true);
           error.setText("You have to give the project a name!");
           System.out.println("Error1");
       }else if(name.getText().length() < 6){
           error.setText("The name of the project has to be at least 6 characters");
           System.out.println("Error2");
       }else {

           GameMeta game = new GameMeta(name.getText());

           if (twoDim.isSelected()) {
               game.setGraphics(GameMeta.TWODIM);
           } else {
               game.setGraphics(GameMeta.THREEDIM);
           }

           if (choosePlatform.isSelected()) {
               game.setPlatform(platformTextField.getText());
           }

           if (!discription.getText().equals("")) {
               game.setDescription(discription.getText());
           }

           System.out.println(game);

           DatabaseConnector.setDatabaseInfo("localhost", "worldbuilding", "root", "Kvj900I23dDf");

           String s  = "INSERT INTO project(pro_id, name, discription, type) VALUES (4,'"+ game.getName() + "', '" + game.getDiscription() + "', 'game');";

           try {
               DatabaseConnector.exec(s);
           }catch (Exception e){
               e.printStackTrace();
               error.setText("Something went wrong");
               System.out.println("Error3");
           }
           // TODO write project to db

           // TODO redirect to project page

           // TODO
       }


    }



    public void initialize(){

        error.setVisible(false);
        platformTextField.setVisible(false);
        twoDim.setToggleGroup(graphics);
        twoDim.setSelected(true);
        threeDim.setToggleGroup(graphics);

        noPlatform.setToggleGroup(platform);
        noPlatform.setSelected(true);
        choosePlatform.setToggleGroup(platform);
    }

*/
}
