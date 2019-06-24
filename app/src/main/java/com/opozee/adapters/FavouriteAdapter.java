package com.opozee.adapters;



        import android.content.Context;
        import android.content.Intent;

        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.SeekBar;
        import android.widget.TextView;


        import com.opozee.R;
        import com.opozee.activities.QuestionDetailActivity;
        import com.opozee.pojo.FavouriteResponse;
        import com.opozee.utils.AppGlobal;
        import com.opozee.utils.Utils;
        import com.squareup.picasso.Picasso;

        import org.apache.commons.lang3.StringEscapeUtils;

        import java.util.List;

        import butterknife.BindView;
        import butterknife.ButterKnife;
        import de.hdodenhof.circleimageview.CircleImageView;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder>  {

    List<FavouriteResponse.PostQuestionDetail> usersList;
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
        @BindView(R.id.tv_count_likes)
        public TextView tv_count_likes;
        @BindView(R.id.tv_count_dislikes)
        public TextView tv_count_dislikes;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            seekBar.setEnabled(false);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FavouriteAdapter(Context mContext, List<FavouriteResponse.PostQuestionDetail> usersList) {

        this.mContext = mContext;
        this.usersList = usersList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view-
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.tv_name.setText(Utils.capitalize(usersList.get(position).getOwnerUserName()));

        holder.tv_user_name.setText("@"+usersList.get(position).getName().replace(" ", "").toLowerCase());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, QuestionDetailActivity.class);
                intent.putExtra("id", usersList.get(position).getId());
                intent.putExtra(AppGlobal.LAST_FRAG_TYPE, AppGlobal.FAVOURITEFRAG);
                mContext.startActivity(intent);
            }
        });

        String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(usersList.get(position).getQuestion());
        holder.tv_question.setText(fromServerUnicodeDecoded.trim());
        String imageURL = usersList.get(position).getUserImage();
        String defaultURL = "http://23.111.138.246:8021/Content/Upload/ProfileImage/oposee-profile.png";

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

        initDataToSeekbar(holder.seekBar, holder.tv_count_likes, holder.tv_count_dislikes,yesCount, noCount,position);

    }

    private void initDataToSeekbar(SeekBar seekBar, TextView tv_count_likes, TextView tv_count_dislikes, int likes, int dislikes,int status) {


//        try {
//            int scoreYes = 0;
//            int scoreNo = 0;
//            int _score = 0;
//            for (int k = 0; k < usersList.get(status).getComments().size(); k++) {
//                _score = usersList.get(status).getComments().get(k).getLikescount() - usersList.get(status).getComments().get(k).getDislikescount();
//                Log.d("Percentage=","_score="+_score);
//                if (usersList.get(status).getComments().get(k).isAgree() == true) {
//                    scoreYes = scoreYes + (_score > 0 ? _score : 0);
//                    Log.d("Percentage=","scoreYes="+scoreYes);
//                } else {
//                    scoreNo = scoreNo + (_score > 0 ? _score : 0);
//                    Log.d("Percentage=","scoreNo="+scoreNo);
//                }
//
//            }
//            Log.d("Percentage=","scoreNo=1----"+scoreNo);
//            Log.d("Percentage=","scoreYes=1----"+scoreYes);
//            int finalscore=scoreYes+scoreNo;
//            double finaldata=(double) scoreYes/finalscore;
//            Log.d("Percentage=","finalscore=1----"+finalscore);
//            Log.d("Percentage=","finaldata=1----"+finaldata);
//            double finalpercentage = (double) finaldata *100;
//            int percentage=(int)finalpercentage;
//            Log.d("Percentage=",""+percentage+"-------------------------------");
//            if (percentage <= 0) {
//                percentage = 0;
//                scoreNo = 0;
//                scoreYes = 0;
//                seekBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progress_bg));
//                seekBar.setSecondaryProgress(0);
//                seekBar.invalidate();
//            } else {
//                seekBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progress_line));
////            seekBar.setProgress(dislikes_percentage);
//                seekBar.setSecondaryProgress( percentage);
//                seekBar.invalidate();
//            }
//
//            tv_count_dislikes.setText("No " + percentage + "%");
//            tv_count_likes.setText(percentage + "% Yes");
//
//        } catch (Exception e) {
//            Log.d("Percentage=",e.toString());
//        }


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
        tv_count_likes.setText( likes_percentage + "% Yes" );


    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return usersList.size();
    }
}


