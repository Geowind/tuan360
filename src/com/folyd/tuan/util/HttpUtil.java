package com.folyd.tuan.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class HttpUtil {
	/**
	 * POST 方式请求服务器端数据
	 * 
	 * @param url
	 * @param params
	 * @return 以字节数组类型返回数据结果
	 * @throws IOException
	 */
	public static byte[] post(String url, HashMap<String, String> params)
			throws IOException {
		byte[] data = null;
		URL mUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
		conn.setRequestMethod("POST");
		conn.setReadTimeout(5000);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		byte[]  content  = getContent(params);
		// 设置请求头信息
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		// 计算数据字节
		conn.setRequestProperty("Content-Length", content.length + "");
		// 打字节输出流
		OutputStream out = conn.getOutputStream();
		out.write(content);
		out.flush();
		out.close();
		if (conn.getResponseCode() == 200) {
			// 解析输入流
			System.out.println("conn.getResponseCode() == 200");
			InputStream is = conn.getInputStream();
			data = StreamUtil.readInputStream(is);
		}
		return data;
	}
	
		private static byte[] getContent(HashMap<String, String> params)
			throws UnsupportedEncodingException {
		Set<Entry<String, String>> set = params.entrySet();
		StringBuilder builder = new StringBuilder();
		for (Entry<String, String> item : set) {
			builder.append(item.getKey()).append("=")
					.append(URLEncoder.encode(item.getValue(), "UTF-8"))
					.append("&");
		}
		String content = null;
		if (builder.length() > 0) {
			content = builder.substring(0, builder.length() - 1);
		}
		System.out.println("content:" + content);
		return content.getBytes();
	}

	/**
	 * 以POST 的方式请求服务器端的数据
	 * 
	 * @param url
	 * @param params
	 * @param charSet
	 * @return 以String 类型返回数据结果
	 * @throws IOException
	 */
	public static String post(String url, HashMap<String, String> params,
			String charSet) throws IOException {
		byte[] result = post(url, params);
		return new String(result, charSet);
	}

	/**
	 * GET 请求方式获取服务器端数据
	 * 
	 * @param url
	 * @return 返回byte 数组
	 * @throws IOException
	 */
	public static byte[] get(String url) throws IOException {
		System.out.println("HttpUtil  byte[] get(String url)");
		byte[] data = null;
		URL mUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
		conn.setRequestMethod("GET");
		conn.setReadTimeout(5000);
		if (conn.getResponseCode() == 200) {
			System.out.println("conn.getResponseCode() == 200");
			InputStream is = conn.getInputStream();
			data = StreamUtil.readInputStream(is);
		}
		return data;
	}
	/**
	 * GET 请求方式获取服务器端数据
	 * 
	 * @param url
	 * @param charSet
	 * @return 以String形式返回
	 * @throws IOException
	 */
	public static String get(String url, String charSet) throws IOException {
		byte[] data = get(url);
		return new String(data, charSet);
	}


}
