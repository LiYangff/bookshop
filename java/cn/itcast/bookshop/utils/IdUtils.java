package cn.itcast.bookshop.utils;

import java.util.UUID;

public class IdUtils {
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
}
