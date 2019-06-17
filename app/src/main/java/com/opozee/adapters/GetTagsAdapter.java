package com.opozee.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opozee.R;
import com.opozee.model.GetTagsModel;

import java.util.ArrayList;

public class GetTagsAdapter extends RecyclerView.Adapter<GetTagsAdapter.MyHolder> {

    public ArrayList<GetTagsModel> getTagsAdapterslist;
  public Context context;
   public GetTagsAdapter(Context context, ArrayList<GetTagsModel> getTagsModels){
       this.context=context;
       this.getTagsAdapterslist=getTagsModels;
   }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_tags,viewGroup,false);
       MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
GetTagsModel getTagsModel=getTagsAdapterslist.get(i);
myHolder.tv_tags.setText(getTagsModel.getHashtag());
    }

    @Override
    public int getItemCount() {
        return getTagsAdapterslist.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
       TextView tv_tags;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_tags=itemView.findViewById(R.id.tv_tags);
        }
    }
}
