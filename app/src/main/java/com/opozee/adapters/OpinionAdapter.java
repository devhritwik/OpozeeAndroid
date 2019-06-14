package com.opozee.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    private List<QuestionDetailResponse.Comment> usersList;
    private Context mContext;
    private QuestionDetailResponse.Comment userData;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
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


        public LinearLayout ll_subreation, ll_subreation_no;
        public LinearLayout ll_likeGif, ll_factualGif, ll_smileyGif, ll_wowGif, ll_sadGif, ll_angryGif;

        @BindView(R.id.ll_questionsView)
        public LinearLayout ll_questionView;

        @BindView(R.id.iv_likeSub)
        public ImageView iv_likeSub;

        public GifImageView gif_like, gif_factual, gif_smiley, gif_wow, gif_angry;
        public ImageView iv_sad;

        public TextView tv_like,tv_factual,tv_smily,tv_wow,tv_sad,tv_angry;
//        public LinearLayout ll_slide1,ll_slide2,ll_slide_3,ll_subreactionsliding,ll_yes_inner;

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

            gif_wow = v.findViewById(R.id.iv_wow);
            iv_sad = v.findViewById(R.id.iv_sad);
            gif_angry = v.findViewById(R.id.iv_angry);

            tv_like=v.findViewById(R.id.tv_likeGif);
            tv_factual=v.findViewById(R.id.tv_factual);
            tv_smily=v.findViewById(R.id.tv_smiley);
            tv_wow=v.findViewById(R.id.tv_wow);
            tv_sad=v.findViewById(R.id.tv_sad);
            tv_angry=v.findViewById(R.id.tv_angry);

//            ll_slide1=v.findViewById(R.id.ll_slide1);
//            ll_slide2=v.findViewById(R.id.ll_slide2);
//            ll_slide_3=v.findViewById(R.id.ll_slide3);
//            ll_subreactionsliding=v.findViewById(R.id.ll_subreactionsliding);
//            ll_yes_inner=v.findViewById(R.id.ll_yes_inner);

