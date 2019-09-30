import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Klient extends Application {

    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setMinHeight(1000);
        window.setMinWidth(1400);

        Parent root = FXMLLoader.load(getClass().getResource("firstScreen.fxml"));
        Scene loginWindow = new Scene(root);

        Pane pane = new Pane();
        pane.setId("Test");
        if (pane.getId() != null){
            System.out.println("ID: " + pane.getId());
        }
        loginWindow.getStylesheets().add("masterStyle.css");
        window.setScene(loginWindow);
        window.show();
        /*window.setOnCloseRequest(event -> {

        });*/
    }
}












