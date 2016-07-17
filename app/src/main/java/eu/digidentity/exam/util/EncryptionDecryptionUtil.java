package eu.digidentity.exam.util;

import android.util.Log;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Md. Mainul Hasan Patwary on 17-Jul-16.
 * Email: mhasan_mitul@yahoo.com
 * Skype: mhasan_mitul
 */
public class EncryptionDecryptionUtil{

    private static final String DEBUG_TAG = EncryptionDecryptionUtil.class.getName();

    public static String encrypt(String content, String key, String initVector) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(content.getBytes());

            return new String(new Base64().encode(encrypted));
        } catch (Exception ex) {
            Log.e(DEBUG_TAG,ex.getMessage(),ex);
        }

        return null;
    }

    public static String decrypt(String encrypted, String key, String initVector) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(new Base64().decode(encrypted.getBytes()));

            return new String(original);
        } catch (Exception ex) {
            Log.e(DEBUG_TAG,ex.getMessage(),ex);
        }

        return null;
    }
}
