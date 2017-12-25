package com.jybi.report.controller;

import com.jybi.report.annotation.PermessionLimit;
import com.jybi.report.interceptor.PermissionInterceptor;
import com.jybi.report.model.ReturnT;
import com.jybi.report.model.User;
import com.jybi.report.services.JybiReportUserSerivce;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 登录管理
 * Created by Administrator on 2017/9/6.
 */
@Controller
@RequestMapping(value ="/bi")
public class LoginController {

    @Autowired
    public JybiReportUserSerivce jybiReportUserSerivce;


    /**
     * 登录验证接口
     * */
    @RequestMapping(value ="/login",method = RequestMethod.POST)
//    @PermessionLimit(limit=false)
    @ResponseBody
    public ReturnT<String> login(HttpServletRequest request, HttpServletResponse response,@RequestBody User temp){
        //验证帐号密码是否为空
        if(StringUtils.isNotBlank(temp.getUserName()) && StringUtils.isNotBlank(temp.getPassword())){
            //根据帐号密码获取用户信息
            User user = jybiReportUserSerivce.login(temp.getUserName(),temp.getPassword());
            if (user != null) {
                //如果用户不为空则将信息保存到系统缓存和session
                PermissionInterceptor.login(request,response,user);
                return ReturnT.SUCCESS;

            } else {
                //如果用户为空则可能是帐号或密码错误
                return new ReturnT<String>(ReturnT.FAIL_CODE, "账号或密码错误");
            }
        }else{
            //密码和帐号不能为空
            return new ReturnT<String>(ReturnT.FAIL_CODE, "帐号或密码不能为空");
        }
    }

    /**
     * 新增用户接口
     * */
    @RequestMapping(value ="/addInfo",method = RequestMethod.POST)
    @ResponseBody
    public ReturnT<String> add(HttpServletRequest request,@RequestBody User user){
        //从session中取出用户信息
        User temp = (User)request.getSession().getAttribute("user");
        //添加创建人信息，创建人的ID
        user.setCreater(temp.getId());
        return jybiReportUserSerivce.addInfo(user);
    }


    /**
     * 验证未通过跳转接口
     * */
    @RequestMapping(value ="/toLogin")
//    @PermessionLimit(limit=false)
    @ResponseBody
    public ReturnT<String> toLogin(){
        return new ReturnT<String>(ReturnT.NO_LOGIN, "用户未登录或已失效");
    }


    /**
     * 用户登出接口
     * */
    @RequestMapping(value="logout", method=RequestMethod.POST)
    @ResponseBody
    public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response){
        //判断用户是否已是登录状态
        if (PermissionInterceptor.ifLogin(request)) {
            //已登录则清空系统缓存和session里的信息
            PermissionInterceptor.logout(request, response);
        }
        return ReturnT.SUCCESS;
    }

}
