public class Project {
    private String name;
    private int id;
    private boolean edited;
    public Project(String name, int id){
        this.name = name;
        this.id = id;
        edited = false;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public boolean isEdited(){
        return  edited;
    }

    public void setEdited(boolean status){
        edited = status;
    }

    public void setName(String name){
        if(name.equals(this.name)){
            edited = true;
            this.name = name;
        }
    }

    public String toString(){
        return name + " // " + id + " // " + edited;
    }
}
