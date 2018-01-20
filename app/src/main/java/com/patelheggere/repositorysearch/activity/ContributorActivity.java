package com.patelheggere.repositorysearch.activity;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.patelheggere.repositorysearch.R;
import com.patelheggere.repositorysearch.adapter.ContributorRepositoryAdapter;
import com.patelheggere.repositorysearch.models.ContributorModel;
import com.patelheggere.repositorysearch.models.ItemsModel;
import com.patelheggere.repositorysearch.singletons.MySingletonClass;
import com.patelheggere.repositorysearch.utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContributorActivity extends AppCompatActivity {

    private static final String TAG ="ContributorActivity";
    private ActionBar mActionBar;
    private ContributorModel contributor;
    private ImageView mContributorImage;
    private Context mContext;
    private List<ItemsModel> mRepoList;
    private RecyclerView mRecyclerViewRepo;
    private ContributorRepositoryAdapter mContRepoAdapter;
    private TextView mTvRepoList;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor);
        mContext = ContributorActivity.this;
        contributor = getIntent().getParcelableExtra("contributor");
        initiliazeComponents();
        updateDetails();
        loadRepos();
    }

    private void initiliazeComponents()
    {
        mRepoList = new ArrayList<>();
        mActionBar = getSupportActionBar();
        if(mActionBar!=null)
        {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle(getString(R.string.contributor_title));
        }
        mContributorImage = findViewById(R.id.ivcontributor);
        mRecyclerViewRepo = findViewById(R.id.rvcontributor);
        mTvRepoList = findViewById(R.id.tvrepolist);
        mProgressBar = findViewById(R.id.progressBar);
        mContRepoAdapter = new ContributorRepositoryAdapter(mContext, mRepoList);
        mRecyclerViewRepo.setAdapter(mContRepoAdapter);
        mRecyclerViewRepo.setLayoutManager(new LinearLayoutManager(this));
    }
    private void updateDetails()
    {
        if(contributor.getAvatar_url()!=null) {
            Glide.with(mContext)
                    .load(contributor.getAvatar_url())
                    .thumbnail(0.5f)
                    .into(mContributorImage);
        }
    }

    private void loadRepos()
    {
        mProgressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest repoJsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AppConstants.GET_CONTRIBUTOR_REPOS + contributor.getLogin() + "/repos", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i<response.length() ; i++)
                {
                    ItemsModel itemsModel = new ItemsModel();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        itemsModel.setName(jsonObject.getString("name"));
                        mRepoList.add(itemsModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mTvRepoList.setText("Found "+String.valueOf(response.length())+" Repositories by "+ contributor.getLogin());
                mContRepoAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.INVISIBLE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.INVISIBLE);
                Snackbar.make(findViewById(android.R.id.content), getString(R.string.somethin_wrong), Snackbar.LENGTH_LONG ).show();
            }
        });

        MySingletonClass.getInstance().addToRequestQueue(repoJsonArrayRequest);
    }

    //killing activity when back button of action bar pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
