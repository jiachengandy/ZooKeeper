package com.yhaitao.config.util;

import java.util.Properties;

/**
 * 配置文件解析工具。
 * @author yanghaitao
 *
 */
public class PropertiesUtil {
	/**
	 * 解析数据。
	 * @param data zk更新的配置数据
	 * @return 配置文件
	 */
	public static Properties parse(byte[] data) {
		/** 根据回车拆分原始数据 **/
		String propertiesData = new String(data);
		String[] split = propertiesData.split("\\\n");
		Properties properties = new Properties();
		for(String keyValue : split) {
			/** 根据等号拆分每一行数据 **/
			String[] split2 = keyValue.split("\\=");
			/** 以#号大头的是注释行 **/
			if(!keyValue.startsWith("#") 
					&& split2 != null && split2.length >= 2) {
				if(split2.length == 2) {
					properties.setProperty(split2[0].trim(), split2[1].trim());
				}
				else {
					properties.setProperty(split2[0].trim(), packStringArray(split2));
				}
			}
		}
		return properties;
	}
	
	/**
	 * 等号拼接出去第0个字符串的所有字符串
	 * @param split 被等号拆分的字符串数据
	 * @return
	 */
	private static String packStringArray(String[] split) {
		int size = split.length;
		StringBuffer sb = new StringBuffer();
		for(int i = 1; i < size; i++) {
			sb.append(split[i]);
			if((size - 1) != i) {
				sb.append("=");
			}
		}
		return sb.toString();
	}
}
