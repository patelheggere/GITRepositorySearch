package com.patelheggere.repositorysearch.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.patelheggere.repositorysearch.R;
import com.patelheggere.repositorysearch.adapter.RepositoryAdapter;
import com.patelheggere.repositorysearch.models.ItemsModel;
import com.patelheggere.repositorysearch.models.SearchResultModel;
import com.patelheggere.repositorysearch.singletons.MySingletonClass;
import com.patelheggere.repositorysearch.utils.AppConstants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<String> mRepoList;
    private RecyclerView mRepoListRecyclerView;
    private RepositoryAdapter mRepositoryAdapter;
    private Button mBtnSearch;
    private EditText mEtSearch;
    private Gson mGson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        initializeSearchButton();
    }


    private void initialize()
    {
        mRepoList = new ArrayList<>();
        mEtSearch = findViewById(R.id.etsearch);
        mRepoListRecyclerView = findViewById(R.id.rvrepolist);
        mRepositoryAdapter = new RepositoryAdapter(this,mRepoList);
        mRepoListRecyclerView.setAdapter(mRepositoryAdapter);
        mRepoListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void initializeSearchButton()
    {
        mBtnSearch = findViewById(R.id.btsearch);
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRepositories();
            }
        });
    }

    private void searchRepositories()
    {
        mGson = new Gson();
        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, AppConstants.SEARCH_URL + mEtSearch.getText().toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: "+response.toString());
                SearchResultModel searchResultModel = new SearchResultModel();
                searchResultModel = mGson.fromJson(response.toString(), SearchResultModel.class);
                Log.d(TAG, "onResponse: Total Count:"+searchResultModel.getTotal_count());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.toString());
            }
        });

        MySingletonClass.getInstance().addToRequestQueue(mJsonObjectRequest);
    }
}
