package com.article.poetry.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.RandomStringUtils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PoetryUtil {

	
	/**
	 * Generate Random Id
	 * @return
	 */
	public static String generateUniqueId() {
		return RandomStringUtils.randomAlphanumeric(5) + (new SimpleDateFormat("yyyymmddHHMMSSsss")).format(Calendar.getInstance().getTime());
	}
}
