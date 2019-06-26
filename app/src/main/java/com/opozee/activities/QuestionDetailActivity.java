package com.opozee.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.flexbox.FlexboxLayout;
import com.opozee.OpozeeActivity;
import com.opozee.R;
import com.opozee.adapters.OpinionAdapter;
import com.opozee.application.QuestionnaireApplication;
import com.opozee.bookmark_mvp.model.BookMarkInteractorImpl;
import com.opozee.bookmark_mvp.presenter.BookMarkPresenterImpl;
import com.opozee.bookmark_mvp.view.BookMarkView;
import com.opozee.fragments.AddOpinionDialogFragment;
import com.opozee.like_dislike_mvp.model.LikeDislikeInteractorImpl;
import com.opozee.like_dislike_mvp.presenter.LikeDislikePresenterImpl;
import com.opozee.like_dislike_mvp.view.LikeDislikeView;
import com.opozee.params.BookMarkParams;
import com.opozee.params.LikeDislikeParams;
import com.opozee.params.ProfileParams;
import com.opozee.params.QuestionDetailParams;
import com.opozee.pojo.BookMarkResponse;
import com.opozee.pojo.LikeDislikeResponse;
import com.opozee.pojo.ProfileResponse;
import com.opozee.pojo.QuestionDetailResponse;
import com.opozee.profile_mvp.model.ProfileInteractorImpl;
import com.opozee.profile_mvp.presenter.ProfilePresenterImpl;
import com.opozee.profile_mvp.view.ProfileView;
import com.opozee.question_detail_mvp.model.QuestionDetailInteractorImpl;
import com.opozee.question_detail_mvp.presenter.QuestionDetailPresenterImpl;
import com.opozee.question_detail_mvp.view.QuestionDetailView;
import com.opozee.utils.AppGlobal;
import com.opozee.utils.Utils;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ro.andreidobrescu.emojilike.ActivityWithEmoji;
import ro.andreidobrescu.emojilike.EmojiLikeTouchDetector;

import static com.opozee.activities.EmptyFragmentActivity.EMPTY_FRAGMENT_ACTIVITY_TITLE_ARGUMENT;
import static com.opozee.fragments.ProfileFragment.PROFILE_FRAGMENG_ARGUEMENT_USER_ID;
import static com.opozee.fragments.TagSeachFragment.SEARCH_TAG_ARGUMENT;

public class QuestionDetailActivity extends  com.opozee.newemojilikegif.ActivityWithEmoji implements QuestionDetailView, BookMarkView, LikeDislikeView, ProfileView {

    @BindView(R.id.question_details_opinion_recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_user_name)
    public TextView tv_user_name;

    @BindView(R.id.iv_user)
    public CircleImageView iv_user;
    @BindView(R.id.iv_send)
    public ImageView iv_send;
    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_share)
    public ImageView iv_share;
    @BindView(R.id.iv_favourite)
    public ImageView iv_favourite;
    @BindView(R.id.edit_message)
    public EditText edit_message;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.tv_question)
    public TextView tv_question;
    @BindView(R.id.tv_time)
    public TextView tv_time;

    @BindView(R.id.question_details_tag_container)
    ViewGroup mTagsContainer;

    @BindView(R.id.linear_yes_no)
    public LinearLayout linear_yes_no;
    @BindView(R.id.btn_yes)
    public Button btn_yes;
    @BindView(R.id.btn_no)
    public Button btn_no;

    @BindView(R.id.send_message_layout)
    public RelativeLayout send_message_layout;
    @BindView(R.id.tv_count_likes)
    public TextView tv_count_likes;
    @BindView(R.id.tv_count_dislikes)
    public TextView tv_count_dislikes;
    @BindView(R.id.add_opinion_fab)
    public FloatingActionButton mAddOpinionFAB;
    @BindView(R.id.tv_reaction)
    public TextView tv_reaction;

    public RelativeLayout rl_questions;

    private final Object lock = new Object();

    private QuestionDetailPresenterImpl mPresenter;
    private int id;
    private BookMarkPresenterImpl mBookMarkPresenter;
    private LikeDislikePresenterImpl mLikeDislikePresenter;
    private QuestionDetailResponse response;
    private int agreeStatus;
    private ProfilePresenterImpl mProfilePresenter;
    private Integer tokens;
    private String from;
    private boolean mIsBookmarked;
    private String mQuestionText;
    private int mQuestionId;
    public static View view_reaction;
    public LinearLayout linear;
    public Toolbar toolbar;
    public static OpinionAdapter ticketNumber;
    public List<QuestionDetailResponse.Comment> commentList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        rl_questions = findViewById(R.id.rl_questions);
        view_reaction = findViewById(R.id.view_reaction);
        linear = findViewById(R.id.linear);
        toolbar = findViewById(R.id.toolbar);
        ButterKnife.bind(this);
        seekBar.setEnabled(false);
        id = getIntent().getIntExtra("id", 0);
        from = getIntent().getStringExtra("from");
        Log.e("FROM>>> ", "" + from);
        //set presenter to attach with model and view so that they can interact with each other for data transfer
        setPresenter();
        //call API
        getDetail();
        //set presenter to attach view with interactor to get the data from API
        setProfile();
        //call API to populate data
        getProfile();

        //setupYesNoButtonMode();
        setupAddingOpinionInterface();

