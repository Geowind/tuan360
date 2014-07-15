package com.folyd.tuan.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 获取MD5值
 * 
 * @author Folyd
 * 
 */
public class MD5 {

	public static String getMD5(String content) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(content.getBytes());
			return getHashString(digest);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getHashString(MessageDigest digest) {
		StringBuilder builder = new StringBuilder();
		for (byte b : digest.digest()) {
			builder.append(Integer.toHexString((b >> 4) & 0xf));
			builder.append(Integer.toHexString(b & 0xf));
		}
		return builder.toString();
	}

	public static String md5(String s) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");

		md.update(s.getBytes());

		byte digest[] = md.digest();
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < digest.length; i++) {
			result.append(Integer.toHexString(0xFF & digest[i]));
		}

		return (result.toString());
	}
}
