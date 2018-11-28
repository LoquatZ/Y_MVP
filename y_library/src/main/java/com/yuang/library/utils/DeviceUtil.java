package com.yuang.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import timber.log.Timber;

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

	@SuppressLint({"MissingPermission", "HardwareIds"})
	public static String getIMEI() {
		if (TextUtils.isEmpty(sDeviceId)) {
			String value = null;
			try {
				TelephonyManager tm = (TelephonyManager) sContext.getSystemService(Context.TELEPHONY_SERVICE);
				if (tm != null) {
					value = tm.getDeviceId();
				}
			} catch (Exception e) {
				Timber.e(e);
			}

			sDeviceId = value;
			StringBuffer sb = new StringBuffer();
			if (sDeviceId != null) {
				for (int i = 0; i < sDeviceId.length(); i++) {
					char ch = sDeviceId.charAt(i);
					if (ch > '9' || ch < '0') {
						ch = (char) ('0' + (((int) ch) % 10));
					}
					sb.append(ch);
				}
			}
			sDeviceId = sb.toString();
		}
		return sDeviceId;
	}

	/**
	 * IMSI
	 * @return
	 */
	@SuppressLint({"MissingPermission", "HardwareIds"})
	public static String getIMSI() {
		String value = "";
		try {
			TelephonyManager tm = (TelephonyManager) sContext.getSystemService(Context.TELEPHONY_SERVICE);
			if (tm != null) {
				value = tm.getSubscriberId();
			}
			if (value == null) {
				value = "";
			}
		} catch (Exception e) {
			Timber.e(e);
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
