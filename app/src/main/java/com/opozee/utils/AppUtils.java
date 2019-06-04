package com.opozee.utils;

import android.content.Context;

public class AppUtils {

	// private Context mContext;

	// public AppUtils(Context context) {
	// mContext = context;
	// mSharePreferences = AppSP.getInstance(mContext);
	// }

	public static boolean isLoggedIn(Context context) {
		AppSP mSharePreferences;
		mSharePreferences = AppSP.getInstance(context);
		return mSharePreferences.readBool(AppGlobal.IS_LOGGED_IN, false);
	}

	public static String getUserRole(Context context) {
		AppSP mSharePreferences;
		mSharePreferences = AppSP.getInstance(context);
		return mSharePreferences.readString("UserRole");
	}
}
