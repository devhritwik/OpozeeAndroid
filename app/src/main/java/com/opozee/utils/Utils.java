package com.opozee.utils;

import android.animation.Animator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.opozee.R;
import com.opozee.pojo.LoginResponse;
import com.opozee.pojo.ProfileResponse;
import com.opozee.pojo.loginemail.LoginEmail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TimeZone;
import java.util.TreeMap;

public class Utils {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final int MONTH_MILLS = 30 * DAY_MILLIS;
    private static final int YEAR_MILLS = 365 * MONTH_MILLS;


    public static final String MIXPANEL_TOKEN = "414c39b7ab192ac62a36f71bfe023d32";
    public static final String DEFAULT_PROFILE_PIC_URL = "https://opozee.com:81/Content/Upload/ProfileImage/oposee-profile.png";

    public static ProgressDialog mProgressDialog;
    private static String givenDate;

    /* formatting the likes counts and dislike counts*/
    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }


    //get the user id of the user who have logged in
    public static String getLoggedInUserId(Context context) {
        return AppSP.getInstance(context).readInt(AppGlobal.USER_ID) + "";
    }

    public static String getsortedorder(Context context) {
        return AppSP.getInstance(context).readInt(AppGlobal.SORT_ORDER) + "";
    }

    public static String formatConversion(double ServicePrice) {
        DecimalFormat form = new DecimalFormat("0.00");
        String price = form.format(ServicePrice);
        return price;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    //to capitalize each first alphabet of the word
    public static String capitalize(@NonNull String input) {

        String[] words = input.toLowerCase().split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (isEmpty(word)) continue;
            if (i > 0 && word.length() > 0) {
                builder.append(" ");
            }

            String cap = word.substring(0, 1).toUpperCase() + word.substring(1);
            builder.append(cap);
        }
        return builder.toString();
    }

    public static void slideUpVisibility(Context mContext, View view, int visibilityStatus) {
        Animation animate = AnimationUtils.loadAnimation(mContext, R.anim.slide_alert_in);
        animate.setDuration(1000);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(visibilityStatus);
    }

    public static void slideDownVisibility(Context mContext, View view, int visibilityStatus) {
        Animation animate = AnimationUtils.loadAnimation(mContext, R.anim.slide_alert_out);
        animate.setDuration(1000);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(visibilityStatus);
    }

    //animations for the buttons
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void circularReveal(final Button button, final ProgressBar pBar) {
        int x = button.getRight() / 2;
        int y = button.getBottom() / 2;

        int startRadius = (int) Math.hypot(button.getWidth() / 2, button.getHeight() / 2);
        ;
        int endRadius = 0;

        Animator anim = ViewAnimationUtils.createCircularReveal(button, x, y, startRadius, endRadius);
        anim.setDuration(500);
//        main_relative.setVisibility(View.VISIBLE);

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                pBar.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
                button.clearAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        anim.start();
    }

    public static void saveUserDataInSharePreferences(Context context, LoginResponse.UserData userData) {
        AppSP sp = AppSP.getInstance(context);

        sp.savePreferences(AppGlobal.USER_ID, userData.getUserID());
        sp.savePreferences(AppGlobal.Email, userData.getEmail());
        sp.savePreferences(AppGlobal.UserName, userData.getUserName());
        sp.savePreferences(AppGlobal.FirstName, userData.getUserName());
        sp.savePreferences(AppGlobal.LastName, userData.getUserName());
        sp.savePreferences(AppGlobal.IS_LOGGED_IN, true);
//        sp.savePreferences(AppGlobal.UserRole, userProfile.getResponse().getUserData().getUserRole());
        sp.savePreferences(AppGlobal.Photo, userData.getImageURL());
    }

    public static void saveemailuserdata(Context context, LoginEmail loginEmail) {
        AppSP sp = AppSP.getInstance(context);

        sp.savePreferences(AppGlobal.USER_ID, Integer.parseInt(loginEmail.getData().getId()));
        sp.savePreferences(AppGlobal.Email, loginEmail.getData().getEmail());
        sp.savePreferences(AppGlobal.UserName, loginEmail.getData().getUserName());
        sp.savePreferences(AppGlobal.FirstName, "");
        sp.savePreferences(AppGlobal.LastName, "");
        sp.savePreferences(AppGlobal.IS_LOGGED_IN, true);
//        sp.savePreferences(AppGlobal.UserRole, userProfile.getResponse().getUserData().getUserRole());
        sp.savePreferences(AppGlobal.Photo, loginEmail.getData().getImageURL());
    }

    public static void savesortedata(Context context, int sort) {
        AppSP appSP = AppSP.getInstance(context);
        appSP.savePreferences(AppGlobal.SORT_ORDER, sort);
    }

    //for customToast
    public static void showCustomToast(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
//        Toast toast = new Toast(activity);
//        View v = activity.getLayoutInflater().inflate(R.layout.custom_toast, null);
//        TextView tvMessage = (TextView) v.findViewById(R.id.tvMessage);
//        tvMessage.setText(msg);
//        toast.setView(v);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.show();
    }

    public static boolean isNetworkAvail(Context context) {
        boolean flag = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();

            if (info.getType() == ConnectivityManager.TYPE_MOBILE || info.getType() == ConnectivityManager.TYPE_WIFI) {
                System.out.println(info.getTypeName());
                flag = true;
            } else {
                flag = false;
            }

        } catch (Exception exception) {
//            Toast.makeText(context,"Error is "+exception,Toast.LENGTH_LONG).show();
            System.out.println("Exception at network connection....." + exception);
        }

        return flag;
    }

    public static void showProgress(Activity activity) {
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public static void dismissProgress() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (Exception e) {

        }
    }

    public static File savebitmap(Bitmap bmp, String directory) {
//        String extStorageDirectory = Environment.getDownloadCacheDirectory().toString();
        OutputStream outStream = null;
        // String temp = null;
        File file = new File(directory, "temp.png");
        if (file.exists()) {
            file.delete();
            file = new File(directory, "temp.png");
        }
        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    static Bitmap createRotatedBitmap(Bitmap bm, float degree) {
        Bitmap bitmap = null;
        if (degree != 0) {
            Matrix matrix = new Matrix();
            matrix.preRotate(degree);
            bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                    bm.getHeight(), matrix, true);
        }

        return bitmap;
    }

    static float getDegree(String exifOrientation) {
        float degree = 0;
        switch (exifOrientation) {
            case "6":
                degree = 90;
                break;
            case "3":
                degree = 180;
                break;
            case "8":
                degree = 270;
                break;
        }
        return degree;
    }


    //animations for the buttons
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void unReveal(final Button button, final ProgressBar pBar) {
        pBar.setVisibility(View.GONE);
        int x = button.getRight() / 2;
        int y = button.getBottom() / 2;

        int startRadius = 0;
        int endRadius = (int) Math.hypot(button.getWidth(), button.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(button, x, y, startRadius, endRadius);
        anim.setDuration(1000);
//        main_relative.setVisibility(View.VISIBLE);


        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                button.clearAnimation();
                pBar.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        anim.start();

    }

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();

//        if (time > now || time <= 0) {
//            return null;
//        }

        // TODO: localize
        final long diff = now - time;
        Log.d("CurrentUTC=", "" + diff);
        Log.d("CurrentUTC=", "" + time);
        Log.d("CurrentUTC=", "" + now);
        if (diff < MINUTE_MILLIS) {
            return "a few seconds ago";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            long hours = 0;
            String timeago = "";
            double difference = (double) diff / HOUR_MILLIS;
            long differenceint = diff / HOUR_MILLIS;
            double finaldata = differenceint - differenceint;
            if (finaldata > 0.5) {
                hours = (diff / HOUR_MILLIS) + 1;
            } else {
                hours = (diff / HOUR_MILLIS);
            }

            if (hours <= 22) {
                timeago = hours + " hours ago";
            } else if ((diff / HOUR_MILLIS) > 22 && (diff / HOUR_MILLIS) <= 36) {
                timeago = "a day ago";
            }

            return timeago;
        } else if (diff < 48 * HOUR_MILLIS) {
            long hours = 0;
            String timeago = "";
            double difference = (double) diff / HOUR_MILLIS;
            if (difference >= 22 && difference <= 36) {
                timeago = "a day ago";
            }else if(difference>36&&difference<=48){
                timeago = "2 day ago";
            }
            return timeago;
        } else {
            long hours = 0;
            String timeago = "";
            double difference = (double) diff / HOUR_MILLIS;
            if (difference >= 22 && difference <= 36) {
                timeago = "a day ago";
            } else if (difference >= 36 && difference <= 48) {
                timeago = "2 day ago";
            } else if ((diff / DAY_MILLIS) <= 25) {
//                long finaldatas=(long) (difference/DAY_MILLIS);
                double finaldays = (double) diff / DAY_MILLIS;
                long finaldaya = diff / DAY_MILLIS;
                double differencedata=finaldays=finaldaya;
                if(differencedata>0.5){
                    timeago=diff/DAY_MILLIS +1+"days ago";
                }else{
                    timeago = diff / DAY_MILLIS + " days ago";
                }

            } else if ((diff / DAY_MILLIS) > 25 && (diff / DAY_MILLIS) <= 45) {
                timeago = "a month ago";
            } else if ((diff / DAY_MILLIS) > 45 && (diff / DAY_MILLIS) <= 60) {
                timeago = "2 months ago";
            } else if ((diff / DAY_MILLIS) > 60 && (diff / DAY_MILLIS) <= 365) {
                long monthago = (diff / MONTH_MILLS);
                timeago = monthago + " months ago";
            } else if ((diff / DAY_MILLIS) > 365) {
                long yearsago = (diff / YEAR_MILLS);
                timeago = yearsago + " years ago";
            }
//            return diff / DAY_MILLIS + " days ago";
            return timeago;
        }
    }

