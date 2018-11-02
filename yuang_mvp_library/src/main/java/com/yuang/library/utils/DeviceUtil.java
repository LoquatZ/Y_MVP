package com.yuang.library.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DeviceUtil {
	static Context sContext = null;

	public static void init(Context c) {
		sContext = c;
	}

	/**
	 * IMEI
	 *
	 * @param
	 * @return
	 */
	static String sDeviceId = null;

	public static String getPackname(){
		return sContext.getPackageName();
	}

	public static String getIMEI() {
		if (sDeviceId == null) {
			String value = null;
			try {
				TelephonyManager tm = (TelephonyManager) sContext.getSystemService(Context.TELEPHONY_SERVICE);
				value = tm.getDeviceId();
			} catch (Exception e) {
			}

			sDeviceId = value;
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < sDeviceId.length(); i++) {
				char ch = sDeviceId.charAt(i);
				if (ch > '9' || ch < '0') {
					ch = (char) ('0' + (((int) ch) % 10));
				}
				sb.append(ch);
			}

			sDeviceId = sb.toString();
		}

		return sDeviceId;
	}

	/**
	 * IMSI
	 * @return
	 */
	public static String getIMSI() {
		String value = "";
		TelephonyManager tm = (TelephonyManager) sContext.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm != null) {
			value = tm.getSubscriberId();
		}

		if (value == null) {
			value = "";
		}

		return value;
	}

	public static String getUUID() {
		String ANDROID_ID = Settings.Secure.getString(sContext.getContentResolver(), Settings.Secure.ANDROID_ID);
		if (ANDROID_ID == null){
			ANDROID_ID = "";
		}
		String imei = getIMEI();
		String r = imei + ANDROID_ID;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(r.getBytes());
			byte[] dig = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : dig){
				sb.append(String.format("%02x", b));
			}
			
			
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		
		if (TextUtils.isEmpty(imei)){
			if (TextUtils.isEmpty(ANDROID_ID)){
				return getIMSI();
			}
			
			return ANDROID_ID;
		}
		
		return imei;
	}
	
	
	public static String getChannel() {
		ApplicationInfo appInfo;
		try {
			appInfo = sContext.getPackageManager().getApplicationInfo(
					sContext.getPackageName(), PackageManager.GET_META_DATA);
			if (appInfo.metaData != null) {
				String value = appInfo.metaData.getString("DIST_CHANNEL");
				return value;
			}
		} catch (Exception e) {
		}
		return "";
	}
}
