package org.kannegiesser.imagequest.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageResult implements Parcelable {

    public String title;
    public int imageWidth;
    public int imageHeight;
    public String imageUrl;
    public int thumbWidth;
    public int thumbHeight;
    public String thumbUrl;

    public ImageResult() {
    }

    protected ImageResult(JSONObject json) throws JSONException {
        title = json.getString(JsonKeys.TITLE);
        imageWidth = Integer.parseInt(json.getString(JsonKeys.WIDTH));
        imageHeight = Integer.parseInt(json.getString(JsonKeys.HEIGHT));
        imageUrl = json.getString(JsonKeys.URL);
        thumbWidth = Integer.parseInt(json.getString(JsonKeys.THUMB_WIDTH));
        thumbHeight = Integer.parseInt(json.getString(JsonKeys.THUMB_HEIGHT));
        thumbUrl = json.getString(JsonKeys.THUMB_URL);
    }

    protected ImageResult(Parcel in) {
        title = in.readString();
        imageWidth = in.readInt();
        imageHeight = in.readInt();
        imageUrl = in.readString();
        thumbWidth = in.readInt();
        thumbHeight = in.readInt();
        thumbUrl = in.readString();
    }

    public static ArrayList<ImageResult> fromJson(JSONArray jsonResults) throws JSONException {
        ArrayList<ImageResult> imageResults = new ArrayList<>();
        for (int i = 0; i < jsonResults.length(); i++) {
            imageResults.add(new ImageResult(jsonResults.getJSONObject(i)));
        }
        return imageResults;
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

    class JsonKeys {
        static final String TITLE = "title";
        static final String WIDTH = "width";
        static final String HEIGHT = "height";
        static final String URL = "url";
        static final String THUMB_WIDTH = "tbWidth";
        static final String THUMB_HEIGHT = "tbHeight";
        static final String THUMB_URL = "tbUrl";
    }
}
