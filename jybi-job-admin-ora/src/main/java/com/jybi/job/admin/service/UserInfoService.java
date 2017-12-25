package com.jybi.job.admin.service;

import com.jybi.job.admin.core.model.UserInfo;
import com.jybi.job.core.biz.model.ReturnT;


import java.util.Map;

/**
 * Created by js_gg on 2017/11/29.
 */
public interface UserInfoService {
    public Map<String, Object> pageList(int start, int length, String userName);

    public ReturnT<String> add(UserInfo userInfo);

    public ReturnT<String> reschedule(UserInfo userInfo);

    public ReturnT<String> remove(String id);

    public UserInfo login(String loginName,String password);

    public ReturnT<String> reset(String id);

    public ReturnT<String> changePwd(String userName,String oldPassword,String newPassword);
}
