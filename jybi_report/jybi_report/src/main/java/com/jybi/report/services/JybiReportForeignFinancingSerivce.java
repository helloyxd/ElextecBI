package com.jybi.report.services;

import com.jybi.report.dao.JybiReportForeignFinancingDao;
import com.jybi.report.dao.JybiReportUserDao;
import com.jybi.report.model.ForeignFinancing;
import com.jybi.report.model.ReturnT;
import com.jybi.report.model.User;
import com.jybi.report.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 对外融资serivce
 * Created by yfyuan on 2016/12/2.
 */
@Repository
public class JybiReportForeignFinancingSerivce {


    @Autowired
    private JybiReportForeignFinancingDao jybiReportForeignFinancingDao;

    /**
     * 预览接口（数据会根据集团名，类别，区域公司排序）
     * */
    public List<ForeignFinancing> queryAll(){
        return jybiReportForeignFinancingDao.queryAll();
    }

    public ReturnT<String> addInfo(ForeignFinancing foreignFinancing){
        try {
            foreignFinancing.setId(UUID.randomUUID().toString());
            jybiReportForeignFinancingDao.save(foreignFinancing);
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "新增信息失败:" + e.getMessage());
        }
    }

    /**
     * 删除信息
     * */
    public ReturnT<String> delInfo(String id){
        try {
            jybiReportForeignFinancingDao.delete(id);
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "删除信息失败:" + e.getMessage());
        }
    }

    /**
     * 修改信息
     * */
    public ReturnT<String> updateInfo(ForeignFinancing foreignFinancing){
        try {
            jybiReportForeignFinancingDao.updateInfo(foreignFinancing);
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "修改信息失败:" + e.getMessage());
        }
    }

    /**
     * 分页获取信息
     * */
    public Map<String,Object> pageList(int pageNo,int pageSize,String objName){
        List<ForeignFinancing> list = jybiReportForeignFinancingDao.pageList(pageNo, pageSize, objName);
        int list_count = jybiReportForeignFinancingDao.pageListCount(pageNo, pageSize, objName);
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("pageList",list);
        maps.put("total",list_count);
        return maps;
    }


}
