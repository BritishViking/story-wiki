import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;



public class EditCategoryController extends Controller {
    @FXML Pane attributeHolder;
    @FXML TextField titleFIeld;
    private CategoryMeta meta;


    private VBox attributVBox = new VBox();

    public void back(ActionEvent event){
        directTo("firstScreen.fxml", getEventStage(event));
    }

    @Override
    public void setProject(Project p) {
        super.setProject(p);
        dbo db = new dbo();


    }

    public void setMeta(CategoryMeta meta){
        this.meta = meta;
        titleFIeld.setText(meta.getName());

        if(meta.getAttributeNumber() != 0) {
            String[] attributes = meta.getAttributes();


            for (int i = 0; i < attributes.length; i++) {
                System.out.println(attributes[i]);
                addAtt(attributes[i]);
            }
        }
    }

    public void create(ActionEvent event){
        //TODO build category

        unselectAllButOne(-1);
        int vSize = attributVBox.getChildren().size();  // antall attributter
        int oldSize = meta.getAttributeNumber();

        int index = -1;
        for(int i = 0; i<oldSize; i++){
            FXCategoryAttribute attr = ((FXCategoryAttribute)attributVBox.getChildren().get(i));
            if(attr.isDeleted()){
                System.out.println(i);
                meta.deleteAttribute(i);
            }else if(attr.isEdited()){
                meta.updateAttribute(i, attr.getText());
            }

            System.out.println("NAME and INDEX: "+ attr.getIndex() + " "+ attr.getText() + " // isEdited? " + attr.isEdited());
        }

        int newSize = vSize - oldSize;

        for(int i= 0; i<newSize; i++){
            FXCategoryAttribute attr = ((FXCategoryAttribute)attributVBox.getChildren().get(oldSize + i));
            if(!attr.isDeleted()){
                meta.addNewAttribute(attr.getText());
            }
        }


        //TODO do security

        if(titleFIeld.equals("")){
            System.out.println("You must fill in the titlefield");
            return;
        }else if (!titleFIeld.equals(meta.getName())){
            meta.updateName(titleFIeld.getText());
        }

        // TODO Update category

        dbo db = new dbo();

        db.updateCategory(meta);
        System.out.println("\n\n");
        System.out.println(meta);

        // TODO direct homr (run back?)

        directTo("firstScreen.fxml", getEventStage(event));
    }

    public void addAtt(String name){
        System.out.println(attributVBox.getChildren().size());
        FXCategoryAttribute ent = new FXCategoryAttribute(name, attributVBox.getChildren().size());

        ent.setOnMouseClicked(e->{
            unselectAllButOne(ent.getIndex());
        });
        attributVBox.getChildren().add(ent);
    }


    public void newAtt(ActionEvent e){
        addAtt("New Attribute");
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


    public void initialize(){


        attributeHolder.getChildren().add(attributVBox);
        attributVBox.setSpacing(30);



    }
}
