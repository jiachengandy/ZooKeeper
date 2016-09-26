package com.yhaitao.zookeeper.util;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;

/**
 * ZooKeeper链接工具。
 * <p>服务启动时发现报如下错误：org.apache.zookeeper.KeeperException$ConnectionLossException: KeeperErrorCode = ConnectionLoss。
 * error是在调用zk.exists（）时抛出的。 * 于是开始google，发现问题是原因是：new zookeeper之后，zookeeper的还没有连接好，就去调用，当然会抛错。
 * 继续查找资料，比较优雅的解决方案为如下：
 * @author yanghaitao
 *
 */
public class ConnectedWatcherUtil {
	/**
	 * 等待ZooKeeper链接成功。
	 * @param zooKeeper 正在链接中的ZooKeeper
	 */
	public static void waitUntilConnected(ZooKeeper zooKeeper) {
		CountDownLatch connectedLatch = new CountDownLatch(1);
		Watcher watcher = new ConnectedWatcher(connectedLatch);
		zooKeeper.register(watcher);
		if (States.CONNECTING == zooKeeper.getState()) {
			try {
				connectedLatch.await();
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
		}
	}
	
	/**
	 * 链接监控
	 * @author yanghaitao
	 *
	 */
	static class ConnectedWatcher implements Watcher {
		private CountDownLatch connectedLatch;
		ConnectedWatcher(CountDownLatch connectedLatch) {
			this.connectedLatch = connectedLatch;
		}
		/** 监听 **/
		@Override
		public void process(WatchedEvent event) {
			if (event.getState() == KeeperState.SyncConnected) {
				connectedLatch.countDown();
			}
		}
	}
}
