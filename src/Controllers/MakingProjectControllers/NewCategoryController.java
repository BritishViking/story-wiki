import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;



public class NewCategoryController extends Controller {
    @FXML Pane attributeHolder;
    @FXML TextField titleFIeld;


    private VBox attributVBox = new VBox();

    public void back(ActionEvent event){
        directTo("firstScreen.fxml", getEventStage(event));
    }


    public void create(ActionEvent event){
        //TODO build category

        unselectAllButOne(-1);
        int vSize = attributVBox.getChildren().size();  // antall attributter

        String[] temp = new String[vSize];
        int index = -1;
        for(int i = 0; i<vSize; i++){
            if(!((FXCategoryAttribute)attributVBox.getChildren().get(i)).isDeleted()){
                index++;
                temp[index] = ((FXCategoryAttribute)attributVBox.getChildren().get(i)).getText();
            }
        }
        String[] attr;
        if(index!= -1) {
          attr = new String[index + 1];

          for(int i = 0; i<attr.length; i++){
              attr[i] = temp[i];
          }

        }else{
            attr = null;
        }


        //TODO do security

        if(titleFIeld.equals("")){
            System.out.println("You must fill in the titlefield");
            return;
        }

        // TODO WRITE IF ok

        DBO db = new DBO();

        System.out.println(db.createNewCategory(titleFIeld.getText(), attr, getProject()));


        // TODO direct homr (run back?)

        directTo("firstScreen.fxml", getEventStage(event));
    }


    public void newAtt(){

        System.out.println(attributVBox.getChildren().size());
        FXCategoryAttribute ent = new FXCategoryAttribute("New Attribute", attributVBox.getChildren().size());

        ent.setOnMouseClicked(e->{
            unselectAllButOne(ent.getIndex());
        });
        attributVBox.getChildren().add(ent);

    }
    public void unselectAllButOne(int index){
        for(int i = 0; i<attributVBox.getChildren().size(); i++){
            if( i == index){
                ((FXCategoryAttribute) attributVBox.getChildren().get(i)).setSelected(true); //  dette betyr attribute nr i setSelected(true)
            }else{
                ((FXCategoryAttribute) attributVBox.getChildren().get(i)).setSelected(false); // se oppe
            }
        }
    }

    public void writeProjetc(){
        if(this.getProject() != null){
            System.out.println(this.getProject());
        }else{
            System.out.println("Project Is Null");
        }
    }

    public void initialize(){

        String[] att = {"maria", "Er", "KUl"};


        for(int i = 0; i<att.length; i++){
            FXCategoryAttribute ent = new FXCategoryAttribute(att[i], i);
            ent.setOnMouseClicked(e->{
                unselectAllButOne(ent.getIndex());
            });
            attributVBox.getChildren().add(ent);

        }
        attributeHolder.getChildren().add(attributVBox);
        attributVBox.setSpacing(30);



    }
}
