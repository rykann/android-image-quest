package org.kannegiesser.imagequest.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult implements Parcelable {

    class Keys {
        static final String TITLE = "title";
        static final String WIDTH = "width";
        static final String HEIGHT = "height";
        static final String URL = "url";
        static final String THUMB_WIDTH = "tbWidth";
        static final String THUMB_HEIGHT = "tbHeight";
        static final String THUMB_URL = "tbUrl";
    }

    public String title;
    public int imageWidth;
    public int imageHeight;
    public String imageUrl;
    public int thumbWidth;
    public int thumbHeight;
    public String thumbUrl;

    public ImageResult() {
    }

    protected ImageResult(Parcel in) {
        this.title = in.readString();
        this.imageWidth = in.readInt();
        this.imageHeight = in.readInt();
        this.imageUrl = in.readString();
        this.thumbWidth = in.readInt();
        this.thumbHeight = in.readInt();
        this.thumbUrl = in.readString();
    }

    public static ImageResult fromJson(JSONObject json) throws JSONException {
        ImageResult result = new ImageResult();
        result.title = json.getString(Keys.TITLE);
        result.imageWidth = Integer.parseInt(json.getString(Keys.WIDTH));
        result.imageHeight = Integer.parseInt(json.getString(Keys.HEIGHT));
        result.imageUrl = json.getString(Keys.URL);
        result.thumbWidth = Integer.parseInt(json.getString(Keys.THUMB_WIDTH));
        result.thumbHeight = Integer.parseInt(json.getString(Keys.THUMB_HEIGHT));
        result.thumbUrl = json.getString(Keys.THUMB_URL);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.imageWidth);
        dest.writeInt(this.imageHeight);
        dest.writeString(this.imageUrl);
        dest.writeInt(this.thumbWidth);
        dest.writeInt(this.thumbHeight);
        dest.writeString(this.thumbUrl);
    }

    public static final Parcelable.Creator<ImageResult> CREATOR = new Parcelable.Creator<ImageResult>() {
        public ImageResult createFromParcel(Parcel source) {
            return new ImageResult(source);
        }

        public ImageResult[] newArray(int size) {
            return new ImageResult[size];
        }
    };
}
