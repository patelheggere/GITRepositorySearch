package com.patelheggere.repositorysearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.patelheggere.repositorysearch.R;

import java.util.List;


public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder>
{
    public Context mContext;
    public List<String> fromTos;

    public RepositoryAdapter(Context mContext, List<String> fromTos)
    {
        this.mContext = mContext;
        this.fromTos = fromTos ;

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
        /*FromTo frmto = fromTos.get(position);

        if(frmto!=null)
        {
           if(frmto.getFrom().getLocation().getName()!=null)
            holder.mDptName.setText(frmto.getFrom().getLocation().getName());
           if(frmto.getFrom().getDeparture()!=null)
               holder.mTimePlat.setText(frmto.getFrom().getDeparture());
           if(frmto.getFrom().getPlatform()!=null)
               holder.mTimePlat.setText(holder.mTimePlat.getText()+"-Platform:"+frmto.getFrom().getPlatform());

            if(frmto.getTo().getLocation().getName()!=null)
                holder.mArrivalName.setText(frmto.getTo().getLocation().getName());
            if(frmto.getTo().getArrival()!=null)
                holder.mArrivalTime.setText(frmto.getTo().getArrival());
            if(frmto.getTo().getPlatform()!=null)
                holder.mArrivalTime.setText(holder.mArrivalTime.getText()+"-Platform:"+frmto.getTo().getPlatform());
        }*/

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return fromTos.size();
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
        }
    }
}
