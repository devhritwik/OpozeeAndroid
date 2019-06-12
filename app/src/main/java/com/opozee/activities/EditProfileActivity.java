package com.opozee.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.opozee.R;
import com.opozee.params.ProfileParams;
import com.opozee.pojo.ProfileResponse;
import com.opozee.profile_mvp.model.ProfileInteractorImpl;
import com.opozee.profile_mvp.presenter.ProfilePresenterImpl;
import com.opozee.profile_mvp.view.ProfileView;
import com.opozee.utils.AppGlobal;
import com.opozee.utils.AppSP;
import com.opozee.utils.ImageLoaderFromDevice;
import com.opozee.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;

public class EditProfileActivity extends ActivityManagePermission implements ProfileView {

    @BindView(R.id.iv_user)
    CircleImageView iv_user;
    @BindView(R.id.iv_edit)
    ImageView iv_edit;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;

    @BindView(R.id.log_out_button)
    TextView mLogOutButton;

//    @BindView(R.id.etFirstName)
//    EditText etFirstName;
//    @BindView(R.id.etLastName)
//    EditText etLastName;

    private ProfilePresenterImpl mPresenter;
    private ImageLoaderFromDevice imageLoader;
    private File mSelectedImageFile;
    EditText et_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //add the whole view to the butterknife
        ButterKnife.bind(this);

        et_username=findViewById(R.id.et_edit_username);
        //initialize the imageLoader class to make interface for getting the profile pic using camera or gallery
        imageLoader = new ImageLoaderFromDevice(EditProfileActivity.this);

        //set presenter to attach view with interactor to get the data from API
        setProfile();

        //call API to populate data
        getProfile();


