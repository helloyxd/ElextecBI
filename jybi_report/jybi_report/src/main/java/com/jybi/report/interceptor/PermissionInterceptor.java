package com.jybi.report.interceptor;


import com.jybi.report.annotation.PermessionLimit;
import com.jybi.report.model.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 权限拦截, 简易版
 * @author xuxueli 2015-12-12 18:09:04
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {
	

	//保存登录信息的MAP
	public static Map<String,String> LOGIN_MAP = new HashMap<String,String>();


	/**
	 * 用户登录成功，保存用户信息
	 * */
	public static boolean login(HttpServletRequest request, HttpServletResponse response, User user){
		//将用户信息保存进session
		request.getSession().setAttribute("user",user);
		//将sessionID和用户名以MAP的形式保存进系统缓存
		LOGIN_MAP.put(request.getSession().getId(),user.getUserName());
		return true;
	}

	/**
	 * 用户登出，清空登录信息
	 * */
	public static void logout(HttpServletRequest request, HttpServletResponse response){
		//清空session里的用户信息
		request.getSession().removeAttribute("user");
		//清空系统缓存里的sessionID和用户名
		LOGIN_MAP.remove(request.getSession().getId());
	}

	/**
	 * 验证用户是否登录超时或未登录
	 * */
	public static boolean ifLogin(HttpServletRequest request){

		//从系统缓存里取出对应sessionID的用户名
		String indentityInfo = LOGIN_MAP.get(request.getSession().getId());

		//从session中取出请求用户的信息
		User temp = (User)request.getSession().getAttribute("user");

		//如果缓存里的用户名为空或者session中的用户信息为空或者session中保存的用户信息与缓存中的不一致则返回false
		if (indentityInfo==null || temp==null || !indentityInfo.equals(temp.getUserName())) {
			return false;
		}
		return true;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//判断请求是否存在
		if (!(handler instanceof HandlerMethod)) {
			return super.preHandle(request, response, handler);
		}

		//验请登录信息
		if (!ifLogin(request)) {
//			HandlerMethod method = (HandlerMethod)handler;
//			PermessionLimit permission = method.getMethodAnnotation(PermessionLimit.class);
//			if (permission == null || permission.limit()) {
			response.sendRedirect(request.getContextPath() + "/bi/toLogin");
				//request.getRequestDispatcher("/toLogin").forward(request, response);
			return false;
//			}
		}
		return super.preHandle(request, response, handler);
	}
	
}
