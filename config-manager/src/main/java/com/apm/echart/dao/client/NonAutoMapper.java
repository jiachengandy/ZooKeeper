package com.apm.echart.dao.client;

import com.apm.echart.dao.model.UserT;

/**
 * 手动定义接口。
 * @author yanghaitao
 *
 */
public interface NonAutoMapper {
	/**
	 * 向表中添加数据。
	 * @param user
	 */
	public void addUser(UserT user);
}
