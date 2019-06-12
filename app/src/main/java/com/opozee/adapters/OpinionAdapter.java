package com.opozee.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.like.LikeButton;
import com.like.OnLikeListener;
import com.opozee.R;
import com.opozee.activities.ProfileActivity;
import com.opozee.activities.QuestionDetailActivity;
import com.opozee.application.QuestionnaireApplication;
import com.opozee.pojo.QuestionDetailResponse;
import com.opozee.utils.Utils;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

import static com.opozee.fragments.ProfileFragment.PROFILE_FRAGMENG_ARGUEMENT_USER_ID;

public class OpinionAdapter extends RecyclerView.Adapter<OpinionAdapter.ViewHolder> {

    List<QuestionDetailResponse.Comment> usersList;
    private Context mContext;
    private QuestionDetailResponse.Comment userData;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @BindView(R.id.iv_user_yes)
        CircleImageView iv_user_yes;
        @BindView(R.id.iv_user_no)
        CircleImageView iv_user_no;
        @BindView(R.id.tv_name_yes)
        public TextView tv_name_yes;
        @BindView(R.id.tv_user_name_yes)
        public TextView tv_user_name_yes;
        @BindView(R.id.tv_time_yes)
        public TextView tv_time_yes;
        @BindView(R.id.tv_opinion_yes)
        TextView tv_opinion_yes;
        @BindView(R.id.tv_name_no)
        public TextView tv_name_no;
        @BindView(R.id.tv_user_name_no)
        public TextView tv_user_name_no;
        @BindView(R.id.tv_time_no)
        public TextView tv_time_no;
        @BindView(R.id.tv_opinion_no)
        TextView tv_opinion_no;
        @BindView(R.id.card_yes)
        CardView card_yes;
        @BindView(R.id.card_no)
        CardView card_no;
        @BindView(R.id.tv_like_yes)
        public TextView tv_like_yes;
        @BindView(R.id.tv_like_no)
        public TextView tv_like_no;
        @BindView(R.id.tv_dislike_yes)
        public TextView tv_dislike_yes;
        @BindView(R.id.tv_dislike_no)
        public TextView tv_dislike_no;

        @BindView(R.id.ll_like)
        public LinearLayout ll_like;
        @BindView(R.id.ll_dislike)
        public LinearLayout ll_dislike;

        @BindView(R.id.ll_like_yes)
        public LinearLayout ll_like_yes;
        @BindView(R.id.ll_dislike_no)
        public LinearLayout ll_dislike_no;

        @BindView(R.id.iv_like)
        public ImageView iv_like;

        @BindView(R.id.iv_dislike)
        public ImageView iv_dislike;

        @BindView(R.id.iv_like_yes)
        public ImageView iv_like_yes;

        @BindView(R.id.iv_dislike_no)
        public ImageView iv_dislike_no;


        public static LinearLayout ll_subreation, ll_subreation_no;
        public static LinearLayout ll_likeGif, ll_factualGif, ll_smileyGif, ll_wowGif, ll_sadGif, ll_angryGif;

        @BindView(R.id.ll_questionsView)
        public LinearLayout ll_questionView;

        @BindView(R.id.iv_likeSub)
        public ImageView iv_likeSub;

        public GifImageView gif_like, gif_factual, gif_smiley;


//        @BindView(R.id.btn_like_yes)
//        public LikeButton btn_like_yes;
//        @BindView(R.id.btn_dislike_yes)
//        public LikeButton btn_dislike_yes;
//        @BindView(R.id.btn_like_no)
//        public LikeButton btn_like_no;
//        @BindView(R.id.btn_dislike_no)
//        public LikeButton btn_dislike_no;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            ll_subreation = v.findViewById(R.id.ll_subreation1);
            ll_subreation_no = v.findViewById(R.id.ll_subreation_no1);
            ll_likeGif = v.findViewById(R.id.ll_likeGif);
            ll_factualGif = v.findViewById(R.id.ll_factual_gif);
            ll_smileyGif = v.findViewById(R.id.smileyGif);
            ll_wowGif = v.findViewById(R.id.ll_wowGif);
            ll_sadGif = v.findViewById(R.id.ll_sadGif);
            ll_angryGif = v.findViewById(R.id.ll_angryGif);

