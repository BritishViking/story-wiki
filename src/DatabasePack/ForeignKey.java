package DatabasePack;

public class ForeignKey {
    private final String refrenceTable;
    private final String label;
    private final String refrenceLabel;
    private final int value;


    public final int INT = 0;
    public final int VARCHAR30 = 1;
    public  final int BOOLEAN = 2;


    public ForeignKey(String label, int value, String refrenceTable, String refrenceLabel){
        this.refrenceLabel = refrenceLabel;
        this.refrenceTable = refrenceTable;
        this.label = label;
        this.value = value;


        if(value <0 || value>2){
            throw new IllegalArgumentException("Invalid value for variable value (2nd argument of constructor in ForeignKey)");
        }
    }

    public String getLabel(){
        return label;
    }
    public String getRefrenceLabel(){
        return refrenceLabel;
    }
    public String getRefrenceTable(){
        return refrenceTable;
    }

    public String getValue(){

        return Values.getValueName(value);
    }

    public int getValueNUmber(){
        return value;
    }


    public String toString(){
        String string = "Label: " + label + " | Refrenced Table: " + refrenceTable + " | Refrenced Label: " + refrenceLabel;

        return string;
    }


}

/*


 2.2  ForeignKey, object that represent a (not-validated) foreignKey, makes sure the format is right;

        -String refrenceTable {read only} //table it references too
        -String refrenceLabel {read only}
        -String label {read only}  //table it is a foreign key in
        -boolean notNull; {read only}
        -String value; {read only}
        +int INT; {read only}
        +int VARCHAR30; {read only}
        +int BOOLEAN; {read only}

        ----------------------------------------------------------------------------
        +ForeignKey(String label, String value, String refrenceTable, String refrenceLabel, boolean isNull);

        +String getLabel();
        +String getRefrenceLabel();
        +String getRefrenceTable();
        +String getValue();
        +int getValueNUmber();
        +boolean isNotNulll();
 */