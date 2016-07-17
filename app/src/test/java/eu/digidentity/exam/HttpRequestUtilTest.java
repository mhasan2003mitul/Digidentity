package eu.digidentity.exam;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import eu.digidentity.exam.util.HttpRequestUtil;

/**
 * Created by Md. Mainul Hasan Patwary on 17-Jul-16.
 * Email: mhasan_mitul@yahoo.com
 * Skype: mhasan_mitul
 */
@RunWith(MockitoJUnitRunner.class)
public class HttpRequestUtilTest {

    @Mock
    Context mMockContext;


    @Before
    public void setUp(){
        Mockito.when(mMockContext.getString(R.string.catalog_items_url)).thenReturn("https://marlove.net/mock/v1/items");
        Mockito.when(mMockContext.getString(R.string.authorization_header_name)).thenReturn("Authorization");
        Mockito.when(mMockContext.getString(R.string.authorization_header_value)).thenReturn("8894e4b60fb5aec440e45b0b6d615916");
    }

    @Test
    public void testHttpRequest()throws Exception{

        Map<String,String> header = new HashMap<>();
        header.put(mMockContext.getString(R.string.authorization_header_name), mMockContext.getString(R.string.authorization_header_value));

        HttpRequestUtil httpRequestUtil = new HttpRequestUtil(mMockContext.getString(R.string.catalog_items_url), header, HttpRequestUtil.HTTPRequestMethod.GET);

        Assert.assertTrue(httpRequestUtil.sendRequestToServer() != null);
    }
}
