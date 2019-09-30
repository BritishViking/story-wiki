package DatabasePack;

public class   Values {
    public static final int INT = 0;
    public static final int STRING = 1;
    public static final int BOOLEAN = 2;

    public static final String NOTNULL = "NOT NULL";
    public static final String NULL = "NULL";

    private static final String[] values = {"INT", "STRING", "BOOLEAN"};

    public static String getValueName(int valNumber ){
        if(valNumber < 0 || valNumber > 2){
            return null;
        }

        return values[valNumber];

    }

    public static int getValueNumber(String value){

        for(int i = 0; i < values.length; i++){

            if(value.toUpperCase().equals(values[i])){

                return i;
            }
        }

        return -1;
    }

    public static String getNullable(boolean notNull) {
        if (notNull) {
            return NOTNULL;
        } else {
            return NULL;
        }
    }

    public static int getValueAsInteger(Object object) throws ClassNotFoundException{

        if(object == null){
            throw new NullPointerException("Can not determine value of null");
        }

        String  klassNavn = object.getClass().getName();

        if ( klassNavn== "java.lang.String"){
            return STRING;
        }else if(klassNavn == "java.lang.Integer" ){
            return INT;
        }else if(klassNavn == "java.lang.Boolean"){
            return BOOLEAN;
        }else {
            System.out.println(klassNavn);

        }

        return -1;
    }

    public static String getValueAsString(Object object) throws ClassNotFoundException{
        int val = getValueAsInteger(object);

        if(val == -1 ){
            return null;
        }

        return values[val];
    }


    public static int getValueFromSQLA(String sqlVal){
        if (sqlVal.equals("INT") || sqlVal.equals("SMALLINT")){
            return INT;

        }


        if(sqlVal.equals("Boolean")){
            return BOOLEAN;
        }

        if(sqlVal.equals("VARCHAR") ||  sqlVal.equals("TEXT") || sqlVal.equals("TINYTEXT")){
            return STRING;
        }

        System.out.println("UNKNOWN TYPE " + sqlVal);

        return -1;
    }

    public static boolean sqlIsNullable(int val){
        if(val==0){
            return false;
        }else{
            return true;
        }
    }

}

/*
2.4 VAULES
    +static int INT {readOnly}
    +static int VARCHAR30 {readOnly}
    +static int BOOLEAN {readOnly}
    +static String NOTNULL {readOnly}
    +static String NULL {readOnly}

    -static String[] values {readOnly}

    ------------------------------------------
    +static String getValueName(int valNumber)
    +static int getValueNumber(String value)
    +static String getNullable(boolean notNull) // returns the Values.NOTNULL or Values.NULL, depending on the value of notNUll
    +String forClass(Class klass)
    +boolaen sqlIsNullable(int resultsetSqlIsNullableMethod
*/