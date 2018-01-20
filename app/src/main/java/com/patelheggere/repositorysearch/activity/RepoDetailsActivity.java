package com.patelheggere.repositorysearch.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.patelheggere.repositorysearch.R;
import com.patelheggere.repositorysearch.adapter.ContributorAdapter;
import com.patelheggere.repositorysearch.models.ContributorModel;
import com.patelheggere.repositorysearch.models.ItemsModel;
import com.patelheggere.repositorysearch.models.OwnerModel;
import com.patelheggere.repositorysearch.singletons.MySingletonClass;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RepoDetailsActivity extends AppCompatActivity {

    private static final String TAG = "RepoDetailsActivity";
    private ActionBar mActionBar;
    private ItemsModel item;
    private OwnerModel ownerModel;
    private TextView mTvName, mTvProjectLink, mTvDescription, mContributors;
    private ImageView mImageView;
    private GridView mGridView;
    private List<ContributorModel> mContributorList;
    private ContributorAdapter mContributorAdapter;
    private Context mContext;
    private Gson mGson;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_details);
        mContext = RepoDetailsActivity.this;
        item = getIntent().getParcelableExtra("details");
        ownerModel = getIntent().getParcelableExtra("owner");
        item.setOwner(ownerModel);
        initializeComponents();
        initializeContributors();
        updateDetails();
        loadContributors();
    }

    private void initializeComponents()
    {
        mContributorList =new ArrayList<>();
        mActionBar = getSupportActionBar();
        if(mActionBar!=null)
        {
            mActionBar.setTitle(getString(R.string.repo_details));
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        mImageView = findViewById(R.id.ivavatar);
        mTvName = findViewById(R.id.tvname2);
        mTvDescription = findViewById(R.id.tvdescription2);
        mTvProjectLink = findViewById(R.id.tvprojectlink2);
        mProgressBar = findViewById(R.id.progressBar);
    }

    private void updateDetails()
    {
        if(item.getName()!=null)
            mTvName.setText(item.getName());
        if(item.getDescription()!=null)
            mTvDescription.setText(item.getDescription());
        if(item.getContents_url()!=null)
            mTvProjectLink.setText(item.getContents_url());
       if(item.getOwner().getAvatar_url()!=null) {
           Glide.with(mContext)
                   .load(item.getOwner().getAvatar_url())
                   .thumbnail(0.5f)
                   .into(mImageView);
       }
    }

    private void loadContributors()
    {
        mGson = new Gson();
        mProgressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest contributorsJsonArrayRequest = new JsonArrayRequest(Request.Method.GET, item.getContributors_url(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Type listType = new TypeToken<List<ContributorModel>>(){}.getType();
                mContributorList = mGson.fromJson(response.toString(), listType);
                setAdapter();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.INVISIBLE);
                Snackbar.make(findViewById(android.R.id.content), getString(R.string.somethin_wrong), Snackbar.LENGTH_LONG ).show();
            }
        });
        MySingletonClass.getInstance().addToRequestQueue(contributorsJsonArrayRequest);
    }
    private void setAdapter()
    {
        mContributorAdapter = new ContributorAdapter(RepoDetailsActivity.this, mContributorList );
        mGridView.setAdapter(mContributorAdapter);
        mProgressBar.setVisibility(View.INVISIBLE);
    }
    private void initializeContributors()
    {
        mGridView = findViewById(R.id.gv_contributors);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContributorDetails(mContributorList.get(position));
            }
        });
    }

    private void ContributorDetails(ContributorModel contributor)
    {
        Intent contributorDetailsIntent = new Intent(mContext, ContributorActivity.class);
        contributorDetailsIntent.putExtra("contributor", contributor);
        startActivity(contributorDetailsIntent);
    }
    @Override
    public void onBackPressed()
    {
        finish();
    }
}
