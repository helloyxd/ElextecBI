package com.jybi.job.admin.dao;

import com.jybi.job.admin.core.model.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by js_gg on 2017/11/28.
 */
public interface UserInfoDao {

    public UserInfo validate(@Param("loginName") String loginName, @Param("password") String password);

    public UserInfo selectOne(@Param("id") String id);

    public UserInfo selectByLoginName(@Param("loginName") String loginName);

    public void save(UserInfo user);

    public void updatePassword(UserInfo user);

    public void updateInfo(UserInfo user);

    public void delete(@Param("id") String id);

    public List<UserInfo> pageList(@Param("offset") int offset, @Param("pagesize") int pagesize,@Param("userName") String userName);

    public int pageListCount(@Param("offset") int offset, @Param("pagesize") int pagesize,@Param("userName") String userName);


}
