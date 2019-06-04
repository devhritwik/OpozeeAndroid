package com.opozee.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSP {
	/**
	 * data members
	 */
	public SharedPreferences instanceSP;

	private static AppSP instance = null;

	// private Context mContext;

	/**
	 * Constructor
	 * 
	 * @param context
	 */
	// private Constructor to make it singleton
	private AppSP(Context context) {
		// this.mContext = context;
		instanceSP = (SharedPreferences) context.getSharedPreferences(AppGlobal.sharedPrefName, Context.MODE_PRIVATE);
		// this.mContext = context;
		instanceSP = PreferenceManager.getDefaultSharedPreferences(context
				.getApplicationContext());
	}

	/**
	 * methods
	 * 
	 */
	// get instance to insure only single instance is exist
	public static AppSP getInstance(Context context) {

		if (null == instance) {
			instance = new AppSP(context);
		}

		return instance;
	}

	// read integer preferences
	public int readInt(String key) {
		return instanceSP.getInt(key, -1);
	}
	
	public int readInt(String key, int value) {
		return instanceSP.getInt(key, value);
	}

	// read String preferences
	public String readString(String key) {
		return instanceSP.getString(key, "");
	}

	// read String preferences
	public String readString(String key, String value) {
		return instanceSP.getString(key, value);
	}

	// read boolean preferences and default value will be false
	public boolean readBool(String key, boolean defaultVal) {
		return instanceSP.getBoolean(key, defaultVal);
	}

	// save String preferences
	public boolean savePreferences(String key, String value) {
		return instanceSP.edit().putString(key, value).commit();
	}

	// save integer preferences
	public boolean savePreferences(String key, int value) {
		return instanceSP.edit().putInt(key, value).commit();
	}

	// save boolean preferences
	public boolean savePreferences(String key, boolean value) {
		return instanceSP.edit().putBoolean(key, value).commit();
	}

	// save boolean preferences
	public void clear() {
		instanceSP.edit().clear().commit();
	}
}