//
//    0 - 45 seconds	a few seconds ago
//45 - 90 seconds	a minute ago
//90 seconds - 45 minutes	X minutes ago
//45 - 90 minutes	an hour ago
//90 minutes - 22 hours	X hours ago
//22 - 36 hours	a day ago
//36 hours - 25 days	X days ago
//25 - 45 days	a month ago
//45 - 345 days	X months ago
//345 - 545 days (1.5 years)	a year ago
//546 days+	X years ago

    /**
     * change the server time to current time zone
     *
     * @param strTime
     * @return the system time
     */
    public static String getSystemLocalTime(String strTime) {
//        2019-02-28 02:02:09.56
        long timeInMillisec = 0;
        Date d3 = null;
        TimeZone tz = TimeZone.getTimeZone("EST");
        System.out.println(tz.getDisplayName());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(tz);
        Date d1 = null;

        try {
            Log.d("DATE", "date before parse " + strTime);
            d1 = format.parse(strTime);
            Log.e("PARSED DATE", "" + d1);
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            givenDate = format2.format(d1);

            Log.e("Date Given", "d1   >>>   " + givenDate);

        } catch (Exception e) {
            Log.e("exception", e.getMessage());
        }
        return givenDate;

    }

    public static long convertdatestring(String strtime) {
//        String givenDateString = "Tue Apr 23 16:08:28 GMT+05:30 2013";
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(strtime);
            long timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
            return timeInMilliseconds;
        } catch (ParseException e) {
            Log.d("TimeFormat_Log", "Exception=" + e.toString());
            return 0;
        }
    }

    public static String convertESTToLocalTime(String strTime) {


        long timeInMillisec = 0;
        Date d3 = null;
        TimeZone tz = TimeZone.getTimeZone("EST");
        System.out.println(tz.getDisplayName());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(tz);
        Log.d("TimeFormat_Log", "Method=" + tz);
        Date d1 = null;

        try {
            Log.d("DATE", "date before parse " + strTime);
            d1 = format.parse(strTime);
            Log.e("PARSED DATE", "" + d1);
//            Mar 29 '13 at 16:44
            SimpleDateFormat format2 = new SimpleDateFormat("dd MMM, yy-HH:mm");
            givenDate = format2.format(d1);

            Log.e("Date Given", "d1   >>>   " + givenDate);
            return givenDate;
        } catch (Exception e) {
            Log.e("exception", e.getMessage());
        }

        return null;

    }

    public static Date getUTCLocalTime(String strTime) {

        long timeInMillisec = 0;
        Date d3 = null;
        TimeZone tz = TimeZone.getTimeZone("UTC");
        System.out.println(tz.getDisplayName());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d4 = null;
        SimpleDateFormat zoneformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        zoneformat.setTimeZone(tz);
//        Log.e("WITHOUT FORMAT DATE> ",""+strTime);
        Date d1 = null;
        Date d2 = new Date();
//        Log.e("Current Date >> ","d2   >>>  "+d2);

        try {
            Log.d("DATE", "date before parse " + strTime);
            d1 = format.parse(strTime);
            Log.d("PARSED DATE", "" + d1);
            d3 = new Date(d1.getTime() + TimeZone.getDefault().getOffset(d2.getTime()));
            Log.e("d3 >>> ", " " + d3);
            String[] dateArray = d3.toString().split(" ");
            String dateStr = dateArray[0] + " " + dateArray[1] + " " + dateArray[2] + " " + dateArray[3] + " " + dateArray[5];
//            "Tue Jul 26 12:23:32 GMT+05:30 2016
            SimpleDateFormat format1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");
//            Date d3 = format.parse(zoneformat.format(d2));
            d4 = format1.parse(dateStr);
            Log.e("d4 >>> ", "" + d4);
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String givenDate = format2.format(d4);

//            Log.e("Date Given","d1   >>>   "+givenDate);
//            Log.e("Current Date >> ","d2   >>>  "+d2);

            timeInMillisec = d4.getTime();
        } catch (Exception e) {
            Log.e("exception", e.getMessage());
        }
        return d1;

    }

    public static String getlocaltime(String dates) {
//        String dateStr = "Jul 16, 2013 12:08:59 AM";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(dates);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = df.format(date);
        return formattedDate;

    }

    public static void saveDataInSharePreferencesPreferences(ProfileResponse loginSignupResponse, Object o) {

    }
}