            gif_like = v.findViewById(R.id.iv_likeSub);
            gif_factual = v.findViewById(R.id.iv_factual);
            gif_smiley = v.findViewById(R.id.iv_smiley);

            ll_likeGif.setVisibility(View.GONE);
            ll_factualGif.setVisibility(View.GONE);
            ll_smileyGif.setVisibility(View.GONE);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OpinionAdapter(Context mContext, List<QuestionDetailResponse.Comment> usersList) {

        this.mContext = mContext;
        this.usersList = usersList;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public OpinionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view-
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.opinion_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.ll_subreation.setVisibility(View.GONE);
//        holder.ll_subreation_no.setVisibility(View.GONE);

//        for(int k=0;k<usersList.size();k++){
            if (usersList.get(position).getIschecked() != null) {
                if (usersList.get(position).getIschecked() == true) {

                        holder.ll_likeGif.setVisibility(View.VISIBLE);
                        holder.ll_factualGif.setVisibility(View.VISIBLE);
                        holder.ll_smileyGif.setVisibility(View.VISIBLE);

                }else{
                    holder.ll_likeGif.setVisibility(View.GONE);
                    holder.ll_factualGif.setVisibility(View.GONE);
                     holder.ll_smileyGif.setVisibility(View.GONE);
                }
            } else {
                holder.ll_likeGif.setVisibility(View.GONE);
                holder.ll_factualGif.setVisibility(View.GONE);
                holder.ll_smileyGif.setVisibility(View.GONE);
            }
//        }

        View.OnClickListener openProfile = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (holder.ll_subreation.getVisibility() == View.VISIBLE) {
//                    holder.ll_subreation.setVisibility(View.GONE);
                }
                if (holder.ll_subreation_no.getVisibility() == View.VISIBLE) {
//                    holder.ll_subreation.setVisibility(View.GONE);
                }


                Map<String, String> map = new HashMap<>();
                map.put("ProfileOpened", usersList.get(position).getName());
                QuestionnaireApplication.getMixpanelApi().track("Profile Opened", new JSONObject(map));
                int userId = usersList.get(position).getCommentedUserId();
                Intent intent = new Intent(OpinionAdapter.this.mContext, ProfileActivity.class);
                intent.putExtra(PROFILE_FRAGMENG_ARGUEMENT_USER_ID, userId);
                OpinionAdapter.this.mContext.startActivity(intent);
            }
        };


        boolean isYesNo = usersList.get(position).getIsAgree();

        if (isYesNo) {
            holder.card_yes.setVisibility(View.VISIBLE);
            holder.card_yes.setBackground(mContext.getResources().getDrawable(R.drawable.top_belief_view_positive_bg));
            holder.card_no.setVisibility(View.GONE);

            holder.tv_name_yes.setText(Utils.capitalize(usersList.get(position).getName()));
            holder.tv_user_name_yes.setOnClickListener(openProfile);
            holder.iv_user_yes.setOnClickListener(openProfile);

            holder.tv_user_name_yes.setText("@" + usersList.get(position).getCommentedUserName().replace(" ", "").toLowerCase());
            holder.tv_dislike_yes.setText(Utils.format(usersList.get(position).getDislikesCount()));
            holder.tv_like_yes.setText(Utils.format(usersList.get(position).getLikesCount()));
            String imageURL = usersList.get(position).getUserImage();
            String defaultURL = "http://23.111.138.246:8021/Content/Upload/ProfileImage/oposee-profile.png";

            String url = imageURL == null || imageURL.length() == 0 || imageURL.equals("") ? defaultURL : imageURL;
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user)
                    .into(holder.iv_user_yes);
            String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(usersList.get(position).getComment());
            holder.tv_opinion_yes.setText(Html.fromHtml(fromServerUnicodeDecoded.trim()));
