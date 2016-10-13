package com.apm.echart.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.apm.echart.dao.client.NonAutoMapper;
import com.apm.echart.dao.client.UserTMapper;
import com.apm.echart.dao.model.UserT;
import com.apm.echart.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {
	
	@Resource
	private UserTMapper userTMapper;
	
	@Resource
	private NonAutoMapper nonAutoMapper;

	@Override
	public UserT getUserById(int userId) {
		// TODO Auto-generated method stub
		return userTMapper.selectByPrimaryKey(userId);
	}
	
	public void add() {
		UserT user = new UserT();
		user.setId(2);
		user.setUser_name("yht");
		user.setPassword("yuiop");
		user.setAge(26);
		nonAutoMapper.addUser(user);
	}

}
