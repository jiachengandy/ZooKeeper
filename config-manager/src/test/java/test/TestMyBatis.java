package test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.apm.echart.dao.model.UserT;
import com.apm.echart.service.IUserService;

public class TestMyBatis {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-mybatis.xml");
		IUserService iuser = (IUserService)context.getBean("userService");
		UserT userById = iuser.getUserById(1);
		iuser.add();
		System.out.println(userById.getUser_name() + " : " + userById.getPassword());
		context.close();
	}
}
