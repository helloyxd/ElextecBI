package com.jybi.report.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class UserHandlerInterceptor extends WebMvcConfigurerAdapter{

	 /**
     * 配置拦截器
     * @author lance
     * @param registry
     */
	
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new PermissionInterceptor()).addPathPatterns("/bi/**").excludePathPatterns("/bi/toLogin","/bi/login");
	}

}
