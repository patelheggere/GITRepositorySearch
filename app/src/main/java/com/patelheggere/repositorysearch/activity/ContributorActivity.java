package com.patelheggere.repositorysearch.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.patelheggere.repositorysearch.R;

public class ContributorActivity extends AppCompatActivity {

    private static final String TAG ="ContributorActivity";
    private ActionBar mActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor);
        initiliazeComponents();
    }

    private void initiliazeComponents()
    {
        mActionBar = getSupportActionBar();
        if(mActionBar!=null)
        {
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setTitle(getString(R.string.contributor_title));
        }
    }
}
