public class CategroyAttribute {
    private boolean edited;
    private boolean deleted;
    private boolean written;
    private String name;
    private int index;

    public CategroyAttribute(String name, int index){
        this.index = index;
        this.name = name;
        written = true;
    }

    public CategroyAttribute(String name){
        this.index = -1;
        this.name = name;
        written = false;
    }

    public boolean isWritten(){
        return written;
    }

    public String getAttributeName(){
        return name;
    }

    public int getAttributeIndex(){
        return index;
    }

    public boolean isEdited(){
        return edited;
    }

    public void setDeleted(boolean status){
        deleted = status;
    }

    public boolean isDeleted(){
        return deleted;
    }

    public void changeAttributeName(String name){
        if(!(name.equals(this.name))){
            this.name = name;
            edited = true;
        }
    }
}
