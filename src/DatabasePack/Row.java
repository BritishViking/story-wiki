package DatabasePack;

public class Row {
    Object[] values;
    TableHead tb;

    public Row(TableHead tb){
        values = new Object[tb.getColumnCount()];

        this.tb = tb.copy();
    }

    public boolean addValue(String colLabel, Object value){



        int index = tb.getColIndex(colLabel);
        if(index == -1){
            return false;
        }

        if(value != null) {

            try {
                if (!(Values.getValueAsString(value).equals(tb.getColumnValue(index)))) {
                    return false;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (NullPointerException e) {
                e.printStackTrace();
                return false;
            }
            values[index] = value;
        }else{

            if (tb.canBeNull(colLabel)) {
                values[index] = value;
            }else {
                return false;
            }

        }



        return true;
    }

    public String[] getValuesAsStrings(){
        String[] val = new String[values.length];

        for(int i = 0; i<values.length; i++){
            if(values[i] == null){
                val[i] = "null";
            }else{
                val[i] = values[i].toString();
            }
        }

        return val;
    }

    public String toString(){
        String string;

        string = tb.getPkName() + ": " + values[0];

        for(int i = 1; i<values.length; i++){
            string += "\n"+ tb.getColumnName(i)+ ": " + values[i];
        }

        return string;
    }

    public boolean isAddable(){
        for(int i = 0; i<values.length; i++){
            if (values[i] == null){
                if(!(tb.canBeNull(i))){
                    return false;
                }
            }
        }
        return true;
    }

    public static void skriv(Object object){
        System.out.println(object);
    }

    public TableHead getTableHead() {
        return tb.copy();
    }

    public String getValueAsString(int columnIndex){
        if(columnIndex<0 || columnIndex>values.length){
            return null;
        }
        if(values[columnIndex] == null){

            return null;
        }
        return  values[columnIndex].toString();
    }

    public Row copy(){
        Row r = new Row(tb);

        for (int i = 0; i<values.length; i++){
            r.addValue(tb.getColumnName(i), values[i]);
        }

        return r;
    }


}
/*
Row
-Object[][] values
-TableHead tb

---------------------------------
+ Row(Tablehead tb);
+ addValue(String label, Object value)
+ String getValueAsString(String columnLabel);
+ String getValueAsString(int columnIndex);
+ toString
+ isAddable
+ getTableHead
*/