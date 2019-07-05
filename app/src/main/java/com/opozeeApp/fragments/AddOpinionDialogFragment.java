package com.opozeeApp.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.opozeeApp.R;
import com.opozeeApp.activities.QuestionDetailActivity;
import com.opozeeApp.add_opinion_mvp.model.AddOpinionInteractorImpl;
import com.opozeeApp.add_opinion_mvp.presenter.AddOpinionPresenter;
import com.opozeeApp.add_opinion_mvp.presenter.AddOpinionPresenterImpl;
import com.opozeeApp.add_opinion_mvp.view.AddOpinionView;
import com.opozeeApp.params.OpinionParams;
import com.opozeeApp.pojo.OpinionResponse;
import com.opozeeApp.utils.Utils;

import org.apache.commons.lang3.StringEscapeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class AddOpinionDialogFragment extends DialogFragment implements AddOpinionView {

    @BindView(R.id.add_opinion_edit_text)
    EditText mOpinionEditText;

    @BindView(R.id.add_opinion_submit_button)
    Button mSubmitButton;

    @BindView(R.id.add_opinion_disagree_button)
    Button mDisagreeButton;

    @BindView(R.id.add_opinion_agree_button)
    Button mAgreeButton;

    @BindView(R.id.warning_select_a_side)
    TextView mWarningTextView;

    @BindView(R.id.tv_countLength)
    TextView tv_countLength;

    private final String TAG = AddOpinionDialogFragment.class.getSimpleName();
    private State mCurrentState;
    private AddOpinionPresenter mAddOpinionPresenter;

    enum State {
        Unselected,
        Agree,  // agreeStats = 1
        Disagree   // agreeStats = 0
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d(TAG, "onCreate()");
    }


    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Log.d(TAG, "onCreateView()");
        View v = inflater.inflate(R.layout.add_opinion_dialog_fragment, container, false);
        // retrieve display dimensions
        ButterKnife.bind(this, v);
        setState(State.Unselected);
        mAddOpinionPresenter = new AddOpinionPresenterImpl();
        mAddOpinionPresenter.attachView(this, new AddOpinionInteractorImpl());


        mOpinionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tv_countLength.setText(String.valueOf(mOpinionEditText.getText().toString().length()));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Do all the stuff to initialize your custom view

        return v;
    }

    @OnClick(R.id.add_opinion_dialog_cancel_button)
    void onCancelClicked() {
        dismiss();
    }


    @OnClick(R.id.add_opinion_agree_button)
    public void onAgreeClicked() {
//        Log.d(TAG, "onAgreeClicked()");
        mWarningTextView.setVisibility(View.GONE);
        setState(State.Agree);


    }

    @OnClick(R.id.add_opinion_disagree_button)
    public void onDisagreeClicked() {
//        Log.d(TAG, "onDisagreeClicked()");
        mWarningTextView.setVisibility(View.GONE);
        setState(State.Disagree);
    }

    @OnClick(R.id.add_opinion_submit_button)
    public void onSubmitButtonClicked() {
        String opinion = mOpinionEditText.getText().toString();
//        Log.d(TAG, "onSubmitButtonClicked, opinion: " + opinion);

        if (mCurrentState == State.Unselected) {
            mWarningTextView.setVisibility(View.VISIBLE);
            return;
        }

        if (opinion.trim().length() != 0 && opinion.trim() != null) {
            //the below line has been added for smileys or emojis
            String toServerUnicodeEncoded = StringEscapeUtils.escapeJava(opinion.trim());
            toServerUnicodeEncoded = toServerUnicodeEncoded.replace("\\n", "<br/>");

            //SpannableString contentText = new SpannableString(toServerUnicodeEncoded);
            //toServerUnicodeEncoded = Html.toHtml(contentText);

            int agreeStatus = mCurrentState == State.Agree ? 1 : 0;
            OpinionParams params = new OpinionParams();
            params.setId(String.valueOf(0));
            params.setQuestion_id(String.valueOf(((QuestionDetailActivity) getActivity()).getId()));
            params.setCommentedUserId(Utils.getLoggedInUserId(getActivity()));
            params.setComment(toServerUnicodeEncoded);
            params.setOpinionAgreeStatus(agreeStatus);


            if (Utils.isNetworkAvail(getActivity())) {
                mAddOpinionPresenter.addOpinion(params);
            } else {
                Utils.showCustomToast(getActivity(), getString(R.string.internet_alert));
            }
        }


    }


    public void setState(State state) {
        mCurrentState = state;
        switch (state) {
            case Unselected:
                mAgreeButton.setBackground(getResources().getDrawable(R.drawable.add_opinion_button_unselected_bg));
                mDisagreeButton.setBackground(getResources().getDrawable(R.drawable.add_opinion_button_unselected_bg));
                mDisagreeButton.setTextColor(getResources().getColor(R.color.light_grey));
                mAgreeButton.setTextColor(getResources().getColor(R.color.light_grey));
                break;

            case Agree:
                mAgreeButton.setBackground(getResources().getDrawable(R.drawable.add_opinion_button_selected_bg));
                mDisagreeButton.setBackground(getResources().getDrawable(R.drawable.add_opinion_button_unselected_bg));
                mDisagreeButton.setTextColor(getResources().getColor(R.color.light_grey));
                mAgreeButton.setTextColor(getResources().getColor(R.color.white_color));

                break;

            case Disagree:
                mAgreeButton.setBackground(getResources().getDrawable(R.drawable.add_opinion_button_unselected_bg));
                mDisagreeButton.setBackground(getResources().getDrawable(R.drawable.add_opinion_button_selected_bg));
                mDisagreeButton.setTextColor(getResources().getColor(R.color.white_color));
                mAgreeButton.setTextColor(getResources().getColor(R.color.light_grey));
                break;
        }


    }

    @Override
    public void showProgress() {
        if (Utils.mProgressDialog == null)
            Utils.showProgress(getActivity());
    }

    @Override
    public void hideProgress() {
        if (Utils.mProgressDialog != null)
            Utils.dismissProgress();
    }

    @Override
    public void onSuccess(OpinionResponse response) {
        Utils.showCustomToast(getActivity(), "Successfully posted belief");
        QuestionDetailActivity activity = (QuestionDetailActivity) getActivity();
        if (activity != null)
            activity.refresh();
        dismiss();


    }

    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(getActivity(), error);
        dismiss();


    }

    @OnTextChanged(R.id.add_opinion_edit_text)
    public void onAddOpinionEditTextChanged() {
        mWarningTextView.setVisibility(View.GONE);
    }


}
