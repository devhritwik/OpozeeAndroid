package com.opozee.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.opozee.R;
import com.opozee.fragments.ProfileFragment;
import com.opozee.model.FollowesUsers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowerUsers extends RecyclerView.Adapter<FollowerUsers.MyViewHolder> {
    ProfileFragment context;
    ArrayList<FollowesUsers> followerUsersArrayList;
    String loggedInUserId;

    public FollowerUsers(ProfileFragment activity, ArrayList<FollowesUsers> followerslist, String loggedInUserId) {
    this.followerUsersArrayList=followerslist;
    this.context=activity;
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
                        ((ProfileFragment) context).unfollowuser(followingUser.getOwneruserid(),"false", followingUser.getUserid());
                    }else{
//                        myViewHolder.btn_status.setText("Follow");
                        ((ProfileFragment) context).followuser(followingUser.getOwneruserid(),"true", followingUser.getUserid());
                    }

                }
            });

            myViewHolder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Map<String, String> map = new HashMap<>();
//                    map.put("ProfileOpened", followingUser.getUsername());
//                    QuestionnaireApplication.getMixpanelApi().track("Profile Opened", new JSONObject(map));
//                    int userId = Integer.parseInt(followingUser.getUserid());
//                    Intent intent = new Intent(Followingadapter.this.context,ProfileActivity.class);
//                    intent.putExtra(PROFILE_FRAGMENG_ARGUEMENT_USER_ID, userId);
//                    Followingadapter.this.context.startActivity(intent);
                    ((ProfileFragment) context).relodfragment(Integer.parseInt(followingUser.getUserid()));
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
