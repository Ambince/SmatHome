package com.xiaye.smarthome.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class GenerationId {
	
	static Format format = new SimpleDateFormat("yyMMddHHmmssSS");
	static Date date = new Date();
	static AtomicInteger atom = new AtomicInteger();

	public static String getId() {
		String id = format.format(date) + atom.incrementAndGet();
		return id;
	}
}