//            ll_likeGif.setVisibility(View.GONE);
//            ll_factualGif.setVisibility(View.GONE);
//            ll_smileyGif.setVisibility(View.GONE);


        }

        void bind(final int position) {
            final QuestionDetailResponse.Comment item = usersList.get(position);
//            mTextView.setText(item.getText());


            ll_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (item.getLikes()) {
////                        iv_like.setImageResource(R.drawable.thumb_off);
////                        ((QuestionDetailActivity) mContext).likeDislike(2, usersList.get(position).getId());
//                    } else {

                        for (QuestionDetailResponse.Comment item1 : usersList) {
                            item1.setIschecked(false);
                        }
                        item.setCode(0);
                        item.setIschecked(true);

                        notifyDataSetChanged();

//                    }
                }
            });


            ll_dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (item.getDisLikes()) {
////                        iv_dislike.setImageResource(R.drawable.dislike_thumb_off);
////                        ((QuestionDetailActivity) mContext).likeDislike(3, usersList.get(position).getId());
//                    }
//                    else {
                        for (QuestionDetailResponse.Comment item1 : usersList) {
                            item1.setIschecked(false);
                        }
                        item.setIschecked(true);
                        item.setCode(1);

                        notifyDataSetChanged();
//                    }
                }
            });

            ll_like_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (item.getLikes()) {
////                        iv_like_yes.setImageResource(R.drawable.thumb_off);
////                        ((QuestionDetailActivity) mContext).likeDislike(2, usersList.get(position).getId());
//                    }
//                    else {
                        for (QuestionDetailResponse.Comment item1 : usersList) {
                            item1.setIschecked(false);
                        }
                        item.setCode(2);
                        item.setIschecked(true);

                        notifyDataSetChanged();
//                        iv_like_yes.setImageResource(R.drawable.thumb_on);
//                        ((QuestionDetailActivity) mContext).likeDislike(1, usersList.get(position).getId());
//                    }
                }
            });

            ll_dislike_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (item.getDisLikes()) {
////                        iv_dislike.setImageResource(R.drawable.dislike_thumb_off);
////                        ((QuestionDetailActivity) mContext).likeDislike(3, usersList.get(position).getId());
//                    }
//                    else {
                        for (QuestionDetailResponse.Comment item1 : usersList) {
                            item1.setIschecked(false);
                        }
                        item.setCode(3);
                        item.setIschecked(true);
                        notifyDataSetChanged();

//                    }
//
                }
            });

            ll_questionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (QuestionDetailResponse.Comment item1 : usersList) {
                        item1.setIschecked(false);
                    }

                    notifyDataSetChanged();
                }
            });


            if (item.getIschecked() != null) {
                if (item.getIschecked()) {

                    switch (item.getCode()) {
                        case 0:

                            gif_like.setImageResource(R.drawable.noresizelike);
                            gif_factual.setImageResource(R.drawable.noresizefactual);
                            gif_smiley.setImageResource(R.drawable.noresizefunny);

                            if (ll_likeGif.getVisibility() == View.VISIBLE) {
//                                ll_subreactionsliding.setVisibility(View.GONE);
//                                ll_yes_inner.setVisibility(View.VISIBLE);
                                ll_likeGif.setVisibility(View.INVISIBLE);
                                ll_factualGif.setVisibility(View.INVISIBLE);
                                ll_smileyGif.setVisibility(View.INVISIBLE);
                            } else {
//                                ll_yes_inner.setVisibility(View.GONE);
//                                ll_subreactionsliding.setVisibility(View.VISIBLE);
                                ll_likeGif.setVisibility(View.VISIBLE);
                                ll_factualGif.setVisibility(View.VISIBLE);
                                ll_smileyGif.setVisibility(View.VISIBLE);
                            }

                            break;
                        case 1:
                            gif_like.setImageResource(R.drawable.noresizewows);
                            gif_factual.setImageResource(R.drawable.noresizesad);
                            gif_smiley.setImageResource(R.drawable.noresizeangry);

                            if (ll_likeGif.getVisibility() == View.VISIBLE) {
//                                ll_yes_inner.setVisibility(View.VISIBLE);
//                                ll_subreactionsliding.setVisibility(View.GONE);
                                ll_likeGif.setVisibility(View.INVISIBLE);
                                ll_factualGif.setVisibility(View.INVISIBLE);
                                ll_smileyGif.setVisibility(View.INVISIBLE);
                            } else {
//                                ll_yes_inner.setVisibility(View.GONE);
//                                ll_subreactionsliding.setVisibility(View.VISIBLE);
                                ll_likeGif.setVisibility(View.VISIBLE);
                                ll_factualGif.setVisibility(View.VISIBLE);
                                ll_smileyGif.setVisibility(View.VISIBLE);
                            }


                            break;
                        case 2:

                            gif_wow.setImageResource(R.drawable.noresizelike);
                            iv_sad.setImageResource(R.drawable.noresizefactual);
                            gif_angry.setImageResource(R.drawable.noresizefunny);

                            if (ll_wowGif.getVisibility() == View.VISIBLE) {
                                ll_subreation_no.setVisibility(View.GONE);
                                ll_wowGif.setVisibility(View.INVISIBLE);
                                ll_sadGif.setVisibility(View.INVISIBLE);
                                ll_angryGif.setVisibility(View.INVISIBLE);
                            } else {
                                ll_subreation_no.setVisibility(View.VISIBLE);
                                ll_wowGif.setVisibility(View.VISIBLE);
                                ll_sadGif.setVisibility(View.VISIBLE);
                                ll_angryGif.setVisibility(View.VISIBLE);
                            }


                            break;
                        case 3:

                            gif_wow.setImageResource(R.drawable.noresizewows);
                            iv_sad.setImageResource(R.drawable.noresizesad);
                            gif_angry.setImageResource(R.drawable.noresizeangry);

                            if (ll_wowGif.getVisibility() == View.VISIBLE) {
                                ll_subreation_no.setVisibility(View.GONE);
                                ll_wowGif.setVisibility(View.INVISIBLE);
                                ll_sadGif.setVisibility(View.INVISIBLE);
                                ll_angryGif.setVisibility(View.INVISIBLE);
                            } else {
                                ll_subreation_no.setVisibility(View.VISIBLE);
                                ll_wowGif.setVisibility(View.VISIBLE);
                                ll_sadGif.setVisibility(View.VISIBLE);
                                ll_angryGif.setVisibility(View.VISIBLE);
                            }
                            break;
                    }


                } else {
                    ll_likeGif.setVisibility(View.INVISIBLE);
                    ll_factualGif.setVisibility(View.INVISIBLE);
                    ll_smileyGif.setVisibility(View.INVISIBLE);

                    ll_wowGif.setVisibility(View.INVISIBLE);
                    ll_sadGif.setVisibility(View.INVISIBLE);
                    ll_angryGif.setVisibility(View.INVISIBLE);

                    ll_subreation_no.setVisibility(View.GONE);
//                    ll_subreactionsliding.setVisibility(View.GONE);
//                    ll_yes_inner.setVisibility(View.VISIBLE);

//                    gif_like.setAnimation(null);
//                    gif_factual.setAnimation(null);
//                    gif_smiley.setAnimation(null);
                }
            }
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public OpinionAdapter(Context mContext, List<QuestionDetailResponse.Comment> usersList) {

        this.mContext = mContext;
        this.usersList = usersList;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public OpinionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        holder.bind(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        View.OnClickListener openProfile = new View.OnClickListener() {

            @Override
            public void onClick(View v) {


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

            if(usersList.get(position).getCode()==0){
                if(usersList.get(position).getLikesthought()!=null){
                    holder.tv_like.setText(usersList.get(position).getLikesthought());
                }

                if(usersList.get(position).getLikesfactual()!=null){
                    holder.tv_factual.setText(usersList.get(position).getLikesfactual());
                }

                if(usersList.get(position).getLikesfunny()!=null){
                    holder.tv_smily.setText(usersList.get(position).getLikesfunny());
                }
            }else if(usersList.get(position).getCode()==1){
                if(usersList.get(position).getDislikenomaterial()!=null){
                    holder.tv_like.setText(usersList.get(position).getDislikenomaterial());
                }

                if(usersList.get(position).getDislikefakenewscount()!=null){
                    holder.tv_factual.setText(usersList.get(position).getDislikefakenewscount());
                }

                if(usersList.get(position).getDislikebiasedcount()!=null){
                    holder.tv_smily.setText(usersList.get(position).getDislikebiasedcount());
                }
            }


            if (!String.valueOf(usersList.get(position).getCommentedUserId()).equals(Utils.getLoggedInUserId(mContext))) {
                final Animation animZoomIn = AnimationUtils.loadAnimation(mContext,R.anim.zoomin);


//                holder.ll_slide1.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View view, MotionEvent motionEvent) {
//                        Toast.makeText(mContext, "ontouchlike", Toast.LENGTH_SHORT).show();
//                        int X = (int) motionEvent.getX();
//                        int Y = (int) motionEvent.getY();
//                        int eventaction = motionEvent.getAction();
//
//                        switch (eventaction) {
//                            case MotionEvent.ACTION_DOWN:
////
//                        holder.gif_like.startAnimation(animZoomIn);
//                                break;
//
//                            case MotionEvent.ACTION_MOVE:
////                                Toast.makeText(mContext, "MOVE "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
//                                holder.gif_like.startAnimation(animZoomIn);
//                                break;
//
//                            case MotionEvent.ACTION_UP:
////                                Toast.makeText(mContext, "ACTION_UP "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
//                                holder.gif_like.startAnimation(animZoomIn);
//                                break;
//                                default:
//                                    holder.gif_like.setAnimation(null);
//                                    break;
//                        }
//                        return true;
//                    }
//                });
//
//
//
//                holder.ll_slide2.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View view, MotionEvent motionEvent) {
//                        Toast.makeText(mContext, "ontouchlike", Toast.LENGTH_SHORT).show();
//                        int X = (int) motionEvent.getX();
//                        int Y = (int) motionEvent.getY();
//                        int eventaction = motionEvent.getAction();
//
//                        switch (eventaction) {
//                            case MotionEvent.ACTION_DOWN:
////
//                                holder.gif_factual.startAnimation(animZoomIn);
//                                break;
//
//                            case MotionEvent.ACTION_MOVE:
////                                Toast.makeText(mContext, "MOVE "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
//                                holder.gif_factual.startAnimation(animZoomIn);
//                                break;
//
//                            case MotionEvent.ACTION_UP:
////                                Toast.makeText(mContext, "ACTION_UP "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
//                                holder.gif_factual.startAnimation(animZoomIn);
//                                break;
//                            default:
//                                holder.gif_factual.setAnimation(null);
//                                break;
//                        }
//                        return true;
//                    }
//                });
//
//
//
//                holder.ll_slide_3.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View view, MotionEvent motionEvent) {
//                        Toast.makeText(mContext, "ontouchlike", Toast.LENGTH_SHORT).show();
//                        int X = (int) motionEvent.getX();
//                        int Y = (int) motionEvent.getY();
//                        int eventaction = motionEvent.getAction();
//
//                        switch (eventaction) {
//                            case MotionEvent.ACTION_DOWN:
////
//                                holder.gif_smiley.startAnimation(animZoomIn);
//                                break;
//
//                            case MotionEvent.ACTION_MOVE:
////                                Toast.makeText(mContext, "MOVE "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
//                                holder.gif_smiley.startAnimation(animZoomIn);
//                                break;
//
//                            case MotionEvent.ACTION_UP:
////                                Toast.makeText(mContext, "ACTION_UP "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
//                                holder.gif_smiley.startAnimation(animZoomIn);
//                                break;
//                            default:
//                                holder.gif_smiley.setAnimation(null);
//                                break;
//                        }
//                        return true;
//                    }
//                });







                holder.ll_likeGif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (usersList.get(position).getCode() == 0) {
                            if (usersList.get(position).getLikes()) {
                                holder.iv_like.setImageResource(R.drawable.thumb_off);
                                ((QuestionDetailActivity) mContext).likeDislike(2, usersList.get(position).getId(), 1);
                            } else {
                                holder.iv_like.setImageResource(R.drawable.thumb_on);
                                ((QuestionDetailActivity) mContext).likeDislike(1, usersList.get(position).getId(), 1);
                            }

                        } else if (usersList.get(position).getCode() == 1) {
                            if (usersList.get(position).getDisLikes()) {
                                holder.iv_dislike.setImageResource(R.drawable.dislike_thumb_off);
                                ((QuestionDetailActivity) mContext).likeDislike(3, usersList.get(position).getId(), 4);
                            } else {
                                holder.iv_dislike.setImageResource(R.drawable.dislike_thumb_on);
                                ((QuestionDetailActivity) mContext).likeDislike(0, usersList.get(position).getId(), 4);
                            }

                        }

                    }
                });

                holder.ll_factualGif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (usersList.get(position).getCode() == 0) {
                            if (usersList.get(position).getLikes()) {
                                holder.iv_like.setImageResource(R.drawable.thumb_off);
                                ((QuestionDetailActivity) mContext).likeDislike(2, usersList.get(position).getId(), 2);
                            } else {
                                holder.iv_like.setImageResource(R.drawable.thumb_on);
                                ((QuestionDetailActivity) mContext).likeDislike(1, usersList.get(position).getId(), 2);
                            }

                        } else if (usersList.get(position).getCode() == 1) {
                            if (usersList.get(position).getDisLikes()) {
                                holder.iv_dislike.setImageResource(R.drawable.dislike_thumb_off);
                                ((QuestionDetailActivity) mContext).likeDislike(3, usersList.get(position).getId(), 5);
                            } else {
                                holder.iv_dislike.setImageResource(R.drawable.dislike_thumb_on);
                                ((QuestionDetailActivity) mContext).likeDislike(0, usersList.get(position).getId(), 5);
                            }

                        }
                    }
                });

                holder.ll_smileyGif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (usersList.get(position).getCode() == 0) {
                            if (usersList.get(position).getLikes()) {
                                holder.iv_like.setImageResource(R.drawable.thumb_off);
                                ((QuestionDetailActivity) mContext).likeDislike(2, usersList.get(position).getId(), 3);
                            } else {
                                holder.iv_like.setImageResource(R.drawable.thumb_on);
                                ((QuestionDetailActivity) mContext).likeDislike(1, usersList.get(position).getId(), 3);
                            }

                        } else if (usersList.get(position).getCode() == 1) {
                            if (usersList.get(position).getDisLikes()) {
                                holder.iv_dislike.setImageResource(R.drawable.dislike_thumb_off);
                                ((QuestionDetailActivity) mContext).likeDislike(3, usersList.get(position).getId(), 6);
                            } else {
                                holder.iv_dislike.setImageResource(R.drawable.dislike_thumb_on);
                                ((QuestionDetailActivity) mContext).likeDislike(0, usersList.get(position).getId(), 6);
                            }

                        }
                    }
                });


