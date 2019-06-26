package com.opozee.adapters;


        import android.content.Context;
        import android.content.Intent;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;


        import com.opozee.R;
        import com.opozee.activities.QuestionDetailActivity;
        import com.opozee.application.QuestionnaireApplication;
        import com.opozee.pojo.NotificationsResponse;
        import com.opozee.utils.AppGlobal;
        import com.opozee.utils.Utils;
        import com.squareup.picasso.Picasso;

        import org.apache.commons.lang3.StringEscapeUtils;
        import org.json.JSONObject;

        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import butterknife.BindView;
        import butterknife.ButterKnife;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder>  {

    List<NotificationsResponse.AllOpinion> usersList;
    private Context mContext;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @BindView(R.id.iv_status)
        ImageView iv_status;
        @BindView(R.id.iv_user)
        ImageView iv_user;
        @BindView(R.id.tv_name)
        public TextView tv_name;
        @BindView(R.id.tv_date)
        public TextView tv_date;
        @BindView(R.id.tv_notification)
        public TextView tv_notification;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NotificationsAdapter(Context mContext, List<NotificationsResponse.AllOpinion> usersList) {

        this.mContext = mContext;
        this.usersList = usersList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view-
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
if((usersList.get(position).getUserName())!=null) {
    holder.tv_name.setText(Utils.capitalize(usersList.get(position).getUserName()));
}
        boolean likeStatus = usersList.get(position).getLike();
        boolean dislikeStatus = usersList.get(position).getDislike();
        boolean commentStatus = usersList.get(position).getComment();
        if(likeStatus)
        {
            holder.iv_status.setImageResource(R.drawable.thumb_on);
        }
        else if(commentStatus)
        {
            holder.iv_status.setImageResource(R.drawable.comment_drawable);
        }
        else if(dislikeStatus)
        {
            holder.iv_status.setImageResource(R.drawable.dislike_thumb_on);
        }
        else
        {
            holder.iv_status.setImageResource(R.drawable.comment_drawable);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String , String> map = new HashMap<>();
                map.put("NotificationText", usersList.get(position).getMessage());
                QuestionnaireApplication.getMixpanelApi().track("Notification clicked", new JSONObject(map));
                Intent intent = new Intent(mContext, QuestionDetailActivity.class);
                intent.putExtra("id", usersList.get(position).getQuestionId());
                intent.putExtra(AppGlobal.LAST_FRAG_TYPE, AppGlobal.NOTIFICATIONFRAG);
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

        String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(usersList.get(position).getMessage());
        holder.tv_notification.setText(fromServerUnicodeDecoded);

//        String timeArr[] = usersList.get(position).getCreationDate().replace("T", " ").split("/.");
//        Log.e("TIME SPLIT ", " " + timeArr[0]);
//        String time = Utils.convertESTToLocalTime(timeArr[0]).replace("-", " at ");

        String timeArr[] = usersList.get(position).getCreationDate().replace("T", " ").split("/.");
        Log.e("TIME SPLIT ", " " + timeArr[0]);
        String time = Utils.convertESTToLocalTime(timeArr[0]).replace("-", " at ");
        String conertdate = timeArr[0].replace("ll", "");

        String timeexact = Utils.getlocaltime(conertdate);
        Long date = Utils.convertdatestring(timeexact);
        String timeago = Utils.getTimeAgo(date);

//            Log.d("TimeFormat_Log",time);
//            Log.d("TimeFormat_Log","conertdate="+conertdate);
//            Log.d("TimeFormat_Log","Long="+date);
//            Log.d("TimeFormat_Log","timeago="+timeago);
//            Log.d("TimeFormat_Log","Date="+timeexact);
        if (timeago != null) {
            holder.tv_date.setText(timeago);
        }


//        holder.tv_date.setText(time);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return usersList.size();
    }
}


