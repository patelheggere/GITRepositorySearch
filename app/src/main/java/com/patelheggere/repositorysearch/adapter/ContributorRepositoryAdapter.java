package com.patelheggere.repositorysearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.patelheggere.repositorysearch.R;
import com.patelheggere.repositorysearch.models.ItemsModel;

import java.util.List;


public class ContributorRepositoryAdapter extends RecyclerView.Adapter<ContributorRepositoryAdapter.ViewHolder>
{
    public Context mContext;
    public List<ItemsModel> mItems;

    public ContributorRepositoryAdapter(Context mContext, List<ItemsModel> mItems)
    {
        this.mContext = mContext;
        this.mItems = mItems ;

    }

    @Override
    public ContributorRepositoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contributor_repo_list, parent, false);
        return new ContributorRepositoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContributorRepositoryAdapter.ViewHolder holder, int position)
    {
        ItemsModel item = mItems.get(position);
        if(item!=null)
        {
           if(item.getName()!=null)
               holder.mTvName.setText(item.getName());
        }

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView mTvName;

        public ViewHolder(View v) {
            super(v);
            mTvName = v.findViewById(R.id.tvreponame);
        }
    }
}
