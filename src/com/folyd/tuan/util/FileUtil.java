package com.folyd.tuan.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FileUtil {

	public static void writeBitmapAsFile(String diskCacheDir, String key,
			Bitmap bitmap) throws IOException {
		key = key.substring(key.lastIndexOf("/") + 1);
		File file = new File(diskCacheDir + key);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
		}
	}

	public static Bitmap readBitmapFileByPath(String diskCacheDir, String key) {
		Bitmap bitmap = null;
		key = key.substring(key.lastIndexOf("/") + 1);
		bitmap = BitmapFactory.decodeFile(diskCacheDir + key);
		return bitmap;
	}

}
