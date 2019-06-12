//package com.opozee;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.LocationManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.test.mock.MockPackageManager;
//import android.util.Log;
//
//
//public class CheckPermissionsRefine extends AppCompatActivity {
//
//    private static final int REQUEST_CODE_PERMISSION = 2;
//
//    String[] storagePermissions = {
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.ACCESS_NETWORK_STATE,
//            Manifest.permission.ACCESS_WIFI_STATE,
//            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.RECEIVE_SMS,
//            Manifest.permission.READ_SMS};
//
//    String TAG = "CHECKPERMISSION_LOG";
//    int count = 0;
//    boolean isSendToSettings = false;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash__screen);
////checkPermissions();
//        //startActivity();
//        if (ActivityCompat.checkSelfPermission(this, storagePermissions[0]) != MockPackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, storagePermissions[1]) != MockPackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, storagePermissions[2]) != MockPackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, storagePermissions[3]) != MockPackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, storagePermissions[4]) != MockPackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, storagePermissions[5]) != MockPackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, storagePermissions[6]) != MockPackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, storagePermissions[7]) != MockPackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, storagePermissions[8]) != MockPackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, storagePermissions[9]) != MockPackageManager.PERMISSION_GRANTED
//                ) {
//            checkPermissions();
//        } else {
//            startActivity();
//        }
//    }
//
//    private void checkPermissions() {
//        Log.d("CountValue", " " + count);
//        if (count <= 2) {
//            try {
//                if (ActivityCompat.checkSelfPermission(this, storagePermissions[0]) != MockPackageManager.PERMISSION_GRANTED
//                        || ActivityCompat.checkSelfPermission(this, storagePermissions[1]) != MockPackageManager.PERMISSION_GRANTED
//                        || ActivityCompat.checkSelfPermission(this, storagePermissions[2]) != MockPackageManager.PERMISSION_GRANTED
//                        || ActivityCompat.checkSelfPermission(this, storagePermissions[3]) != MockPackageManager.PERMISSION_GRANTED
//                        || ActivityCompat.checkSelfPermission(this, storagePermissions[4]) != MockPackageManager.PERMISSION_GRANTED
//                        || ActivityCompat.checkSelfPermission(this, storagePermissions[5]) != MockPackageManager.PERMISSION_GRANTED
//                        || ActivityCompat.checkSelfPermission(this, storagePermissions[6]) != MockPackageManager.PERMISSION_GRANTED
//                        || ActivityCompat.checkSelfPermission(this, storagePermissions[7]) != MockPackageManager.PERMISSION_GRANTED
//                        || ActivityCompat.checkSelfPermission(this, storagePermissions[8]) != MockPackageManager.PERMISSION_GRANTED
//                        || ActivityCompat.checkSelfPermission(this, storagePermissions[9]) != MockPackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(this, storagePermissions, REQUEST_CODE_PERMISSION);
//                    Log.d(TAG, "storagePermissions");
//                    count++;
//                } else {
//                    startActivity();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            AlertDialog.Builder alert = new AlertDialog.Builder(this);
//            alert.setCancelable(false);
//            alert.setMessage("Permission required to proceed further ");
//            alert.setPositiveButton("Allow permissions", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
////                    Toast.makeText(Permission.this, "check permission " + count, Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                    Uri uri = Uri.fromParts("package", getPackageName(), null);
//                    intent.setData(uri);
//                    startActivityForResult(intent, 101);
//                    isSendToSettings = true;
//                }
//            });
//            alert.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    isSendToSettings = false;
//                    Intent intent = new Intent(Intent.ACTION_MAIN);
//                    intent.addCategory(Intent.CATEGORY_HOME);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
//                    startActivity(intent);
//                    finish();
//                    System.exit(0);
//                }
//            });
//            alert.show();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        //Checking the request code of our request
//        if (requestCode == REQUEST_CODE_PERMISSION) {
//            //If permission is granted
//            if (grantResults.length > 0) {
//                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                    checkPermissions();
//                    return;
//                } else if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {
//                    checkPermissions();
//                    return;
//                } else if (grantResults[2] != PackageManager.PERMISSION_GRANTED) {
//                    checkPermissions();
//                    return;
//                } else if (grantResults[3] != PackageManager.PERMISSION_GRANTED) {
//                    checkPermissions();
//                    return;
//                } else if (grantResults[4] != PackageManager.PERMISSION_GRANTED) {
//                    checkPermissions();
//                    return;
//                } else if (grantResults[5] != PackageManager.PERMISSION_GRANTED) {
//                    checkPermissions();
//                    return;
//                } else if (grantResults[6] != PackageManager.PERMISSION_GRANTED) {
//                    checkPermissions();
//                    return;
//                } else if (grantResults[7] != PackageManager.PERMISSION_GRANTED) {
//                    checkPermissions();
//                    return;
//                } else if (grantResults[8] != PackageManager.PERMISSION_GRANTED) {
//                    checkPermissions();
//                    return;
//                } else if (grantResults[9] != PackageManager.PERMISSION_GRANTED) {
//                    checkPermissions();
//                    return;
//                } else {
//                    startActivity();
//                }
//            } else {
//                checkPermissions();
//            }
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (isSendToSettings) {
////            Toast.makeText(this, "resume", Toast.LENGTH_SHORT).show();
//            if (ActivityCompat.checkSelfPermission(this, storagePermissions[0]) != MockPackageManager.PERMISSION_GRANTED
//                    || ActivityCompat.checkSelfPermission(this, storagePermissions[1]) != MockPackageManager.PERMISSION_GRANTED
//                    || ActivityCompat.checkSelfPermission(this, storagePermissions[2]) != MockPackageManager.PERMISSION_GRANTED
//                    || ActivityCompat.checkSelfPermission(this, storagePermissions[3]) != MockPackageManager.PERMISSION_GRANTED
//                    || ActivityCompat.checkSelfPermission(this, storagePermissions[4]) != MockPackageManager.PERMISSION_GRANTED
//                    || ActivityCompat.checkSelfPermission(this, storagePermissions[5]) != MockPackageManager.PERMISSION_GRANTED
//                    || ActivityCompat.checkSelfPermission(this, storagePermissions[6]) != MockPackageManager.PERMISSION_GRANTED
//                    || ActivityCompat.checkSelfPermission(this, storagePermissions[7]) != MockPackageManager.PERMISSION_GRANTED
//                    || ActivityCompat.checkSelfPermission(this, storagePermissions[8]) != MockPackageManager.PERMISSION_GRANTED
//                    || ActivityCompat.checkSelfPermission(this, storagePermissions[9]) != MockPackageManager.PERMISSION_GRANTED
//                    ) {
//                checkPermissions();
//            } else {
//                startActivity();
//            }
//            isSendToSettings = false;
//        }
//    }
//
//    private void startActivity() {
////        if (checkLocationOnOff()) {
////            String id = preferences.getString(GlobalVariables.USERID, "-1");
////            if (id.trim().equals("-1")) {
////                Intent intent = new Intent(CheckPermissions.this, SignInActivity.class);
////                startActivity(intent);
////                finish();
////            } else {
////                Intent intent = new Intent(CheckPermissions.this, MapScreen.class);
////                startActivity(intent);
////                finish();
////            }
////        } else {
////            Intent i = new Intent().setClass(CheckPermissions.this, LocationOff.class);
////            startActivity(i);
////            finish();
////        }
//    }
//
//    public boolean checkLocationOnOff() {
//        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//    }
//
//
////    public void requestPermissions() {
////        Dexter.withActivity(this)
////                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
////                .withListener(new MultiplePermissionsListener() {
////                    @Override
////                    public void onPermissionsChecked(MultiplePermissionsReport report) {
////                        // check if all permissions are granted
////                        if (report.areAllPermissionsGranted()) {
////                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
////                        } else {                            // show alert dialog navigating to Settings
////                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
////                            Uri uri = Uri.fromParts("package", getPackageName(), null);
////                            intent.setData(uri);
////                            startActivityForResult(intent, 101);
////                        }
////
////                        // check for permanent denial of any permission
////                        if (report.isAnyPermissionPermanentlyDenied()) {
////                            // show alert dialog navigating to Settings
////                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
////                            Uri uri = Uri.fromParts("package", getPackageName(), null);
////                            intent.setData(uri);
////                            startActivityForResult(intent, 101);
////                        }
////                    }
////
////                    @Override
////                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
////                        token.continuePermissionRequest();
////                    }
////                }).
////                withErrorListener(new PermissionRequestErrorListener() {
////                    @Override
////                    public void onError(DexterError error) {
////                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
////                    }
////                }).onSameThread().check();
////    }
//
//   /* @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        //Checking the request code of our request
//        if (requestCode == REQUEST_CODE_PERMISSION) {
//            //If permission is granted
//            permissionCount = permissionCount + 1;
//            if (grantResults.length > 0) {
//                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
////                    checkPermissions();
//                    return;
//                } else if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {
////                    checkPermissions();
//                    return;
//                } else if (grantResults[2] != PackageManager.PERMISSION_GRANTED) {
////                    checkPermissions();
//                    return;
//                } else if (grantResults[3] != PackageManager.PERMISSION_GRANTED) {
////                    checkPermissions();
//                    return;
//                } else if (grantResults[4] != PackageManager.PERMISSION_GRANTED) {
////                    checkPermissions();
//                    return;
//                } else if (grantResults[5] != PackageManager.PERMISSION_GRANTED) {
////                    checkPermissions();
//                    return;
//                } else if (grantResults[6] != PackageManager.PERMISSION_GRANTED) {
////                    checkPermissions();
//                    return;
//                } else if (grantResults[7] != PackageManager.PERMISSION_GRANTED) {
////                    checkPermissions();
//                    return;
//                } else if (grantResults[8] != PackageManager.PERMISSION_GRANTED) {
////                    checkPermissions();
//                    return;
//                } else if (grantResults[9] != PackageManager.PERMISSION_GRANTED) {
////                    checkPermissions();
//                    return;
//                } else {
//                    startActivity();
//                }
//                Log.d("CHECKPERMISSION", "permissionCount " + permissionCount + " storagePermissions " + storagePermissions.length);
//
//                if (permissionCount == storagePermissions.length) {
//
//                }
//
//            } else {
//                checkPermissions();
//            }
//        }
//    }*/
//}



////////Check Data///
//package com.app.upperlooksdemo;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Build;
//import android.provider.MediaStore;
//import android.support.design.widget.Snackbar;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Base64;
//import android.util.Log;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.app.upperlooksdemo.Response.Registeration_user.ImageUpdate.Imageupdate;
//import com.app.upperlooksdemo.Response.Registeration_user.Updateprofile.Update;
//import com.app.upperlooksdemo.Webrequest.WebRequest;
//import com.bumptech.glide.Glide;
//import com.mikhaellopez.circularimageview.CircularImageView;
//
//import java.io.ByteArrayOutputStream;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class Updateprofile extends UpperLooksActivity implements View.OnClickListener {
//
//    ImageView iv_profile;
//    EditText et_name, et_email, et_number;
//    ImageButton ib_name, ib_email, ib_number;
//    Button btn_submit;
//    SharedValues sharedValues;
//    JsonCreator jsonCreator;
//    String name, email, mobilenumber, id;
//    int userid;
//    WebRequest webRequest;
//    WebRequest.APIInterface apiInterface;
//    Update update;
//    retrofit2.Call<Update> updateCall;
//    String token, base64String;
//    String TAG = "UPDATEPROFILE_LOG";
//    ProgressDialog recentProgress, imageprogress;
//    ImageView iv_back;
//    CircularImageView iv_userDp;
//    View view_photo;
//    Bitmap bitmap;
//    Imageupdate imageupdate;
//    retrofit2.Call<Imageupdate> imageupdateCall;
//    private static final int PERMISSION_REQUEST_CODE = 200;
//    String url1;
//    private TextView tv_toolName;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_updateprofile);
//        tv_toolName = findViewById(R.id.tv_toolName);
//        tv_toolName.setText("Profile");
//        sharedValues = new SharedValues(Updateprofile.this);
//        jsonCreator = new JsonCreator(Updateprofile.this);
//        name = sharedValues.getpreferance(SharedValues.USER_NAME);
//        email = sharedValues.getpreferance(SharedValues.USER_EMAIL);
//        mobilenumber = sharedValues.getpreferance(SharedValues.USER_NUMBER);
//        id = sharedValues.getpreferance(SharedValues.USER_ID);
//        token = sharedValues.getpreferance(SharedValues.TOKEN);
//        url1 = sharedValues.getpreferance(SharedValues.USER_IMAGE);
//        Log.d(TAG, "USERDP" + url1);
//        webRequest = WebRequest.getSingleton(Updateprofile.this);
//
//        userid = Integer.parseInt(id);
//        iv_profile = findViewById(R.id.iv_user_dp);
//        et_name = findViewById(R.id.et_update_name);
//        et_email = findViewById(R.id.et_update_email);
//        et_number = findViewById(R.id.et_update_phone_number);
//        ib_name = findViewById(R.id.ib_update_name);
//        //ib_email=findViewById(R.id.ib_update_email);
//        ib_number = findViewById(R.id.ib_update_phone_number);
//        btn_submit = findViewById(R.id.btn_update);
//        iv_back = findViewById(R.id.iv_back);
//        view_photo = findViewById(R.id.view_photo_background);
//        iv_userDp = findViewById(R.id.iv_user_dp);
//        imageprogress = new ProgressDialog(Updateprofile.this);
//        imageprogress.setCancelable(false);
//        imageprogress.setMessage("Image Updated");
//        imageprogress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//
//        view_photo.setOnClickListener(this);
//        ib_number.setOnClickListener(this);
//        // ib_email.setOnClickListener(this);
//        ib_name.setOnClickListener(this);
//        btn_submit.setOnClickListener(this);
//        iv_back.setOnClickListener(this);
//        et_name.setEnabled(false);
//        et_number.setEnabled(false);
//        et_email.setEnabled(false);
//        et_email.setClickable(false);
//        et_email.setFocusable(false);
//        btn_submit.setVisibility(View.GONE);
//        et_name.setText(name);
//        et_number.setText(mobilenumber);
//        et_email.setText(email);

//        et_number.setSelection(et_number.getText().length());
//        et_name.setSelection(et_name.getText().length());
//        et_email.setSelection(et_email.getText().length());
//        if (!(url1.equals(""))) {
//            Glide.with(Updateprofile.this).load(url1).centerCrop().into(iv_userDp);
//            Glide.with(Updateprofile.this)
//            .load(Uri.parse(url1) // add your image url
//            .transform(new DataNotUsed(Updateprofile.this)) // applying the image transformer
//            .into(iv_userDp));
//        }
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.ib_update_name:
//                et_name.setEnabled(true);
//                et_number.setSelection(et_number.getText().length());
//                et_name.setSelection(et_name.getText().length());
//                et_email.setSelection(et_email.getText().length());
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//                et_name.requestFocus();
//                btn_submit.setVisibility(View.VISIBLE);
//                break;
//            case R.id.ib_update_phone_number:
//                et_number.setSelection(et_number.getText().length());
//                et_name.setSelection(et_name.getText().length());
//                et_email.setSelection(et_email.getText().length());
//                InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm1.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//                et_number.requestFocus();
//
//                et_number.setEnabled(true);
//                btn_submit.setVisibility(View.VISIBLE);
//                break;
////            case R.id.ib_update_email:
////                et_email.setEnabled(true);
////                btn_submit.setVisibility(View.VISIBLE);
////                break;
//            case R.id.btn_update:
//                internetcheck();
//                break;
//            case R.id.iv_back:
////                Intent intent = new Intent(Updateprofile.this, MainActivity.class);
////                startActivity(intent);
//                finish();
//                break;
//            case R.id.view_photo_background:
//                if (checkPermission()) {
//                    //main logic or main code
//
//                    // . write your main code to execute, It will execute if the permission is already given.
//                    photonetcheck();
//                } else {
//
//                    requestPermission();
//                }
//                break;
//
//        }
//    }
//
//
//    private void updateprofile(String s, String s1, String s2) {
//        String json = jsonCreator.updateprofile(userid, s, s2, s1);
//        Log.d("updateprofilejson", json);
//        updateprofileapi(json);
//    }
//
//    private void updateprofileapi(String json) {
//        String mtoken;
//        if (token != null) {
//            mtoken = token;
//        } else {
//            mtoken = Globalvariables.anonymousToken;
//        }
//        Log.d(TAG, "" + mtoken);
//        updateCall = webRequest.apiInterface.updateprofile(json, mtoken, Globalvariables.CONTENT_TYPE);
//        updateCall.enqueue(new Callback<Update>() {
//            @Override
//            public void onResponse(Call<Update> call, Response<Update> response) {
//                update = response.body();
//                if (update == null) {
//                    recentProgress.cancel();
//                    Toast.makeText(getApplicationContext(), "Profile Not Updated", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (update.getResp().equals("0")) {
//                        recentProgress.cancel();
//                        et_name.setText(et_name.getText().toString());
//                        et_number.setText(et_number.getText().toString());
//                        sharedValues.AddPreference(SharedValues.USER_NAME, et_name.getText().toString());
//                        sharedValues.AddPreference(SharedValues.USER_NUMBER, et_number.getText().toString());
//                        et_name.setEnabled(false);
//                        et_number.setEnabled(false);
//                        et_email.setEnabled(false);
//                        btn_submit.setVisibility(View.GONE);
//                        Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Profile Not Updated", Toast.LENGTH_SHORT).show();
//                        recentProgress.cancel();
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Update> call, Throwable t) {
//                recentProgress.cancel();
//            }
//        });
//    }
//
//    private void getimage() {
//
//        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(Updateprofile.this);
//
//        builder.setTitle("Take Photo");
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//                if (options[item].equals("Take Photo"))
//
//                {
//                    // Toast.makeText(getApplicationContext(), "start", Toast.LENGTH_SHORT).show();
//                    Intent start = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(start, 1);
//
//
//                } else if (options[item].equals("Choose from Gallery"))
//
//                {
//
//                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//                    startActivityForResult(intent, 2);
//
//
//                } else if (options[item].equals("Cancel")) {
//
//                    dialog.dismiss();
//
//                }
//
//            }
//
//        });
//        builder.show();
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == 1) {
//                bitmap = (Bitmap) data.getExtras().get("data");
//
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                byte[] imageBytes = baos.toByteArray();
//
//                base64String = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//                Log.d(TAG, "BASE64:" + base64String);
//                imageprogress.show();
//                getimagejson(base64String);
//
//
//            } else if (requestCode == 2) {
//                Uri selectedImage = data.getData();
//
//                String[] filePath = {MediaStore.Images.Media.DATA};
//
//                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
//
//                c.moveToFirst();
//
//                int columnIndex = c.getColumnIndex(filePath[0]);
//
//                String picturePath = c.getString(columnIndex);
//
//                c.close();
//
//                bitmap = (BitmapFactory.decodeFile(picturePath));
//
//
//                Log.d(TAG, "BASE64:" + base64String);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                byte[] imageBytes = baos.toByteArray();
//
//                base64String = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//                imageprogress.show();
//                getimagejson(base64String);
//            }
//
//
//        }
//
//
//    }
//
//    private void getimagejson(String base64String) {
//        // Log.d(TAG,"Baseeeeeeeeeee"+base64String);
//        String json = jsonCreator.updateimage(userid, base64String);
//        // Log.d(TAG,"Imagejson"+json);
//        getimageupdateapi(json);
//    }
//
//    private void getimageupdateapi(String json) {
//        String mtoken;
//        if (token != null) {
//            mtoken = token;
//        } else {
//            mtoken = Globalvariables.anonymousToken;
//        }
//        imageupdateCall = webRequest.apiInterface.updateimage(json, mtoken, Globalvariables.CONTENT_TYPE);
//        imageupdateCall.enqueue(new Callback<Imageupdate>() {
//            @Override
//            public void onResponse(Call<Imageupdate> call, Response<Imageupdate> response) {
//                imageupdate = response.body();
//                Log.d(TAG, "response of image" + String.valueOf(imageupdate));
//                if (imageupdate == null) {
//                    imageprogress.cancel();
//                    Toast.makeText(getApplicationContext(), "Some error occured in image update", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (imageupdate.getResp().equals("0")) {
//                        imageprogress.cancel();
//                        Log.d(TAG, "response of image" + String.valueOf(imageupdate));
//                        String url = imageupdate.getResult().getProfileImage();
//                        String image = Globalvariables.BASE_URL + url;
//
//                        sharedValues.AddPreference(SharedValues.USER_IMAGE, image);
//                        Glide.with(Updateprofile.this).load(image).centerCrop().into(iv_userDp);
//                        Log.d(TAG, "Iamgeglide" + image);
//                        Toast.makeText(getApplicationContext(), "Image Update", Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        imageprogress.cancel();
//                        Toast.makeText(getApplicationContext(), "Some error occured in image update", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Imageupdate> call, Throwable t) {
//                imageprogress.cancel();
//                Log.d(TAG, "Failure" + t.getMessage());
//            }
//        });
//    }
//
//    private boolean checkPermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            // Permission is not granted
//            return false;
//        }
//        return true;
//    }
//
//    private void requestPermission() {
//
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case PERMISSION_REQUEST_CODE:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
//
//                    // main logic
//                } else {
//                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                            showMessageOKCancel("You need to allow access permissions",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                requestPermission();
//                                            }
//                                        }
//                                    });
//                        }
//                    }
//
//                }
//                break;
//        }
//
//    }
//
//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(Updateprofile.this)
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }
//
//
//    private void internetcheck() {
//        boolean updatecheck = InternetConnection.isNetworkConnected(Updateprofile.this);
//        if (updatecheck == true) {
//            recentProgress = new ProgressDialog(Updateprofile.this);
//            recentProgress.setCancelable(false);
//            recentProgress.setMessage("Profile updated");
//            recentProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            recentProgress.show();
//            if (et_name.getText().toString().trim().equals("") && et_number.getText().toString().trim().equals("")) {
//                Toast.makeText(getApplicationContext(), "Text field is Empty", Toast.LENGTH_SHORT).show();
//            } else {
//                updateprofile(et_name.getText().toString(), et_email.getText().toString(), et_number.getText().toString());
//            }
//
//        } else {
//            checkinternet();
//        }
//    }
//
//    private void checkinternet() {
//        Snackbar snackbar = Snackbar
//                .make(Updateprofile.this.findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
//        snackbar.show();
//        snackbar.setAction("RETRY", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                internetcheck();
//
//            }
//        });
//    }
//
//
//    private void photonetcheck() {
//        boolean updatecheck = InternetConnection.isNetworkConnected(Updateprofile.this);
//        if (updatecheck == true) {
//            getimage();
//
//        } else {
//            photocheckinternet();
//        }
//    }
//
//    private void photocheckinternet() {
//        Snackbar snackbar = Snackbar
//                .make(Updateprofile.this.findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
//        snackbar.show();
//        snackbar.setAction("RETRY", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                photonetcheck();
//
//            }
//        });
//    }
//}

