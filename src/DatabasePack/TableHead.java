package DatabasePack;

public class TableHead {

    private ForeignKey[] fks = null;
    private String name;
    private String[][] columns; //navn, verdi, nullabe


    public TableHead(String name, String pkName, int pkValue){
        String valName = Values.getValueName(pkValue);
        if(valName == null ){
            throw new IllegalArgumentException("Invalid value for primary key");
        }
        this.name = name;
        columns  = new String[1][3];
        columns[0][0] = pkName;
        columns[0][1] = valName;
        columns[0][2] = Values.NOTNULL;
    }
    public static void skriv(Object o){
        System.out.println(o);
    }

    public String getName(){
        return name;
    }

    public ForeignKey[] getForeignKeys() {
        ForeignKey[] newFks = null;
        if (fks != null) {
            newFks = new ForeignKey[fks.length];
            for (int i = 0; i < fks.length; i++) {
                newFks[i] = fks[i];
            }
        }
        return newFks;
    }

    public String getPkName(){
        return columns[0][0];
    }

    public String[] getColumns(){
        String[] col = new String[columns.length];
        for (int i = 0; i<columns.length; i++){
            col[i] = columns[i][0];
        }

        return col;
    }

    public String getPkValue(){
        return columns[0][1];
    }

    public int getColumnCount(){
        return columns.length;
    }

    public String getColumnValue(String label){
        for (int i = 0; i<columns.length; i++){
            if(label.equals(columns[i][0])){
                return columns[i][1];
            }
        }

        return null;
    }
    public String getColumnValue(int columnIndex){
        if(columnIndex<0 || columnIndex>columns.length-1){
            return null;
        }
        return columns[columnIndex][1];
    }
    public boolean addColumn(String name, int value, boolean notNull){
        String val = Values.getValueName(value);

        if(val == null){
            throw new IllegalArgumentException("Invalid column value");
        }

        if(columnExist(name)){
            return false;
        }

        addColumnSpace();
        int index = columns.length;
        columns[index-1][0] = name;
        columns[index-1][1] = val;
        columns[index-1][2] = Values.getNullable(notNull);

        return true;
    }

    public String getColumnName(String colLabel){
        int index = getColIndex(colLabel);

        return getColumnName(index);
    }

    public String getColumnName(int colIndex){
        if(colIndex <0 || colIndex> columns.length-1){
            return null;
        }

        return columns[colIndex][0];
    }

    public int getColIndex(String colLabel){
        for( int i = 0; i<columns.length; i++){
            if(columns[i][0].equals(colLabel)){
                return i;
            }
        }

        return -1;
    }

    private void addColumnSpace(){
        String[][] newColumns = new String[columns.length+1][3];

        for(int i = 0; i<columns.length; i++){
            newColumns[i] = columns[i];
        }

        columns = newColumns;
    }
    public boolean addForeignKeyConstraint(ForeignKey fk){


        if(!(columnExist(fk.getLabel(), fk.getValue()))){
            return false;
        }

        addFksSpace();

        fks[fks.length-1] = fk;

        return true;
    }

    private void addFksSpace(){
        ForeignKey[] newFks;
        if(fks != null) {
            newFks = new ForeignKey[fks.length+1];
            for (int i = 0; i < fks.length; i++) {
                newFks[i] = fks[i];
            }
        }else{
            newFks = new ForeignKey[1];
        }

        fks = newFks;
    }

    public String toString(){
        String string = "Primary Key: " +columns[0][0]+ " Value: " + columns[0][1];
        string +="\n";

        string += columns[1][0] + " " + columns[1][1];

        if((columns[1][2]).equals(Values.NOTNULL)){
            string += " " + columns[1][2];

        }
        for(int i = 2; i<columns.length; i++){
            string +="\n";
            string += columns[i][0] + " " + columns[i][1];

            if((columns[i][2]).equals(Values.NOTNULL)){
                string += " " + columns[i][2];

            }

        }

        if(fks != null){
            string += "\n\nForeign Key(s)\n";

            string += fks[0];
            for(int i = 1; i<fks.length; i++){
                string += "\n" + fks[i];
            }
        }

        return string;
    }

    public boolean columnExist(String columnName){
        for(int i = 0; i<columns.length; i++){
            if(columnName.equals(columns[i][0])){
                return true;
            }
        }

        return false;
    }
    public boolean columnExist(String columnName, String val){


        for (int i = 0; i < columns.length; i++) {
            if (columnName.equals(columns[i][0]) && val.equals(columns[i][1])) {
                return true;
            }

        }


        return false;
    }

    public boolean canBeNull(int colIndex){
        if ( colIndex < 0 || colIndex > columns.length-1){
            throw new IllegalArgumentException("Invalid Column index");
        }

        if(columns[colIndex][2].equals(Values.NOTNULL)){
            return false;
        }else {
            return true;
        }
    }

    public boolean canBeNull(String colLabel){

        int colIndex = getColIndex(colLabel);

        if(colIndex == -1){
            throw  new IllegalArgumentException("Collum Label Does Not Exist");
        }
        return canBeNull(colIndex);
    }

    public TableHead copy(){
        TableHead tb = new TableHead(name, getPkName(), Values.getValueNumber(getPkValue()));

        for(int i = 0; i<columns.length; i++){
            tb.addColumn(getColumnName(i), Values.getValueNumber(getColumnValue(i)), !canBeNull(i));

        }

        if( fks != null) {
            for (int i = 0; i < fks.length; i++) {
                tb.addForeignKeyConstraint(fks[i]);
            }
        }

        return tb;
    }

    public boolean equals(TableHead ntb){
        if(this.getColumnCount() != ntb.getColumnCount()){
            return false;
        }
        String nyColVal;
        String nyColName;
        String  nyCollIsNullable;
        for (int i = 0; i<columns.length; i++){
            nyColVal = ntb.getColumnValue(i);
            nyColName = ntb.getColumnName(i);
            nyCollIsNullable = Values.getNullable(!ntb.canBeNull(i));

            if(!(columns[i][1].equals(nyColVal) && columns[i][0].equals(nyColName) && columns[i][2].equals(nyCollIsNullable))){
                return false;
            }
        }

        return true;
    }


}

/*

2.1 TableHead, object used by the databaseObject as a

        -ForeignKey[] fks;
        -String name
        -String[][] columns;


        -----------------------------
        TableHead(String pkName, int pkValue) // the first value becomes the pk


        +String getName();
        +ForeignKeys[] getForeignKeys();
        +String getPkName();
        +String getPkValue();
        +int getColumnCount();
        +String getColumnValue(String label); // returns null if the column is not in the table
        +String getColumnValue(int columnIndex); //return null if the index is invalid;
        +String getColumnName(String label)       un
        +String getColumnName(int columnIndex);           un
        +String getColIndex(String label)
        +boolean addColumn(String name, int value, boolean notNUll);
        -void addColumnSpace();
        +boolean addForeignKeyConstraint(ForeignKey fk);
        -void addFksSpace();
        +boolean columnExist(String label);
        +boolean columnExist(String label, String value);
        +boolean canBeNull(int colIndex)   un
        +boolean canBeNull(String colLabel)   un
        +TableHead copy()
        +boolean equals(TableHead tb);
 */


/* With recursive */
/* */