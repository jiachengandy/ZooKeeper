package com.apm.echart.service;

import com.apm.echart.dao.model.UserT;

public interface IUserService {  
    public UserT getUserById(int userId);
    public void add();
}
