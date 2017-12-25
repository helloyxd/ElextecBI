package com.jybi.job.admin.service.impl;

import com.jybi.job.admin.core.model.UserInfo;
import com.jybi.job.admin.core.util.MD5Util;
import com.jybi.job.admin.dao.UserInfoDao;
import com.jybi.job.admin.service.UserInfoService;


import com.jybi.job.core.biz.model.ReturnT;
import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by js_gg on 2017/11/29.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private static Logger logger = LoggerFactory.getLogger(XxlJobServiceImpl.class);

    @Resource
    private UserInfoDao userInfoDao;


    @Override
    public Map<String,Object> pageList(int start, int length, String userName){
        // page query
        List<UserInfo> list = userInfoDao.pageList(start, length, userName);
        int list_count = userInfoDao.pageListCount(start, length, userName);

        // package result
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("recordsTotal", list_count);		// 总记录数
        maps.put("recordsFiltered", list_count);	// 过滤后的总记录数
        maps.put("data", list);  					// 分页列表
        return maps;
    }

    @Override
    public ReturnT<String> add(UserInfo userInfo){
        if (StringUtils.isBlank(userInfo.getLoginName())) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "请输入用户登录名");
        }
        if (StringUtils.isBlank(userInfo.getPassword())) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "请输入用户密码");
        }
        if (StringUtils.isBlank(userInfo.getRepassword())) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "请再次输入密码");
        }
        if (!userInfo.getPassword().equals(userInfo.getRepassword())) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "两次密码输入不一致");
        }
        //验证登录帐号名是否重复
        UserInfo user = userInfoDao.selectByLoginName(userInfo.getLoginName());
        if(user != null){
            return new ReturnT<String>(ReturnT.FAIL_CODE, "用户名重复");
        }
        try {
            //密码加密处理
            String repassword  = userInfo.getPassword();
            userInfo.setPassword(MD5Util.encode(repassword));
            //生成UUID为用户ID
            userInfo.setId(UUID.randomUUID().toString());
            userInfo.setCreateTime(new Date());
            userInfoDao.save(userInfo);
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ReturnT<String>(ReturnT.FAIL_CODE, "新增用户失败:" + e.getMessage());
        }
    }

    @Override
    public ReturnT<String> reschedule(UserInfo userInfo) {
        try {
            userInfoDao.updateInfo(userInfo);
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ReturnT<String>(ReturnT.FAIL_CODE, "更新用户失败:" + e.getMessage());
        }
    }

    @Override
    public ReturnT<String> remove(String id){
        try {
            userInfoDao.delete(id);
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ReturnT<String>(ReturnT.FAIL_CODE, "删除用户失败:" + e.getMessage());
        }
    }

    @Override
    public UserInfo login(String loginName,String password){
        String repassword = MD5Util.encode(password);
        return userInfoDao.validate(loginName,repassword);
    }

    @Override
    public ReturnT<String> reset(String id){
        try {
            UserInfo user = new UserInfo();
            user.setId(id);
            user.setPassword(MD5Util.encode("123456"));
            userInfoDao.updatePassword(user);
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ReturnT<String>(ReturnT.FAIL_CODE, "重置密码失败:" + e.getMessage());
        }


    }

    public ReturnT<String> changePwd(String userName,String oldPassword,String newPassword){
        UserInfo user = userInfoDao.validate(userName,MD5Util.encode(oldPassword));
        if(user == null){
            return new ReturnT<String>(ReturnT.FAIL_CODE, "旧密码不正确");
        }
        user.setPassword(MD5Util.encode(newPassword));
        userInfoDao.updatePassword(user);
        return ReturnT.SUCCESS;
    }

}
