package com.opozee.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opozee.R;
import com.opozee.activities.QuestionDetailActivity;
import com.opozee.application.QuestionnaireApplication;
import com.opozee.models.Belief;
import com.opozee.utils.AppGlobal;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserBeliefAdapter extends RecyclerView.Adapter<UserBeliefAdapter.ViewHolder> {


    List<Belief> mBeliefList;
    private Context mContext ;

    public UserBeliefAdapter(Context context, List<Belief> beliefList) {
        super();
        this.mContext = context;
        this.mBeliefList  = beliefList;
    }

    @Override
    public UserBeliefAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view-
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_belief_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {


        final Belief currBelief = mBeliefList.get(position);
        viewHolder.mBeliefText.setText(Html.fromHtml(currBelief.getBeliefText()));
        viewHolder.mNumDislikes.setText(Integer.toString(currBelief.getDislikesCount()));
        viewHolder.mNumLikes.setText(Integer.toString(currBelief.getLikesCount()));
        if(currBelief.getQuestionText()!=null){
            viewHolder.mQuestionText.setText(Html.fromHtml(currBelief.getQuestionText()));
        }

        if (currBelief.isAgree()){
            viewHolder.mBeliefContainer.setBackground(mContext.getResources().getDrawable(R.drawable.top_belief_view_positive_bg));
        } else {
            viewHolder.mBeliefContainer.setBackground(mContext.getResources().getDrawable(R.drawable.top_belief_view_negative_bg));
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("Question Text" , currBelief.getBeliefText());
                QuestionnaireApplication.getMixpanelApi().track("Question from Profile questions", new JSONObject(map));
                Intent intent = new Intent(mContext, QuestionDetailActivity.class);
                intent.putExtra("id", currBelief.getQuestionId());
                intent.putExtra(AppGlobal.LAST_FRAG_TYPE, AppGlobal.HOMEFRAG);
                mContext.startActivity(intent);
            }
        });

    }

     public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.belief_container)
         LinearLayout mBeliefContainer ;

        @BindView(R.id.tv_belief_text)
        TextView mBeliefText;

        @BindView(R.id.num_likes_tv)
        TextView mNumLikes;

        @BindView(R.id.num_dislikes_tv)
        TextView mNumDislikes;

        @BindView(R.id.profile_belief_view_question_text)
        TextView mQuestionText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    @Override
    public int getItemCount() {
        return mBeliefList.size();
    }
}
