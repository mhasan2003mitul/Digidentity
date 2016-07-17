package eu.digidentity.exam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.digidentity.exam.model.CatalogItem;
import eu.digidentity.exam.unit.CryptoUtil;
import eu.digidentity.exam.unit.DownloadUtil;
import eu.digidentity.exam.unit.EncryptionDecryptionUtil;
import eu.digidentity.exam.unit.HttpRequestUtil;
import eu.digidentity.exam.unit.Util;
import eu.digidentity.exam.view.adapter.CatalogItemAdapter;

/**
 * Created by Md. Mainul Hasan Patwary on 14-Jul-16.
 * Email: mhasan_mitul@yahoo.com
 * Skype: mhasan_mitul
 */
public class DigidentityHomeActivity extends Activity {
    private final static String DEBUG_TAG = DigidentityHomeActivity.class.getName();


    private GestureDetectorCompat mGestureDetectorCompat;

    private ListView mCatalogItemListView;
    private CatalogItemAdapter mCatalogItemAdapter;
    private ProgressDialog mProgressDialog;

    private boolean isScrollUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home);

        try {
            mCatalogItemListView = (ListView) findViewById(R.id.catalogItemListView);

            mProgressDialog = new ProgressDialog(DigidentityHomeActivity.this);
            mProgressDialog.setMessage(getResources().getString(R.string.loading_text));
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);

            mGestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());

            mCatalogItemListView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = MotionEventCompat.getActionMasked(motionEvent);

                    mGestureDetectorCompat.onTouchEvent(motionEvent);

                    return false;
                }
            });

            mCatalogItemListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                private int scrollStatus = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

                @Override
                public void onScrollStateChanged(AbsListView absListView, int i) {
                    scrollStatus = i;
                }

                @Override
                public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    Log.v(DEBUG_TAG, "FVI: " + firstVisibleItem + " VIC: " + visibleItemCount + " TIC: " + totalItemCount);
                    if (firstVisibleItem == 0 && scrollStatus != SCROLL_STATE_IDLE &&
                            !isScrollUp && !mProgressDialog.isShowing()) {
                        makeRequestToGetCatalogItems(getResources().getString(R.string.catalog_items_url) + "?max_id=" + mCatalogItemAdapter.getItem(firstVisibleItem).getmId());
                    } else if ((firstVisibleItem + visibleItemCount == totalItemCount) &&
                            scrollStatus != SCROLL_STATE_IDLE && isScrollUp &&
                            !mProgressDialog.isShowing()) {
                        makeRequestToGetCatalogItems(getResources().getString(R.string.catalog_items_url) + "?since_id=" + mCatalogItemAdapter.getItem(firstVisibleItem).getmId());
                    }
                }
            });

            mCatalogItemAdapter = new CatalogItemAdapter(DigidentityHomeActivity.this, new ArrayList<CatalogItem>());
            mCatalogItemListView.setAdapter(mCatalogItemAdapter);

            if (isServerCacheAvailable()) {
                mProgressDialog.show();
                String contentData = loadCacheData();
                Log.v(DEBUG_TAG, "Data: " + contentData);
                Handler handler = new Handler(Looper.getMainLooper()){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        mProgressDialog.dismiss();
                        Log.v(DEBUG_TAG,"Decrypted data: "+msg.obj.toString());
                        refreshCatalogList(Arrays.asList(Util.toObj(msg.obj.toString(), CatalogItem[].class)));
                    }
                };
                new CryptoUtil(handler).execute(CryptoUtil.CryptoOperation.DECRYPTION.name(),contentData,getResources().getString(R.string.encryptionKey),getResources().getString(R.string.initVector));

            } else {
                makeRequestToGetCatalogItems(getResources().getString(R.string.catalog_items_url));
            }
        } catch (Exception ex) {
            Log.v(DEBUG_TAG, ex.getMessage(), ex);
        }
    }

    private void makeRequestToGetCatalogItems(String url) {
        mProgressDialog.show();

        Map<String, String> header = new HashMap<>();
        header.put(getResources().getString(R.string.authorization_header_name), getResources().getString(R.string.authorization_header_value));
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                try {
                    mProgressDialog.dismiss();
                    Log.v(DEBUG_TAG, "Catalog Data: " + msg.obj.toString());

                    cacheServerData(msg.obj.toString());
                    refreshCatalogList(Arrays.asList(Util.toObj(msg.obj.toString(), CatalogItem[].class)));
                } catch (Exception ex) {
                    Log.v(DEBUG_TAG, ex.getMessage(), ex);
                }
            }
        };

        new DownloadUtil(handler).execute(url, header, HttpRequestUtil.HTTPRequestMethod.GET);
    }

    private void refreshCatalogList(List<CatalogItem> catalogItems) {
        if (catalogItems.size() > 0 && catalogItems.size() <= 10) {

            for (CatalogItem catalogItem :
                    catalogItems) {
                Log.v(DEBUG_TAG, catalogItem.getmText());
            }
            List<CatalogItem> subList;
            if (isScrollUp) {
                if (mCatalogItemAdapter.getCatalogItems().size() < catalogItems.size()) {
                    subList = new ArrayList<>(mCatalogItemAdapter.getCatalogItems().subList(0, mCatalogItemAdapter.getCatalogItems().size()));
                } else {
                    subList = new ArrayList<>(mCatalogItemAdapter.getCatalogItems().subList(0, catalogItems.size()));

                }
                mCatalogItemAdapter.getCatalogItems().removeAll(subList);
                mCatalogItemAdapter.getCatalogItems().addAll(catalogItems);
            } else {
                if (mCatalogItemAdapter.getCatalogItems().size() < catalogItems.size()) {
                    subList = new ArrayList<>(mCatalogItemAdapter.getCatalogItems().subList(0, mCatalogItemAdapter.getCatalogItems().size()));
                } else {
                    subList = new ArrayList<>(mCatalogItemAdapter.getCatalogItems().subList(mCatalogItemAdapter.getCatalogItems().size() - catalogItems.size(), mCatalogItemAdapter.getCatalogItems().size()));
                }
                mCatalogItemAdapter.getCatalogItems().removeAll(subList);
                mCatalogItemAdapter.getCatalogItems().addAll(0, catalogItems);
            }
            mCatalogItemAdapter.notifyDataSetChanged();
        }
    }

    private boolean isServerCacheAvailable() {
        File cacheFile = getBaseContext().getFileStreamPath(getResources().getString(R.string.cache_file_name));
        return cacheFile.exists();
    }

    private String loadCacheData() throws Exception {
        FileInputStream fileInputStream = null;
        StringBuffer dataBuffer = new StringBuffer();
        try {
            fileInputStream = openFileInput(getResources().getString(R.string.cache_file_name));
            byte buffer[] = new byte[1024];
            int len;
            while ((len = fileInputStream.read(buffer, 0, buffer.length)) != -1) {
                dataBuffer.append(new String(buffer, 0, len));
            }
        } catch (Exception ex) {
            Log.v(DEBUG_TAG, ex.getMessage(), ex);
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }

        return dataBuffer.toString();
    }

    private void cacheServerData(String responseContent) throws Exception {
        final FileOutputStream fileOutputStream = openFileOutput(getResources().getString(R.string.cache_file_name), Context.MODE_PRIVATE);
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                try {
                    fileOutputStream.write(msg.obj.toString().getBytes());
                } catch (Exception ex) {
                    Log.v(DEBUG_TAG, ex.getMessage(), ex);
                } finally {
                    try {
                        fileOutputStream.close();
                    } catch (Exception ex) {
                        Log.v(DEBUG_TAG, ex.getMessage(), ex);
                    }
                }
            }
        };
        new CryptoUtil(handler).execute(CryptoUtil.CryptoOperation.ENCRYPTION.name(), responseContent,getResources().getString(R.string.encryptionKey),getResources().getString(R.string.initVector));
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (e1.getY() > e2.getY()) {
                isScrollUp = true;
            } else {
                isScrollUp = false;
            }
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
