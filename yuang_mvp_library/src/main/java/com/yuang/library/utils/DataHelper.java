package com.yuang.library.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class DataHelper {
	public static final String pref_file_name = "settings";

	public static String getStringValue(Context context, String key,
			String defaultValue) {
		SharedPreferences preferences = context.getSharedPreferences(
				pref_file_name, Context.MODE_MULTI_PROCESS);
		return preferences.getString(key, defaultValue);
	}

	public static void setStringValue(Context context, String key, String value) {
		SharedPreferences preferences = context.getSharedPreferences(
				pref_file_name, Context.MODE_MULTI_PROCESS);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static int getIntValue(Context context, String key, int defaultValue) {
		SharedPreferences preferences = context.getSharedPreferences(
				pref_file_name, Context.MODE_MULTI_PROCESS);
		return preferences.getInt(key, defaultValue);
	}

	public static void setIntValue(Context context, String key, int value) {
		SharedPreferences preferences = context.getSharedPreferences(
				pref_file_name, Context.MODE_MULTI_PROCESS);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static long getLongValue(Context context,String key, long defaultValue){
		SharedPreferences preferences = context.getSharedPreferences(
				pref_file_name, Context.MODE_MULTI_PROCESS);
		return preferences.getLong(key, defaultValue);
	}
	public static void setLongValue(Context context,String key,long value){
		SharedPreferences preferences = context.getSharedPreferences(
				pref_file_name, Context.MODE_MULTI_PROCESS);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static float getFloatValue(Context context, String key,
			float defaultValue) {
		SharedPreferences preferences = context.getSharedPreferences(
				pref_file_name, Context.MODE_MULTI_PROCESS);
		return preferences.getFloat(key, defaultValue);
	}

	public static void setFloatValue(Context context, String key, float value) {
		SharedPreferences preferences = context.getSharedPreferences(
				pref_file_name, Context.MODE_MULTI_PROCESS);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	public static boolean getBooleanValue(Context context, String key) {
		return getBooleanValue(context, key, false);
	}
	
	public static boolean getBooleanValue(Context context, String key, boolean defaultValue) {
		SharedPreferences preferences = context.getSharedPreferences(
				pref_file_name, Context.MODE_MULTI_PROCESS);
		return preferences.getBoolean(key, defaultValue);
	}

	public static void setBooleanValue(Context context, String key,
			boolean value) {
		SharedPreferences preferences = context.getSharedPreferences(
				pref_file_name, Context.MODE_MULTI_PROCESS);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
}
