package eu.digidentity.exam.unit;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Md. Mainul Hasan Patwary on 17-Jul-16.
 * Email: mhasan_mitul@yahoo.com
 * Skype: mhasan_mitul
 */
public class CryptoUtil extends AsyncTask<String, Integer, String> {
    private final static String DEBUG_TAG = CryptoUtil.class.getName();

    private Handler mUIOSHandler;

    public enum CryptoOperation {
        ENCRYPTION, DECRYPTION
    }

    private CryptoUtil() {

    }

    public CryptoUtil(Handler handler){
        this.mUIOSHandler = handler;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            if (strings[0].equals(CryptoOperation.ENCRYPTION.name())) {
                return EncryptionDecryptionUtil.encrypt(strings[1],strings[2],strings[3]);
            } else {
                return EncryptionDecryptionUtil.decrypt(strings[1],strings[2],strings[3]);
            }
        } catch (Exception ex) {
            Log.v(DEBUG_TAG, ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String content) {
        super.onPostExecute(content);
        if (mUIOSHandler != null) {
            Message message = mUIOSHandler.obtainMessage();
            message.obj = content;
            mUIOSHandler.sendMessage(message);
        }
    }


}