//        linear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(commentList.size()>0) {
//                    for (int i = 0; i < commentList.size(); i++) {
//                        commentList.get(i).setIschecked(false);
//                        if(ticketNumber!=null) {
//                            ticketNumber.notifyDataSetChanged();
//                        }
//                    }
//                }
////                for (QuestionDetailResponse.Comment item1 : usersList) {
////                    item1.setIschecked(false);
////                }
//            }
//        });
//        toolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(commentList.size()>0) {
//                    for (int i = 0; i < commentList.size(); i++) {
//                        commentList.get(i).setIschecked(false);
//                        if(ticketNumber!=null) {
//                            ticketNumber.notifyDataSetChanged();
//                        }
//                    }
//                }
//
//            }
//        });

//        rl_questions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "test visibility", Toast.LENGTH_SHORT).show();
//                if(OpinionAdapter.ViewHolder.ll_subreation.getVisibility()==View.VISIBLE){
//                    OpinionAdapter.ViewHolder.ll_subreation.setVisibility(View.GONE);
//                }
//            }
//        });
//        view_reaction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(QuestionDetailActivity.this, "Check testing", Toast.LENGTH_SHORT).show();
//                if (OpinionAdapter.ViewHolder.ll_subreation.getVisibility() == View.VISIBLE) {
//                    OpinionAdapter.ViewHolder.ll_subreation.setVisibility(View.GONE);
//                    view_reaction.setVisibility(View.GONE);
//                }
//            }
//        });


    }

    private void getProfile() {
        if (Utils.isNetworkAvail(this)) {
            mProfilePresenter.profile(getProfileParams());
        } else {
            Utils.showCustomToast(this, getString(R.string.internet_alert));
        }

    }

    private ProfileParams getProfileParams() {
        ProfileParams params = new ProfileParams();
        params.setType(AppGlobal.TYPE_GET_PROFILE);
        params.setUser_id(Utils.getLoggedInUserId(this));
        params.setViewuserid(Utils.getLoggedInUserId(this));
        return params;
    }

    private void setProfile() {
        mProfilePresenter = new ProfilePresenterImpl();
        mProfilePresenter.attachView(this, new ProfileInteractorImpl());
    }

    @Override
    public void onFirstNameError(String error) {

    }

    @Override
    public void onLastNameError(String error) {
    }

    @Override
    public void onSuccess(ProfileResponse response) {
        if (response.getResponse().getType() == AppGlobal.TYPE_GET_PROFILE) {
            //after getting data update the UI
            tokens = response.getResponse().getUserProfile().getBalanceToken();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        activity.toolBar.getMenu().clear();
    }

    @OnClick(R.id.iv_back)
    void onBackClick() {
        if (from == null)
            finish();
        else if (from.equals("notification")) {
            Intent i = new Intent(QuestionDetailActivity.this, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (from == null)
            super.onBackPressed();
        else if (from.equals("notification")) {
            Intent i = new Intent(QuestionDetailActivity.this, HomeActivity.class);
            i.putExtra("from", "DetailActivity");
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
    }

    @OnClick(R.id.iv_share)
    void onShareClick() {
        final String appPackageName = getPackageName();

        Map<String, String> map = new HashMap<>();
        QuestionnaireApplication.getMixpanelApi().track("share clicked", new JSONObject(map));
        Intent sendIntent = new Intent();
        String url = "https://opozee.com/qid/" + Integer.toString(mQuestionId);
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey I'd like to know your opinion on this question. " + url);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

    }

    @OnClick(R.id.iv_favourite)
    void onFavouriteClick() {
        synchronized (lock) {
            if (mIsBookmarked) {
                bookMarkQuestion(false);
                mIsBookmarked = false;
                iv_favourite.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_empty));
            } else {
                bookMarkQuestion(true);
                mIsBookmarked = true;
                iv_favourite.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_filled));
            }
        }
    }

    private void getDetail() {
        QuestionDetailParams params = new QuestionDetailParams();
        params.setQuestionId(String.valueOf(id));
        params.setUserid(Utils.getLoggedInUserId(QuestionDetailActivity.this));
        if (Utils.isNetworkAvail(QuestionDetailActivity.this)) {
            mPresenter.getQuestionDetail(params);
        } else {
            Utils.showCustomToast(QuestionDetailActivity.this, getString(R.string.internet_alert));
        }
    }

    private void getDetailWithoutShowingLoading() {
        QuestionDetailParams params = new QuestionDetailParams();
        params.setQuestionId(String.valueOf(id));
        params.setUserid(Utils.getLoggedInUserId(QuestionDetailActivity.this));
        if (Utils.isNetworkAvail(QuestionDetailActivity.this)) {
            mPresenter.getQuestionDetailWithoutShowingLoading(params);
        } else {
            Utils.showCustomToast(QuestionDetailActivity.this, getString(R.string.internet_alert));
        }
    }
