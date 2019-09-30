package DatabasePack;

public class Table  {
    private Row[] values;
    TableHead tb;

    public Table(TableHead tb){
        this.tb = tb ;
        values = null;
    }

    public TableHead getTableHead() {
        return tb.copy();
    }

    public boolean deleteRow(String pkValue){
        return false;
    }

    private void clean(){

    }

    public Row[] getValues(){
        Row[] valcopy = new Row[values.length];

        for (int i = 0; i<valcopy.length; i++){
            valcopy[i] = values[i];
        }

        return valcopy;
    }

    public boolean addRow(Row row){
        if (!(row.isAddable())){

            return false;
        }


        if(!(row.getTableHead().equals(tb))){
            return false;
        }

        addRowSpace();

        values[values.length-1] = row.copy();

        return true;
    }

    private void addRowSpace(){
        if(values ==null){
            values = new Row[1];
        }else {
            Row[] newRows = new Row[values.length + 1];

            for (int i = 0; i < values.length; i++) {
                newRows[i] = values[i];
            }

            values = newRows;
        }
    }

    public String toString(){
        int colCount = tb.getColumnCount();
        String string = "";
        String s1 = "+";
        String s2 = "|";
        String s3= "+";



        int s2length;
        String word;


        for(int i = 0; i<colCount; i++) {
            s1 += " - - - - - - - - - +";
            s3 += " - - - - - - - - - +";

            word = tb.getColumnName(i);
            if(word.length()>18){
                word = word.substring(0, 15) + "...";
            }

            s2length = 18 - word.length();
            s2 += " " + word;

            for(int j=0; j<s2length; j++){
                s2+=" ";
            }

            s2+= "|";
        }





        string = s1 + "\n" + s2 + "\n" + s3;

        int vallength;

        if(values != null) {
            vallength = values.length;
        }else{
            vallength = 1;
        }

        for (int i = 0; i<vallength; i++){
            string +="\n|";

            for(int j = 0; j<colCount; j++){

                if(values != null) {

                    word = values[i].getValueAsString(j);
                    if(word == null){
                        word = "null";
                    }

                }else{
                    word = "null";
                }

                if(word.length()>18){
                    word = word.substring(0, 15) + "...";
                }


                s2length = 18 - word.length();
                string += " " + word;

                String s  = "helllp";
                int h = 1;

                for(int k = 0; k<s2length; k++){
                    string +=  " ";
                }
                string += "|";

            }

            string += "\n" + s3;

        }

        return string;

    }

    public static void skriv(Object o){
        System.out.println(o);
    }

    public String getInsert(){
        String name = tb.getName();
        String sql = "INSERT INTO " + name + "(";

        String[] columns = tb.getColumns();
        String columnsString = columns[0];

        for(int i = 1; i<columns.length; i++){
            columnsString += ", " + columns[i];
        }



        String[] val = values[0].getValuesAsStrings();
        sql += columnsString + ") VALUES(" + val[0] ;
        for (int i = 1; i<values.length; i++){
            sql += ", " + val[i];
        }


        for(int i = 1; i<values.length; i++){
            val = values[i].getValuesAsStrings();
            sql += "); \nINSERT INTO " + name + "(" + columnsString + ") VALUES(" + val[0];

            for(int j = 1; j<tb.getColumnCount(); j++){
                sql += ", " + val[j];
            }
            sql += ")";
        }

        sql += "); ";
        return sql;
    }
}


/*


 -String[][] values //value[][0] = pk,
     -isNotNull
     -TableHead tb; //meta about the table

     ------------------------------------
     +Table( TableHead tb)

     +TableHead getTableHead chec
     +boolean addRow(String[] values);
     +boolean deleteRow(String pkValue); ----
     +Row[]  getValues()  ------
     +TableHead getTableHead()
     -addRowSpace();
     +String toString()
 */