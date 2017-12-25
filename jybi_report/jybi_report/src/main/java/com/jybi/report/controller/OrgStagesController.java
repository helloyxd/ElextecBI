package com.jybi.report.controller;

import com.jybi.report.model.ForeignFinancing;
import com.jybi.report.model.OrgStages;
import com.jybi.report.model.ReturnT;
import com.jybi.report.services.JybiReportOrgStagesSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 集团-分期列表获取
 * Created by js_gg on 2017/12/20.
 */
@Controller
@RequestMapping(value ="/bi/orgStages")
public class OrgStagesController {


    @Autowired
    private JybiReportOrgStagesSerivce jybiReportOrgStagesSerivce;


    /**
     * 获取所有集团接口
     * */
    @RequestMapping(value ="/queryAllOrg",method = RequestMethod.POST)
    @ResponseBody
    public ReturnT<List<Map<String,Object>>> queryAllOrg(){
        //获取所有集团信息
        List<OrgStages> list = jybiReportOrgStagesSerivce.queryAllOrg();
        List<Map<String,Object>> returnMap = new ArrayList<Map<String, Object>>();
        //将信息重新封装成map
        for(int i=0;i<list.size();i++){
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("orgId",list.get(i).getOrgId());
            params.put("orgName",list.get(i).getOrgName());
            returnMap.add(params);
        }
        return new ReturnT<List<Map<String,Object>>>(returnMap);
    }

    /**
     * 获取所属集团下所有区域公司
     * */
    @RequestMapping(value ="/queryByOrgId",method = RequestMethod.POST)
    @ResponseBody
    public ReturnT<List<String>> queryByOrgId(String id){
        //根据集团ID获取区域公司信息
        List<OrgStages> list = jybiReportOrgStagesSerivce.queryByOrgId(id);
        List<String> returnMap = new ArrayList<String>();
        //将信息重新封装成map
        for(int i=0;i<list.size();i++){
            returnMap.add(list.get(i).getRegionComName());
        }
        return new ReturnT<List<String>>(returnMap);
    }

}
