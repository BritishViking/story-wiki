import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Contains methods for reading from a properties file
 */
public class PropertiesCache {
    private final Properties p = new Properties();
    private static final PropertiesCache instance = new PropertiesCache();

    /**
     * Sets up a stream to the properties file db.properties
     * @throws IOException if the file is not found
     */
    private PropertiesCache() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream( "db.properties" );
        try {
            p.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the Instance og the properties cache
     * @return an instance of a propertiesCache
     */
    public static PropertiesCache getInstance(){
        return instance;
    }

    /**
     * Gets a property in a properties file based on the input key
     * @param key a String used to access a specific property in a properties file
     * @return a String from the properties file based on the key
     */
    public String getProperty(String key){
        return p.getProperty(key);
    }

    public static void main(String[]args){
        System.out.println();
    }
}


