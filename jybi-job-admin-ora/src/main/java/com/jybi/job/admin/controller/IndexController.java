package com.jybi.job.admin.controller;

import com.jybi.job.admin.controller.annotation.PermessionLimit;
import com.jybi.job.admin.core.model.UserInfo;
import com.jybi.job.admin.core.util.CookieUtil;
import com.jybi.job.admin.service.XxlJobService;
import com.jybi.job.admin.controller.interceptor.PermissionInterceptor;
import com.jybi.job.admin.service.UserInfoService;
import com.jybi.job.core.biz.model.ReturnT;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * index controller
 * @author xuxueli 2015-12-19 16:13:16
 */
@Controller
public class IndexController {

	@Resource
	private XxlJobService xxlJobService;

	@Resource
	private UserInfoService userInfoService;

	@RequestMapping("/")
	public String index(Model model) {

		Map<String, Object> dashboardMap = xxlJobService.dashboardInfo();
		model.addAllAttributes(dashboardMap);

		return "index";
	}

    @RequestMapping("/triggerChartDate")
	@ResponseBody
	public ReturnT<Map<String, Object>> triggerChartDate() {
        ReturnT<Map<String, Object>> triggerChartDate = xxlJobService.triggerChartDate();
        return triggerChartDate;
    }
	
	@RequestMapping("/toLogin")
	@PermessionLimit(limit=false)
	public String toLogin(Model model, HttpServletRequest request) {
		if (PermissionInterceptor.ifLogin(request)) {
			return "redirect:/";
		}
		return "login";
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	@ResponseBody
	@PermessionLimit(limit=false)
	public ReturnT<String> loginDo(HttpServletRequest request, HttpServletResponse response, String userName, String password, String ifRemember){
		if (!PermissionInterceptor.ifLogin(request)) {
			UserInfo user = userInfoService.login(userName,password);
			if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)
					&& user != null) {
				boolean ifRem = false;
				if (StringUtils.isNotBlank(ifRemember) && "on".equals(ifRemember)) {
					ifRem = true;
				}
				PermissionInterceptor.login(request,response, ifRem,userName,password);
//				CookieUtil.set(response,"username",userName,ifRem);
			} else {
				return new ReturnT<String>(500, "账号或密码错误");
			}
		}
//		if (!PermissionInterceptor.ifLogin(request)) {
//			if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)
//					&& PropertiesUtil.getString("xxl.job.login.username").equals(userName)
//					&& PropertiesUtil.getString("xxl.job.login.password").equals(password)) {
//				boolean ifRem = false;
//				if (StringUtils.isNotBlank(ifRemember) && "on".equals(ifRemember)) {
//					ifRem = true;
//				}
//				PermissionInterceptor.login(response, ifRem);
//				CookieUtil.set(response,"username",userName,ifRem);
//			} else {
//				return new ReturnT<String>(500, "账号或密码错误");
//			}
//		}
		return ReturnT.SUCCESS;
	}
	
	@RequestMapping(value="logout", method=RequestMethod.POST)
	@ResponseBody
	@PermessionLimit(limit=false)
	public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response){
		if (PermissionInterceptor.ifLogin(request)) {
			PermissionInterceptor.logout(request, response);
		}
		return ReturnT.SUCCESS;
	}
	
	@RequestMapping("/help")
	public String help() {

		/*if (!PermissionInterceptor.ifLogin(request)) {
			return "redirect:/toLogin";
		}*/

		return "help";
	}
	
}
