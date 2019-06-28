package com.opozeeApp.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.opozeeApp.R;
import com.opozeeApp.WebRequest;
import com.opozeeApp.adapters.GetTagsAdapter;
import com.opozeeApp.adapters.HomeQuestionsAdapter;
import com.opozeeApp.model.GetTagsModel;
import com.opozeeApp.pojo.PostedQuestionsResponse;
import com.opozeeApp.pojo.getallhashtags.GetAllTags;
import com.opozeeApp.searchfragments.AllSearchFragment;
import com.opozeeApp.searchfragments.CountSearchFragment;
import com.opozeeApp.searchfragments.GotSearchFragment;
import com.opozeeApp.searchfragments.QaSearchFragment;
import com.opozeeApp.searchfragments.QuestionFragment;
import com.opozeeApp.searchfragments.TestSearchFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeNewFragment extends Fragment {
    private int pageIndex = 1;
    private int pageSize = 10;
    private List<PostedQuestionsResponse.PostQuestionDetail> questionsList = new ArrayList<>();
    private HomeQuestionsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private boolean isRefreshed = false;
    public static WebRequest webRequest;
    WebRequest.APIInterface apiInterface;
    retrofit2.Call<GetAllTags> getAllTagsCall;
    GetAllTags getAllTags;
    public RecyclerView rv_tags;
    public GetTagsAdapter getTagsAdapter;
    public static ArrayList<GetTagsModel> getTagsModelist=new ArrayList<>();
    public TabLayout tab_layout;
    ViewPager viewPager;
    private FragmentActivity myContext;
    public static String tabName="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_new, container, false);
        tab_layout=rootView.findViewById(R.id.tab_layout);
        viewPager=rootView.findViewById(R.id.viewPager);

        webRequest = WebRequest.getSingleton(getActivity());
        Log.d("Home_Log","oncreate");
//        rv_tags=rootView.findViewById(R.id.rv_tags);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        rv_tags.setLayoutManager(linearLayoutManager);
//        rv_tags.setItemAnimator(new DefaultItemAnimator());


        pageIndex = 1;
        questionsList.clear();
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();



        ArrayList<GetTagsModel> titleTabs=new ArrayList<>();
        titleTabs = getAllTags();

        tab_layout.setupWithViewPager(viewPager);
        tab_layout.setOnTabSelectedListener(onTabSelectedListener(viewPager));


//        for (String module : titleTabs) {
//            tab_layout.addTab(tab_layout.newTab().setText(module));
//        }


        return rootView;
    }

    private ArrayList<GetTagsModel> getAllTags() {
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        getAllTagsCall = WebRequest.apiInterface.getalltags();
        getAllTagsCall.enqueue(new Callback<GetAllTags>() {
            @Override
            public void onResponse(Call<GetAllTags> call, Response<GetAllTags> response) {
                if (response != null) {
                    GetAllTags getAllTags = response.body();
                    int code = Integer.parseInt(getAllTags.getSuccess());
                    switch (code) {
                        case 200:
                            for (int i = 0; i < getAllTags.getData().size(); i++) {
                                GetTagsModel getTagsModel = new GetTagsModel();
                                getTagsModel.setHashtag(getAllTags.getData().get(i).getHashTag());
                                getTagsModel.setCount(getAllTags.getData().get(i).getCount());
                                getTagsModelist.add(getTagsModel);

                            }
                            for(int i=0;i<getTagsModelist.size();i++){
                                Log.d("TabName=",getTagsModelist.get(i).getHashtag());
                                tab_layout.addTab(tab_layout.newTab().setText(getTagsModelist.get(i).getHashtag()));
                            }
                            tab_layout.post(new Runnable() {
                                @Override
                                public void run() {
                                    // don't forget to add Tab first before measuring..
                                    DisplayMetrics displayMetrics = new DisplayMetrics();
                                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                                    int widthS = displayMetrics.widthPixels;
                                    tab_layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                                    int widthT = tab_layout.getMeasuredWidth();

                                    if (widthS > widthT) {
                                        tab_layout.setTabMode(TabLayout.MODE_FIXED);
                                        tab_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT));
                                    }
                                }
                            });
                            createViewPager(viewPager);
                            progressDialog.dismiss();
//                            getTagsAdapter = new GetTagsAdapter(getActivity(), getTagsModelist);
//                            rv_tags.setAdapter(getTagsAdapter);
                            break;
                        default:
                            progressDialog.dismiss();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAllTags> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
        return getTagsModelist;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(myContext.getSupportFragmentManager());
//       for(int i=0;i<getTagsModelist.size();i++){
           adapter.addFragment(new AllSearchFragment(), getTagsModelist.get(0).getHashtag());
           adapter.addFragment(new CountSearchFragment(), getTagsModelist.get(1).getHashtag());
           adapter.addFragment(new GotSearchFragment(), getTagsModelist.get(2).getHashtag());
           adapter.addFragment(new QaSearchFragment(), getTagsModelist.get(3).getHashtag());
           adapter.addFragment(new QuestionFragment(), getTagsModelist.get(4).getHashtag());
           adapter.addFragment(new TestSearchFragment(), getTagsModelist.get(5).getHashtag());
//       }
       viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(6);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));
//        onTabSelectedListener(viewPager);

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {

            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getCount() {

            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("Home_Log","onResume");
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager viewPager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){
//                    case 0:
//                        if (AllSearchFragment.loaddata==true) {
//                            AllSearchFragment.tabseleted();
//                        }
//                        break;
//                    case 1:
//                        CountSearchFragment.tabseleted();
//                        break;
//                    case 2:
//                        GotSearchFragment.tabseleted();
//                        break;
//                    case 3:
//                        QaSearchFragment.tabseleted();
//                        break;
//                        case 4:
//                        QuestionFragment.tabseleted();
//                        break;
//                        case 5:
//                        TestSearchFragment.tabseleted();
//                        break;



                }
//tabName=String.valueOf(tab.getText());
////if(HomeFragment.loaddata==true) {
//    viewPager.setCurrentItem(tab.getPosition());
////    HomeFragment.tabseleted();
//}

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        };
    }


}
