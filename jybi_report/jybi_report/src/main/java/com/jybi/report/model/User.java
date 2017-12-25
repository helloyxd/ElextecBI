package com.jybi.report.model;

import java.util.Date;

/**
 * 用户实体类
 * Created by js_gg on 2017/12/19.
 */
public class User {
    private String id;
    private String userName;
    private String password;
    private String fullNaame;
    private String departmentId;
    private Integer status;
    private String creater;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullNaame() {
        return fullNaame;
    }

    public void setFullNaame(String fullNaame) {
        this.fullNaame = fullNaame;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
