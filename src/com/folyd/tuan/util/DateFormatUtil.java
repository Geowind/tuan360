package com.folyd.tuan.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatUtil {
	static long DAY_SEC = 86400;// 24*60*60
	static long HOUR_SEC = 3600;// 60*60

	public static String format(long time) {
		int day, hour, minute;
		day = (int) (time / DAY_SEC);
		time = time % DAY_SEC;
		hour = (int) (time / HOUR_SEC);
		time = time % HOUR_SEC;
		minute = (int) (time / 60);

		return day + "天" + hour + "时" + minute + "分";
	}

	public static String getDateTime(long longTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA);
		return sdf.format(new Date(longTime));
	}

}
