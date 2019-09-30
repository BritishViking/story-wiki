
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class OpenScreenController extends Controller {

    @FXML Button editProjects;
    @FXML Button newProjects;

    @FXML Label proLabel;

    public void editEvent(ActionEvent event){


        // TODO redirect to edit controller
        directTo("articleSearch.fxml", getEventStage(event));
       /* articlePageController art = (articlePageController) directTo("articlePage.fxml", getEventStage(event));
        art.setArticle("Jonisburg");*/
    }

    public void createNewArticle(ActionEvent e){
        directTo("newArticle.fxml", getEventStage(e));
    }

    public void createEvent(ActionEvent event){

        directTo("chooseType.fxml", getEventStage(event));

    }

    public void newCategory(ActionEvent event){
        directTo("newCategory.fxml", getEventStage(event));
    }

    public void edit(ActionEvent event){
        articleEditPageController art = (articleEditPageController)directTo("articlePageEdit.fxml", getEventStage(event));
        art.setArticle("Jonisburg");
    }

    public void switchPro(){
        if(getProject() != null) {
            if (getProject().getId() != 2) {
                setProject(new Project("Ny", 2));
            } else {
                setProject(new Project("Test", 1));
            }
        }else{
            setProject((new Project("Test", 1)));
        }

        proLabel.setText(getProject().getName());
        System.out.println(getProject());
    }

    @Override
    public void setProject(Project p) {
        super.setProject(p);
        proLabel.setText(getProject().getName());
    }

    public void initialize(){
        setProject(new Project("Test", 1));
    }

    public void editCat(ActionEvent e){
        directTo("findCategory.fxml", getEventStage(e));

    }
}