//
//    private void postOpinion(String opinion)
//    {
//
//        if(opinion.trim().length() != 0 && opinion.trim() != null)
//        {
//            send_message_layout.setVisibility(View.GONE);
//            //the below line has been added for smileys or emojis
//            String toServerUnicodeEncoded = StringEscapeUtils.escapeJava(opinion.trim());
//            OpinionParams params = new OpinionParams();
//            params.setId(String.valueOf(0));
//            params.setQuestion_id(String.valueOf(id));
//            params.setCommentedUserId(Utils.getLoggedInUserId(QuestionDetailActivity.this));
//            params.setComment(toServerUnicodeEncoded);
//            params.setOpinionAgreeStatus(agreeStatus);
//
//            if (Utils.isNetworkAvail(QuestionDetailActivity.this)) {
//                mOpinionPresenter.addOpinion(params);
//            } else {
//                Utils.showCustomToast(QuestionDetailActivity.this, getString(R.string.internet_alert));
//            }
//        }
//    }

    private void setPresenter() {
        //opinion presenter
//        mOpinionPresenter = new AddOpinionPresenterImpl();
//        mOpinionPresenter.attachView(this, new AddOpinionInteractorImpl());

        //question detail presenter
        mPresenter = new QuestionDetailPresenterImpl();
        mPresenter.attachView(this, new QuestionDetailInteractorImpl());

        //bookmark presenter
        mBookMarkPresenter = new BookMarkPresenterImpl();
        mBookMarkPresenter.attachView(this, new BookMarkInteractorImpl());

        //likeDislikepresenter
        mLikeDislikePresenter = new LikeDislikePresenterImpl();
        mLikeDislikePresenter.attachView(this, new LikeDislikeInteractorImpl());

    }

    //like dislike opinion
