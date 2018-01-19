package com.patelheggere.repositorysearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.patelheggere.repositorysearch.R;
import com.patelheggere.repositorysearch.models.ContributorModel;
import com.patelheggere.repositorysearch.models.ItemsModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ContributorAdapter extends BaseAdapter
{
    public Context mContext;
    public List<ContributorModel> mContributor;
    LayoutInflater inflater;

    public ContributorAdapter(Context mContext, List<ContributorModel> mContributor)
    {
        this.mContext = mContext;
        this.mContributor = mContributor ;
        this.inflater =  (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mContributor.size();
    }

    @Override
    public Object getItem(int position) {
        return mContributor.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contributors_list, null);
        }
        TextView mTvName =  convertView.findViewById(R.id.tvcontibutorname);
        CircleImageView mContImage = convertView.findViewById(R.id.cvcontributor);
        if(mContributor.get(position).getLogin()!=null)
            mTvName.setText(mContributor.get(position).getLogin());
        if(mContributor.get(position).getAvatar_url()!=null)
        {
            Glide.with(mContext)
                    .load(mContributor.get(position).getAvatar_url())
                    .thumbnail(0.5f)
                    .into(mContImage);
        }
        return convertView;
    }



}
