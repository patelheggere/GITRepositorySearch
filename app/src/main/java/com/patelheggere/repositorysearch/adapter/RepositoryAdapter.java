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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.patelheggere.repositorysearch.R;
import com.patelheggere.repositorysearch.models.ItemsModel;

import java.util.List;


public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder>
{
    public Context mContext;
    public List<ItemsModel> mItems;

    public RepositoryAdapter(Context mContext, List<ItemsModel> mItems)
    {
        this.mContext = mContext;
        this.mItems = mItems ;

    }

    @Override
    public RepositoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repository_list, parent, false);
        return new RepositoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepositoryAdapter.ViewHolder holder, int position)
    {
        ItemsModel item = mItems.get(position);
        if(item!=null)
        {
           if(item.getName()!=null)
               holder.mTvName.setText(item.getName());
           if(item.getFull_name()!=null)
               holder.mTvFullName.setText(item.getFull_name());
           if(item.getWatchers_count()!=0)
               holder.mTvWatcherCount.setText(String.valueOf(item.getWatchers_count()));
           if(item.getOwner().getAvatar_url()!=null) {
               Log.d("Bind", "onBindViewHolder: "+item.getOwner().getAvatar_url());
               Glide.with(mContext).load(item.getOwner().getAvatar_url())
                       .thumbnail(0.5f)
                       .into(holder.mAvatarImage);
           }
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
        private TextView mTvFullName;
        private TextView mTvWatcherCount;
        private TextView mTvCommitCount;
        private ImageView mAvatarImage;

        public ViewHolder(View v) {
            super(v);
            mTvName = v.findViewById(R.id.tvname2);
            mTvFullName = v.findViewById(R.id.tvfullname2);
            mTvWatcherCount = v.findViewById(R.id.tvwatchercount2);
            mTvCommitCount = v.findViewById(R.id.tvcommitcount2);
            mAvatarImage = v.findViewById(R.id.ivavatar);
        }
    }
}