//    LikeDislikeOpinion
    public void likeDislike(int commentStatus, int opinionId,int reactiontype,List<QuestionDetailResponse.Comment> commentList1) {
//        if(commentList.size()>0) {
//            for (int i = 0; i < commentList.size(); i++) {
//                commentList.get(i).setIschecked(false);
//                if(ticketNumber!=null) {
//                    ticketNumber.notifyDataSetChanged();
//
//                }
//            }
//        }
//        ticketNumber.notifyItemChanged(0);
//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if(commentList1.size()>0&&ticketNumber!=null&&recyclerView!=null) {
////                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(QuestionDetailActivity.this, LinearLayoutManager.VERTICAL, false);
////                        ticketNumber = new OpinionAdapter(QuestionDetailActivity.this, commentList1);
////                        recyclerView.setLayoutManager(linearLayoutManager);
////                        recyclerView.setAdapter(ticketNumber);
//
//                    }
//                }
//            }, 500);
//        ticketNumber.notifyDataSetChanged();

        if (Utils.isNetworkAvail(QuestionDetailActivity.this)) {
            Log.d("Data_Log",""+commentStatus);
            Log.d("Data_Log",""+opinionId);
            Log.d("Data_Log",""+reactiontype);

            mLikeDislikePresenter.dislike(getParams(commentStatus, opinionId,reactiontype));
        } else {
            Utils.showCustomToast(QuestionDetailActivity.this, getString(R.string.internet_alert));
        }
    }

    //like dislike opinion
    private void bookMarkQuestion(boolean isBooked) {
        if (Utils.isNetworkAvail(QuestionDetailActivity.this)) {
            Map<String, String> map = new HashMap<>();
            map.put("Bookmark change", Boolean.toString(!isBooked) + " to " + Boolean.toString(isBooked));
            map.put("Question", mQuestionText);
            QuestionnaireApplication.getMixpanelApi().track("Bookmark clicked", new JSONObject(map));
            mBookMarkPresenter.bookMark(getBookMarkParams(isBooked));
        } else {
            Utils.showCustomToast(QuestionDetailActivity.this, getString(R.string.internet_alert));
        }
    }

    private BookMarkParams getBookMarkParams(boolean isBooked) {
        BookMarkParams params = new BookMarkParams();
        params.setBookmark(isBooked);
        params.setId(String.valueOf(0));
        params.setQuestion_id(String.valueOf(id));
        params.setUserId(Utils.getLoggedInUserId(QuestionDetailActivity.this));
        return params;
    }

    //get likedislike params
    private LikeDislikeParams getParams(int commentStatus, int opinionId,int reactiontype) {
        LikeDislikeParams params = new LikeDislikeParams();
        params.setId(0);
        params.setQuestId(id);
        params.setCommentedId(opinionId);
        params.setCommentedUserId(Integer.parseInt(Utils.getLoggedInUserId(QuestionDetailActivity.this)));
        params.setCommentStatus(commentStatus);
        params.setReactiontype(reactiontype);
        return params;
    }

    public void setAdapter(List<QuestionDetailResponse.Comment> homeList) {
        commentList=homeList;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(QuestionDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        ticketNumber = new OpinionAdapter(QuestionDetailActivity.this, homeList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(ticketNumber);
    }

//    @OnClick(R.id.iv_send)
//    void onSendClick()
//    {
//
//        hideKeyBoard();
//        if(tokens == 0 || tokens < 0)
//        {
//            tokensAlert();
//        }
//        else {
//
//            postOpinion(edit_message.getText().toString());
//        }
//
//    }

    @Override
    public void showProgress() {
        if (Utils.mProgressDialog == null)
            Utils.showProgress(QuestionDetailActivity.this);
    }

    @Override
    public void hideProgress() {
        if (Utils.mProgressDialog != null)
            Utils.dismissProgress();
    }

    @Override
    public void onSuccess(LikeDislikeResponse response) {
        //dont Update the data (If uppdate data uncomment following line)
//        getDetailWithoutShowingLoading();
      getDetail();
        //Do nothing
    }

    @Override
    public void onSuccess(BookMarkResponse response) {

    }

    @Override
    public void onSuccess(QuestionDetailResponse response) {
        setUI(response);

        setAdapter(response.getResponse().getAllOpinion().getComments());
    }

    public void slideUpVisibility(Context mContext, final View view, int visibilityStatus) {
        view.setVisibility(visibilityStatus);
        Animation animate = AnimationUtils.loadAnimation(mContext, R.anim.slide_alert_in);
        animate.setDuration(1000);
        animate.setFillAfter(true);
        view.startAnimation(animate);

    }

    public void slideDownVisibility(Context mContext, final View view, final int visibilityStatus) {
        Animation animate = AnimationUtils.loadAnimation(mContext, R.anim.slide_alert_out);
        animate.setDuration(1000);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(visibilityStatus);
                if (view instanceof LinearLayout) {
                    slideUpVisibility(QuestionDetailActivity.this, send_message_layout, View.VISIBLE);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animate);

    }

    private void setUI(final QuestionDetailResponse response) {
        if (response.getResponse().getAllOpinion().getPostQuestionDetail() != null) {
            this.response = response;
            mQuestionId = response.getResponse().getAllOpinion().getPostQuestionDetail().getId();
            String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(response.getResponse().getAllOpinion().getPostQuestionDetail().getQuestion());
            mQuestionText = fromServerUnicodeDecoded;
            tv_question.setText(Html.fromHtml(fromServerUnicodeDecoded.trim()));
            tv_user_name.setText("@" + response.getResponse().getAllOpinion().getPostQuestionDetail().getOwnerUserName().replace(" ", "").toLowerCase());
            //tv_name.setText(Utils.capitalize(response.getResponse().getAllOpinion().getPostQuestionDetail().getOwnerUserName()));
            final JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.putOpt("ProfileOpened", response.getResponse().getAllOpinion().getPostQuestionDetail().getOwnerUserName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            tv_user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QuestionnaireApplication.getMixpanelApi().track("Profile Opened", jsonObject);
                    int userId = response.getResponse().getAllOpinion().getPostQuestionDetail().getOwnerUserID();
                    Intent intent = new Intent(QuestionDetailActivity.this, ProfileActivity.class);
                    intent.putExtra(PROFILE_FRAGMENG_ARGUEMENT_USER_ID, userId);
                    startActivity(intent);
                }
            });
            iv_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QuestionnaireApplication.getMixpanelApi().track("Profile Opened", jsonObject);
                    int userId = response.getResponse().getAllOpinion().getPostQuestionDetail().getOwnerUserID();
                    Intent intent = new Intent(QuestionDetailActivity.this, ProfileActivity.class);
                    intent.putExtra(PROFILE_FRAGMENG_ARGUEMENT_USER_ID, userId);
                    startActivity(intent);
                }
            });

            mTagsContainer.removeAllViews();
            String tagString = response.getResponse().getAllOpinion().getPostQuestionDetail().getHashTags();
            Log.d("HASHTAG=",tagString);
