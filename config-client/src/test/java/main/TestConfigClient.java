package main;

import com.yhaitao.config.ConfigClient;
import com.yhaitao.config.ConfigClientLisenter;

public class TestConfigClient {
	public static void main(String[] args) {
		String zkList = "hdp1:2181,hdp2:2181,hdp3:2181";
		String configPath = "/cst_etl_mongo2hive";
		
		ConfigClient client = new ConfigClient();
		client.setZkList(zkList);
		client.setConfigPath(configPath);
		client.setConfigLisenter(new ConfigClientLisenter() {
			public void update(String key, String oldValue, String newValue) {
				System.out.println("update key : " + key + ", oldValue : " + oldValue + ", newValue : " + newValue);
			}
			public void remove(String key, String value) {
				System.out.println("remove key : " + key + ", value : " + value);
			}
			public void add(String key, String value) {
				System.out.println("add key : " + key + ", value : " + value);
			}
		});
		client.start();
		
	}
}
