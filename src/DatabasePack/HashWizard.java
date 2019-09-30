package DatabasePack;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class HashWizard {

    public static String generateHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec keySpec = new PBEKeySpec(chars, salt, iterations, 64*8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(keySpec).getEncoded();

        return iterations +":"+ toHex(salt) +":"+ toHex(hash);
    }

    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    //Takes the input password from user and the hash password from the database with the correspondent username.
    public static boolean validatePassword(String inputPassword, String dbPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = dbPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec keySpec = new PBEKeySpec(inputPassword.toCharArray(), salt, iterations, hash.length *8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] newHash = skf.generateSecret(keySpec).getEncoded();

        int diff = hash.length ^ newHash.length;
        for(int i = 0; i < hash.length && i < newHash.length; i++){
            diff |= hash[i] ^ newHash[i];
        }
        return diff == 0;
    }

    private static String toHex(byte[] array){
        return DatatypeConverter.printHexBinary(array);
    }

    private static byte[] fromHex(String hex){
        return DatatypeConverter.parseHexBinary(hex);

    }


    //test, make a JUNIT test -> then delete
    public static void main(String[]args) throws NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] salt = getSalt();
        System.out.println(toHex(salt));
        String hash = generateHash("password");
        System.out.println(hash);

        byte[] test = fromHex(toHex(salt));
        String res ="";
        for(int i = 0; i < test.length; i++){
            res += test[i];
        }
        System.out.println(res);

        if(validatePassword("password", "1000:A2CC2A20CC6348C883F3155C0A0F267D:467981835F3BE047FCD72B8170450FAB4624708835E5EC6C4BC6A5F1A072C072DD217C04A15DAC7803AA04EC983695ECC99B062D4CE655700A9B8F73CB57B7CC")){
            System.out.println("Password match!");
        }else{
            System.out.println("Password does not match!");
        }
        /*BigInteger bi = new BigInteger(1, salt);
        System.out.println(bi);
*/
        String test2 = "1000:A88F7B2F08F285157E833B9B027F3130:6F0367D6AC8A0EBFC55C1CB9820121458ED16C3D2AAB4AA243ED7DA86AECAF26FF11BC8C4C45AE7AEFD3B4EE5658ABD3C5E6B2063CABCF35F99D54A5C374B944";
        System.out.println(test2.length());
    }
}

