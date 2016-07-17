package eu.digidentity.exam.unit;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Md. Mainul Hasan Patwary on 14-Jul-16.
 * Email: mhasan_mitul@yahoo.com
 * Skype: mhasan_mitul
 */
public class HttpRequestUtil {

    private static final String TAG = HttpRequestUtil.class.getName();

    private String mUrlString;
    private Map<String,String> httpHeader;
    private HTTPRequestMethod httpRequestMethod;

    public enum HTTPRequestMethod{
        GET,POST,DELETE;
    }

    private enum Protocol {
        HTTP, HTTPS
    }

    public HttpRequestUtil(String url, Map<String,String> httpHeader, HTTPRequestMethod httpRequestMethod) {
        this.mUrlString = url;
        this.httpHeader = httpHeader;
        this.httpRequestMethod = httpRequestMethod;
    }

    public String sendRequestToServer() throws Exception {
        Log.v(TAG,"Requested URL: "+this.mUrlString);
        URL url = new URL(this.mUrlString);
        URLConnection urlConnection = (URLConnection) url.openConnection();
        String responseContent;

        for (Map.Entry<String,String> header:this.httpHeader.entrySet())
        {
            urlConnection.setRequestProperty(header.getKey(),header.getValue());
        }

        if (url.getProtocol().equalsIgnoreCase(Protocol.HTTP.name())){
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod(this.httpRequestMethod.name());
            responseContent = fetchContent(httpURLConnection.getInputStream());
        }
        else {
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) urlConnection;
            httpURLConnection.setRequestMethod(this.httpRequestMethod.name());
            responseContent = fetchContent(httpURLConnection.getInputStream());
        }

        return responseContent;
    }

    @NonNull
    private String fetchContent(InputStream inputStream) throws Exception
    {
        StringBuffer responseBuffer = new StringBuffer();
        byte data[]=new byte[1024];
        int len;
        while ((len = inputStream.read(data,0,data.length)) != -1){
            responseBuffer.append(new String(data,0,len));
        }

        return responseBuffer.toString();
    }
}
