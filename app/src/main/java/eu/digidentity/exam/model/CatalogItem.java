package eu.digidentity.exam.model;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Created by Md. Mainul Hasan Patwary on 14-Jul-16.
 * Email: mhasan_mitul@yahoo.com
 * Skype: mhasan_mitul
 */
public class CatalogItem {
    @SerializedName("_id")
    private String mId;
    @SerializedName("text")
    private String mText;
    @SerializedName("confidence")
    private String mConfidence;
    @SerializedName("img")
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CatalogItem == false)
        {
            return false;
        }
        if (this == obj)
        {
            return true;
        }
        final CatalogItem otherObject = (CatalogItem) obj;

        return new EqualsBuilder().append(this.getmId(),otherObject.getmId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getmId()).toHashCode();
    }
}
