package com.yuang.library.utils;

import android.util.Base64;

public class EncodeProvider {
	
	/**
	 * Base64 encode/decode
	 * @param data
	 * @return
	 */
	public static byte[] encodeBase64(byte[] data) {
		return Base64.encode(data, Base64.NO_WRAP);
	}
	
	public static String encodeBase64String(byte[] data) {
		return encodeBase64String(data, false);
	}

	public static String encodeBase64String(byte[] data, boolean wrap) {
		String result = Base64.encodeToString(data, Base64.NO_WRAP);
		if (wrap) {
			StringBuffer sb = new StringBuffer();
			int len = result.length();
			int start = 0;
			while (start < len) {
				if (start + 72 < len) {
					sb.append(result.substring(start, start + 72));
					start += 72;
				} else {
					sb.append(result.substring(start, len));
					start = len;
				}
				sb.append("\r\n");
			}

			return sb.toString();
		}

		return result;
	}

	public static byte[] decodeBase64(byte[] data) {
		return Base64.decode(data, Base64.NO_WRAP);
	}

	public static byte[] decodeBase64(String data) {
		return Base64.decode(data, Base64.NO_WRAP);
	}
	
	/**
	 * Hex Encode/Decode
	 */
	public static String encodeHex(byte[] data, boolean upper){
		StringBuffer sb =new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			sb.append(String.format("%02X", data[i]));
		}
		
		if (upper){
			return sb.toString();
		}else{
			return sb.toString().toLowerCase();
		}
	}

	private static byte char2byte(byte c){
		if (c >= 'a' && c <= 'f'){
			return (byte) (c - 'a' + 10);
		}else if (c <= 'F' && c >= 'A'){
			return (byte) (c - 'A' + 10);
		}else if (c <= '9' && c >= '0'){
			return (byte) (c - '0');
		}else{
			return 0;
		}
	}
	public static byte[] decodeHex(String text){
		byte[] data = text.getBytes();
		int len = data.length;
		len /= 2;
		byte[] ret = new byte[len];

		for (int i = 0; i < len; i++) {
			int high = char2byte(data[2*i]);
			int low = char2byte(data[2*i+1]);
			ret[i] = (byte) ((high << 4) | low);
		}

		return ret;
	}
}
