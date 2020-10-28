package com.util;

public class StringUtil {

	public static boolean isBlank(String str) {
		return "".equals(str) || str == null;
	}
	
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
}
