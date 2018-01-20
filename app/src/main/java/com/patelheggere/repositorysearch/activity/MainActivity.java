package com.patelheggere.repositorysearch.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.patelheggere.repositorysearch.R;
import com.patelheggere.repositorysearch.adapter.RepositoryAdapter;
import com.patelheggere.repositorysearch.models.ItemsModel;
import com.patelheggere.repositorysearch.models.OwnerModel;
import com.patelheggere.repositorysearch.singletons.MySingletonClass;
import com.patelheggere.repositorysearch.utils.AppConstants;
import com.patelheggere.repositorysearch.utils.RecyclerItemClickListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private List<ItemsModel> mRepoList;  //Repository List
    private RecyclerView mRepoListRecyclerView; //Repository Recycler view
    private RepositoryAdapter mRepositoryAdapter; // Repository Adapter
    private Button mBtnSearch; // Search Button
    private EditText mEtSearch; // Edit text for search
    private TextView mTvTotalCount;  // Total Repo Count
    private Gson mGson; // Gson object for Parsing
    private ProgressBar mProgressBar; // progressbar
    private android.support.v7.app.ActionBar mActionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();  //calling initializing UI components
        initializeSearchButton(); // calling initailzing button
    }

    //initializing UI components
    private void initializeComponents()
    {
        mRepoList = new ArrayList<>();
        mActionBar = getSupportActionBar();
        if(mActionBar!=null)
            mActionBar.setTitle(getString(R.string.app_name));
        mProgressBar = findViewById(R.id.progressBar);
        mEtSearch = findViewById(R.id.etsearch);
        mTvTotalCount = findViewById(R.id.tvtotalcount);
        mRepoListRecyclerView = findViewById(R.id.rvrepolist);
        mRepositoryAdapter = new RepositoryAdapter(this,mRepoList);
        mRepoListRecyclerView.setAdapter(mRepositoryAdapter);
        mRepoListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRepoListRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRepoListRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                gotoDetails(mRepoList.get(position));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    //Method for initializing Search Button
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

    //Method to Search Repositories
    private void searchRepositories()
    {
        mGson = new Gson();
        mProgressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, AppConstants.SEARCH_URL + mEtSearch.getText().toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               // Log.d(TAG, "onResponse: "+response.toString());
                mRepoList.clear();
                try {
                    String mTotal = "Total "+String.valueOf(response.getLong("total_count"))+" results found";
                    mTvTotalCount.setText(mTotal);
                    mTvTotalCount.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("items");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i<10; i++)
                {
                    OwnerModel ownerModel = new OwnerModel();
                    ItemsModel itemsModel = new ItemsModel();
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("owner");
                        String str = jsonObject.toString();
                        String str2 = str.replace(str.substring(str.indexOf(":{")+1,str.indexOf("}")+2),"{\"\"}");
                        ownerModel = mGson.fromJson(jsonObject1.toString(), OwnerModel.class);
                        itemsModel.setId(jsonObject.getLong("id"));
                        itemsModel.setName(jsonObject.getString("name"));
                        itemsModel.setFull_name(jsonObject.getString("full_name"));
                        itemsModel.setWatchers(jsonObject.getLong("watchers"));
                        itemsModel.setWatchers_count(jsonObject.getLong("watchers_count"));
                        itemsModel.setDescription(jsonObject.getString("description"));
                        itemsModel.setContributors_url(jsonObject.getString("contributors_url"));
                        itemsModel.setContents_url(jsonObject.getString("html_url"));
                        itemsModel.setOwner(ownerModel);
                        mRepoList.add(itemsModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mRepositoryAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.INVISIBLE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.INVISIBLE);
                Snackbar.make(findViewById(android.R.id.content), getString(R.string.somethin_wrong), Snackbar.LENGTH_LONG ).show();
                Log.d(TAG, "onErrorResponse: "+error.toString());
            }
        });

        MySingletonClass.getInstance().addToRequestQueue(mJsonObjectRequest); //adding into requeste to make call to network
    }

    //calling Repodetails activity
    private void gotoDetails(ItemsModel itemsModel)
    {
        Intent detailsIntent = new Intent(this, RepoDetailsActivity.class);
        detailsIntent.putExtra("details", itemsModel);
        detailsIntent.putExtra("owner", itemsModel.getOwner());
        startActivity(detailsIntent);
    }

}
