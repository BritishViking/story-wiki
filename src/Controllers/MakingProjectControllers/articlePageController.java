

        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
import javafx.fxml.Initializable;
        import javafx.geometry.Insets;
        import javafx.scene.control.*;
        import javafx.scene.layout.VBox;


        import java.net.URL;
        import java.util.ResourceBundle;


public class articlePageController extends Controller implements Initializable {

    @FXML Label articleTitle;
    @FXML VBox articleBody;
    @FXML Button backButton;
    @FXML Label kategory;
    @FXML VBox kategoryBody;
    @FXML Button editButton;
    @FXML ScrollPane scrollBody;
    Article article;

    public void setArticle(String artName){
        dbo db = new dbo();
        setArticle( db.getArticle(artName, getProject()));
    }

    public void setArticle(Article art)
    {

        article = art;
        System.out.println(art);
        if(art != null) {
            articleTitle.setText(art.getHead());
            ArticleEntry[] body = art.getBody();
            if(body != null) {

                    String s = "";

                    for(int i = 0; i<body.length; i++){
                       FXReadOnlyArticleEntry en = new FXReadOnlyArticleEntry(body[i].getHead(), body[i].getBody(), getProject());

                        articleBody.getChildren().addAll(en, new Separator());


                    }

            }

            CategoryEntry entries = art.getCategoryEntries();

            // set category entries
            if(entries!= null) {
                kategory.setText(entries.getCategory().getName());
                DisplayableCatAttEntry[] dEntries = entries.getDisplayableEntries();

                for(int i = 0; i<dEntries.length; i++){
                    kategoryBody.getChildren().add(new FXReadOnlyCatAttEntry(dEntries[i], getProject()) );
                }

            }else{
                kategory.setText("No Category");

            }

        }else{
            setDefault();
        }
    }

    public void edit(ActionEvent event){
        articleEditPageController con = (articleEditPageController) directTo("articlePageEdit.fxml", getEventStage(event));
        con.setArticle(article);
    }

    public void setDefault(){

        articleTitle.setText("No Article found");
    }
    public void initialize(URL location, ResourceBundle resources){
        setDefault();
        articleBody.setSpacing(30);
        scrollBody.setPadding(new Insets(30));
    }

    public void back(ActionEvent event){
        directTo("firstScreen.fxml", getEventStage(event));
    }
}