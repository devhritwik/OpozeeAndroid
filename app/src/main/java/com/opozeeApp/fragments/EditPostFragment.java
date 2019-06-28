package com.opozeeApp.fragments;


        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.opozeeApp.R;
        import com.opozeeApp.activities.HomeActivity;
        import com.opozeeApp.params.PostQuestionParams;
        import com.opozeeApp.params.QuestionDetailParams;
        import com.opozeeApp.pojo.PostQuestionResponse;
        import com.opozeeApp.pojo.QuestionDetailResponse;
        import com.opozeeApp.post_question_mvp.model.PostQuestionInteractorImpl;
        import com.opozeeApp.post_question_mvp.presenter.PostQuestionPresenterImpl;
        import com.opozeeApp.post_question_mvp.view.PostQuestionView;
        import com.opozeeApp.question_detail_mvp.model.QuestionDetailInteractorImpl;
        import com.opozeeApp.question_detail_mvp.presenter.QuestionDetailPresenterImpl;
        import com.opozeeApp.question_detail_mvp.view.QuestionDetailView;
        import com.opozeeApp.utils.AppGlobal;
        import com.opozeeApp.utils.Utils;

        import org.apache.commons.lang3.StringEscapeUtils;

        import butterknife.BindView;
        import butterknife.ButterKnife;
        import butterknife.OnClick;

public class EditPostFragment extends Fragment implements PostQuestionView, QuestionDetailView {

    @BindView(R.id.btn_post)
    Button btn_post;
    @BindView(R.id.edit_hash_tags)
    EditText edit_hash_tags;
    @BindView(R.id.edit_question)
    EditText edit_questions;
    @BindView(R.id.tv_token_staking)
    TextView tv_token_staking;
    @BindView(R.id.tv_token_balance)
    TextView tv_token_balance;
    private PostQuestionPresenterImpl mPresenter;
    private int id;
    private QuestionDetailPresenterImpl mQuesPresenter;

    public EditPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_post, container, false);

        id = getArguments().getInt("id");

        ButterKnife.bind(this, rootView);
        //set Presenter to attach the view with presenter
        setPresenter();
        
        //get question detail
        getDetail();
        return rootView;
    }

    private void getDetail() {
        QuestionDetailParams params = new QuestionDetailParams();
        params.setQuestionId(String.valueOf(id));
        params.setUserid(Utils.getLoggedInUserId(getActivity()));

        if (Utils.isNetworkAvail(getActivity())) {
            mQuesPresenter.getQuestionDetail(params);
        } else {
            Utils.showCustomToast(getActivity(), getString(R.string.internet_alert));
        }
    }

    private void setPresenter(){
        //post question adapter
        mPresenter = new PostQuestionPresenterImpl();
        mPresenter.attachView(this, new PostQuestionInteractorImpl());


        //question detail presenter
        mQuesPresenter = new QuestionDetailPresenterImpl();
        mQuesPresenter.attachView(this, new QuestionDetailInteractorImpl());
    }


    @OnClick(R.id.btn_post)
    void onPostClick()
    {
        if (Utils.isNetworkAvail(getActivity())) {
            mPresenter.postQuestion(getParams());
        } else {
            Utils.showCustomToast(getActivity(), getString(R.string.internet_alert));
        }
    }

    private PostQuestionParams getParams() {
        PostQuestionParams params = new PostQuestionParams();
//        id = 0 for posting & id != 0 for editing
        params.setId(String.valueOf(id));
        params.setHashTags(edit_hash_tags.getText().toString());
        params.setQuestion(StringEscapeUtils.escapeJava(edit_questions.getText().toString()));
        params.setUserId(Utils.getLoggedInUserId(getActivity()));

        return params;
    }

    @Override
    public void showProgress() {
        if(Utils.mProgressDialog == null)
            Utils.showProgress(getActivity());
    }

    @Override
    public void hideProgress() {
        if(Utils.mProgressDialog != null)
            Utils.dismissProgress();
    }

    @Override
    public void onSuccess(QuestionDetailResponse response) {
        setUI(response);
    }

    private void setUI(QuestionDetailResponse response) {
        edit_hash_tags.setText(response.getResponse().getAllOpinion().getPostQuestionDetail().getHashTags());
        edit_questions.setText(response.getResponse().getAllOpinion().getPostQuestionDetail().getQuestion());
    }

    @Override
    public void onSuccess(PostQuestionResponse response) {
        Utils.showCustomToast(getActivity(), "Question Updated successfully");
        ((HomeActivity)getActivity()).getLastFragment(AppGlobal.HOMEFRAG);
    }

    @Override
    public void onQuestionError(String error) {
        edit_questions.requestFocus();
        edit_questions.setError(error);
    }

    @Override
    public void onHashTagError(String error) {
        edit_hash_tags.requestFocus();
        edit_hash_tags.setError(error);
    }

    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(getActivity(),error);
    }
}
