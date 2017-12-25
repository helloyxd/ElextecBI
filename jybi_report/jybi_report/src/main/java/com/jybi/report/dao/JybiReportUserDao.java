package com.jybi.report.dao;

import com.jybi.report.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by js_gg on 2017/12/19.
 */
@Mapper
public interface JybiReportUserDao {
    public List<User> queryAll();

    public User validate(@Param("userName") String userName, @Param("password") String password);

    public void save(User user);

    public User queryByUserName(String userName);
}