//                holder.ll_like.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (usersList.get(position).getLikes()) {
//                            holder.iv_like.setImageResource(R.drawable.thumb_off);
//                            ((QuestionDetailActivity) mContext).likeDislike(2, usersList.get(position).getId());
//                        }
//                        else {
////                            QuestionDetailActivity.view_reaction.setVisibility(View.VISIBLE);
//
////                            holder.gif_like.setImageResource(R.drawable.facebookresizee);
////                            holder.gif_factual.setImageResource(R.drawable.factualresizes);
////                            holder.gif_smiley.setImageResource(R.drawable.haharesize);
////                            holder.ll_likeGif.setVisibility(View.VISIBLE);
////                            holder.ll_factualGif.setVisibility(View.VISIBLE);
////                            holder.ll_smileyGif.setVisibility(View.VISIBLE);
////                            Toast.makeText(mContext, "Touch", Toast.LENGTH_SHORT).show();
////
////                            holder.ll_subreation_no.setVisibility(View.GONE);
////                            holder.ll_subreation.setVisibility(View.VISIBLE);
//
////                            holder.iv_like.setImageResource(R.drawable.thumb_on);
////                            ((QuestionDetailActivity) mContext).likeDislike(1, usersList.get(position).getId());
//                        }
//                    }
//                });

