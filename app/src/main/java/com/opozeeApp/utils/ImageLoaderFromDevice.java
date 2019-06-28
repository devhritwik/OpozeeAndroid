package com.opozeeApp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.opozeeApp.R;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ahsan Javed on 29/07/2015.
 */
public class ImageLoaderFromDevice {

    private Activity mActivity;
    private Bitmap mBitmap;
    public boolean mIsImageSelected = false;
    public final int REQUEST_CAMERA = 1;
    public final int REQUEST__FILE = 2;
    private String TAG = "ImageLoaderFromDevice ";
    private Uri output, input;
    private int ImageFromGallery = 0;
    private File photoFile;
    private String mCurrentPhotoPath = "";


    public ImageLoaderFromDevice(Activity activity) {

        mActivity = activity;
    }

    // takes image whether from gallery or camera
    public void selectImage() {
        final CharSequence[] items = {mActivity.getString(R.string.camera), mActivity.getString(R.string.library)};


        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(mActivity.getString(R.string.choose_source));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                Intent intent;
                switch (item) {
                    case 0:
                        dispatchTakePictureIntent();
                        break;
                    case 1:
                        Intent intent2 = new Intent();
                        intent2.setType("image/*");
                        intent2.setAction(Intent.ACTION_GET_CONTENT);
                        mActivity.startActivityForResult(Intent.createChooser(intent2,
                                "Select Picture"), REQUEST__FILE);
                        break;
                    case 2:
                        dialog.dismiss();
                        break;
                }


            }

        });
        builder.show();
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (takePictureIntent.resolveActivity(mActivity.getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
                if (photoFile != null) {
                    takePictureIntent.putExtra("output", FileProvider.getUriForFile(mActivity, mActivity.getString(R.string.content_provider), photoFile));
                    mActivity.startActivityForResult(takePictureIntent, REQUEST_CAMERA);
                }
            } catch (Exception ex) {
                Log.e(this.TAG, "exception      " + ex.toString());
            }
        }
    }


    public Bitmap onActivityResult(int requestCode, Intent data, ImageView iv) {
        if (requestCode == REQUEST_CAMERA) {
            // mBitmap = (Bitmap) data.getExtras().get("data");
            input = Uri.fromFile(photoFile);
            output = Uri.fromFile(createImageFile());
            Crop.of(input, output).asSquare().start(mActivity);

        } else if (requestCode == REQUEST__FILE) {
            Uri selectedImageUri = data.getData();
            output = Uri.fromFile(createImageFile());
            Crop.of(selectedImageUri, output).asSquare().start(mActivity);
        } else if (requestCode == Crop.REQUEST_CROP) {
            try {
//                iv.getContext().getExternalCacheDir() + "/userImage.jpg"
                ExifInterface exif = new ExifInterface(createImageFile().getAbsolutePath());
                String exifOrientation = exif
                        .getAttribute(ExifInterface.TAG_ORIENTATION);

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), output);
                bitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);

                float degree;
                if (bitmap != null) {
                    degree = Utils.getDegree(exifOrientation);
                    if (degree != 0)
                        bitmap = Utils.createRotatedBitmap(bitmap, degree);
                }
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                mBitmap = bitmap;
                iv.setImageBitmap(bitmap);
                mIsImageSelected = true;

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return mBitmap;
    }


    private File createImageFile(){
        File image = null;
        try {
            image = File.createTempFile("JPEG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_", ".jpg", new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera"));
            mCurrentPhotoPath = image.getAbsolutePath();
            Log.e(TAG, "===========================mCurrentPhotoPath=======" + mCurrentPhotoPath);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            Log.e("EXCEPTION >> ", "" + ex.toString());
        }
        return image;
    }

    public String getBase64String() {
        String mBase64PictureString = "";

        if (mBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream); //compress to which format you want.
            byte[] byte_arr = stream.toByteArray();
            mBase64PictureString = Base64.encodeToString(byte_arr, Base64.DEFAULT);
            return mBase64PictureString;
        } else {
            return "";
        }
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }


    public Uri getOutput(){
        return output;
    }
}
