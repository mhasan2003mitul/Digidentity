package eu.digidentity.exam.util;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Map;

/**
 * Created by Md. Mainul Hasan Patwary on 14-Jul-16.
 * Email: mhasan_mitul@yahoo.com
 * Skype: mhasan_mitul
 */

public class DownloadUtil extends AsyncTask<Object, Integer, String> {
    private static final String DEBUG_TAG = DownloadUtil.class.getName();
    private Handler mUIOSHandler;

    public DownloadUtil(Handler uiOSHandler) {
        this.mUIOSHandler = uiOSHandler;
    }

    @Override
    protected String doInBackground(Object... objects) {
        String responseContent = null;
        try {
            responseContent = new HttpRequestUtil(objects[0].toString(), (Map<String, String>) objects[1], (HttpRequestUtil.HTTPRequestMethod) objects[2]).sendRequestToServer();
        } catch (Exception ex) {
            Log.e(DEBUG_TAG, ex.getMessage(), ex);
        }
        return responseContent;
    }

    @Override
    protected void onPostExecute(String responseContent) {
        super.onPostExecute(responseContent);
        Message message = mUIOSHandler.obtainMessage();
        message.obj = responseContent;
        mUIOSHandler.sendMessage(message);
    }
}