//        DisLike = 0,
//                Like = 1,
//                RemoveLike = 2,
//                RemoveDisLike = 3,
//                Comment = 4


            if (usersList.get(position).getLikes()) {
                holder.iv_like.setImageResource(R.drawable.thumb_on);
//                holder.btn_like_yes.setLiked(true);
            } else {
                holder.iv_like.setImageResource(R.drawable.thumb_off);
//                holder.btn_like_yes.setLiked(false);
            }

            if (usersList.get(position).getDisLikes()) {
                holder.iv_dislike.setImageResource(R.drawable.dislike_thumb_on);
//                holder.btn_dislike_yes.setLiked(true);
            } else {
                holder.iv_dislike.setImageResource(R.drawable.dislike_thumb_off);
//                holder.btn_dislike_yes.setLiked(false);
            }
            if (!String.valueOf(usersList.get(position).getCommentedUserId()).equals(Utils.getLoggedInUserId(mContext))) {
                holder.ll_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (usersList.get(position).getLikes()) {
                            holder.iv_like.setImageResource(R.drawable.thumb_off);
                            ((QuestionDetailActivity) mContext).likeDislike(2, usersList.get(position).getId());
                        } else {
//                            QuestionDetailActivity.view_reaction.setVisibility(View.VISIBLE);

//                            holder.gif_like.setImageResource(R.drawable.facebookresizee);
//                            holder.gif_factual.setImageResource(R.drawable.factualresizes);
//                            holder.gif_smiley.setImageResource(R.drawable.haharesize);
//                            holder.ll_likeGif.setVisibility(View.VISIBLE);
//                            holder.ll_factualGif.setVisibility(View.VISIBLE);
//                            holder.ll_smileyGif.setVisibility(View.VISIBLE);
//                            Toast.makeText(mContext, "Touch", Toast.LENGTH_SHORT).show();
//
//                            holder.ll_subreation_no.setVisibility(View.GONE);
//                            holder.ll_subreation.setVisibility(View.VISIBLE);

//                            holder.iv_like.setImageResource(R.drawable.thumb_on);
//                            ((QuestionDetailActivity) mContext).likeDislike(1, usersList.get(position).getId());
                        }
                    }
                });

                holder.ll_dislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (usersList.get(position).getDisLikes()) {
                            holder.iv_dislike.setImageResource(R.drawable.dislike_thumb_off);
                            ((QuestionDetailActivity) mContext).likeDislike(3, usersList.get(position).getId());
                        } else {
                            for(int i=0;i<usersList.size();i++){
                                if(usersList.get(i).getIschecked()!=null) {
                                    if (usersList.get(i).getIschecked() == true) {
                                        usersList.get(i).setIschecked(false);
                                        notifyItemChanged(i);
                                    } else if (i==position){
                                        usersList.get(position).setIschecked(true);
                                        notifyItemChanged(position);
                                    }
                                }else if(i==position){
                                    usersList.get(position).setIschecked(true);
                                    notifyItemChanged(position);
                                }
//                                usersList.get(i).setIschecked(false);
//                                notifyItemChanged(i);
//                                holder.ll_likeGif.setVisibility(View.GONE);
//                                holder.ll_factualGif.setVisibility(View.GONE);
//                                holder.ll_smileyGif.setVisibility(View.GONE);
                            }

//                             if(usersList.get(position).getIschecked()==true) {
//                                holder.ll_likeGif.setVisibility(View.VISIBLE);
//                                holder.ll_factualGif.setVisibility(View.VISIBLE);
//                                holder.ll_smileyGif.setVisibility(View.VISIBLE);
//                                notifyItemChanged(position);
//                            }
//                            }


//                            holder.gif_like.setImageResource(R.drawable.wowresize);
//                            holder.gif_factual.setImageResource(R.drawable.factualresizes);
//                            holder.gif_smiley.setImageResource(R.drawable.angryresize);
//
//                            holder.ll_subreation_no.setVisibility(View.GONE);
//                            holder.ll_subreation.setVisibility(View.VISIBLE);
//                            holder.iv_dislike.setImageResource(R.drawable.dislike_thumb_on);
//                            ((QuestionDetailActivity) mContext).likeDislike(0, usersList.get(position).getId());
                        }
                    }
                });
                holder.iv_likeSub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "Test Ok", Toast.LENGTH_SHORT).show();
                    }
                });

