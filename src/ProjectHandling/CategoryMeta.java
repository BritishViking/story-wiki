public class CategoryMeta {
    private String name;
    private String oldName;
    private CategroyAttribute[] attributes;
    private int index;
    private boolean nameEdit;
    public final int DELETED = 0;
    public final int EDITED = 1;
    public final int UNEDITED = 2;
    public final int NEWATTR = 3;

    public CategoryMeta(String name, CategroyAttribute[] attributes, int index){
        this.name = name;
        this.attributes = attributes;
        this.index = index;
        this.nameEdit = false;
        oldName = name;

    }



    public enum status{

    }



    public String getName() {
        return name;
    }

    public int getMetaIndex() {
        return index;
    }

    /*public int[] getEditMap(){
        int[] newMap = new int[editMap.length];
        for(int i = 0; i<editMap.length; i++){
           newMap[i] =  editMap[i];
        }
        return newMap;
    }*/

    public void updateName(String name){
        if(!name.equals(this.name)){
            this.name = name;
            nameEdit = true;
        }else if(oldName.equals(name)){
            this.name = name;
            nameEdit = false;
        }
    }

    /*public String getOldAttributeName(int index){
        if(index < 0 || index > (oldAttributes.length -1)){
            throw new IndexOutOfBoundsException("Attribute does not exsist, or has not been written yet");
        }

        return oldAttributes[index];
    }*/

    public void updateAttribute(int index, String newValue){
        if(index < 0 || index > (attributes.length -1)){
            throw new IndexOutOfBoundsException("Attribute does not exsist");
        }
        attributes[index].changeAttributeName(newValue);
    }

    public void addNewAttribute(String value){
        CategroyAttribute[] newAttTab;
        if(attributes !=null) {
            newAttTab = new CategroyAttribute[attributes.length + 1];


            for(int i = 0; i<attributes.length; i++){
                newAttTab[i] = attributes[i];
            }
        }else{
            newAttTab = new CategroyAttribute[1];
        }

        newAttTab[newAttTab.length -1] = new CategroyAttribute(value);

        attributes = newAttTab;
    }

    public void deleteAttribute(int index){
        if(index < 0 || index > (attributes.length -1)){
            throw new IndexOutOfBoundsException("Attribute does not exsist");
        }

        attributes[index].setDeleted(true);
    }

    public String[] getAttributes(){
        String[] attAsString = new String[attributes.length];
        for(int i = 0; i<attAsString.length; i++){
            attAsString[i] = attributes[i].getAttributeName();
        }

        return attAsString;
    }

    public boolean isNameEdit(){
        return nameEdit;
    }

    public int getAttributeNumber(){
        if(attributes!= null){
            return attributes.length;
        }else{
            return 0;
        }
    }
    public String getAttribute(int index){
        if(attributes != null) {
            if (index >= attributes.length || index < 0) {
                System.out.println("Index Out Of Bounds");
                return null;
            } else {
                return attributes[index].getAttributeName();
            }
        }else{
            return null;
        }
    }

    public int getAttributeStatus(int index){
        if(attributes != null) {
            if (index >= attributes.length || index < 0) {
                throw new IndexOutOfBoundsException("Invalid Virtual Attribute index");
            } else {
                if(attributes[index].isDeleted()){
                    return DELETED;
                }else if(!(attributes[index].isWritten())){
                    return NEWATTR;
                }else if(attributes[index].isEdited()){
                    return EDITED;
                }else{
                    return UNEDITED;
                }
            }
        }else{
            return -1;
        }
    }

    public CategroyAttribute getAttributeObject(int index){
        if(index < 0 || index > (attributes.length -1)){
            throw new IndexOutOfBoundsException("Attribute does not exsist");
        }
        return attributes[index];
    }

    public String toString(){
        String s = "--"+ index + ":  " + name + "-- // is edited: " + nameEdit;
        if(attributes != null) {
            for (int i = 0; i < attributes.length; i++) {
                s += "\n" + attributes[i].getAttributeName() ;
                int status = getAttributeStatus(i);
                switch (status) {
                    case DELETED:
                        s += " // status: DELETED";
                        break;
                    case EDITED:
                        s += "  // status: EDITED";
                        break;
                    case UNEDITED:
                        s += " // status: UNDEDITED";
                        break;
                    case NEWATTR:
                        s += " // status: NEWATTR";
                        break;
                }
            }
        }
        return s;
    }

    public static  void  main(String[]args){
        CategroyAttribute[] att = new CategroyAttribute[4];

        att[0] = new CategroyAttribute("Peronality", 9);
        att[1] = new CategroyAttribute("Haircolor", 10);
        att[2] = new CategroyAttribute("Eyecolor", 2);
        att[3] = new CategroyAttribute("SkinColor", 6);


        CategoryMeta cat = new CategoryMeta("Person", att, 1);

        cat.updateAttribute(0, "Names");
        cat.deleteAttribute(1);
        cat.addNewAttribute("Height");
        System.out.println(cat);
    }
}
