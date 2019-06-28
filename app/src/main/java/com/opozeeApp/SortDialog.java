package com.opozeeApp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.opozeeApp.utils.Utils;

public class SortDialog {
    public static Context context;

    public void showDialog(final Activity activity){
        context=activity;
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sortbackgrounddialog);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.transparentborder);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        lp.windowAnimations = R.style.SortDialogAnimation;
        dialog.getWindow().setAttributes(lp);

//        TextView tv_about = (TextView) dialog.findViewById(R.id.tv_about_us);
//        tv_about.setText(msg);

        ConstraintLayout cl_Photos=dialog.findViewById(R.id.cl_lastreaction);
        cl_Photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Utils.savesortedata(activity, 0);
                activity.finish();
                activity.startActivity(activity.getIntent());

            }
        });

        ConstraintLayout cl_cancelDialog = dialog.findViewById(R.id.cl_mostreactions);
        cl_cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Utils.savesortedata(activity, 1);
                activity.finish();
                activity.startActivity(activity.getIntent());
            }
        });

        dialog.show();

        ConstraintLayout cl_wallpaperLibrary=dialog.findViewById(R.id.cl_leatreactions);
        cl_wallpaperLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Utils.savesortedata(activity, 2);
                activity.finish();
                activity.startActivity(activity.getIntent());
            }
        });

    }
}