// implementation 'com.github.bumptech.glide:glide:3.7.0'
//    implementation fileTree(dir: 'libs', include: ['*.jar'])
//            implementation 'com.android.support:appcompat-v7:27.0.1'
//            implementation 'com.android.support.constraint:constraint-layout:1.1.2'
//            implementation 'com.android.support:design:27.0.1'
//
//
//            implementation 'com.github.bumptech.glide:glide:3.7.0'
//            implementation 'com.squareup.picasso:picasso:2.71828'
//
//            implementation 'com.squareup.retrofit2:retrofit:2.3.0'
//            implementation 'com.squareup.retrofit2:converter-gson:2.0.2'
//            implementation 'com.squareup.okhttp:okhttp:2.4.0'
//            implementation 'com.google.code.gson:gson:2.2.4'
//            implementation 'com.squareup.retrofit2:converter-scalars:2.0.2'
//
//            //Circle Imageview
//            implementation 'com.mikhaellopez:circularimageview:3.0.2'
//            //google sign in
//            implementation 'com.google.android.gms:play-services-auth:15.0.1'
//            //facebook sign in
//            implementation 'com.facebook.android:facebook-login:[4,5)'
//
//            //Glide
//            implementation 'com.github.bumptech.glide:glide:3.7.0'
//            implementation 'jp.wasabeef:glide-transformations:2.0.2'
//
////location
//            implementation 'com.google.android.gms:play-services-location:15.0.1'
//            implementation 'com.google.android.gms:play-services-maps:15.0.1'







