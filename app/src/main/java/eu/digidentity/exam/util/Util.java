package eu.digidentity.exam.util;

import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

    public static <T> String toJSON(List<T> responseContent) {
        try {
            readWriteLock.writeLock().lock();
            Gson gson = new Gson();
            return gson.toJson(responseContent);
        }
        catch (Exception ex){
            throw ex;
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