//            if(tagString.contains("#")) {
//                tagString = tagString.replace(",", "");
                String[] tags = tagString.split("#");
                for (String tag : tags) {
                    String[] tagdata=tag.split(",");
                    for (int k=0;k<tagdata.length;k++){
                        View currTagView = generateTagView(tagdata[k]);
                    if (currTagView != null) {
                        mTagsContainer.addView(currTagView);
                    }
                }
//            }else{
//                String[] tags = tagString.split(",");
//                for (String tag : tags) {
//
//                    View currTagView = generateTagView(tag);
//                    if (currTagView != null)
//                        mTagsContainer.addView(currTagView);
//                }
            }

            if (mIsBookmarked) {
                iv_favourite.setImageResource(R.drawable.bookmark_icon_filled);
            } else {
                iv_favourite.setImageResource(R.drawable.bookmark_icon_empty);
            }
            send_message_layout.setVisibility(View.GONE);
            linear_yes_no.setVisibility(View.VISIBLE);


            //below all the four lines are used to get the date in MMM dd yy at HH:mm format
            Log.d("TimeFormat_Log", response.getResponse().getAllOpinion().getPostQuestionDetail().getCreationDate());
            String timeArr[] = response.getResponse().getAllOpinion().getPostQuestionDetail().getCreationDate().replace("T", " ").split("/.");
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
                tv_time.setText(timeago);
            }
            if (response.getResponse().getAllOpinion().getPostQuestionDetail().getReactiontime() != null) {
                tv_reaction.setText("Reaction : " + response.getResponse().getAllOpinion().getPostQuestionDetail().getReactiontime());
            }
