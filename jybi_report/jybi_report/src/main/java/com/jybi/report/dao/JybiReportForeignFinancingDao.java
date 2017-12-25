package com.jybi.report.dao;

import com.jybi.report.model.ForeignFinancing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by js_gg on 2017/12/19.
 */
@Mapper
public interface JybiReportForeignFinancingDao {

    public void save(ForeignFinancing temp);

    public List<ForeignFinancing> queryAll();

    public void delete(String id);

    public void updateInfo(ForeignFinancing temp);

    public Map<String,Object> queryAllOrg();

    public List<ForeignFinancing> pageList(@Param("pageNo") int pageNo,@Param("pageSize") int pageSize,@Param("objName") String objName);

    public int pageListCount(@Param("pageNo") int pageNo,@Param("pageSize") int pageSize,@Param("objName") String objName);
}
