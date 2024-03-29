package com.opozeeApp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.opozeeApp.R;
import com.opozeeApp.activities.ProfileActivity;
import com.opozeeApp.application.QuestionnaireApplication;
//import com.opozee.fragments.ProfileFragment;
import com.opozeeApp.model.FollowesUsers;
import com.opozeeApp.profiletabs.Followers;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.opozeeApp.fragments.Profile_New_Fragment.PROFILE_FRAGMENG_ARGUEMENT_USER_ID;

public class FollowerUsers extends RecyclerView.Adapter<FollowerUsers.MyViewHolder> {
    public static Context context;
    ArrayList<FollowesUsers> followerUsersArrayList;
    String loggedInUserId;

    public FollowerUsers(Context activity, ArrayList<FollowesUsers> followerslist, String loggedInUserId) {
    this.followerUsersArrayList=followerslist;
    context=activity;
    this.loggedInUserId=loggedInUserId;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
     View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.followers_users,viewGroup,false);
     MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        if(followerUsersArrayList.size()>0) {
            final FollowesUsers followingUser = followerUsersArrayList.get(i);
            if (followingUser.getImageurl() != null) {
                if (followingUser.getImageurl().trim().length() > 0) {
                    Picasso.get().load(followingUser.getImageurl()).placeholder(R.drawable.user).error(R.drawable.user).into(myViewHolder.cv_profilePic);
                }
            }

            if(Integer.parseInt(followingUser.getOwneruserid())==Integer.parseInt(loggedInUserId)){
                myViewHolder.btn_status.setVisibility(View.VISIBLE);
                if(followingUser.getIsfollowback().equals("true")){
                    myViewHolder.btn_status.setText("Unfollow");
                }else{
                    myViewHolder.btn_status.setText("Follow");
                }

            }else{
                myViewHolder.btn_status.setVisibility(View.GONE);
            }

            myViewHolder.tv_name.setText(followingUser.getUsername());

            myViewHolder.btn_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(followingUser.getIsfollowback().equals("true")){
//                        myViewHolder.btn_status.setText("Unfollow");
                        Followers.unfollowcall(followingUser.getOwneruserid(),"false", followingUser.getUserid());
                    }else{
//                        myViewHolder.btn_status.setText("Follow");
                        Followers.followfacecall(followingUser.getOwneruserid(),"true", followingUser.getUserid());
                    }

                }
            });

            myViewHolder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String, String> map = new HashMap<>();
                    map.put("ProfileOpened", followingUser.getUsername());
                    QuestionnaireApplication.getMixpanelApi().track("Profile Opened", new JSONObject(map));
                    int userId = Integer.parseInt(followingUser.getUserid());
                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.putExtra(PROFILE_FRAGMENG_ARGUEMENT_USER_ID, userId);
                    context.startActivity(intent);
//                    ((Activity) context).relodfragment(Integer.parseInt(followingUser.getUserid()));
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return followerUsersArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button btn_status;
        CircleImageView cv_profilePic;
        TextView tv_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_status=itemView.findViewById(R.id.btn_followers);
            tv_name=itemView.findViewById(R.id.tv_name12);
            cv_profilePic=itemView.findViewById(R.id.cv_profilePic);
        }
    }
}
