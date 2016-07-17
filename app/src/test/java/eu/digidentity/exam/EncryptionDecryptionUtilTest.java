package eu.digidentity.exam;


import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import eu.digidentity.exam.util.EncryptionDecryptionUtil;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(EncryptionDecryptionUtil.class)
public class EncryptionDecryptionUtilTest {

    @Mock
    Context mMockContext;

    @Before
    public void setUp(){
        Mockito.when(mMockContext.getString(R.string.encryptionKey)).thenReturn("Bar12345Bar12345");
        Mockito.when(mMockContext.getString(R.string.initVector)).thenReturn("RandomInitVector");
    }

    @Test
    public void encryptionTest(){
        PowerMockito.mockStatic(EncryptionDecryptionUtil.class);

        Mockito.when(EncryptionDecryptionUtil.encrypt("Hello World",mMockContext.getString(R.string.encryptionKey),mMockContext.getString(R.string.initVector))).thenReturn("9MU7vSBqfzPnj7iWvvfsEw==");

        Assert.assertEquals("9MU7vSBqfzPnj7iWvvfsEw==", EncryptionDecryptionUtil.encrypt("Hello World",mMockContext.getString(R.string.encryptionKey), mMockContext.getString(R.string.initVector)));
    }

    @Test
    public void decryptionTest(){
        PowerMockito.mockStatic(EncryptionDecryptionUtil.class);

        Mockito.when(EncryptionDecryptionUtil.decrypt("9MU7vSBqfzPnj7iWvvfsEw==",mMockContext.getString(R.string.encryptionKey),mMockContext.getString(R.string.initVector))).thenReturn("Hello World");

        Assert.assertEquals("Hello World", EncryptionDecryptionUtil.decrypt("9MU7vSBqfzPnj7iWvvfsEw==",mMockContext.getString(R.string.encryptionKey), mMockContext.getString(R.string.initVector)));
    }
}