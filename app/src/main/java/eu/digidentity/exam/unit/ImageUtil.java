package eu.digidentity.exam.unit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Md. Mainul Hasan Patwary on 15-Jul-16.
 * Email: mhasan_mitul@yahoo.com
 * Skype: mhasan_mitul
 */
public class ImageUtil {
    private final static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Nullable
    public static Bitmap byteToBitmap(byte[] bitmapByteData) {
        Bitmap catalogItemImage;
        try {
            readWriteLock.writeLock().lock();
            catalogItemImage = BitmapFactory.decodeByteArray(bitmapByteData,0,bitmapByteData.length);
        } finally {
            readWriteLock.writeLock().unlock();
        }
        return catalogItemImage;
    }
}
