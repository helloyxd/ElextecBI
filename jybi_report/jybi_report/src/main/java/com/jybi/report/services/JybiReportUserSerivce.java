package com.jybi.report.services;
import com.jybi.report.dao.JybiReportUserDao;
import com.jybi.report.interceptor.PermissionInterceptor;
import com.jybi.report.model.ReturnT;
import com.jybi.report.model.User;
import com.jybi.report.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * 用户Serivce
 * Created by yfyuan on 2016/12/2.
 */
@Repository
public class JybiReportUserSerivce {



    @Autowired
    private JybiReportUserDao userDao;

    /**
     * 获取所有用户
     * */
    public List<User> queryAll(){
        return userDao.queryAll();
    }

    /**
     * 登录验证
     * */
    public User login(String userName,String password){
        String rePassword = MD5Util.encode(password);
        return userDao.validate(userName,rePassword);
    }

    /**
     * 新增用户
     * */
    public ReturnT<String> addInfo(User user){
        User temp = userDao.queryByUserName(user.getUserName());
        if(temp != null){
            return new ReturnT<String>(ReturnT.FAIL_CODE, "用户名重复");
        }
        try {
            //密码加密处理
            String repassword  = user.getPassword();
            user.setPassword(MD5Util.encode(repassword));
            //生成UUID为用户ID
            user.setId(UUID.randomUUID().toString());
            userDao.save(user);
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "新增用户失败:" + e.getMessage());
        }
    }

}
