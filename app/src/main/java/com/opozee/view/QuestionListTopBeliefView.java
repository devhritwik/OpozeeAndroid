package com.opozee.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opozee.R;
import com.opozee.interfaces.TopBeliefs;
import com.opozee.pojo.PostedQuestionsResponse;
import com.opozee.utils.Utils;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringEscapeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.opozee.utils.Utils.DEFAULT_PROFILE_PIC_URL;

public class QuestionListTopBeliefView  extends CardView{

    View mView;
    TopBeliefs mBelief;




    @BindView(R.id.belief_view_main_linear_layout)
    LinearLayout mMainLinearLayout;

    @BindView(R.id.belief_view_opinion_tv)
    TextView mOpinionTextView;

    @BindView(R.id.belief_view_upvotes)
    TextView mUpvotesView;

    @BindView(R.id.belief_view_downvotes)
    TextView mDownvotesView;

    @BindView(R.id.beleief_view_user_image)
    CircleImageView mUserImage;

    @BindView(R.id.belief_view_user_name_tv)
    TextView mUserName;



    public  QuestionListTopBeliefView(@NonNull Context context, TopBeliefs belief) {
        this(context);
        mBelief = belief;
        setBelief(belief);

    }

    public QuestionListTopBeliefView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.belief_view, this, true);
        initView();
    }

    public QuestionListTopBeliefView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.belief_view, this, true);
        initView();

    }


    public QuestionListTopBeliefView(@NonNull Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.belief_view, this, true);
        initView();


    }


    void initView() {
        ButterKnife.bind(this);
    }

    public void setBelief(TopBeliefs belief) {
        mUserName.setText(String.format("@%s", belief.getCommentedUserName().replace(" ", "").toLowerCase()));
        mOpinionTextView.setText(Html.fromHtml(StringEscapeUtils.unescapeJava(belief.getComment()).trim()));
        mUpvotesView.setText(Utils.format(belief.getLikesCount()));
        mDownvotesView.setText(Utils.format(belief.getDislikesCount()));
        String imageURL1 = belief.getUserImage();
        String url = imageURL1 == null || imageURL1.length() == 0 || imageURL1.equals("")? DEFAULT_PROFILE_PIC_URL : imageURL1;
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(mUserImage);

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)getLayoutParams();
        int sideMargin = getContext().getResources().getDimensionPixelSize(R.dimen._30sdp);
        int upDownMargin =getContext().getResources().getDimensionPixelSize(R.dimen._3sdp);
        if (belief instanceof PostedQuestionsResponse.MostLiked) {
            layoutParams.setMargins(0, upDownMargin,sideMargin,upDownMargin);
            setBackground(getResources().getDrawable(R.drawable.top_belief_view_positive_bg));
        } else {
            layoutParams.setMargins(sideMargin, upDownMargin,0,upDownMargin);
            setBackground(getResources().getDrawable(R.drawable.top_belief_view_negative_bg));
        }
    }
}
