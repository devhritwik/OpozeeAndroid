package com.opozeeApp.adapters;


        import android.content.Context;
        import android.content.Intent;
        import android.support.v7.widget.RecyclerView;
        import android.text.Html;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.SeekBar;
        import android.widget.TextView;


        import com.opozeeApp.R;
        import com.opozeeApp.activities.QuestionDetailActivity;
        import com.opozeeApp.application.QuestionnaireApplication;
        import com.opozeeApp.pojo.PostQuestionResponse;
        import com.opozeeApp.pojo.PostedQuestionsResponse;
        import com.opozeeApp.profiletabs.Questions;
        import com.opozeeApp.utils.AppGlobal;
        import com.opozeeApp.utils.Utils;
        import com.squareup.picasso.Picasso;

        import org.apache.commons.lang3.StringEscapeUtils;
        import org.json.JSONObject;

        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.concurrent.TimeUnit;

        import butterknife.BindView;
        import butterknife.ButterKnife;
        import de.hdodenhof.circleimageview.CircleImageView;

        import static com.opozeeApp.profiletabs.Questions.*;

public class UserPostsAdapter extends RecyclerView.Adapter<UserPostsAdapter.ViewHolder>  {

    List<PostedQuestionsResponse.PostQuestionDetail> usersList;
    private Context mContext;
    public final String TAG="USER_POST_Adapter_LOG";

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @BindView(R.id.iv_user)
        CircleImageView iv_user;
        @BindView(R.id.tv_user_name)
        public TextView tv_user_name;
        @BindView(R.id.tv_name)
        public TextView tv_name;
        @BindView(R.id.tv_time)
        public TextView tv_time;
        @BindView(R.id.tv_question)
        TextView tv_question;
        @BindView(R.id.tv_count_likes)
        public TextView tv_count_likes;
        @BindView(R.id.tv_count_dislikes)
        public TextView tv_count_dislikes;

        @BindView(R.id.seekBar)
        public SeekBar seekBar;
        @BindView(R.id.linear)
        public LinearLayout linear;

        @BindView(R.id.iv_delete)
        public ImageView iv_delete;



        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            seekBar.setEnabled(false);


        }

        void bind(final int position) {

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UserPostsAdapter(Context mContext, List<PostedQuestionsResponse.PostQuestionDetail> usersList) {

        this.mContext = mContext;
        this.usersList = usersList;
        Log.d(TAG,"Adapter Call");
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UserPostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view-
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_post_items, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.bind(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

//        holder.tv_name.setText(Utils.capitalize(usersList.get(position).getName()));
        holder.tv_name.setText(usersList.get(position).getOwnerUserName());

        holder.tv_user_name.setText("@"+usersList.get(position).getOwnerUserName().replace(" ", "").toLowerCase());

        final String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(usersList.get(position).getQuestion());
        holder.tv_question.setText(Html.fromHtml(fromServerUnicodeDecoded.trim()));

//        holder.tv_time.setText(Utils.getTimeAgo(Utils.convertESTToLocalTime(usersList.get(position).getCreationDate()).getTime(), mContext));

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("Question Text" , fromServerUnicodeDecoded);
                QuestionnaireApplication.getMixpanelApi().track("Question from Profile questions", new JSONObject(map));
                Intent intent = new Intent(mContext, QuestionDetailActivity.class);
                intent.putExtra("id", usersList.get(position).getId());
                intent.putExtra(AppGlobal.LAST_FRAG_TYPE, AppGlobal.USERPOSTS);
                mContext.startActivity(intent);
            }
        });


        String imageURL = usersList.get(position).getUserImage();
        String defaultURL = "https://opozee.com:81/Content/Upload/ProfileImage/oposee-profile.png";

        String url = imageURL == null || imageURL.length() == 0 || imageURL.equals("")? defaultURL : imageURL;
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(holder.iv_user);

