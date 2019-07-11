package com.opozeeApp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opozeeApp.ExpandableTextView;
import com.opozeeApp.R;
import com.opozeeApp.activities.QuestionDetailActivity;
import com.opozeeApp.application.QuestionnaireApplication;
import com.opozeeApp.models.Belief;
import com.opozeeApp.profiletabs.Beliefs;
import com.opozeeApp.profiletabs.Questions;
import com.opozeeApp.utils.AppGlobal;
import com.opozeeApp.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
        if (currBelief.getLongForm() == null) {
            viewHolder.expandableTextView.setVisibility(View.GONE);
            viewHolder.mBeliefText.setVisibility(View.VISIBLE);
            viewHolder.mBeliefText.setText((Html.fromHtml(currBelief.getBeliefText())).toString().trim());
        } else {
            viewHolder.mBeliefText.setVisibility(View.VISIBLE);
            viewHolder.expandableTextView.setVisibility(View.VISIBLE);
            viewHolder.mBeliefText.setText((Html.fromHtml(currBelief.getBeliefText())).toString().trim());
            viewHolder.expandableTextView.setText((Html.fromHtml(currBelief.getLongForm())).toString().trim());
        }



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

        if(currBelief.getCreationDate()!=null){
            String[] timeArr = currBelief.getCreationDate().replace("T", " ").split("/.");
            Log.e("TIME SPLIT ", " " + timeArr[0]);
            String time = Utils.convertESTToLocalTime(timeArr[0]).replace("-", " at ");
            String conertdate = timeArr[0].replace("ll", "");

            String timeexact = Utils.getlocaltime(conertdate);
            Long date = Utils.convertdatestring(timeexact);
            long now = System.currentTimeMillis();
            final long diff = now - date;
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);

            if(Integer.parseInt(Utils.getLoggedInUserId(mContext))==currBelief.getUserId()) {
                if (minutes > 10) {
                    viewHolder.iv_delete.setVisibility(View.GONE);
                } else {
                    viewHolder.iv_delete.setVisibility(View.VISIBLE);
                }
            }else{
                viewHolder.iv_delete.setVisibility(View.GONE);
            }

        }

        viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Beliefs.deleterequest(currBelief.getId());
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("Question Text" , currBelief.getBeliefText());
                QuestionnaireApplication.getMixpanelApi().track("Question from Profile questions", new JSONObject(map));
                Intent intent = new Intent(mContext, QuestionDetailActivity.class);
                intent.putExtra("id", currBelief.getQuestionId());
                intent.putExtra(AppGlobal.LAST_FRAG_TYPE, AppGlobal.PROFILEFRAG);
                mContext.startActivity(intent);
            }
        });

    }

     public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.belief_container)
         LinearLayout mBeliefContainer ;

        @BindView(R.id.tv_belief_text)
        TextView mBeliefText;
//                ExpandableTextView mBeliefText;

         @BindView(R.id.tv_belief_text_expand)
         ExpandableTextView expandableTextView;

        @BindView(R.id.num_likes_tv)
        TextView mNumLikes;

        @BindView(R.id.num_dislikes_tv)
        TextView mNumDislikes;

        @BindView(R.id.profile_belief_view_question_text)
        TextView mQuestionText;
        @BindView(R.id.iv_delete)
         ImageView iv_delete;

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
