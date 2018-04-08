package com.yuang.library.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class DigestProvider {
	
	 private static byte[] digest(String algorithm, InputStream is){
		 try {
	         MessageDigest md = MessageDigest.getInstance(algorithm);
	         int len = 0;
	         byte[] buf = new byte[1024];
	         do{
	        	 len = is.read(buf);
	        	 if (len > 0){
	        		 md.update(buf, 0, len);
	        	 }
	         }while(len >= 0);
	         return md.digest();
	     } catch (Exception e) {
	     }

		 return null;
	 }
	 
	 public static byte[] digestText(String algorithm, String text){
		 return digestText(algorithm, text.getBytes());
	 }
	 
	 public static byte[] digestText(String algorithm, byte[] data){
		 return digestText(algorithm, new ByteArrayInputStream(data));
	 }
	 
	 public static byte[] digestText(String algorithm, InputStream is){
		 return digest(algorithm, is);
	 }

	 public static byte[] hmacDigest(String algorithm, byte[] secret, InputStream is){
		try {
			Mac hmac = Mac.getInstance(algorithm);
			SecretKeySpec secret_key = new SecretKeySpec(secret, algorithm);
			hmac.init(secret_key);

			int len = 0;
			byte[] buf = new byte[1024];
			do{
				len = is.read(buf);
				if (len > 0){
					hmac.update(buf, 0, len);
				}
			}while(len >= 0);
			return hmac.doFinal();
		}catch (Exception e){
			e.printStackTrace();
		}

		return null;
	 }

	public static byte[] hmacDigestText(String algorithm, byte[] secret, String text){
		return hmacDigestText(algorithm, secret, text.getBytes());
	}

	public static byte[] hmacDigestText(String algorithm, byte[] secret, byte[] data){
		return hmacDigest(algorithm, secret, new ByteArrayInputStream(data));
	}

	public static byte[] hmacDigestText(String algorithm, byte[] secret, InputStream is){
		return hmacDigest(algorithm, secret, is);
	}
}
