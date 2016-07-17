package eu.digidentity.exam.unit;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import eu.digidentity.exam.model.CatalogItem;

/**
 * Created by Md. Mainul Hasan Patwary on 16-Jul-16.
 * Email: mhasan_mitul@yahoo.com
 * Skype: mhasan_mitul
 */
public class Util {
    private final static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final static Gson gson = new Gson();

    public static <T> T toObj(String responseContent, Class<T> classType) {
        try {
            readWriteLock.writeLock().lock();
            Gson gson = new Gson();
            return gson.fromJson(responseContent, classType);
        }
        catch (Exception ex){
            throw ex;
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void saveString(Context context, String key, String value){
        SharedPreferences sharedpreferences = context.getSharedPreferences(Util.class.getName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static boolean existsInSharedPreference(Context context,String key){
        SharedPreferences sharedpreferences = context.getSharedPreferences(Util.class.getName(), Context.MODE_PRIVATE);
        return sharedpreferences.contains(key);
    }

    public static String getString(Context context,String key){
        SharedPreferences sharedpreferences = context.getSharedPreferences(Util.class.getName(), Context.MODE_PRIVATE);
        return sharedpreferences.getString(key,null);
    }
}
