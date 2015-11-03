package org.kannegiesser.imagequest.clients;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.kannegiesser.imagequest.models.ImageQuery;
import org.kannegiesser.imagequest.models.SearchOptions;

import java.util.HashMap;
import java.util.Map;

public class ImageSearchClientImpl implements ImageSearchClient {

    private static final String TAG = "ImageSearchClientImpl";

    private static final Map<String, String> imageSizeMap = new HashMap<>();
    static {
        imageSizeMap.put("small", "icon");
        imageSizeMap.put("medium", "medium");
        imageSizeMap.put("large", "xxlarge");
        imageSizeMap.put("extra large", "huge");
    }

    private static final Map<String, String> imageTypeMap = new HashMap<>();
    static {
        imageTypeMap.put("faces", "face");
        imageTypeMap.put("photos", "photo");
        imageTypeMap.put("clip art", "clipart");
        imageTypeMap.put("line art", "lineart");
    }

    class ParamNames {
        static final String QUERY = "q";
        static final String VERSION = "v";
        static final String RESULTS_PER_PAGE = "rsz";
        static final String START_INDEX = "start";
        static final String IMAGE_SIZE = "imgsz";
        static final String IMAGE_COLOR = "imgcolor";
        static final String IMAGE_TYPE = "imgtype";
        static final String SITE_FILTER = "as_sitesearch";
    }

    private static final String IMAGE_SEARCH_URL = "https://ajax.googleapis.com/ajax/services/search/images";
    private static final String VERSION_1 = "1.0";

    @Override
    public void findImages(ImageQuery query, JsonHttpResponseHandler responseHandler) {
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = buildParams(query);
        Log.d(TAG, "query params: " + params);
        httpClient.get(IMAGE_SEARCH_URL, buildParams(query), responseHandler);
    }

    private RequestParams buildParams(ImageQuery query) {
        RequestParams params = new RequestParams();
        params.put(ParamNames.VERSION, VERSION_1);
        params.put(ParamNames.QUERY, query.query);
        params.put(ParamNames.START_INDEX, query.startIndex);
        params.put(ParamNames.RESULTS_PER_PAGE, query.resultsPerPage);
        addSearchOptionsToParams(query.options, params);
        return params;
    }

    private void addSearchOptionsToParams(SearchOptions options, RequestParams params) {
        if (options == null) return;

        String imageSize = translateImageSize(options.imageSize);
        if (imageSize != null) {
            params.put(ParamNames.IMAGE_SIZE, imageSize);
        }

        String colorFilter = options.colorFilter;
        if (colorFilter != null && !colorFilter.equals("any")) {
            params.put(ParamNames.IMAGE_COLOR, colorFilter);
        }

        String imageType = translateImageType(options.imageType);
        if (imageSize != null) {
            params.put(ParamNames.IMAGE_TYPE, imageType);
        }

        if (options.siteFilter != null) {
            params.put(ParamNames.SITE_FILTER, options.siteFilter);
        }
    }

    private String translateImageSize(String imageSize) {
        return (imageSize != null) ? imageSizeMap.get(imageSize) : null;
    }

    private String translateImageType(String imageType) {
        return (imageType != null) ? imageTypeMap.get(imageType) : null;
    }
}
