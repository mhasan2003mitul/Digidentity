package eu.digidentity.exam.model;

/**
 * Created by Administrator on 14-Jul-16.
 */
public class CatalogItem {
    private String mId;
    private String mText;
    private String mConfidence;
    private String mImage;


    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmConfidence() {
        return mConfidence;
    }

    public void setmConfidence(String mConfidence) {
        this.mConfidence = mConfidence;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }
}
