package com.opozee.adapters;


        import android.app.Activity;
        import android.content.Intent;
        import android.support.v7.widget.RecyclerView;
        import android.text.Html;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.SeekBar;
        import android.widget.TextView;


        import com.opozee.R;
        import com.opozee.activities.QuestionDetailActivity;
        import com.opozee.application.QuestionnaireApplication;
        import com.opozee.pojo.PostedQuestionsResponse;
        import com.opozee.utils.AppGlobal;
        import com.opozee.utils.Utils;
        import com.opozee.view.QuestionListTopBeliefView;
        import com.squareup.picasso.Picasso;

        import org.apache.commons.lang3.StringEscapeUtils;
        import org.json.JSONObject;

        import java.util.HashMap;
        import java.util.List;

        import butterknife.BindView;
        import butterknife.ButterKnife;
        import de.hdodenhof.circleimageview.CircleImageView;

public class HomeQuestionsAdapter extends RecyclerView.Adapter<HomeQuestionsAdapter.ViewHolder>  {

    List<PostedQuestionsResponse.PostQuestionDetail> usersList;
    private Activity mContext;
//    private PostedQuestionsResponse.PostQuestionDetail userData;
//    private Random random;
//    private final int VIEW_TYPE_ITEM = 0;
//    private final int VIEW_TYPE_LOADING = 1;
//    private OnLoadMoreListener onLoadMoreListener;
//    private boolean isLoading;
//    private int visibleThreshold = 4;
//    private int lastVisibleItem, totalItemCount;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @BindView(R.id.iv_user)
        CircleImageView iv_user;

        @BindView(R.id.tv_name)
        public TextView tv_name;

        @BindView(R.id.tv_user_name)
        public TextView tv_user_name;

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

        @BindView(R.id.best_supporting_view)
        QuestionListTopBeliefView bestSupportingView;

        @BindView(R.id.best_against_view)
        QuestionListTopBeliefView bestAgainstView;

        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);
            seekBar.setEnabled(false);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeQuestionsAdapter(Activity mContext, List<PostedQuestionsResponse.PostQuestionDetail> usersList) {

        this.mContext = mContext;
        this.usersList = usersList;

    }
    // Create new views (invoked by the layout manager)
    @Override
    public HomeQuestionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // create a new view-
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.home_questions_item, parent, false);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;

    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(HomeQuestionsAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final PostedQuestionsResponse.PostQuestionDetail questionDetail = usersList.get(position);
        String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(questionDetail.getQuestion());
        holder.tv_question.setText(Html.fromHtml(fromServerUnicodeDecoded.trim()));
        //holder.tv_question.setText(fromServerUnicodeDecoded.trim());

//        holder.tv_time.setText(Utils.getTimeAgo(Utils.convertESTToLocalTime(usersList.get(position).getCreationDate()).getTime(), mContext));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();
                map.put("questionText", questionDetail.getQuestion());
                JSONObject jsonObject = new JSONObject(map);
                QuestionnaireApplication.getMixpanelApi().track("Question Clicked on Home Fragment" , jsonObject);
                Intent intent = new Intent(mContext, QuestionDetailActivity.class);
                intent.putExtra("id", usersList.get(position).getId());
                intent.putExtra(AppGlobal.LAST_FRAG_TYPE, AppGlobal.HOMEFRAG);
                mContext.startActivity(intent);
            }
        });

        String imageURL = questionDetail.getUserImage();

        String url = (imageURL == null || imageURL.length() == 0 || imageURL.equals(""))? Utils.DEFAULT_PROFILE_PIC_URL : imageURL;
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(holder.iv_user);




        holder.tv_user_name.setText("@"+usersList.get(position).getOwnerUserName().replace(" ", "").toLowerCase());
        holder.tv_name.setText(Utils.capitalize(usersList.get(position).getName()));



        boolean isMostLiked =  questionDetail.getMostLiked() != null;
        boolean isMostDisliked =  questionDetail.getMostDisliked() != null;

        if(isMostLiked) {
            holder.bestSupportingView.setVisibility(View.VISIBLE);
            holder.bestSupportingView.setBelief(questionDetail.getMostLiked());
        }
        else
        {
            holder.bestSupportingView.setVisibility(View.GONE);
        }

        if(isMostDisliked) {
            holder.bestAgainstView.setVisibility(View.VISIBLE);
            holder.bestAgainstView.setBelief(questionDetail.getMostDisliked());

        }
        else
        {
            holder.bestAgainstView.setVisibility(View.GONE);
        }

        int yesCount = usersList.get(position).getYesCount() != null ? usersList.get(position).getYesCount() : 0;
        int noCount = usersList.get(position).getNoCount() != null ? usersList.get(position).getNoCount() : 0;


        initDataToSeekbar(holder.seekBar, holder.tv_count_likes, holder.tv_count_dislikes,yesCount, noCount);

    }

    private void initDataToSeekbar(SeekBar seekBar, TextView tv_count_likes, TextView tv_count_dislikes, int likes, int dislikes) {
        int total = likes + dislikes == 0 ? 100 : likes + dislikes;

        // dislike span
        int dislikes_percentage = ((dislikes * 100 / total));

        Log.e("Dislikes >>", "  "  +  dislikes +  "    " + dislikes_percentage);
        // like span
        int likes_percentage = (likes  * 100 / total);
        Log.e("likes >>", "  "  +  likes +  "    " + likes_percentage);

        if(dislikes == 0 && likes == 0)
        {
            seekBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progress_bg));
            seekBar.setSecondaryProgress(0);
            seekBar.invalidate();
        }
        else
        {
            seekBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progress_line));
//            seekBar.setProgress(dislikes_percentage);
            seekBar.setSecondaryProgress(likes_percentage);
            seekBar.invalidate();
        }

        tv_count_dislikes.setText("No " + dislikes_percentage + "%");
        tv_count_likes.setText( likes_percentage + "% Yes");


    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return usersList == null ? 0 : usersList.size();
    }
}


