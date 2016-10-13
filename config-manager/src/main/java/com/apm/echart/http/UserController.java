package com.apm.echart.http;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apm.echart.dao.model.UserT;
import com.apm.echart.service.IUserService;
import com.apm.echart.util.TimerUtil;

/**
 * 用户信息操作。
 * @author yanghaitao
 *
 */
@Controller
@RequestMapping("user")
public class UserController {
	
	@Resource
	private IUserService iUser;
	
	@RequestMapping("showUser")
	public String toIndex(HttpServletRequest request, Model model) {
		 int userId = Integer.parseInt(request.getParameter("id"));  
	        UserT user = this.iUser.getUserById(userId);  
	        model.addAttribute("user", user);  
	        model.addAttribute("timer", TimerUtil.getSystemTime("yyyy-MM-dd HH:mm:ss"));
		return "showUser";
	}
}