//                holder.ll_dislike.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (usersList.get(position).getDisLikes()) {
//                            holder.iv_dislike.setImageResource(R.drawable.dislike_thumb_off);
//                            ((QuestionDetailActivity) mContext).likeDislike(3, usersList.get(position).getId());
//                        } else {
//
////                            holder.ll_likeGif.setVisibility(View.VISIBLE);
////                                holder.ll_factualGif.setVisibility(View.VISIBLE);
////                                holder.ll_smileyGif.setVisibility(View.VISIBLE);
//////
////                            for (int i = 0; i < usersList.size(); i++) {
////                                usersList.get(i).setIschecked(false);
////                                if(i==position){
////                                    usersList.get(i).setIschecked(true);
////                                }else{
////                                    usersList.get(i).setIschecked(false);
////                                }
//
//
////                                if(usersList.get(i).getIschecked()!=null) {
////                                    if (usersList.get(i).getIschecked() == true) {
////                                        usersList.get(i).setIschecked(false);
//////                                        notifyItemChanged(i);
////                                    } else if (i==position){
////                                        usersList.get(position).setIschecked(true);
//////                                        notifyItemChanged(position);
////                                    }
////                                }
////                                else if(i==position){
////                                    usersList.get(position).setIschecked(true);
//////                                    notifyItemChanged(position);
////                                }
////                                usersList.get(i).setIschecked(false);
////                                notifyItemChanged(i);
////                                holder.ll_likeGif.setVisibility(View.GONE);
////                                holder.ll_factualGif.setVisibility(View.GONE);
////                                holder.ll_smileyGif.setVisibility(View.GONE);
////                            }
////                            QuestionDetailActivity.updatedata(usersList, position);
////                             if(usersList.get(position).getIschecked()==true) {
////                                holder.ll_likeGif.setVisibility(View.VISIBLE);
////                                holder.ll_factualGif.setVisibility(View.VISIBLE);
////                                holder.ll_smileyGif.setVisibility(View.VISIBLE);
////                                notifyItemChanged(position);
////                            }
////                            }
//
//
////                            holder.gif_like.setImageResource(R.drawable.wowresize);
////                            holder.gif_factual.setImageResource(R.drawable.factualresizes);
////                            holder.gif_smiley.setImageResource(R.drawable.angryresize);
////
////                            holder.ll_subreation_no.setVisibility(View.GONE);
////                            holder.ll_subreation.setVisibility(View.VISIBLE);
////                            holder.iv_dislike.setImageResource(R.drawable.dislike_thumb_on);
////                            ((QuestionDetailActivity) mContext).likeDislike(0, usersList.get(position).getId());
//                        }
//                    }
//                });


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

            Log.d("CheckResponse=",usersList.get(position).getLikesthought());
            Log.d("CheckResponse=",usersList.get(position).getLikesfactual());
            Log.d("CheckResponse=",usersList.get(position).getLikesfunny());
            if(usersList.get(position).getCode()==2){
                Log.d("CheckResponse=",usersList.get(position).getLikesthought());
                Log.d("CheckResponse=",usersList.get(position).getLikesfactual());
                Log.d("CheckResponse=",usersList.get(position).getLikesfunny());
                if(usersList.get(position).getLikesthought()!=null){
                    holder.tv_wow.setText(usersList.get(position).getLikesthought());
                }

                if(usersList.get(position).getLikesfactual()!=null){
                    holder.tv_sad.setText(usersList.get(position).getLikesfactual());
                }

                if(usersList.get(position).getLikesfunny()!=null){
                    holder.tv_angry.setText(usersList.get(position).getLikesfunny());
                }
            }else if(usersList.get(position).getCode()==3){
                if(usersList.get(position).getDislikenomaterial()!=null){
                    holder.tv_wow.setText(usersList.get(position).getDislikenomaterial());
                }

                if(usersList.get(position).getDislikefakenewscount()!=null){
                    holder.tv_sad.setText(usersList.get(position).getDislikefakenewscount());
                }

                if(usersList.get(position).getDislikebiasedcount()!=null){
                    holder.tv_angry.setText(usersList.get(position).getDislikebiasedcount());
                }
            }




            if (!String.valueOf(usersList.get(position).getCommentedUserId()).equals(Utils.getLoggedInUserId(mContext))) {

                holder.ll_wowGif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (usersList.get(position).getCode() == 2) {
                            if (usersList.get(position).getLikes()) {
                                holder.iv_like_yes.setImageResource(R.drawable.thumb_off);
                                ((QuestionDetailActivity) mContext).likeDislike(2, usersList.get(position).getId(), 1);
                            } else {
                                holder.iv_like_yes.setImageResource(R.drawable.thumb_on);
                                ((QuestionDetailActivity) mContext).likeDislike(1, usersList.get(position).getId(), 1);
                            }

                        } else if (usersList.get(position).getCode() == 3) {
                            if (usersList.get(position).getDisLikes()) {
                                holder.iv_dislike_no.setImageResource(R.drawable.dislike_thumb_off);
                                ((QuestionDetailActivity) mContext).likeDislike(3, usersList.get(position).getId(), 4);
                            } else {
                                holder.iv_dislike_no.setImageResource(R.drawable.dislike_thumb_on);
                                ((QuestionDetailActivity) mContext).likeDislike(0, usersList.get(position).getId(), 4);
                            }

                        }
                    }
                });

                holder.ll_sadGif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (usersList.get(position).getCode() == 2) {
                            if (usersList.get(position).getLikes()) {
                                holder.iv_like_yes.setImageResource(R.drawable.thumb_off);
                                ((QuestionDetailActivity) mContext).likeDislike(2, usersList.get(position).getId(), 2);
                            } else {
                                holder.iv_like_yes.setImageResource(R.drawable.thumb_on);
                                ((QuestionDetailActivity) mContext).likeDislike(1, usersList.get(position).getId(), 2);
                            }

                        } else if (usersList.get(position).getCode() == 3) {
                            if (usersList.get(position).getDisLikes()) {
                                holder.iv_dislike_no.setImageResource(R.drawable.dislike_thumb_off);
                                ((QuestionDetailActivity) mContext).likeDislike(3, usersList.get(position).getId(), 5);
                            } else {
                                holder.iv_dislike_no.setImageResource(R.drawable.dislike_thumb_on);
                                ((QuestionDetailActivity) mContext).likeDislike(0, usersList.get(position).getId(), 5);
                            }

                        }
                    }
                });
                holder.ll_angryGif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (usersList.get(position).getCode() == 2) {
                            if (usersList.get(position).getLikes()) {
                                holder.iv_like_yes.setImageResource(R.drawable.thumb_off);
                                ((QuestionDetailActivity) mContext).likeDislike(2, usersList.get(position).getId(), 3);
                            } else {
                                holder.iv_like_yes.setImageResource(R.drawable.thumb_on);
                                ((QuestionDetailActivity) mContext).likeDislike(1, usersList.get(position).getId(), 3);
                            }

                        } else if (usersList.get(position).getCode() == 3) {
                            if (usersList.get(position).getDisLikes()) {
                                holder.iv_dislike_no.setImageResource(R.drawable.dislike_thumb_off);
                                ((QuestionDetailActivity) mContext).likeDislike(3, usersList.get(position).getId(), 6);
                            } else {
                                holder.iv_dislike_no.setImageResource(R.drawable.dislike_thumb_on);
                                ((QuestionDetailActivity) mContext).likeDislike(0, usersList.get(position).getId(), 6);
                            }

                        }


                    }
                });

