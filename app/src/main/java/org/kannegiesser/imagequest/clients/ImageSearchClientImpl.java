package org.kannegiesser.imagequest.clients;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.LogHandler;
import com.loopj.android.http.LogInterface;
import com.loopj.android.http.RequestParams;

import org.kannegiesser.imagequest.models.ImageQuery;

public class ImageSearchClientImpl implements ImageSearchClient {

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
        LogInterface log = new LogHandler();
        log.setLoggingLevel(LogInterface.WTF);
        httpClient.setLogInterface(log);
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
