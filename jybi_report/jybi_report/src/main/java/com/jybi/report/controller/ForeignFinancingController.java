package com.jybi.report.controller;

import com.jybi.report.model.ForeignFinancing;
import com.jybi.report.model.ReportMap;
import com.jybi.report.model.ReturnT;
import com.jybi.report.services.JybiReportForeignFinancingSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对外融资报表
 * Created by js_gg on 2017/12/19.
 */
@Controller
@RequestMapping(value ="/bi/foreign")
public class ForeignFinancingController {

    @Autowired
    public JybiReportForeignFinancingSerivce jybiReportForeignFinancingSerivce;

    /**
     * 新增对外融资数据接口
     * */
    @RequestMapping(value ="/addInfo",method = RequestMethod.POST)
    @ResponseBody
    public ReturnT<String> add(@RequestBody ForeignFinancing foreignFinancing){
        return jybiReportForeignFinancingSerivce.addInfo(foreignFinancing);
    }

    /**
     * 获取对外融资数据分页
     * */
    @RequestMapping(value ="/pageList",method = RequestMethod.POST)
    @ResponseBody
    public ReturnT<Map<String,Object>> pageList(int page,int limit,String objName){
        return new ReturnT<Map<String,Object>>(jybiReportForeignFinancingSerivce.pageList(page,limit,objName));
    }

//    @RequestMapping(value ="/testList",method = RequestMethod.POST)
//    @ResponseBody
//    public ReturnT<List<ForeignFinancing>> testList(){
//        return new ReturnT<List<ForeignFinancing>>(jybiReportForeignFinancingSerivce.queryAll());
//    }