        SpannableString content = new SpannableString("Log Out");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        mLogOutButton.setText(content);
    }

    @OnClick(R.id.log_out_button)
    public void onLogoutClick() {
        logoutAlert();
    }

    private void logoutAlert() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        AppSP.getInstance(getApplicationContext()).clear();
                        Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }


    private void getProfile() {
        if (Utils.isNetworkAvail(EditProfileActivity.this)) {
            mPresenter.profile(getParams());
        } else {
            Utils.showCustomToast(EditProfileActivity.this, getString(R.string.internet_alert));
        }

    }

    private ProfileParams getParams() {
        ProfileParams params = new ProfileParams();
        params.setType(AppGlobal.TYPE_GET_PROFILE);
        params.setUser_id(Utils.getLoggedInUserId(EditProfileActivity.this));
        return params;
    }

    private void setProfile() {
        mPresenter = new ProfilePresenterImpl();
        mPresenter.attachView(this, new ProfileInteractorImpl());
    }

    @OnClick(R.id.btnSave)
    public void onSaveClick() {
        if(et_username!=null){
            if(et_username.getText().toString().trim().length()>0){
                editProfile();
            }else{
                et_username.setError("Username is empty");
                et_username.requestFocus();
            }
        }

    }

    @OnClick(R.id.iv_edit)
    public void onEditClick() {
//            permissionDialog();
        imageLoader.selectImage();
    }

    @Override
    public void showProgress() {
        if (Utils.mProgressDialog == null)
            Utils.showProgress(EditProfileActivity.this);
    }

    @Override
    public void hideProgress() {
        if (Utils.mProgressDialog != null)
            Utils.dismissProgress();
    }

    @Override
    public void onFirstNameError(String error) {
//        etFirstName.requestFocus();
//        etFirstName.setError(error);
    }

    @Override
    public void onLastNameError(String error) {
//        etLastName.requestFocus();
//        etLastName.setError(error);
    }

    @Override
    public void onSuccess(ProfileResponse response) {
        if (response.getResponse().getType() == AppGlobal.TYPE_GET_PROFILE) {
            //after getting data update the UI
            updateUI(response);
        } else {
            Utils.showCustomToast(EditProfileActivity.this, "Profile Updated Successfully");
            //updateUI after the updation of the profile
            getProfile();
        }

    }

    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(EditProfileActivity.this, error);
    }

    private void updateUI(ProfileResponse response) {
//        tv_user_name.setText(Utils.capitalize(response.getResponse().getUserProfile().getFirstName() + " " +  response.getResponse().getUserProfile().getLastName()));
        tv_user_name.setText(Utils.capitalize(response.getResponse().getUserProfile().getUserName()));
        et_username.setText(response.getResponse().getUserProfile().getUserName());
//        etEmail.setText(Utils.capitalize(response.getResponse().getUserProfile().getEmail()));
//        etFirstName.setText(Utils.capitalize(response.getResponse().getUserProfile().getFirstName()));
//        etLastName.setText(response.getResponse().getUserProfile().getLastName());
//        etUsername.setText("@"+response.getResponse().getUserProfile().getUserName().replace(" ", "").toLowerCase());

        String imageURL = response.getResponse().getUserProfile().getImageURL();
        String defaultURL = "http://23.111.138.246:8021/Content/Upload/ProfileImage/oposee-profile.png";

        String url = imageURL == null || imageURL.length() == 0 || imageURL.equals("") ? defaultURL : imageURL;
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(iv_user);

    }


    @OnClick(R.id.iv_back)
    void onBackClick() {
        finish();
    }

    ////////////////////STARTING///////////////Profile picture updations code////////////////////////
    private void permissionDialog() {
        askCompactPermissions(new String[]{PermissionUtils.Manifest_CAMERA, PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE, PermissionUtils.Manifest_READ_EXTERNAL_STORAGE}, new PermissionResult() {
            @Override
            public void permissionGranted() {

//                Bitmap bitmap = getBitmapFromAsset("add_profile.png");
//                mSelectedImageFile = Utils.savebitmap(bitmap);
//                sendRequestUploadPhoto();
                imageLoader.selectImage();
            }

            @Override
            public void permissionDenied() {
                Toast.makeText(EditProfileActivity.this.getApplicationContext(), EditProfileActivity.this.getResources().getString(R.string.needCameraPermission), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            imageLoader.onActivityResult(requestCode, data, iv_user);
            if (imageLoader.mIsImageSelected) {
                Bitmap bitmap = imageLoader.getmBitmap();
//                mSelectedImageFile = Utils.savebitmap(bitmap, EditProfileActivity.this.getExternalCacheDir().getAbsolutePath());

                String filepath = storeImage(bitmap, Utils.getLoggedInUserId(EditProfileActivity.this));
                File file = new File(filepath);
                mSelectedImageFile = file;
                Log.d("ImageBitmap=", mSelectedImageFile.toString());
//                mSelectedImageFile = new File(imageLoader.getOutput().getPath());

            }
        }
    }

    public String storeImage(Bitmap imageData, String filename) {
        // get path to external storage (SD card)

        File sdIconStorageDir = null;
        String filepath = "";

        sdIconStorageDir = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/myAppDir/");
        // create storage directories, if they don't exist
        if (!sdIconStorageDir.exists()) {
            sdIconStorageDir.mkdirs();
        }
        try {
            filepath = sdIconStorageDir.toString() + File.separator + filename;
            FileOutputStream fileOutputStream = new FileOutputStream(filepath);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            //Toast.makeText(m_cont, "Image Saved at----" + filePath, Toast.LENGTH_LONG).show();
            // choose another format if PNG doesn't suit you
            imageData.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();

        } catch (FileNotFoundException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            return filepath;
        } catch (IOException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            return filepath;
        }
        return filepath;
    }

    //send data to update profile
    private void editProfile() {
        if (Utils.isNetworkAvail(EditProfileActivity.this)) {
            mPresenter.profile(getEditProfileParams());
        } else {
            Utils.showCustomToast(EditProfileActivity.this, getString(R.string.internet_alert));
        }

    }

    //get the params for the edit profile
    private ProfileParams getEditProfileParams() {
        ProfileParams params = new ProfileParams();
        params.setType(AppGlobal.TYPE_PROFILE_UPDATE);
        params.setUserName(et_username.getText().toString());
        params.setFirstName("");
        params.setLastName("");
        params.setProfilePic(mSelectedImageFile);
        params.setUser_id(Utils.getLoggedInUserId(EditProfileActivity.this));
        return params;
    }

}

