import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;


public class ArticleSearchController extends Controller  {
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
        DBO db = new DBO();

        String[] sT = db.searchByName(s, getProject());
        if(sT.length == 0){

            vBox.getChildren().add(new Label("No Results Found"));
        }else {
            for (int i = 0; i < sT.length; i++) {

                Hyperlink l = new Hyperlink(sT[i]);
                l.setOnAction(e -> {
                    articlePageController contrl = (articlePageController) directTo("articlePage.fxml", getEventStage(e));
                    contrl.setArticle(l.getText());
                });
                vBox.getChildren().add(l);
            }
        }
    }
    public void search(){
        System.out.println(vBox.getChildren().size());
        vBox.getChildren().remove(0, vBox.getChildren().size());
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
