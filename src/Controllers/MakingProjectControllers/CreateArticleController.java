import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class CreateArticleController extends Controller {

    @FXML TextField titleField;
    @FXML Button createButton;
    @FXML Button backButton;
    @FXML TextField categoryField;
    @FXML CheckBox catCheck;
    @FXML Label titleLabel;
    @FXML ScrollPane categorySuggestions;

    VBox categorySuggHolder;

    public void back(ActionEvent event){
        directTo("firstScreen.fxml", getEventStage(event));
    }

    public void check(ActionEvent event){
        if(catCheck.isSelected()){
            categoryField.setDisable(false);


        }else{
            categoryField.setDisable(true);
            categoryField.setText("");
            categorySuggHolder.setVisible(false);
            categorySuggestions.setVisible(false);
        }
    }

    public void checkCategoryTyping(){


        if(!categoryField.getText().equals("")){
            DBO db = new DBO();

            CategoryMeta[] suggiestions = db.findCategories(categoryField.getText(), getProject());

            if(suggiestions!= null) {
                categorySuggHolder.getChildren().clear();

                for (int i = 0; i < suggiestions.length; i++) {
                    Text txt = new Text(suggiestions[i].getName());
                    txt.setFill(Color.GRAY);
                    txt.setOnMouseEntered(e ->{
                        txt.setFill(Color.BLACK);
                        Scene s = ((Node) e.getSource()).getScene();
                        s.setCursor(Cursor.HAND);

                    });

                    txt.setOnMouseExited(e->{
                        txt.setFill(Color.GRAY);
                        Scene s = ((Node) e.getSource()).getScene();
                        s.setCursor(Cursor.DEFAULT);
                    });





                    txt.setOnMouseClicked(e->{

                        categoryField.setText(txt.getText());
                        categorySuggHolder.setVisible(false);
                        categorySuggestions.setVisible(false);




                    });
                    categorySuggHolder.getChildren().add(txt);
                }
            }else{
                categorySuggHolder.getChildren().clear();
                Text t  = new Text("No Result");

                categorySuggHolder.getChildren().addAll(t);
            }
            // do stufff here
            categorySuggHolder.setVisible(true);
            categorySuggestions.setVisible(true);
            System.out.println("Visible");
        }else{
            categorySuggHolder.setVisible(false);
            categorySuggestions.setVisible(false);
            System.out.println("UnVisable");
        }
    }

    public void create(ActionEvent event){
        DBO db = new DBO();

        String title = titleField.getText();
        String category = null;
        if(catCheck.isSelected()){
            category = categoryField.getText();

        }



        Article a = db.createNewArticle(title, db.findCategory(category, getProject() ), getProject());
        articlePageController con = (articlePageController) directTo("articlePage.fxml", getEventStage(event));
        con.setArticle(a);
    }

    public void initialize(){


        catCheck.setSelected(false);
        categoryField.setOnKeyReleased(e->{
            checkCategoryTyping();
        });

        categoryField.setDisable(true);
        categorySuggHolder = new VBox();


        categorySuggestions.setContent(categorySuggHolder);
        categorySuggestions.setPadding(new Insets(10,10,10,10));
        categorySuggestions.setVisible(false);
        categorySuggHolder.setVisible(false);
        categorySuggHolder.setSpacing(10);



    }




}