    /**
     * 获取全部对外融资数据
     * */
    @RequestMapping(value ="/queryAll",method = RequestMethod.GET)
    @ResponseBody
    public ReturnT<List<Map<String,Object>>> queryAll(){
        List<Map<String,Object>> returnTemp = new ArrayList<Map<String,Object>>();
        Integer index = 1;

        List<ForeignFinancing> list = jybiReportForeignFinancingSerivce.queryAll();
        Map<String,String> orgMap = new HashMap<String,String>();
        //获取数据里所有集团
        for(int i=0;i<list.size();i++){
            String temp = (String)orgMap.get(list.get(i).getOrgName());
            if(temp == null){
                orgMap.put(list.get(i).getOrgName(),list.get(i).getOrgName());
            }
        }
        for(String key : orgMap.keySet()) {
//            List<ReportMap<List<ReportMap<List<ReportMap<List<ForeignFinancing>>>>>>> reportTemp = new ArrayList<ReportMap<List<ReportMap<List<ReportMap<List<ForeignFinancing>>>>>>>();
            Map<String,Object> reportTemp = new HashMap<String,Object>();
            ReportMap<List<ReportMap<List<ReportMap<List<ForeignFinancing>>>>>> tempMap = new ReportMap<List<ReportMap<List<ReportMap<List<ForeignFinancing>>>>>>();
            tempMap.setObjName(key);
            Integer orgLength = 0;
            List<ReportMap<List<ReportMap<List<ForeignFinancing>>>>> typeReturnMap = new ArrayList<ReportMap<List<ReportMap<List<ForeignFinancing>>>>>();
            //获取同集团下所有的类型
            Map<String,String> typeMap = new HashMap<String,String>();
            for(int i=0;i<list.size();i++){
                if(key.equals(list.get(i).getOrgName())){
                    String temp = (String)typeMap.get(list.get(i).getType());
                    if(temp == null){
                        typeMap.put(list.get(i).getType(),list.get(i).getType());
                    }
                }
            }
            for(String typeKey:typeMap.keySet()){
                ReportMap<List<ReportMap<List<ForeignFinancing>>>> typeReportMap = new ReportMap<List<ReportMap<List<ForeignFinancing>>>>();
                typeReportMap.setObjName(typeKey);
                List<ReportMap<List<ForeignFinancing>>> comReturnMap = new ArrayList<ReportMap<List<ForeignFinancing>>>();
                Integer typeLength = 0;
                //获取同集团同类型下所有区域公司
                Map<String,String> comMap = new HashMap<String,String>();
                for(int i=0;i<list.size();i++){
                    if(key.equals(list.get(i).getOrgName())){
                        if(typeKey.equals(list.get(i).getType())){
                            String temp = (String)comMap.get(list.get(i).getRegionComName());
                            if(temp == null){
                                comMap.put(list.get(i).getRegionComName(),list.get(i).getRegionComName());
                            }
                        }
                    }
                }
                for(String comKey:comMap.keySet()){
                    ReportMap<List<ForeignFinancing>> comReportMap = new ReportMap<List<ForeignFinancing>>();
                    comReportMap.setObjName(comKey);
                    List<ForeignFinancing> comList = new ArrayList<ForeignFinancing>();
                    for(int i=0;i<list.size();i++){
                        if(key.equals(list.get(i).getOrgName())){
                            if(typeKey.equals(list.get(i).getType())){
                                if(comKey.equals(list.get(i).getRegionComName())){
                                    comList.add(list.get(i));
                                }
                            }
                        }
                    }
                    comReportMap.setSize(comList.size());
                    comReportMap.setObjNextTemp(comList);
                    comReturnMap.add(comReportMap);
                    typeLength+=comList.size();
                }
                typeReportMap.setSize(typeLength);
                typeReportMap.setObjNextTemp(comReturnMap);
                typeReturnMap.add(typeReportMap);
                orgLength+=typeLength;
            }
            tempMap.setSize(orgLength);
            tempMap.setObjNextTemp(typeReturnMap);
            reportTemp.put("index",index);
            reportTemp.put("arr",tempMap);
            returnTemp.add(reportTemp);
            index++;
        }
        return new ReturnT<List<Map<String,Object>>>(returnTemp);

//        Map<String,Map<String,List<ForeignFinancing>>> returnTemp = new HashMap<String,Map<String,List<ForeignFinancing>>>();
//        for(int i=0;i<list.size();i++){
//            Map<String,List<ForeignFinancing>> temp = (Map<String,List<ForeignFinancing>>)returnTemp.get(list.get(i).getOrgName());
//            if(temp == null){
//                Map<String,List<ForeignFinancing>> typeTemp = new HashMap<String,List<ForeignFinancing>>();
//                for(int j=0;j<list.size();j++){
//                    if(list.get(i).getOrgName().equals(list.get(j).getOrgName())){
//                        List<ForeignFinancing> comTemp = (List<ForeignFinancing>)typeTemp.get(list.get(j).getType());
//                        if(comTemp == null){
//                            List<ForeignFinancing> tag = new ArrayList<ForeignFinancing>();
//                            for(int z=0;z<list.size();z++){
//                                if(list.get(z).getOrgName().equals(list.get(i).getOrgName())&&list.get(z).getType().equals(list.get(j).getType())){
//                                    tag.add(list.get(z));
//                                }
//                            }
//                            typeTemp.put(list.get(j).getType(),tag);
//                        }
//                    }
//                }
//                returnTemp.put(list.get(i).getOrgName(),typeTemp);
//            }
//        }
//        return new ReturnT<Map<String,Map<String,List<ForeignFinancing>>>>(returnTemp);
    }

    /**
     * 删除对外融资数据接口
     * */
    @RequestMapping(value ="/delInfo",method = RequestMethod.POST)
    @ResponseBody
    public ReturnT<String> del(String id){
        return jybiReportForeignFinancingSerivce.delInfo(id);
    }

    /**
     * 删除对外融资数据接口
     * */
    @RequestMapping(value ="/updateInfo",method = RequestMethod.POST)
    @ResponseBody
    public ReturnT<String> update(@RequestBody ForeignFinancing foreignFinancing){
        return jybiReportForeignFinancingSerivce.updateInfo(foreignFinancing);
    }

}
