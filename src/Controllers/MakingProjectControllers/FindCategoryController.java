import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class FindCategoryController extends Controller{
    @FXML Button backButton;

    @FXML TextField searchField;
    @FXML Button searchButton;
    @FXML VBox vBox;

    public void back(ActionEvent event){
        directTo("firstScreen.fxml", getEventStage(event));
    }


    public void getAll(){
        vBox.getChildren().remove(0, vBox.getChildren().size());
        searchAndSet("");
    }

    private void searchAndSet(String s){
        dbo db = new dbo();

        CategoryMeta[] sT = db.findCategories(s, getProject());
        if(sT.length == 0){
            vBox.getChildren().add(new Label("No Results Found"));
        }else {
            for (int i = 0; i < sT.length; i++) {

                CategoryMeta test = sT[i];
                Hyperlink l = new Hyperlink(sT[i].getName());
                l.setOnAction(e -> {

                    EditCategoryController contrl = (EditCategoryController) directTo("editCategory.fxml", getEventStage(e));
                    contrl.setMeta(test);

                });
                vBox.getChildren().add(l);
            }
        }
    }
    public void search(){
        System.out.println(vBox.getChildren().size());

        //empty previus searches
        vBox.getChildren().remove(0, vBox.getChildren().size());

        //run new search
        String s = searchField.getText();
        if(!(s.equals("") || s == null)){
            searchAndSet(s);

        }else{
            vBox.getChildren().add(new Label("You have to type something"));
        }
    }



    public void initialize() {

        searchField.setOnKeyPressed(e ->{
            if(e.getCode() ==  KeyCode.ENTER){
                search();
            }
        });
    }
}


