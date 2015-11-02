package org.kannegiesser.imagequest.clients;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.kannegiesser.imagequest.models.ImageQuery;

public class ImageSearchClientImpl implements ImageSearchClient {

    private static final String TAG = "ImageSearchClientImpl";

    class ParamNames {
        static final String QUERY = "q";
        static final String VERSION = "v";
        static final String RESULTS_PER_PAGE = "rsz";
        static final String START_INDEX = "start";
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
        return params;
    }
}
