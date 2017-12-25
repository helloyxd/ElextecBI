package com.jybi.report.dao;

import com.jybi.report.model.OrgStages;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * Created by js_gg on 2017/12/19.
 */
@Mapper
public interface JybiReportOrgStagesDao {
    public List<OrgStages> queryAllOrg();

    public List<OrgStages> queryByOrgId(String id);
}
