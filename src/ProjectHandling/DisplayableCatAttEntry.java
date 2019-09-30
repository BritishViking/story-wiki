public class DisplayableCatAttEntry {
    private int index;
    private String value;

    private CategoryMeta cat;
    private boolean edited = false;
    private boolean isWritten = false;

    public  DisplayableCatAttEntry(int i, String value,  CategoryMeta cat){
        this.index = i;
        this.value = value;
        this.cat = cat;
    }

    public String getValue(){
        return value;
    }


    public int getIndex(){
        return index;
    }

    public CategoryMeta getCategory(){
        return cat;
    }
    public int getPhysicalIndex(){
        return cat.getAttributeObject(index).getAttributeIndex();
    }

    public String getAtt(){
        return cat.getAttribute(index);
    }

    public boolean isSameCategory(CategoryMeta cat){
        if(cat.getName().equals(this.cat.getName())){
            return true;
        }else{
            return false;
        }
    }

    public boolean isEdited(){
        return edited;
    }

    public boolean isWritten() {
        return isWritten;
    }

    public void setWritten(boolean status){
        isWritten = status;
    }

    public void setEdit(boolean status)
    {

        edited = status;
    }

    public void updateValue(String value){
        if(value == null || this.value == null){
            if(value!= null ){
                isWritten = false;
                edited = true;
                this.value = value;
            }
        }else{
            if(!(value.equals(this.value))){
                edited = true;
                this.value = value;
            }
        }
    }

    public String toString(){
        String val;
        if(value == null){
            val = "N/A";
        }else {
            val = value;
        }
        return "index = " + index + "\natt = " + cat.getAttribute(index) + "\nvalue = "+val + "\nedited = " + edited + "\ncategory = " + cat.getName() + "\nIs written = " + isWritten;
    }

    public static void main(String[]args){
        String[] att = {"Name", "Age", "Familiy"};
       /* CategoryMeta cat = new CategoryMeta("Pers,on" , att, 1);

        DisplayableCatAttEntry entry = new DisplayableCatAttEntry(1, "Maria", cat);

        entry.updateValue("Maria");

        System.out.println(entry);*/
    }
}