//            if(response.getResponse().getAllOpinion().getPostQuestionDetail().get)
            if (response.getResponse().getAllOpinion().getPostQuestionDetail().getUserImage() != null) {
                if (response.getResponse().getAllOpinion().getPostQuestionDetail().getUserImage().trim().length() > 0) {
                    Picasso.get().load(response.getResponse().getAllOpinion().getPostQuestionDetail().getUserImage()).into(iv_user);
                }
            }


            int yesCount = response.getResponse().getAllOpinion().getPostQuestionDetail().getYesCount() != null ? response.getResponse().getAllOpinion().getPostQuestionDetail().getYesCount() : 0;
            int noCount = response.getResponse().getAllOpinion().getPostQuestionDetail().getNoCount() != null ? response.getResponse().getAllOpinion().getPostQuestionDetail().getNoCount() : 0;


            initDataToSeekbar(seekBar, tv_count_likes, tv_count_dislikes, yesCount, noCount);
        }

    }

    private void initDataToSeekbar(SeekBar seekBar, TextView tv_count_likes, TextView tv_count_dislikes, int likes, int dislikes) {



        try {
            int scoreYes = 0;
            int scoreNo = 0;
            int _score = 0;
            for (int k = 0; k < response.getResponse().getAllOpinion().getComments().size(); k++) {
                _score = response.getResponse().getAllOpinion().getComments().get(k).getLikesCount() - response.getResponse().getAllOpinion().getComments().get(k).getDislikesCount();
                Log.d("Percentage=","_score="+_score);
                if (response.getResponse().getAllOpinion().getComments().get(k).getIsAgree() == true) {
                    scoreYes = scoreYes + (_score > 0 ? _score : 0);
                    Log.d("Percentage=","scoreYes="+scoreYes);
                } else {
                    scoreNo = scoreNo + (_score > 0 ? _score : 0);
                    Log.d("Percentage=","scoreNo="+scoreNo);
                }

            }
            Log.d("Percentage=","scoreNo=1----"+scoreNo);
            Log.d("Percentage=","scoreYes=1----"+scoreYes);
            int finalscore=scoreYes+scoreNo;
            double finaldata=(double) scoreYes/finalscore;
            Log.d("Percentage=","finalscore=1----"+finalscore);
            Log.d("Percentage=","finaldata=1----"+finaldata);
            double finalpercentage = (double) finaldata *100;
            int percentage=(int)finalpercentage;
            Log.d("Percentage=",""+percentage+"-------------------------------");
            if (percentage <= 0) {
                percentage = 0;
                scoreNo = 0;
                scoreYes = 0;
                seekBar.setProgressDrawable(QuestionDetailActivity.this.getResources().getDrawable(R.drawable.progress_bg));
                seekBar.setSecondaryProgress(0);
                seekBar.invalidate();
            } else {
                seekBar.setProgressDrawable(QuestionDetailActivity.this.getResources().getDrawable(R.drawable.progress_line));
//            seekBar.setProgress(dislikes_percentage);
                seekBar.setSecondaryProgress( percentage);
                seekBar.invalidate();
            }

            tv_count_dislikes.setText("No " + percentage + "%");
            tv_count_likes.setText(percentage + "% Yes");

        } catch (Exception e) {
            Log.d("Percentage=",e.toString());
        }





        //        int total = likes + dislikes == 0 ? 100 : likes + dislikes;
//
//        // dislike span
//        int dislikes_percentage = ((dislikes * 100 / total));
//
//        Log.e("Dislikes >>", "  " + dislikes + "    " + dislikes_percentage);
//        // like span
//        int likes_percentage = (likes * 100 / total);
//        Log.e("likes >>", "  " + likes + "    " + likes_percentage);
//
//        if (dislikes == 0 && likes == 0) {
//            seekBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bg));
//            seekBar.setSecondaryProgress(0);
//            seekBar.invalidate();
//        } else {
//            seekBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_line));
////            seekBar.setProgress(dislikes_percentage);
//            seekBar.setSecondaryProgress(likes_percentage);
//            seekBar.invalidate();
//        }
//
//        tv_count_dislikes.setText("No " + dislikes_percentage + "%");
//        tv_count_likes.setText(likes_percentage + "% Yes");
//
//
//    }
//
//    private void tokensAlert() {
//        new AlertDialog.Builder(QuestionDetailActivity.this)
//                .setMessage("You have 0 tokens in your account. Please email us to refill the account to post opinion.")
//                .setPositiveButton(R.string.email, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                        sendEmail();
//                    }
//                })
//                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                })
//                .show();
    }

    private void sendEmail() {
        Intent i = QuestionDetailActivity.this.getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
        if (i != null) {


            String to = "info@opozee.com";
            String subject = "Regarding Insufficient token balance";
            String body = "I have no tokens balance in my account. Please refill my account.";
            String mailTo = "mailto:" + to +
                    "?&subject=" + Uri.encode(subject) +
                    "&body=" + Uri.encode(body);
            Intent emailIntent = new Intent(Intent.ACTION_VIEW);
            emailIntent.setData(Uri.parse(mailTo));
            startActivity(emailIntent);
        } else {
            Toast.makeText(QuestionDetailActivity.this, "Gmail App Is Not Installed In Your Phone", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(QuestionDetailActivity.this, error);
    }

    public void hideKeyBoard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void setupYesNoMode() {
        linear_yes_no.setVisibility(View.VISIBLE);

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                agreeStatus = 0;
                linear_yes_no.setVisibility(View.GONE);
                send_message_layout.setVisibility(View.VISIBLE);

            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agreeStatus = 1;
                linear_yes_no.setVisibility(View.GONE);
                send_message_layout.setVisibility(View.VISIBLE);

            }
        });

    }


    private void setupAddingOpinionInterface() {
        linear_yes_no.setVisibility(View.GONE);
        mAddOpinionFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new AddOpinionDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "AddOpinionDialogFragment");
            }
        });

    }

    public int getId() {
        return id;
    }

    public void refresh() {
        getDetail();
    }

    @Nullable
    public TextView generateTagView(final String tag) {
        if (tag.isEmpty()) return null;
        TextView tagView = new TextView(this);
        tagView.setText("#" + tag);
        int eightPadding = getResources().getDimensionPixelOffset(R.dimen._8sdp);
        int padding = getResources().getDimensionPixelOffset(R.dimen.tag_view_padding);
        tagView.setGravity(Gravity.CENTER);
        tagView.setPadding(eightPadding, padding, eightPadding, padding);
        tagView.setBackground(getResources().getDrawable(R.drawable.tag_view_bg));
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = padding;
        params.leftMargin = padding;
        params.rightMargin = padding;
        tagView.setLayoutParams(params);

        tagView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("Tag", tag);
                QuestionnaireApplication.getMixpanelApi().track("Tag Clicked", new JSONObject(map));
                Intent intent = new Intent(QuestionDetailActivity.this, EmptyFragmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(EMPTY_FRAGMENT_ACTIVITY_TITLE_ARGUMENT, "#" + tag);
                bundle.putString(SEARCH_TAG_ARGUMENT, tag);
                intent.putExtras(bundle);
                QuestionDetailActivity.this.startActivity(intent);
            }
        });


        return tagView;
    }

    public static void updatedata(List<QuestionDetailResponse.Comment> usersList, int position) {
        usersList.get(position).setIschecked(true);
        ticketNumber.notifyItemChanged(position);

    }

}