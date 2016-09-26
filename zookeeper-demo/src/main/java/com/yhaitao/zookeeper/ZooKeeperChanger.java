package com.yhaitao.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 修改ZooKeeper节点数据。
 * @author yanghaitao
 *
 */
public class ZooKeeperChanger {
	/**
	 * 修改ZooKeeper信息
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		/** 建立zookeeper链接 **/
		String zkList = "hdp1:2181,hdp2:2181,hdp3:2181";
		ZooKeeper zooKeeper = new ZooKeeper(zkList, 10000, new Watcher() {
			public void process(WatchedEvent event) {
				 // TODO Auto-generated method stub
			}
		});
		
		/** 创建节点设置数据 **/
		String node = "/root/sub004";
		String data = "12345678911";
		
		/** 创建根节点 **/
		if(null == zooKeeper.exists("/root", false)) {
			zooKeeper.create("/root", null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		
		/** 创建子节点： 触发（监听:子节点变化） **/
		if(null == zooKeeper.exists(node, false)) {
			zooKeeper.create(node, data.getBytes("UTF-8"), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		
		/** 删除子节点： 触发（监听:子节点变化） **/
		zooKeeper.delete(node, -1);
		
		/** 设置数据： 触发（监听:节点数据变化） **/
		zooKeeper.setData("/root/sub003", "123456789".getBytes("UTF-8"), -1);
		
		/** 关闭链接 **/
		zooKeeper.close();
	}
}