//                holder.ll_like_yes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (usersList.get(position).getLikes()) {
//                            holder.iv_like_yes.setImageResource(R.drawable.thumb_off);
//                            ((QuestionDetailActivity) mContext).likeDislike(2, usersList.get(position).getId());
//                        } else {
////                            holder.ll_subreation_no.setVisibility(View.VISIBLE);
////                            holder.ll_subreation.setVisibility(View.VISIBLE);
//                            holder.iv_like_yes.setImageResource(R.drawable.thumb_on);
//                            ((QuestionDetailActivity) mContext).likeDislike(1, usersList.get(position).getId());
//                        }
//                    }
//
//
//                });
//                holder.ll_dislike_no.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (usersList.get(position).getDisLikes()) {
//                            holder.iv_dislike.setImageResource(R.drawable.dislike_thumb_off);
//                            ((QuestionDetailActivity) mContext).likeDislike(3, usersList.get(position).getId());
//                        } else {
////                            holder.ll_subreation.setVisibility(View.GONE);
////                            holder.ll_subreation_no.setVisibility(View.VISIBLE);
//                            holder.iv_dislike.setImageResource(R.drawable.dislike_thumb_on);
//                            ((QuestionDetailActivity) mContext).likeDislike(0, usersList.get(position).getId());
//                        }
////
//                    }
//                });
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


