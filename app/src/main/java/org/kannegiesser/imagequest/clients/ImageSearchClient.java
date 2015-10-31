package org.kannegiesser.imagequest.clients;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.kannegiesser.imagequest.models.ImageQuery;

public interface ImageSearchClient {
    public void findImages(ImageQuery query, JsonHttpResponseHandler responseHandler);
}
