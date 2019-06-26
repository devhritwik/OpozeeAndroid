package com.opozee;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;

import com.opozee.activities.LoginActivity;
import com.opozee.activities.SplashActivity;


public class CheckPermissionsRefine extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 2;

    String[] storagePermissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    String TAG = "CHECKPERMISSION_LOG";
    int count = 0;
    boolean isSendToSettings = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//checkPermissions();
        //startActivity();
        if (ActivityCompat.checkSelfPermission(this, storagePermissions[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, storagePermissions[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, storagePermissions[2]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, storagePermissions[3]) != PackageManager.PERMISSION_GRANTED
                )
        {
            checkPermissions();
        } else {
            startActivity();
        }
    }



    private void checkPermissions() {
        Log.d(TAG,"CountValue"+" " + count);
        if (count < 2) {
            try {
                if (ActivityCompat.checkSelfPermission(this, storagePermissions[0]) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(this, storagePermissions[1]) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(this, storagePermissions[2]) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(this, storagePermissions[3]) != PackageManager.PERMISSION_GRANTED
                        ) {
                    ActivityCompat.requestPermissions(this, storagePermissions, REQUEST_CODE_PERMISSION);
                    Log.d(TAG, "storagePermissions");
                    count++;
                    Log.d(TAG, "Count" + count);
                } else {
                    Log.d(TAG,"ACTIVITY");
                    startActivity();
                }
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "DIALOG");
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setCancelable(false);
            alert.setMessage("Permission required to proceed further ");
            alert.setPositiveButton("Allow permissions", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                    Toast.makeText(Permission.this, "check permission " + count, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, 101);
                    isSendToSettings = true;
                }
            });
            alert.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                    startActivity(intent);
                    finish();
                    System.exit(0);
                }
            });
            alert.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == REQUEST_CODE_PERMISSION) {
            //If permission is granted
            if (grantResults.length > 0) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                    return;
                } else if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                    return;
                } else if (grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                    return;
                } else if (grantResults[3] != PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                    return;
                }  else {
                    startActivity();
                }
            }
            else {
                checkPermissions();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isSendToSettings) {
            if (ActivityCompat.checkSelfPermission(this, storagePermissions[0]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, storagePermissions[1]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, storagePermissions[2]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, storagePermissions[3]) != PackageManager.PERMISSION_GRANTED
                    ) {
                Log.d(TAG, "RESUME");
                checkPermissions();
            } else {
                Log.d(TAG, "RESUMECHECK");
                startActivity();
            }
            isSendToSettings = false;
        }
    }

    private void startActivity() {
      
        Intent i = new Intent(CheckPermissionsRefine.this, LoginActivity.class);
        startActivity(i);
        finish();

    }






}





