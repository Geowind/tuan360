package com.folyd.tuan.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {

	public static byte[] readInputStream(InputStream is) throws IOException {
		System.out
				.println("StreamUtil  byte[] readInputStream(InputStream is)");
		byte[] data = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = is.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();
		baos.close();
		is.close();

		data = baos.toByteArray();
		return data;
	}

}
