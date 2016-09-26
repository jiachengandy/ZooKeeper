package com.yhaitao.zookeeper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * ZooKeeper节点监听器。
 * @author yanghaitao
 *
 */
public class ZooKeeperListener {
	/**
	 * ZooKeeper链接。
	 */
	private ZooKeeper zooKeeper;

	/**
	 * 获取数据状态。
	 */
	private Stat stat = new Stat();

	/**
	 * 子节点列表。
	 */
	private volatile List<String> nodeList;

	/**
	 * 初始化ZooKeeper监听器。
	 * @param zkList ZK集群地址
	 * @throws IOException
	 */
	public ZooKeeperListener(String zkList) throws Exception {
		/** 链接zookeeper并设置监听器 **/
		zooKeeper = new ZooKeeper(zkList, 5000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
			}
		});
		
		/** 监听:子节点变化 **/
		zooKeeper.getChildren("/root", new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				/** 监听子节点的变化  **/
				if (event.getType() == EventType.NodeChildrenChanged) {
					updateChild();
				}
			}
		}, null);
		
		/** 监听:节点数据变化 **/
		zooKeeper.getData("/root/sub003", new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				/** 监听其数据的变化  **/
				if(event.getType() == EventType.NodeDataChanged) {
					updateData();
				}
			}
		}, null);
	}
	
	/**
	 * 节点数据变化操作
	 */
	public void updateData() {
		try {
			byte[] data = zooKeeper.getData("/root/sub003", false, new Stat());
			System.out.println("NodeDataChanged : " +  new String(data, "utf-8"));
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 子节点变化操作。
	 */
	private void updateChild() {
		try {
			/** 获取子节点列表 **/
			List<String> subList = zooKeeper.getChildren("/root", true);
			/** 获取子节点下的数据 **/
			List<String> newNodeList = new ArrayList<String>();
			for (String subNode : subList) {
				byte[] data = zooKeeper.getData("/root/" + subNode, false, stat);
				newNodeList.add(new String(data, "utf-8"));
			}
			/** 子节点下的数据 **/
			nodeList = newNodeList;
			System.out.println("server list updated: " + nodeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 监听入口。
	 */
	public static void main(String[] args) {
		/** 启动监听器 **/
		String zkList = "hdp1:2181,hdp2:2181,hdp3:2181";
		try {
			new ZooKeeperListener(zkList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/** 永久等待，监听zk **/
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
