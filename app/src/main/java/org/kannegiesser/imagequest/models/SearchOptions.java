package org.kannegiesser.imagequest.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchOptions implements Parcelable {
    public String imageSize;
    public String colorFilter;
    public String imageType;
    public String siteFilter;

    public SearchOptions() {
    }

    public static SearchOptions defaultOptions() {
        SearchOptions searchOptions = new SearchOptions();
        searchOptions.imageSize = "any";
        searchOptions.colorFilter = "any";
        searchOptions.imageType = "any";
        return searchOptions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageSize);
        dest.writeString(this.colorFilter);
        dest.writeString(this.imageType);
        dest.writeString(this.siteFilter);
    }

    protected SearchOptions(Parcel in) {
        this.imageSize = in.readString();
        this.colorFilter = in.readString();
        this.imageType = in.readString();
        this.siteFilter = in.readString();
    }

    public static final Parcelable.Creator<SearchOptions> CREATOR = new Parcelable.Creator<SearchOptions>() {
        public SearchOptions createFromParcel(Parcel source) {
            return new SearchOptions(source);
        }

        public SearchOptions[] newArray(int size) {
            return new SearchOptions[size];
        }
    };
}
