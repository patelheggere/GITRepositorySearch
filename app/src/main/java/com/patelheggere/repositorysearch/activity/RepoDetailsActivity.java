package com.patelheggere.repositorysearch.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.patelheggere.repositorysearch.R;
import com.patelheggere.repositorysearch.models.ItemsModel;

public class RepoDetailsActivity extends AppCompatActivity {

    private static final String TAG = "RepoDetailsActivity";
    private ActionBar mActionBar;
    private ItemsModel item;
    private TextView mTvName, mTvProjectLink, mTvDescription, mContributors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_details);
        item = getIntent().getParcelableExtra("details");
        initializeComponents();
        initializeContributors();
        updateDetails();
    }

    private void initializeComponents()
    {
        mActionBar = getSupportActionBar();
        if(mActionBar!=null)
        {
            mActionBar.setTitle(getString(R.string.repo_details));
            mActionBar.setHomeButtonEnabled(true);
        }
        mTvName = findViewById(R.id.tvname2);
        mTvDescription = findViewById(R.id.tvdescription2);
        mTvProjectLink = findViewById(R.id.tvprojectlink2);
    }

    private void updateDetails()
    {
        if(item.getName()!=null)
            mTvName.setText(item.getName());
        if(item.getDescription()!=null)
            mTvDescription.setText(item.getDescription());
        if(item.getContents_url()!=null)
            mTvProjectLink.setText(item.getContents_url());
    }
    private void initializeContributors()
    {
        //mContributors = findViewById()
    }
    @Override
    public void onBackPressed()
    {
        finish();
    }
}
