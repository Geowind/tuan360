package com.folyd.tuan.util;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;

/**
 * 格式化字符串
 * @author Administrator
 *
 */
public class SpanUtil {
    /**
     * 高亮字符串中特定字段
     * @param textValue
     * @param color
     * @param start
     * @param end
     * @return
     */
	public static SpannableString highLight(String textValue, String color,
			int start, int end) {
		SpannableString span = new SpannableString(textValue);
		span.setSpan(new ForegroundColorSpan(Color.parseColor(color)), 0, end,
				SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);//包含边界
//		SpannableString.SPAN_INCLUSIVE_INCLUSIVE;
//		(0,1) [0,1] (0,1] [0,1)
		//exclusive 
		return span;
	}

	/**
	 * 给字符串特定字段加下划线ar
	 * @param deleteValue
	 * @param start
	 * @param end
	 * @return
	 */
	public static SpannableString deleteStyle(String deleteValue, int start,
			int end) {
		SpannableString span = new SpannableString(deleteValue);
		span.setSpan(new StrikethroughSpan(), start, end,
				SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
		return span;
	}

}
