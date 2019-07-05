package com.opozeeApp.adapters;



        import android.content.Context;
        import android.content.Intent;

        import android.support.v7.widget.RecyclerView;
        import android.text.Html;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.SeekBar;
        import android.widget.TextView;


        import com.opozeeApp.R;
        import com.opozeeApp.activities.QuestionDetailActivity;
        import com.opozeeApp.pojo.SearchQuestionResponse;
        import com.opozeeApp.utils.AppGlobal;
        import com.opozeeApp.utils.Utils;
        import com.squareup.picasso.Picasso;

        import org.apache.commons.lang3.StringEscapeUtils;

        import java.util.List;

        import butterknife.BindView;
        import butterknife.ButterKnife;
        import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>  {

    List<SearchQuestionResponse.SearchQuestion> usersList;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @BindView(R.id.tv_time)
        public TextView tv_time;
        @BindView(R.id.iv_user)
        public CircleImageView iv_user;
        @BindView(R.id.tv_question)
        TextView tv_question;
        @BindView(R.id.tv_user_name)
        public TextView tv_user_name;
        @BindView(R.id.tv_name)
        public TextView tv_name;
        @BindView(R.id.seekBar)
        SeekBar seekBar;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            seekBar.setEnabled(false);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchAdapter(Context mContext, List<SearchQuestionResponse.SearchQuestion> usersList) {

        this.mContext = mContext;
        this.usersList = usersList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view-
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.tv_name.setText(usersList.get(position).getOwnerUserName());

        holder.tv_user_name.setText("@"+usersList.get(position).getOwnerUserName().replace(" ", "").toLowerCase());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, QuestionDetailActivity.class);
                intent.putExtra(AppGlobal.LAST_FRAG_TYPE, AppGlobal.SEARCHFRAG);
                intent.putExtra("id", usersList.get(position).getId());
                mContext.startActivity(intent);
            }
        });

        String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(usersList.get(position).getQuestion());
        holder.tv_question.setText(Html.fromHtml(fromServerUnicodeDecoded.trim()));

        String imageURL = usersList.get(position).getUserImage();
        String defaultURL = "https://opozee.com:81/Content/Upload/ProfileImage/oposee-profile.png";

        String url = imageURL == null || imageURL.length() == 0 || imageURL.equals("")? defaultURL : imageURL;
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(holder.iv_user);
//        String timeArr[] = usersList.get(position).getCreationDate().replace("T", " ").split("/.");
//        Log.e("TIME SPLIT ", " " + timeArr[0]);
//        String time = Utils.convertESTToLocalTime(timeArr[0]).replace(" ", " at ");
//        holder.tv_time.setText(time);


        int yesCount = usersList.get(position).getYesCount() != null ? usersList.get(position).getYesCount() : 0;
        int noCount = usersList.get(position).getNoCount() != null ? usersList.get(position).getNoCount() : 0;


        initDataToSeekbar(holder.seekBar, yesCount, noCount);

    }

    private void initDataToSeekbar(SeekBar seekBar, int likes, int dislikes) {
        int total = likes + dislikes == 0 ? 100 : likes + dislikes;

        // dislike span
        int dislikes_percentage = ((dislikes * 100 / total));
        // like span
        int likes_percentage = (likes  * 100 / total);
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
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return usersList.size();
    }
}


