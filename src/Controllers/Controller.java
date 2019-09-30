import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Controller {

    private Project project = null;
    private Stage stage;

    public Project getProject() {
        return project;
    }

    public void setProject(Project p){

        project = p;
    }

    public Stage getEventStage(ActionEvent event){
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
    public  Controller directTo(String fxmlFileName, Stage window){
        try {

            stage = window;



            if(stage.isFullScreen()){
                System.out.println("is fullscreen");
            }else{
                System.out.println("is not fullscreen");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();

            Scene rootScene = new Scene(root);

            window.setScene(rootScene);
            window.show();


            Controller con = loader.getController();
            con.setProject(project);



            return con;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public Stage getStage(){
        return stage;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

}
