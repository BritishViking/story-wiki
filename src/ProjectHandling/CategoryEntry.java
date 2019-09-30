public class CategoryEntry {
    private CategoryMeta category;
    private DisplayableCatAttEntry[] entries;
    private boolean edited = false;

    public CategoryEntry(CategoryMeta info){
        this.category = info;

        entries = new DisplayableCatAttEntry[category.getAttributeNumber()];

        for(int i = 0; i<entries.length; i++){
            entries[i] = new DisplayableCatAttEntry(i, null, category);
        }
    }



    public CategoryMeta getCategory() {
        return category;
    }
    public boolean getEdited(){
        return edited;
    }

    public int getSize(){
        return entries.length;
    }
    public String getEntry(String attributeName){
        int index = findAttributeNumber(attributeName);

        if(index != -1){
            String result = entries[index].getValue();
            if(result == null){
                return "N/A";
            }else{
                return result;
            }
        }

        return null;
    }

    /**
     * @deprecated
     * @return
     */
    public String[] getValues() {
        String[] values = new String[entries.length];
        for(int i = 0; i<values.length; i++){
            if(entries[i] != null) {
                values[i] = entries[i].getValue();
            }else{
                values[i] = null;
            }
        }
        return values;
    }

    public void setEdited(boolean status){
        edited = status;
    }

    public boolean addEntry(String attributeName, String entry){
        edited = true;
        int index = findAttributeNumber(attributeName);

        if(index != -1){
            entries[index] = new DisplayableCatAttEntry(index, entry, category);
            return true;
        }

        return false;

    }



    private int findAttributeNumber(String attributeName){
        String[] attributes = category.getAttributes();
        for(int i = 0; i<attributes.length; i++ ){
            if(attributeName.equals(attributes[i])){
                return i;
            }
        }

        return -1;
    }

    public String toString(){
        String s = "--" + category.getMetaIndex() + ":  " + category.getName() + "--";
        String[] attr = category.getAttributes();
        for(int i = 0; i< attr.length; i++){
            s += "\n" + attr[i] + ":  ";
            if(entries[i].getValue() != null){
                s += entries[i].getValue() + " // edited: " + entries[i].isEdited() + " // written: " + entries[i].isWritten();
            }else{
                s += "N/A";
            }
        }
        return s;
    }



    public boolean setEntries(String[] values){
        if(values.length == this.entries.length){
            for(int i = 0; i<entries.length; i++){
                this.entries[i].updateValue(values[i]);
                this.entries[i].setWritten(true);
                this.entries[i].setEdit(false);
            }
            return true;
        }

        return false;
    }
/*
    public boolean saveEntries(DisplayableCatAttEntry[] entries){

        for(int i = 0; i< entries.length; i++){
            if(!entries[i].isSameCategory(category) || (entries[i].getMetaIndex() >= values.length) || (entries[i].getMetaIndex() < 0)){
                return false;
            }
        }

        for(int i = 0; i< entries.length; i++){

            values[entries[i].getMetaIndex()]  = entries[i].getValue();


        }

        return true;
    } */



    public DisplayableCatAttEntry[] getDisplayableEntries(){


        return entries;

    }

    public static void main(String[]args){
        /*String[] att = {"Name", "Age", "Familiy"};
        CategoryMeta cat = new CategoryMeta("Person", att, 1);
        CategoryEntry ent = new CategoryEntry(cat);

        System.out.println(ent);

        System.out.println(ent.addEntry("Name", "Septer"));
        System.out.println(ent.addEntry("Age", "27"));
        System.out.println(ent.addEntry("Familiyi", "27"));

        DisplayableCatAttEntry[] entt = ent.getDisplayableEntries();

        for(int i = 0; i<entt.length; i++){
            System.out.println("-----------------------" + entt[i]);
        }*/
    }
}
