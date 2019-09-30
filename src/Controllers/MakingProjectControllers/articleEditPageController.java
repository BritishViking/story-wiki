
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class articleEditPageController extends Controller {
    @FXML Button save;
   /* @FXML TableColumn<DisplayableCatAttEntry, String>  col_thing = new TableColumn<>("Entry");
    @FXML TableColumn<DisplayableCatAttEntry, String> col_name = new TableColumn<>("Name");*/
 //   @FXML TableView<DisplayableCatAttEntry> tabKat = new TableView<>();
    @FXML VBox tabKat;
    @FXML Label articleTitle;
    @FXML VBox bodyContainer;
    @FXML Label kategory;
    @FXML Button addNewBody;
    private FXArticleEntry[] vboxEntries;
    private FXCatAttEntry[] vboxCatEntries;

    private ObservableList<DisplayableCatAttEntry> obsListe;

    Article art;

    public void setArticle(String artName){
        dbo db = new dbo();
        Article art = db.getArticle(artName, getProject());
        if(art != null) {
            setArticle(art);
        }
    }

    public void setArticle(Article art){
        this.art = art;
        articleTitle.setText(art.getHead());


        CategoryEntry cat = art.getCategoryEntries();
        if(cat != null) {
            CategoryMeta catMeta = cat.getCategory();
            kategory.setText(catMeta.getName());
            if(cat.getDisplayableEntries() != null)
                setAttributes(cat.getDisplayableEntries());
        }else{
            kategory.setText("No category");
        }

        System.out.println(art);

        if(art.getBody() != null)
        setBody(art.getBody());
    }

    public void delete(ActionEvent event){
        dbo db = new dbo();

        db.deleteArticle(art.getArticleNUmber());
        directTo("firstScreen.fxml", getEventStage(event));
    }

    public void  setBody(ArticleEntry[] ent){
        vboxEntries = new FXArticleEntry[ent.length];
        for(int i = 0; i<vboxEntries.length; i++){
            vboxEntries[i] = new FXArticleEntry(ent[i], i);
            vboxEntries[i].setOnMouseClicked(e -> {
                FXArticleEntry entry = (FXArticleEntry) e.getSource();

                unselectAllArticleButOne(entry.getIndex());
            });
        }
        ArticleEntry jk = new ArticleEntry("Maria", "mariamaria", 1);

        bodyContainer.setVisible(true);

        for(int i= 0; i<vboxEntries.length; i++){
            bodyContainer.getChildren().add(vboxEntries[i]);
        }


    }

    public void unselectAllArticleButOne(int index){
        for(int i = 0; i<vboxEntries.length; i++){
            if(i!= index){
                vboxEntries[i].setSelected(false);
            }else{
                vboxEntries[i].setSelected(true);
            }
        }
    }

    public void unselectAllCatAttEntButOne(int index){
        if(vboxCatEntries != null) {
            for (int i = 0; i < vboxCatEntries.length; i++) {
                if (vboxCatEntries[i].getEntry().getIndex() == index) {
                    vboxCatEntries[i].setSelected(true);
                } else {
                    vboxCatEntries[i].setSelected(false);
                }
            }
        }
    }



    public void setAttributes(DisplayableCatAttEntry[] entries) {

        vboxCatEntries = new FXCatAttEntry[entries.length];
        for (int i = 0; i < entries.length; i++) {

            FXCatAttEntry ent = new FXCatAttEntry(entries[i]);
            vboxCatEntries[i] = ent;
            ent.setOnMouseClicked(e -> {
                unselectAllCatAttEntButOne(ent.getEntry().getIndex());
            });
            tabKat.getChildren().add(ent);

        }
    }

    public void save(ActionEvent event){

        unselectAllCatAttEntButOne(-1);
        if(vboxEntries != null) {

            for (int i = 0; i < vboxEntries.length; i++) {
                vboxEntries[i].save();

            }
        }

        dbo db = new dbo();
        System.out.println("\n------------------------------------------------\n"+art);
       db.saveArticle(art);


        articlePageController con = (articlePageController) directTo("articlePage.fxml", getEventStage(event));
        con.setArticle(art.getHead());
    }

    public void addNewBody(ActionEvent event){
        System.out.println("Add new!");
        ArticleEntry[] oldEntries = art.getBody();
        int length;
        if(oldEntries != null){
            length = oldEntries.length;
        }else{
            length = 0;
        }
        ArticleEntry en = new ArticleEntry("New head", "New Body", length);

        art.addEntry(en);
        makeSpace();
        vboxEntries[vboxEntries.length-1] = new FXArticleEntry(en, (vboxEntries.length-1));
        bodyContainer.setVisible(true);
        bodyContainer.getChildren().add(vboxEntries[vboxEntries.length-1]);
        unselectAllArticleButOne((vboxEntries.length-1));
    }

    private void makeSpace(){
        if(vboxEntries != null) {
            FXArticleEntry[] temp = new FXArticleEntry[vboxEntries.length + 1];
            for (int i = 0; i < vboxEntries.length; i++) {
                temp[i] = vboxEntries[i];
            }

            vboxEntries = temp;
        }else{
            vboxEntries = new FXArticleEntry[1];
        }
    }


    public void initialize() {
        bodyContainer.setSpacing(30);
        tabKat.setSpacing(20);

    }
}
