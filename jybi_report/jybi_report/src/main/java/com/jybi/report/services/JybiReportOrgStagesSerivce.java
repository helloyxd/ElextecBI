package com.jybi.report.services;

import com.jybi.report.dao.JybiReportOrgStagesDao;
import com.jybi.report.dao.JybiReportUserDao;
import com.jybi.report.model.OrgStages;
import com.jybi.report.model.ReturnT;
import com.jybi.report.model.User;
import com.jybi.report.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


/**
 * 集团-分期serivce
 * Created by yfyuan on 2016/12/2.
 */
@Repository
public class JybiReportOrgStagesSerivce {



    @Autowired
    private JybiReportOrgStagesDao orgStagesDao;

    /**
     * 获取所有集团信息
     * */
    public List<OrgStages> queryAllOrg(){
        return orgStagesDao.queryAllOrg();
    }

    /**
     * 根据集团ID获取集团下所有区域公司信息
     * */
    public List<OrgStages> queryByOrgId(String id){
        return orgStagesDao.queryByOrgId(id);
    }



}
