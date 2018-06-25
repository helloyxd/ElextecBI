package com.jybi.job.admin.controller.interceptor;

import com.jybi.job.admin.controller.annotation.PermessionLimit;
import com.jybi.job.admin.core.model.MdmUser;
import com.jybi.job.admin.core.util.CookieUtil;
import com.jybi.job.admin.service.IMDMClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * 权限拦截, 简易版
 * @author xuxueli 2015-12-12 18:09:04
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {
	
	
	@Autowired
	IMDMClientService mdmClientService;
	
//	public static final String LOGIN_IDENTITY_KEY = "LOGIN_IDENTITY";
	public static Map<String,String> LOGIN_MAP = new HashMap<String,String>();
//	public static final Map<String,Object> LOGIN_MAP = new HashMap<String,Object>();
//	public static final String LOGIN_IDENTITY_TOKEN;
//    static {
//        String username = PropertiesUtil.getString("xxl.job.login.username");
//        String password = PropertiesUtil.getString("xxl.job.login.password");
//        String temp = username + "_" + password;
//        LOGIN_IDENTITY_TOKEN = new BigInteger(1, temp.getBytes()).toString(16);
//    }
	
	public static boolean login(HttpServletRequest request,HttpServletResponse response, boolean ifRemember,String userName,String password){
//		String temp = userName + "_" + password;
//		String LOGIN_IDENTITY_TOKEN = new BigInteger(1, temp.getBytes()).toString(16);
//		CookieUtil.set(response, LOGIN_IDENTITY_KEY, LOGIN_IDENTITY_TOKEN, ifRemember);
		request.getSession().setAttribute("userName",userName);
		LOGIN_MAP.put(request.getSession().getId(),userName);
		return true;
	}
	
	public boolean login(HttpServletRequest request,String userId,String userName) {
		MdmUser user = mdmClientService.getMdmUser(userId, userName);
		if(user==null) {
			return false;
		}
		request.getSession().setAttribute("userName",userName);
		LOGIN_MAP.put(request.getSession().getId(),userName);
		return true;
	}
	
	
	public static void logout(HttpServletRequest request, HttpServletResponse response){
//		CookieUtil.remove(request, response, LOGIN_IDENTITY_KEY);
//		CookieUtil.remove(request, response, "username");
		LOGIN_MAP.remove(request.getSession().getId());
	}
	public static boolean ifLogin(HttpServletRequest request){
//		String indentityInfo = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
//		if (indentityInfo==null || !LOGIN_IDENTITY_TOKEN.equals(indentityInfo.trim())) {
//			return false;
//		}
		String indentityInfo = LOGIN_MAP.get(request.getSession().getId());

		if (indentityInfo==null || !indentityInfo.equals(request.getSession().getAttribute("userName"))) {
			return false;
		}
		return true;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return super.preHandle(request, response, handler);
		}
		if (!ifLogin(request)) {
			String userName = request.getParameter("userName");
			String userId = request.getParameter("userId");
			if(userName==null || userName.equals("") || userId==null || userId.equals("")) {
				response.sendRedirect(request.getContextPath() + "/toLogin");
				return false;
			}
			
			if(!this.login(request, userId, userName)) {
				response.sendRedirect(request.getContextPath() + "/toLogin");
				return false;
			}
			
			HandlerMethod method = (HandlerMethod)handler;
			PermessionLimit permission = method.getMethodAnnotation(PermessionLimit.class);
			if (permission == null || permission.limit()) {
				response.sendRedirect(request.getContextPath() + "/toLogin");
				//request.getRequestDispatcher("/toLogin").forward(request, response);
				return false;
			}
		}
		return super.preHandle(request, response, handler);
	}
	
}
