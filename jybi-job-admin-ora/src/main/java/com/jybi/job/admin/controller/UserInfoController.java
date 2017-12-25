package com.jybi.job.admin.controller;
import com.jybi.job.admin.core.model.UserInfo;
import com.jybi.job.admin.service.UserInfoService;
import com.jybi.job.core.biz.model.ReturnT;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by js_gg on 2017/11/28.
 */
@Controller
@RequestMapping("/userinfo")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;

    @RequestMapping
    public String index(Model model) {
        return "userinfo/userinfo.index";
    }


    @RequestMapping("/pageList")
    @ResponseBody
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                         String userName) {
        return userInfoService.pageList(start, length, userName);
    }

    @RequestMapping("/add")
    @ResponseBody
    public ReturnT<String> add(UserInfo userInfo) {
        return userInfoService.add(userInfo);
    }

    @RequestMapping("/reschedule")
    @ResponseBody
    public ReturnT<String> reschedule(UserInfo userInfo) {
        return userInfoService.reschedule(userInfo);
    }

    @RequestMapping("/remove")
    @ResponseBody
    public ReturnT<String> remove(String id) {
        return userInfoService.remove(id);
    }


    @RequestMapping("/reset")
    @ResponseBody
    public ReturnT<String> reset(String id) {
        return userInfoService.reset(id);
    }

    @RequestMapping("/changePwd")
    @ResponseBody
    public ReturnT<String> changePwd(String oldPassword, String newPassword, HttpServletRequest request) {
        String userName = request.getSession().getAttribute("userName").toString();
        return userInfoService.changePwd(userName,oldPassword,newPassword);
    }


}
