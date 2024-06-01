package cn.itcast.bookshop.utils;

import java.util.UUID;

public class FileUploadutils {
	public static String subFileName(String fileName) {
		int index = fileName.lastIndexOf("\\");
		if (index == -1) {
			return fileName;
		}
		return fileName.substring(index + 1);
	}

	// 获得随机UUID文件名
	public static String generateRandomFileName(String fileName) {
		int index = fileName.lastIndexOf(".");
		//if (index != 1) { 2023年6月  是这个  当时没报错，2023年12月 发生报错 进行修改
		if (index != -1) {
			String ext = fileName.substring(index);
			return UUID.randomUUID().toString() + ext;
		}
		return UUID.randomUUID().toString();
	}

	// 获得hashcode生成二级目录
	public static String generateRandomDir(String uuidFileName) {
		int hashCode = uuidFileName.hashCode();
		//一级目录
		int d1 = hashCode & 0xf;
		//二级目录
		int d2 = (hashCode >> 4) & 0xf;
		return "/" + d1 + "/" + d2;
	}
}
