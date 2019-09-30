import javafx.scene.control.Separator;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class FXReadOnlyArticleEntry extends TextFlow {

    private Text head;
    private Text[] body;

    public FXReadOnlyArticleEntry(String headString, String bodyString, Project pro){

        head = new Text(headString + "\n\n");

        try {
            body = TextWizard.textAsDisplayable(bodyString, pro );
        }catch (TextFormattingException e){
            e.printStackTrace();
        }


       this.head.setFont(new Font(20));

       this.getChildren().add(head);
       for(int i = 0;i<body.length;i++){
           this.getChildren().add(body[i]);
       }



    }
}