//                holder.btn_dislike_yes.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View view) {
//                        Toast.makeText(mContext, "On Button Click", Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//                });
//
//                holder.btn_like_yes.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View view) {
//                        return false;
//                    }
//                });

//                holder.btn_dislike_yes.setOnLikeListener(new OnLikeListener() {
//                    @Override
//                    public void liked(LikeButton likeButton) {
//                        holder.btn_dislike_yes.setLiked(true);
//                        ((QuestionDetailActivity) mContext).likeDislike(0, usersList.get(position).getId());
//                    }
//
//                    @Override
//                    public void unLiked(LikeButton likeButton) {
//                        holder.btn_dislike_yes.setLiked(false);
//                        ((QuestionDetailActivity) mContext).likeDislike(3, usersList.get(position).getId());
//                    }
//                });

//                holder.btn_like_yes.setOnLikeListener(new OnLikeListener() {
//                    @Override
//                    public void liked(LikeButton likeButton) {
//                        holder.btn_like_yes.setLiked(true);
//                        ((QuestionDetailActivity) mContext).likeDislike(1, usersList.get(position).getId());
//                    }

//                    @Override
//                    public void unLiked(LikeButton likeButton) {
//                        holder.btn_like_yes.setLiked(false);
//                        ((QuestionDetailActivity) mContext).likeDislike(2, usersList.get(position).getId());
//                    }
//                });
            } else {
//                holder.btn_like_yes.setEnabled(false);
//                holder.btn_dislike_yes.setEnabled(false);
                holder.ll_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "You cannot vote on your own beliefs", Toast.LENGTH_LONG).show();
                    }
                });

                holder.ll_dislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "You cannot vote on your own beliefs", Toast.LENGTH_LONG).show();
                    }
                });

            }

//            String timeArr[] = usersList.get(position).getCreationDate().replace("T", " ").split("/.");
//            Log.e("TIME SPLIT ", " " + timeArr[0]);
//            String time = Utils.convertESTToLocalTime(timeArr[0]).replace(" ", " at ");
//            holder.tv_time_yes.setText(time);

        } else {

            holder.card_yes.setVisibility(View.GONE);
            holder.card_no.setVisibility(View.VISIBLE);
            holder.card_no.setBackground(mContext.getResources().getDrawable(R.drawable.top_belief_view_negative_bg));


            holder.tv_name_no.setText(Utils.capitalize(usersList.get(position).getName()));

            holder.tv_user_name_no.setOnClickListener(openProfile);
            holder.iv_user_no.setOnClickListener(openProfile);

            holder.tv_user_name_no.setText("@" + usersList.get(position).getCommentedUserName().replace(" ", "").toLowerCase());
            holder.tv_dislike_no.setText(Utils.format(usersList.get(position).getDislikesCount()) + "");
            holder.tv_like_no.setText(Utils.format(usersList.get(position).getLikesCount()) + "");
            String imageURL = usersList.get(position).getUserImage();
            String defaultURL = "http://23.111.138.246:8021/Content/Upload/ProfileImage/oposee-profile.png";

            String url = imageURL == null || imageURL.length() == 0 || imageURL.equals("") ? defaultURL : imageURL;
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user)
                    .into(holder.iv_user_no);
            String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(usersList.get(position).getComment());
            holder.tv_opinion_no.setText(Html.fromHtml(fromServerUnicodeDecoded.trim()));
