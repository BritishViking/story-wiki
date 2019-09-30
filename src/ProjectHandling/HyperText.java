import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HyperText extends Text {
    String article;
    public  HyperText(String txt, String article, Project pro){
        super(txt);
        this.article = article;
        this.setFill(Color.BLUE);
        this.setOnMouseClicked(e -> {
            try {
                Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();


                FXMLLoader loader = new FXMLLoader(getClass().getResource("articlePage.fxml"));
                Parent root = loader.load();

                articlePageController con = loader.getController();
                Scene rootScene = new Scene(root);

                window.setScene(rootScene);
                window.show();

                con.setProject(pro);
                con.setArticle(article);


            }catch (IOException p){
                p.printStackTrace();

            }
        });

        this.setOnMouseEntered(e -> {
            Scene window = ((Node) e.getSource()).getScene();
            window.setCursor(Cursor.HAND);
        });

        this.setOnMouseExited(e -> {
            Scene windoe = ((Node) e.getSource()).getScene();
            windoe.setCursor(Cursor.DEFAULT);
        });

    }
}