//
//        String timeArr[] = usersList.get(position).getCreationDate().replace("T", " ").split("/.");
//        Log.e("TIME SPLIT ", " " + timeArr[0]);
//        String time = Utils.convertESTToLocalTime(timeArr[0]).replace(" ", " at ");
//        holder.tv_time.setText(time);

        if(usersList.get(position).getCreationDate()!=null) {
            String[] timeArr = usersList.get(position).getCreationDate().replace("T", " ").split("/.");
            Log.e("TIME SPLIT ", " " + timeArr[0]);
            String time = Utils.convertESTToLocalTime(timeArr[0]).replace("-", " at ");
            String conertdate = timeArr[0].replace("ll", "");

            String timeexact = Utils.getlocaltime(conertdate);
            Long date = Utils.convertdatestring(timeexact);
            long now = System.currentTimeMillis();
            final long diff = now - date;
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);


            if(Integer.parseInt(Utils.getLoggedInUserId(mContext))==usersList.get(position).getOwnerUserID()) {
                if (minutes > 10) {
                    holder.iv_delete.setVisibility(View.GONE);
                } else {
                    holder.iv_delete.setVisibility(View.VISIBLE);
                }
            }else{
                holder.iv_delete.setVisibility(View.GONE);
            }

        }

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Questions.deleterequest(usersList.get(position).getId());
            }
        });

        int yesCount = usersList.get(position).getYesCount() != null ? usersList.get(position).getYesCount() : 0;
        int noCount = usersList.get(position).getNoCount() != null ? usersList.get(position).getNoCount() : 0;


        boolean isMostLiked = usersList.get(position).getMostLiked() != null;
        boolean isMostDisliked = usersList.get(position).getMostDisliked() != null;

        if(isMostLiked||isMostDisliked){
            holder.tv_count_likes.setVisibility(View.VISIBLE);
        }else{
            holder.tv_count_likes.setVisibility(View.GONE);
        }

        initDataToSeekbar(holder.seekBar, holder.tv_count_likes, holder.tv_count_dislikes,yesCount, noCount,position );

    }

    private void initDataToSeekbar(SeekBar seekBar, TextView tv_count_likes, TextView tv_count_dislikes, int likes, int dislikes,int status) {


        try {
            int scoreYes = 0;
            int scoreNo = 0;
            int _score = 0;
            for (int k = 0; k < usersList.get(status).getComments().size(); k++) {
                _score = usersList.get(status).getComments().get(k).getLikescount() - usersList.get(status).getComments().get(k).getDislikescount();
                Log.d(TAG,"Percentage="+"_score="+_score);
                if (usersList.get(status).getComments().get(k).isAgree() == true) {
                    scoreYes = scoreYes + (_score > 0 ? _score : 0);
                    Log.d(TAG,"Percentage="+"scoreYes="+scoreYes);
                } else {
                    scoreNo = scoreNo + (_score > 0 ? _score : 0);
                    Log.d(TAG,"Percentage="+"scoreNo="+scoreNo);
                }

            }
            Log.d(TAG,"Percentage="+"scoreNo=1----"+scoreNo);
            Log.d(TAG,"Percentage="+"scoreYes=1----"+scoreYes);
            int finalscore=scoreYes+scoreNo;
            double finaldata=(double) scoreYes/finalscore;
            Log.d(TAG,"Percentage="+"finalscore=1----"+finalscore);
            Log.d(TAG,"Percentage="+"finaldata=1----"+finaldata);
            double finalpercentage = finaldata *100;
            int percentage=(int)finalpercentage;
            Log.d(TAG,"Percentage="+""+percentage+"-------------------------------");
            if (percentage <= 0) {
                percentage = 0;
                scoreNo = 0;
                scoreYes = 0;
                seekBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progress_bg));
                seekBar.setSecondaryProgress(0);
                seekBar.invalidate();
            } else {
                seekBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progress_line));
//            seekBar.setProgress(dislikes_percentage);
                seekBar.setSecondaryProgress( percentage);
                seekBar.invalidate();
            }
if(percentage>0){
    int dislikepercent=100-percentage;
    tv_count_dislikes.setText("No " + dislikepercent + "%");
}else{
    tv_count_dislikes.setText("No " + 0 + "%");
}

            tv_count_likes.setText(percentage + "% Yes");

        } catch (Exception e) {
            Log.d(TAG,"Percentage="+e.toString());
        }






        //        int total = likes + dislikes == 0 ? 100 : likes + dislikes;
//
//        // dislike span
//        int dislikes_percentage = ((dislikes * 100 / total));
//
//        Log.e("Dislikes >>", "  "  +  dislikes +  "    " + dislikes_percentage);
//        // like span
//        int likes_percentage = (likes  * 100 / total);
//        Log.e("likes >>", "  "  +  likes +  "    " + likes_percentage);
//
//        if(dislikes == 0 && likes == 0)
//        {
//            seekBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progress_bg));
//            seekBar.setSecondaryProgress(0);
//            seekBar.invalidate();
//        }
//        else
//        {
//            seekBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progress_line));
////            seekBar.setProgress(dislikes_percentage);
//            seekBar.setSecondaryProgress(likes_percentage);
//            seekBar.invalidate();
//        }
//
//        tv_count_dislikes.setText("No " + dislikes_percentage + "%");
//        tv_count_likes.setText( likes_percentage + "% Yes");


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.d(TAG,"Size="+usersList.size());
        return usersList.size();
    }
}