//        DisLike = 0,
//                Like = 1,
//                RemoveLike = 2,
//                RemoveDisLike = 3,
//                Comment = 4


            if (usersList.get(position).getLikes()) {
//                holder.btn_like_no.setLiked(true);
                holder.iv_like_yes.setImageResource(R.drawable.thumb_on);
            } else {
//                holder.btn_like_no.setLiked(false);
                holder.iv_like_yes.setImageResource(R.drawable.thumb_off);
            }

            if (usersList.get(position).getDisLikes()) {
//                holder.btn_dislike_no.setLiked(true);
                holder.iv_dislike_no.setImageResource(R.drawable.dislike_thumb_on);
            } else {
//                holder.btn_dislike_no.setLiked(false);
                holder.iv_dislike_no.setImageResource(R.drawable.dislike_thumb_off);
            }
            if (!String.valueOf(usersList.get(position).getCommentedUserId()).equals(Utils.getLoggedInUserId(mContext))) {

                holder.ll_like_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (usersList.get(position).getLikes()) {
                            holder.iv_like_yes.setImageResource(R.drawable.thumb_off);
                            ((QuestionDetailActivity) mContext).likeDislike(2, usersList.get(position).getId());
                        } else {
//                            holder.ll_subreation_no.setVisibility(View.VISIBLE);
//                            holder.ll_subreation.setVisibility(View.VISIBLE);
                            holder.iv_like_yes.setImageResource(R.drawable.thumb_on);
                            ((QuestionDetailActivity) mContext).likeDislike(1, usersList.get(position).getId());
                        }
                    }


                });
                holder.ll_dislike_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (usersList.get(position).getDisLikes()) {
                            holder.iv_dislike.setImageResource(R.drawable.dislike_thumb_off);
                            ((QuestionDetailActivity) mContext).likeDislike(3, usersList.get(position).getId());
                        } else {
//                            holder.ll_subreation.setVisibility(View.GONE);
//                            holder.ll_subreation_no.setVisibility(View.VISIBLE);
                            holder.iv_dislike.setImageResource(R.drawable.dislike_thumb_on);
                            ((QuestionDetailActivity) mContext).likeDislike(0, usersList.get(position).getId());
                        }
//
                    }
                });
//                holder.btn_dislike_no.setOnLikeListener(new OnLikeListener() {
//                    @Override
//                    public void liked(LikeButton likeButton) {
//                        holder.btn_dislike_no.setLiked(true);
//                        ((QuestionDetailActivity) mContext).likeDislike(0, usersList.get(position).getId());
//                    }
//
//                    @Override
//                    public void unLiked(LikeButton likeButton) {
//                        holder.btn_dislike_no.setLiked(false);
//                        ((QuestionDetailActivity) mContext).likeDislike(3, usersList.get(position).getId());
//                    }
//                });

//                holder.btn_like_no.setOnLikeListener(new OnLikeListener() {
//                    @Override
//                    public void liked(LikeButton likeButton) {
//                        holder.btn_like_no.setLiked(true);
//                        ((QuestionDetailActivity) mContext).likeDislike(1, usersList.get(position).getId());
//                    }
//
//                    @Override
//                    public void unLiked(LikeButton likeButton) {
//                        holder.btn_like_no.setLiked(false);
//                        ((QuestionDetailActivity) mContext).likeDislike(2, usersList.get(position).getId());
//                    }
//                });
            } else {
//                holder.btn_like_no.setEnabled(false);
//                holder.btn_dislike_no.setEnabled(false);

                holder.ll_like_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "You cannot vote on your own beliefs", Toast.LENGTH_LONG).show();
                    }


                });
                holder.ll_dislike_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "You cannot vote on your own beliefs", Toast.LENGTH_LONG).show();
//
                    }
                });
            }

//            String timeArr[] = usersList.get(position).getCreationDate().replace("T", " ").split("/.");
//            Log.e("TIME SPLIT ", " " + timeArr[0]);
//            String time = Utils.convertESTToLocalTime(timeArr[0]).replace("-", " at ");
//            holder.tv_time_no.setText(time);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return usersList.size();
    }
}


