package com.opozee.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.opozee.R;
import com.opozee.activities.ProfileActivity;
import com.opozee.application.QuestionnaireApplication;
//import com.opozee.fragments.ProfileFragment;
import com.opozee.model.FollowingUser;
import com.opozee.profiletabs.Followings;
import com.opozee.utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.opozee.fragments.Profile_New_Fragment.PROFILE_FRAGMENG_ARGUEMENT_USER_ID;


public class Followingadapter extends RecyclerView.Adapter<Followingadapter.MyViewHoder> {

    public Context context;
    ArrayList<FollowingUser> followingUserArrayList;
    String loggedInUserId;


    public Followingadapter(Context activity, ArrayList<FollowingUser> followingUserList, String loggedInUserId) {
        this.context = activity;
        this.followingUserArrayList = followingUserList;
        this.loggedInUserId=loggedInUserId;
    }

    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.followers_users, viewGroup, false);
        MyViewHoder myViewHolder = new MyViewHoder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder myViewHoder, int i) {
        if (followingUserArrayList.size() > 0) {
            final FollowingUser followingUser = followingUserArrayList.get(i);
            if (followingUser.getImageurl() != null) {
                if (followingUser.getImageurl().trim().length() > 0) {
                    Picasso.get().load(followingUser.getImageurl()).placeholder(R.drawable.user).error(R.drawable.user).into(myViewHoder.cv_profilePic);
                }
            }
            if(Integer.parseInt(followingUser.getOwneruserid())==Integer.parseInt(loggedInUserId)){

                myViewHoder.btn_status.setVisibility(View.VISIBLE);
                myViewHoder.btn_status.setText("Unfollow");
            }else{
                myViewHoder.btn_status.setVisibility(View.GONE);
            }



            myViewHoder.tv_name.setText(followingUser.getUsername());
            myViewHoder.btn_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Followings.unfollowcall(followingUser.getOwneruserid(),"false", followingUser.getUserid());

                }
            });

            myViewHoder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String, String> map = new HashMap<>();
                    map.put("ProfileOpened", followingUser.getUsername());
                    QuestionnaireApplication.getMixpanelApi().track("Profile Opened", new JSONObject(map));
                    int userId = Integer.parseInt(followingUser.getUserid());
                    Intent intent = new Intent(Followingadapter.this.context,ProfileActivity.class);
                    intent.putExtra(PROFILE_FRAGMENG_ARGUEMENT_USER_ID, userId);
                    Followingadapter.this.context.startActivity(intent);
                    context.startActivity(intent);
//                    context.relodfragment(Integer.parseInt(followingUser.getUserid()));
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return followingUserArrayList.size();
    }

    public class MyViewHoder extends RecyclerView.ViewHolder {
        Button btn_status;
        CircleImageView cv_profilePic;
        TextView tv_name;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            btn_status = itemView.findViewById(R.id.btn_followers);
            tv_name = itemView.findViewById(R.id.tv_name12);
            cv_profilePic = itemView.findViewById(R.id.cv_profilePic);
        }
    }
}
