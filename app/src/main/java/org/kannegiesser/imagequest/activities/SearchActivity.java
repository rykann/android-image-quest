package org.kannegiesser.imagequest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kannegiesser.imagequest.R;
import org.kannegiesser.imagequest.adapters.ImageResultsAdapter;
import org.kannegiesser.imagequest.clients.ImageSearchClient;
import org.kannegiesser.imagequest.clients.ImageSearchClientImpl;
import org.kannegiesser.imagequest.fragments.SearchOptionsDialog;
import org.kannegiesser.imagequest.listeners.EndlessScrollListener;
import org.kannegiesser.imagequest.models.ImageQuery;
import org.kannegiesser.imagequest.models.ImageResult;
import org.kannegiesser.imagequest.models.SearchOptions;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity implements SearchOptionsDialog.SearchOptionsDialogListener {

    private static final String TAG = "SearchActivity";

    private EditText etQuery;
    private Button btnSearch;
    private GridView gvResults;
    private ImageSearchClient imageSearchClient = new ImageSearchClientImpl();
    private ArrayList<ImageResult> imageResults = new ArrayList<>();
    private ImageResultsAdapter imageResultsAdapter;
    private SearchOptions searchOptions = SearchOptions.defaultOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageResultsAdapter = new ImageResultsAdapter(this, imageResults);
        initViews();
    }

    private void initViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        gvResults = (GridView) findViewById(R.id.gvResults);
        gvResults.setAdapter(imageResultsAdapter);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                intent.putExtra("imageResult", imageResultsAdapter.getItem(position));
                startActivity(intent);
            }
        });
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                Log.d(TAG, String.format("onLoadMore page: %d, totalItemsCount: %d", page, totalItemsCount));
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                fetchImageResults(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
        addButtonListener();
    }

    private void addButtonListener() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchImageResults(0);
            }
        });
    }

    //TODO: check for network access
    private void fetchImageResults(int page) {
        Log.d(TAG, "fetchImageResults page: " + page);
        if (page == 0) {
            imageResultsAdapter.clear();
        }

        ImageQuery query = new ImageQuery();
        query.query = etQuery.getText().toString();
        query.resultsPerPage = 8;
        query.startIndex = page * 8;
        query.options = searchOptions;

        imageSearchClient.findImages(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonResults = response.getJSONObject("responseData").getJSONArray("results");
                    imageResultsAdapter.addAll(ImageResult.fromJson(jsonResults));
                } catch (JSONException e) {
                    Log.e(TAG, "Unable to parse search results", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, String.format("Image search failed. Status: %d, Body: %s", statusCode, responseString), throwable);
                Toast.makeText(SearchActivity.this, "Image search failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.miSearchOptions) {
            showOptionsDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showOptionsDialog() {
        FragmentManager fm = getSupportFragmentManager();
        SearchOptionsDialog dialog = SearchOptionsDialog.newInstance(searchOptions);
        dialog.show(fm, "fragment_search_options");
    }

    @Override
    public void onSaveSearchOptions(SearchOptions searchOptions) {
        this.searchOptions = searchOptions;
        //TODO: only fetch if search options were changed
        fetchImageResults(0);
    }
}
