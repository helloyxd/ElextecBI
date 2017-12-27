package com.jybi.job.executor.util;

import com.jybi.job.core.log.XxlJobLogger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.awt.SystemColor.info;

/**
 * Created by js_gg on 2017/12/7.
 */
public class UpdateUtil {


    /**
     * 销售日报表更新
     *
     * */
    public static void myDailySalesUpdate(JdbcTemplate jdbcTemplate1,JdbcTemplate jdbcTemplate2,String thisDay) throws Exception{

        List<Map<String,Object>> list = jdbcTemplate1.queryForList("EXEC rpt_bz_jyrbcs_with_dqfl '" + thisDay + "'");

        if(list.size()>0){
            jdbcTemplate2.update("DELETE FROM my_daily_sales_report WHERE update_time=" + "'"+thisDay+"'");
        }

        for(int i=0;i<list.size();i++){
            String regionComName = "";
            if("".equals(list.get(i).get("区域公司").toString()) || list.get(i).get("区域公司") == null){
                regionComName = list.get(i).get("项目公司").toString();
            }else{
                regionComName = list.get(i).get("区域公司").toString();
            }
            String id = UUID.randomUUID().toString();
            jdbcTemplate2.update("INSERT INTO " +
                    "my_daily_sales_report(" +
                    "id," +
                    "org_id," +
                    "org_code," +
                    "org_name," +
                    "region_com_code," +
                    "region_com_name," +
                    "project_com_id," +
                    "project_com_code," +
                    "project_com_name," +
                    "project_id," +
                    "project_code," +
                    "project_name," +
                    "first_level_type_name," +
                    "sec_level_type_name," +
                    "product_type_code," +
                    "visiting_customer," +
                    "call_customer," +
                    "new_set," +
                    "new_set_amount," +
                    "signature_today," +
                    "signature_today_amount," +
                    "total_withdrawal," +
                    "new_withdrawal," +
                    "receivable_withdrawal," +
                    "at_work," +
                    "receivable_amount," +
                    "receivable_expire_amount," +
                    "not_due," +
                    "due_day," +
                    "be_overdue_1_15," +
                    "be_overdue_16_30," +
                    "be_overdue_31_90," +
                    "be_overdue_90," +
                    "not_signed_set," +
                    "not_signed_amount," +
                    "month_plan_sale," +
                    "month_sales," +
                    "month_cumulative_sign," +
                    "month_cumnlative_plan_sale," +
                    "day_cumnlative_sale," +
                    "month_plan_withdrawal," +
                    "month_cumnlative_withdrawal," +
                    "month_cumnlative_plan_withdrawal," +
                    "day_cumnlative_actual_withdrawal," +
                    "expand_visiting_customer," +
                    "expand_cumnlative_visiting_customer," +
                    "expand_deal_amount," +
                    "expand_cumnlative_deal_amount," +
                    "expand_deal_customer," +
                    "expand_cumnlative_deal_customer," +
                    "remark," +
                    "project_by_stages_id," +
                    "project_by_stages_code," +
                    "project_by_stages," +
                    "update_time) VALUES ("+
                    "'"+id+"'"+","+
                    (list.get(i).get("集团guid")==null ? "''," : "'"+list.get(i).get("集团guid")+"'"+",") +
                    (list.get(i).get("集团code")==null ? "''," : "'"+list.get(i).get("集团code")+"'"+",") +
                    (list.get(i).get("集团")==null ? "''," : "'"+list.get(i).get("集团")+"'"+",") +
                    (list.get(i).get("区域公司code")==null ? "''," : "'"+list.get(i).get("区域公司code")+"'"+",") +
                    (regionComName==null ? "''," : "'"+regionComName+"'"+",") +
                    (list.get(i).get("项目公司guid")==null ? "''," : "'"+list.get(i).get("项目公司guid")+"'"+",") +
                    (list.get(i).get("项目公司code")==null ? "''," : "'"+list.get(i).get("项目公司code")+"'"+",") +
                    (list.get(i).get("项目公司")==null ? "''," : "'"+list.get(i).get("项目公司")+"'"+",") +
                    (list.get(i).get("项目guid")==null ? "''," : "'"+list.get(i).get("项目guid")+"'"+",") +
                    (list.get(i).get("项目code")==null ? "''," : "'"+list.get(i).get("项目code")+"'"+",") +
                    (list.get(i).get("项目")==null ? "''," : "'"+list.get(i).get("项目")+"'"+",") +
                    (list.get(i).get("一级物业类型")==null ? "''," : "'"+list.get(i).get("一级物业类型")+"'"+",") +
                    (list.get(i).get("二级物业类型")==null ? "''," : "'"+list.get(i).get("二级物业类型")+"'"+",") +
                    (list.get(i).get("产品类型code")==null ? "''," : "'"+list.get(i).get("产品类型code")+"'"+",") +
                    list.get(i).get("来访客户")+","+
                    list.get(i).get("来电客户")+","+
                    list.get(i).get("新订套数")+","+
                    list.get(i).get("新订金额")+","+
                    list.get(i).get("本日签约套数")+","+
                    list.get(i).get("本日签约金额")+","+
                    list.get(i).get("本日总回笼")+","+
                    list.get(i).get("本日新增")+","+
                    list.get(i).get("本日应收款")+","+
                    list.get(i).get("本日工抵")+","+
                    list.get(i).get("总款")+","+
                    list.get(i).get("到期")+","+
                    list.get(i).get("未到期")+","+
                    list.get(i).get("到期当天")+","+
                    list.get(i).get("逾期1-15")+","+
                    list.get(i).get("逾期16-30")+","+
                    list.get(i).get("逾期31-90")+","+
                    list.get(i).get("逾期90以上")+","+
                    list.get(i).get("已定未签套数")+","+
                    list.get(i).get("已定未签金额")+","+
                    list.get(i).get("本月计划销售")+","+
                    list.get(i).get("本月销售套数")+","+
                    list.get(i).get("本月累计签约")+","+
                    list.get(i).get("截至当月累计计划销售")+","+
                    list.get(i).get("截至当日累计销售")+","+
                    list.get(i).get("本月计划回笼")+","+
                    list.get(i).get("本月累计回笼")+","+
                    list.get(i).get("截至当月累计计划回笼")+","+
                    list.get(i).get("截至当日累计实际回笼")+","+
                    list.get(i).get("拓展来访")+","+
                    list.get(i).get("累计来访")+","+
                    list.get(i).get("拓展成交金额")+","+
                    list.get(i).get("累计成交金额")+","+
                    list.get(i).get("拓展成交户数")+","+
                    list.get(i).get("累计拓展成交户数")+","+
                    (list.get(i).get("备注")==null ? "''," : "'"+list.get(i).get("备注")+"'"+",") +
                    (list.get(i).get("项目分期guid")==null ? "''," : "'"+list.get(i).get("项目分期guid")+"'"+",") +
                    (list.get(i).get("项目分期code")==null ? "''," : "'"+list.get(i).get("项目分期code")+"'"+",") +
                    (list.get(i).get("项目分期")==null ? "''," : "'"+list.get(i).get("项目分期")+"'"+",") +
                    "'"+thisDay+"'"+
                    ")");
        }
        XxlJobLogger.log(thisDay + "数据更新完成");
    }


    /**
     * 提取预算编制数据（当年）
     *
     * */
//    @Transactional
    public static void myBudgetOrganizationUpdate(JdbcTemplate jdbcTemplate1,JdbcTemplate jdbcTemplate2) throws Exception{

//        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy");
        String thisDay = TimeUtil.dateToStr(new Date(),"yyyy");

        List<Map<String,Object>> projectList = jdbcTemplate2.queryForList("select project_by_stages_id from my_org_com_project_stages");

        if(projectList.size()>0){
            jdbcTemplate2.update("DELETE FROM my_budget_organization WHERE length(id)>8");
        }

//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String thisTime = TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");

        for(int j=0;j<projectList.size();j++){
            if(projectList.get(j).get("project_by_stages_id").toString().length()>8){
                StringBuilder sqlsub = new StringBuilder("EXEC usp_s_SaleBudgetDtl_bz ");
                sqlsub.append("'");
                sqlsub.append(projectList.get(j).get("project_by_stages_id"));
                sqlsub.append("',");
                sqlsub.append("'");
                sqlsub.append(thisDay);
                sqlsub.append("',");
                sqlsub.append("1");
                List<Map<String,Object>>  list = new ArrayList<Map<String,Object>>();
                list = jdbcTemplate1.queryForList(sqlsub.toString());

                for(int i=0;i<list.size();i++){
                    String regionComName = "";
                    if("".equals(list.get(i).get("区域公司").toString()) || list.get(i).get("区域公司") == null){
                        regionComName = list.get(i).get("项目公司").toString();
                    }else{
                        regionComName = list.get(i).get("区域公司").toString();
                    }

                    String id = UUID.randomUUID().toString();
                    jdbcTemplate2.update("INSERT INTO " +
                            "my_budget_organization(" +
                            "id," +
                            "org_id," +
                            "org_code," +
                            "org_name," +
                            "region_com_code," +
                            "region_com_name," +
                            "project_com_id," +
                            "project_com_code," +
                            "project_com_name," +
                            "project_id," +
                            "project_code," +
                            "project_name," +
                            "project_by_stages_id," +
                            "project_by_stages_code," +
                            "project_by_stages," +
                            "proj_code," +
                            "product_guid," +
                            "product_code," +
                            "product_name," +
                            "level," +
                            "total_target," +
                            "dt_total," +
                            "past_year_total," +
                            "year_target," +
                            "fact1," +
                            "fact2," +
                            "fact3," +
                            "fact4," +
                            "fact5," +
                            "fact6," +
                            "fact7," +
                            "fact8," +
                            "fact9," +
                            "fact10," +
                            "fact11," +
                            "fact12," +
                            "plan1," +
                            "plan2," +
                            "plan3," +
                            "plan4," +
                            "plan5," +
                            "plan6," +
                            "plan7," +
                            "plan8," +
                            "plan9," +
                            "plan10," +
                            "plan11," +
                            "plan12," +
                            "year_dt," +
                            "year_target_ye," +
                            "later_year_total," +
                            "total_target_ye," +
                            "is_edit," +
                            "is_edit_fact," +
                            "if_end," +
                            "use_symbol," +
                            "use_symbol_target," +
                            "use_symbol_sum_target," +
                            "use_symbol_fact," +
                            "showLink," +
                            "year," +
                            "update_time) VALUES ("+
                            "'"+id+"'"+","+
                            (list.get(i).get("集团guid")==null ? "''," : "'"+list.get(i).get("集团guid")+"'"+",") +
                            (list.get(i).get("集团code")==null ? "''," : "'"+list.get(i).get("集团code")+"'"+",") +
                            (list.get(i).get("集团")==null ? "''," : "'"+list.get(i).get("集团")+"'"+",") +
                            (list.get(i).get("区域公司code")==null ? "''," : "'"+list.get(i).get("区域公司code")+"'"+",") +
                            (regionComName==null ? "''," : "'"+regionComName+"'"+",") +
                            (list.get(i).get("项目公司guid")==null ? "''," : "'"+list.get(i).get("项目公司guid")+"'"+",") +
                            (list.get(i).get("项目公司code")==null ? "''," : "'"+list.get(i).get("项目公司code")+"'"+",") +
                            (list.get(i).get("项目公司")==null ? "''," : "'"+list.get(i).get("项目公司")+"'"+",") +
                            (list.get(i).get("项目guid")==null ? "''," : "'"+list.get(i).get("项目guid")+"'"+",") +
                            (list.get(i).get("项目code")==null ? "''," : "'"+list.get(i).get("项目code")+"'"+",") +
                            (list.get(i).get("项目")==null ? "''," : "'"+list.get(i).get("项目")+"'"+",") +
                            (list.get(i).get("项目分期guid")==null ? "''," : "'"+list.get(i).get("项目分期guid")+"'"+",") +
                            (list.get(i).get("项目分期code")==null ? "''," : "'"+list.get(i).get("项目分期code")+"'"+",") +
                            (list.get(i).get("项目分期")==null ? "''," : "'"+list.get(i).get("项目分期")+"'"+",") +
                            (list.get(i).get("ProjCode")==null ? "''," : "'"+list.get(i).get("ProjCode")+"'"+",") +
                            (list.get(i).get("ProductGUID")==null ? "''," : "'"+list.get(i).get("ProductGUID")+"'"+",") +
                            (list.get(i).get("ProductCode")==null ? "''," : "'"+list.get(i).get("ProductCode")+"'"+",") +
                            (list.get(i).get("ProductName")==null ? "''," : "'"+list.get(i).get("ProductName")+"'"+",") +
                            list.get(i).get("Level")+","+
                            list.get(i).get("TotalTarget")+","+
                            list.get(i).get("DtTotal")+","+
                            list.get(i).get("PastYearTotal")+","+
                            list.get(i).get("YearTarget")+","+
                            list.get(i).get("Fact1")+","+
                            list.get(i).get("Fact2")+","+
                            list.get(i).get("Fact3")+","+
                            list.get(i).get("Fact4")+","+
                            list.get(i).get("Fact5")+","+
                            list.get(i).get("Fact6")+","+
                            list.get(i).get("Fact7")+","+
                            list.get(i).get("Fact8")+","+
                            list.get(i).get("Fact9")+","+
                            list.get(i).get("Fact10")+","+
                            list.get(i).get("Fact11")+","+
                            list.get(i).get("Fact12")+","+
                            list.get(i).get("Plan1")+","+
                            list.get(i).get("Plan2")+","+
                            list.get(i).get("Plan3")+","+
                            list.get(i).get("Plan4")+","+
                            list.get(i).get("Plan5")+","+
                            list.get(i).get("Plan6")+","+
                            list.get(i).get("Plan7")+","+
                            list.get(i).get("Plan8")+","+
                            list.get(i).get("Plan9")+","+
                            list.get(i).get("Plan10")+","+
                            list.get(i).get("Plan11")+","+
                            list.get(i).get("Plan12")+","+
                            list.get(i).get("YearDt")+","+
                            list.get(i).get("YearTargetYe")+","+
                            list.get(i).get("LaterYearTotal")+","+
                            list.get(i).get("TotalTargetYe")+","+
                            list.get(i).get("IsEdit")+","+
                            list.get(i).get("IsEditFact")+","+
                            list.get(i).get("IfEnd")+","+
                            list.get(i).get("useSymbol")+","+
                            list.get(i).get("useSymbolTarget")+","+
                            list.get(i).get("useSymbolSumTarget")+","+
                            list.get(i).get("useSymbolFact")+","+
                            list.get(i).get("showLink")+","+
                            (list.get(i).get("Year")==null ? list.get(i).get("Year")+"," : "'"+list.get(i).get("Year")+"'"+",") +
                            "'"+thisTime+"'"+
                            ")");
                }
            }
        }
        XxlJobLogger.log(TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss")+"预算编制数据更新完成");
    }

    /**
     * 去库存销售数据
     *
     * */
//    @Transactional
    public static void myDelStockSalesUpdate(JdbcTemplate jdbcTemplate1,JdbcTemplate jdbcTemplate2) throws Exception{

//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String thisDay = TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");

        List<Map<String,Object>> list = jdbcTemplate1.queryForList("select a.jtguid as 集团guid,a.jtcode as 集团code,a.jtname as 集团,\n" +
                "a.QyCompanyCode as 区域公司code,a.QyCompanyName as 区域公司,\n" +
                "a.gsguid as 项目公司guid,a.gscode as 项目公司code,a.BUName as 项目公司,\n" +
                "a.xmguid as 项目guid,a.xmcode as 项目code,a.pprojname as 项目, \n" +
                "a.pprojguid as 项目分期guid,a.ppcode as 项目分期code,a.ProjName as 项目分期 , \n" +
                "a.FirstLevelName as 一级产品类型,a.BProductTypeName as 二级产品类型,a.BProductTypeCode as 产品类型code,\n" +
                "xs.xsts,xs.xsmj,xs.xsje,xs.Amount as xshl,\n" +
                "kc.ts,kc.mj,kc.je,\n" +
                "xs.xsts1,xs.xsmj1,xs.xsje1,xs.Amount1 as xshl1,\n" +
                "kc.ts1,kc.mj1,kc.je1,\n" +
                "xs.xsts2,xs.xsmj2,xs.xsje2,xs.Amount2 as xshl2,\n" +
                "kc.ts2,kc.mj2,kc.je2\n" +
                "from (\n" +
                "\tselect distinct  \n" +
                "\tm1.BUGUID as jtguid,m1.BUCode as jtcode,m1.BUName as jtname,\n" +
                "\tm.QyCompanyCode,m.QyCompanyName,\n" +
                "\tm.BUGUID as gsguid,m.BUCode as gscode,m.BUName,\n" +
                "\tp1.ProjGUID as xmguid,p1.ProjCode as xmcode,p1.ProjName as pprojname,\n" +
                "\tp.ProjGUID as pprojguid,p.ProjCode as ppcode,p.ProjName, \n" +
                "\tbt.FirstLevelName,bt.BProductTypeName,bt.BProductTypeShortName,r.BProductTypeCode \n" +
                "\tfrom p_Project p   \n" +
                "\tleft join p_Project p1 on p.ParentCode = p1.ProjCode  \n" +
                "\tleft join p_room r on p.ProjGUID = r.ProjGUID  \n" +
                "\tleft join myBusinessUnit m on p.BUGUID = m.BUGUID  \n" +
                "\tleft join myBusinessUnit m1 on m.ParentGUID=m1.BUGUID  \n" +
                "\tleft join p_BuildProductType bt on r.BProductTypeCode = bt.BProductTypeCode  \n" +
                "\twhere r.BProductTypeCode is not null and p.IfEnd = 1  \n" +
                ")a\n" +
                "left join\n" +
                "(\n" +
                "\t\tselect xs.ProjGUID,xs.BProductTypeCode,--tss,ts,mj,je\n" +
                "\t\tsum(ISNULL(xs.xsts,0)) as xsts,sum(isnull(xs.xsmj,0)) as xsmj,\n" +
                "\t\tsum(isnull(xs.xsje,0)) as xsje,sum(isnull(xs.Amount,0)) as Amount,\n" +
                "\n" +
                "\t\tsum(isnull(case when xs.YszDate>=DATEADD(yy, DATEDIFF(yy,0,GETDATE()), 0) then xsts else 0 end,0)) as xsts1,\n" +
                "\t\tsum(isnull(case when xs.YszDate>=DATEADD(yy, DATEDIFF(yy,0,GETDATE()), 0) then xs.xsmj else 0 end,0)) as xsmj1,\n" +
                "\t\tsum(isnull(case when xs.YszDate>=DATEADD(yy, DATEDIFF(yy,0,GETDATE()), 0) then xs.xsje else 0 end,0)) as xsje1,\n" +
                "\t\tsum(isnull(case when xs.YszDate>=DATEADD(yy, DATEDIFF(yy,0,GETDATE()), 0) then xs.Amount else 0 end,0)) as Amount1,\n" +
                "\n" +
                "\t\tsum(isnull(case when xs.YszDate<=dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,GETDATE()), 0)) then xsts else 0 end,0)) as xsts2,\n" +
                "\t\tsum(isnull(case when xs.YszDate<=dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,GETDATE()), 0)) then xs.xsmj else 0 end,0)) as xsmj2,\n" +
                "\t\tsum(isnull(case when xs.YszDate<=dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,GETDATE()), 0)) then xs.xsje else 0 end,0)) as xsje2,\n" +
                "\t\tsum(isnull(case when xs.YszDate<=dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,GETDATE()), 0)) then xs.Amount else 0 end,0)) as Amount2\n" +
                "\n" +
                "\t\tfrom\n" +
                "\t\t-----销售明细\n" +
                "\t\t(\n" +
                "\t\t\t\tselect distinct a.ProjGUID,a.BProductTypeCode,a.RoomGUID,1 as xsts,\n" +
                "\t\t\t\tisnull(case when bp.BProductTypeShortName like '%车%' then 1 else a.YsBldArea end,0) as xsmj,\n" +
                "\t\t\t\ta.YszDate,isnull(a.xsje,0) xsje,isnull(a.Amount,0) Amount\n" +
                "\t\t\t\tfrom (\n" +
                "\t\t\t\t\t select c.ProjGUID,r.BProductTypeCode,c.RoomGUID,r.YsBldArea,b.YszDate,c.RoomTotal as xsje,Amount\n" +
                "\t\t\t\t\t from s_Contract c\n" +
                "\t\t\t\t\t left join p_room r on r.RoomGUID=c.RoomGUID\n" +
                "\t\t\t\t\t left join p_Building b on b.BldGUID=r.BldGUID\n" +
                "\t\t\t\t\t left join (select g.SaleGUID,SUM(RmbAmount) Amount \n" +
                "\t\t\t\t\t\t\t\tfrom s_getin g\n" +
                "\t\t\t\t\t\t\t\tleft join s_Voucher v on v.VouchGUID=g.VouchGUID\n" +
                "\t\t\t\t\t\t\t\twhere YEAR(v.ShDate)=YEAR(GETDATE()) and MONTH(v.ShDate)=MONTH(GETDATE()) and v.ShDate<DATEADD(DD,1,GETDATE())\n" +
                "\t\t\t\t\t\t\t\tgroup by g.SaleGUID\n" +
                "\t\t\t\t\t\t\t\t) gg on gg.SaleGUID=c.TradeGUID\n" +
                "\t\t\t\t\t where c.Status='激活'\n" +
                "\t\t\t\t\t and YEAR(c.AuditingDate)=YEAR(GETDATE()) and MONTH(c.AuditingDate)=MONTH(GETDATE()) and c.AuditingDate<DATEADD(DD,1,GETDATE()) \n" +
                "\t\t\t\t\t and b.YszDate is not null\n" +
                "\t\t\t\t\t union \n" +
                "\t\t\t\t\t select c.ProjGUID,r.BProductTypeCode,oc.RoomGUID,r.YsBldArea,b.YszDate,oc.CjTotal as xsje,0 as Amount \n" +
                "\t\t\t\t\t from s_OCAttachRoom oc\n" +
                "\t\t\t\t\t left join s_Contract c on c.ContractGUID=oc.SaleGUID\n" +
                "\t\t\t\t\t left join p_room r on r.RoomGUID=oc.RoomGUID\n" +
                "\t\t\t\t\t left join p_Building b on b.BldGUID=r.BldGUID\n" +
                "\t\t\t\t\t where c.Status='激活' \n" +
                "\t\t\t\t\t and YEAR(c.AuditingDate)=YEAR(GETDATE()) and MONTH(c.AuditingDate)=MONTH(GETDATE()) and c.AuditingDate<DATEADD(DD,1,GETDATE())\n" +
                "\t\t\t\t\t and b.YszDate is not null\n" +
                "\t\t\t\t\t )a left join p_BuildProductType bp on bp.BProductTypeCode=a.BProductTypeCode\n" +
                "\t\t\t\t)xs \n" +
                "\t\t\t\tGROUP BY xs.ProjGUID,xs.BProductTypeCode \n" +
                ")xs on a.pprojguid = xs.ProjGUID and a.BProductTypeCode = xs.BProductTypeCode\n" +
                "left join\n" +
                "(\n" +
                "\t\t\t\n" +
                "\t\t\tselect b.ProjGUID,b.BProductTypeCode,--tss,ts,mj,je\n" +
                "\t\t\tsum(ISNULL(b.ts,0)) as ts,sum(isnull(b.mj,0)) as mj,\n" +
                "\t\t\tsum(isnull(b.je,0)) as je,\n" +
                "\n" +
                "\t\t\tsum(isnull(case when b.YszDate>=DATEADD(yy, DATEDIFF(yy,0,GETDATE()), 0) then ts else 0 end,0)) as ts1,\n" +
                "\t\t\tsum(isnull(case when b.YszDate>=DATEADD(yy, DATEDIFF(yy,0,GETDATE()), 0) then b.mj else 0 end,0)) as mj1,\n" +
                "\t\t\tsum(isnull(case when b.YszDate>=DATEADD(yy, DATEDIFF(yy,0,GETDATE()), 0) then b.je else 0 end,0)) as je1,\n" +
                "\t\t\t\n" +
                "\n" +
                "\t\t\tsum(isnull(case when b.YszDate<=dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,GETDATE()), 0)) then ts else 0 end,0)) as ts2,\n" +
                "\t\t\tsum(isnull(case when b.YszDate<=dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,GETDATE()), 0)) then b.mj else 0 end,0)) as mj2,\n" +
                "\t\t\tsum(isnull(case when b.YszDate<=dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,GETDATE()), 0)) then b.je else 0 end,0)) as je2\n" +
                "\t\t\t\n" +
                "\t\t\tfrom \n" +
                "\t\t\t(\t\n" +
                "\t\t\t\tselect bykc.ProjGUID,bykc.BProductTypeCode,1 as ts, \n" +
                "\t\t\t\tisnull(case when bt.BProductTypeShortName like '%车%' then 1 else BldArea end,0) as mj,\n" +
                "\t\t\t\tisnull(bykc.TotalDj,0) as je,bykc.YszDate\n" +
                "\t\t\t\tfrom\n" +
                "\t\t\t\t(\t\n" +
                "\t\t\t\t\tselect distinct a.ProjGUID,a.RoomGUID,a.BProductTypeName,BProductTypeShortName,a.BProductTypeCode,a.TotalDj,a.BldArea,a.YszDate \n" +
                "\t\t\t\t\tfrom(\n" +
                "\t\t\t\t\t\t--本月库存\n" +
                "\t\t\t\t\t\tselect r.ProjGUID,r.RoomGUID,bt.BProductTypeName,BProductTypeShortName,bt.BProductTypeCode,r.TotalDj,r.BldArea,b.YszDate\n" +
                "\t\t\t\t\t\tfrom p_room r \n" +
                "\t\t\t\t\t\tleft join p_BuildProductType bt on r.BProductTypeCode = bt.BProductTypeCode \n" +
                "\t\t\t\t\t\tleft join p_Building b on r.BldGUID = b.BldGUID \n" +
                "\t\t\t\t\t\tleft join(select RoomGUID,AuditingDate from s_Contract where Status = '激活'\n" +
                "\t\t\t\t\t\t\t\t  union all\n" +
                "\t\t\t\t\t\t\t\t  select oc.RoomGUID,c.AuditingDate from s_OCAttachRoom oc\n" +
                "\t\t\t\t\t\t\t\t  left join s_Contract c on oc.SaleGUID=c.ContractGUID\n" +
                "\t\t\t\t\t\t\t\t  where Status = '激活'\n" +
                "\t\t\t\t\t\t)c on r.RoomGUID = c.RoomGUID\n" +
                "\t\t\t\t\t\twhere r.IsMember = 0 and c.AuditingDate is null and b.YszDate<DATEADD(DD,1,GETDATE())\n" +
                "\t\t\t\t\t\tunion all\n" +
                "\t\t\t\t\t\tselect c.ProjGUID,r.RoomGUID,bt.FirstLevelName,BProductTypeShortName,bt.BProductTypeCode,r.TotalDj,r.BldArea,b.YszDate from s_Contract c \n" +
                "\t\t\t\t\t\tleft join p_room r on c.RoomGUID = r.RoomGUID\n" +
                "\t\t\t\t\t\tleft join p_BuildProductType bt on r.BProductTypeCode = bt.BProductTypeCode \n" +
                "\t\t\t\t\t\tleft join p_Building b on r.BldGUID = b.BldGUID \n" +
                "\t\t\t\t\t\twhere ((c.Status ='激活' and AuditingDate > dateadd(DD,1,GETDATE())) or (AuditingDate > dateadd(DD,1,GETDATE()) and CloseDate > dateadd(DD,1,GETDATE())) )\n" +
                "\t\t\t\t\t\tand b.YszDate<DATEADD(DD,1,GETDATE())\n" +
                "\t\t\t\t\t\tunion all\n" +
                "\t\t\t\t\t\tselect c.ProjGUID,r.RoomGUID,bt.FirstLevelName,BProductTypeShortName,bt.BProductTypeCode,r.TotalDj,r.BldArea,b.YszDate from s_OCAttachRoom o\n" +
                "\t\t\t\t\t\tleft join s_Contract c on c.ContractGUID = o.SaleGUID \n" +
                "\t\t\t\t\t\tleft join p_room r on o.RoomGUID = r.RoomGUID\n" +
                "\t\t\t\t\t\tleft join p_BuildProductType bt on r.BProductTypeCode = bt.BProductTypeCode\n" +
                "\t\t\t\t\t\tleft join p_Building b on r.BldGUID = b.BldGUID  \n" +
                "\t\t\t\t\t\twhere b.YszDate<DATEADD(DD,1,GETDATE()) and ((c.Status ='激活' and AuditingDate > dateadd(DD,1,GETDATE())) or (AuditingDate > dateadd(DD,1,GETDATE()) and CloseDate > dateadd(DD,1,GETDATE())) )\n" +
                "\t\t\t\t\t)a \n" +
                "\t\t\t\t)bykc left join p_BuildProductType bt on bykc.BProductTypeCode = bt.BProductTypeCode\n" +
                "\t\t\t)b group by b.ProjGUID,b.BProductTypeCode\n" +
                ")kc on a.pprojguid=kc.ProjGUID and a.BProductTypeCode=kc.BProductTypeCode\n" +
                "ORDER BY a.jtname,a.QyCompanyName,a.pprojname,a.BProductTypeName DESC");

        if (list.size() > 0) {
            jdbcTemplate2.update("DELETE FROM my_del_stock_sales_report WHERE 1=1");
        }

        for (int i = 0; i < list.size(); i++) {
            String regionComName = "";
            if ("".equals(list.get(i).get("区域公司").toString()) || list.get(i).get("区域公司") == null) {
                regionComName = list.get(i).get("项目公司").toString();
            } else {
                regionComName = list.get(i).get("区域公司").toString();
            }
            String id = UUID.randomUUID().toString();
            jdbcTemplate2.update("INSERT INTO " +
                    "my_del_stock_sales_report(" +
                    "id," +
                    "org_id," +
                    "org_code," +
                    "org_name," +
                    "region_com_code," +
                    "region_com_name," +
                    "project_com_id," +
                    "project_com_code," +
                    "project_com_name," +
                    "project_id," +
                    "project_code," +
                    "project_name," +
                    "project_by_stages_id," +
                    "project_by_stages_code," +
                    "project_by_stages," +
                    "product_type_code," +
                    "first_level_type_name," +
                    "sec_level_type_name," +
                    "xsts," +
                    "xsmj," +
                    "xsje," +
                    "xshl," +
                    "syts," +
                    "symj," +
                    "syje," +
                    "ys_in_year_xsts," +
                    "ys_in_year_xsmj," +
                    "ys_in_year_xsje," +
                    "ys_in_year_xshl," +
                    "ys_in_year_syts," +
                    "ys_in_year_symj," +
                    "ys_in_year_syje," +
                    "ys_out_year_xsts," +
                    "ys_out_year_xsmj," +
                    "ys_out_year_xsje," +
                    "ys_out_year_xshl," +
                    "ys_out_year_syts," +
                    "ys_out_year_symj," +
                    "ys_out_year_syje," +
                    "update_time) VALUES (" +
                    "'" + id + "'" + "," +
                    (list.get(i).get("集团guid") == null ?  "''," : "'" + list.get(i).get("集团guid") + "'" + ",") +
                    (list.get(i).get("集团code") == null ? "''," : "'" + list.get(i).get("集团code") + "'" + ",") +
                    (list.get(i).get("集团") == null ? "''," : "'" + list.get(i).get("集团") + "'" + ",") +
                    (list.get(i).get("区域公司code") == null ? "''," : "'" + list.get(i).get("区域公司code") + "'" + ",") +
                    (regionComName == null ? "''," : "'" + regionComName + "'" + ",") +
                    (list.get(i).get("项目公司guid") == null ?  "''," : "'" + list.get(i).get("项目公司guid") + "'" + ",") +
                    (list.get(i).get("项目公司code") == null ?  "''," : "'" + list.get(i).get("项目公司code") + "'" + ",") +
                    (list.get(i).get("项目公司") == null ? "''," : "'" + list.get(i).get("项目公司") + "'" + ",") +
                    (list.get(i).get("项目guid") == null ?  "''," : "'" + list.get(i).get("项目guid") + "'" + ",") +
                    (list.get(i).get("项目code") == null ?  "''," : "'" + list.get(i).get("项目code") + "'" + ",") +
                    (list.get(i).get("项目") == null ? "''," : "'" + list.get(i).get("项目") + "'" + ",") +
                    (list.get(i).get("项目分期guid") == null ?  "''," : "'" + list.get(i).get("项目分期guid") + "'" + ",") +
                    (list.get(i).get("项目分期code") == null ?  "''," : "'" + list.get(i).get("项目分期code") + "'" + ",") +
                    (list.get(i).get("项目分期") == null ? "''," : "'" + list.get(i).get("项目分期") + "'" + ",") +
                    (list.get(i).get("产品类型code") == null ?  "''," : "'" + list.get(i).get("产品类型code") + "'" + ",") +
                    (list.get(i).get("一级产品类型") == null ? "''," : "'" + list.get(i).get("一级产品类型") + "'" + ",") +
                    (list.get(i).get("二级产品类型") == null ? "''," : "'" + list.get(i).get("二级产品类型") + "'" + ",") +
                    list.get(i).get("xsts") + "," +
                    list.get(i).get("xsmj") + "," +
                    list.get(i).get("xsje") + "," +
                    list.get(i).get("xshl") + "," +
                    list.get(i).get("ts") + "," +
                    list.get(i).get("mj") + "," +
                    list.get(i).get("je") + "," +
                    list.get(i).get("xsts1") + "," +
                    list.get(i).get("xsmj1") + "," +
                    list.get(i).get("xsje1") + "," +
                    list.get(i).get("xshl1") + "," +
                    list.get(i).get("ts1") + "," +
                    list.get(i).get("mj1") + "," +
                    list.get(i).get("je1") + "," +
                    list.get(i).get("xsts2") + "," +
                    list.get(i).get("xsmj2") + "," +
                    list.get(i).get("xsje2") + "," +
                    list.get(i).get("xshl2") + "," +
                    list.get(i).get("ts2") + "," +
                    list.get(i).get("mj2") + "," +
                    list.get(i).get("je2") + "," +
                    "'" + thisDay + "'" +
                    ")");
        }
        XxlJobLogger.log(TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss") + "去库存销售数据更新完成");
    }

    /**
     * 提取项目资料数据
     *
     * */
//    @Transactional
    public static void myOrgComProjectUpdate(JdbcTemplate jdbcTemplate1,JdbcTemplate jdbcTemplate2) throws Exception{

//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String thisDay = TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");

        List<Map<String,Object>> list = jdbcTemplate1.queryForList("select  \n" +
                "    m2.buguid as '集团guid', m2.BUName as '集团', m2.BUCode as '集团code',\n" +
                "    m1.QyCompanyCode as '区域公司code', m1.QyCompanyName as '区域公司',\n" +
                "    m1.buguid as '项目公司guid', m1.BUCode as '项目公司code', m1.BUName as '项目公司',\n" +
                "    p2.ProjGUID as '项目guid', p2.ProjCode as '项目code', p2.ProjName as '项目'\n" +
                "from p_Project p2 \n" +
                "     left join myBusinessUnit m1 on p2.BUGUID=m1.BUGUID\n" +
                "     left join myBusinessUnit m2 on m1.ParentGUID=m2.BUGUID\n" +
                "where p2.IfEnd=0\n" +
                "order by 集团guid,项目公司guid,项目guid");

        if(list.size()>0){
            jdbcTemplate2.update("DELETE FROM my_org_com_project WHERE length(id)>8");
        }
        for(int i=0;i<list.size();i++){
            String regionComName = "";
            if("".equals(list.get(i).get("区域公司").toString()) || list.get(i).get("区域公司") == null){
                regionComName = list.get(i).get("项目公司").toString();
            }else{
                regionComName = list.get(i).get("区域公司").toString();
            }
            String id = UUID.randomUUID().toString();
            jdbcTemplate2.update("INSERT INTO " +
                    "my_org_com_project(" +
                    "id," +
                    "org_id," +
                    "org_code," +
                    "org_name," +
                    "region_com_code," +
                    "region_com_name," +
                    "project_com_id," +
                    "project_com_code," +
                    "project_com_name," +
                    "project_id," +
                    "project_code," +
                    "project_name," +
                    "update_time) VALUES ("+
                    "'"+id+"'"+","+
                    (list.get(i).get("集团guid")==null ? "''," : "'"+list.get(i).get("集团guid")+"'"+",") +
                    (list.get(i).get("集团code")==null ? "''," : "'"+list.get(i).get("集团code")+"'"+",") +
                    (list.get(i).get("集团")==null ? "''," : "'"+list.get(i).get("集团")+"'"+",") +
                    (list.get(i).get("区域公司code")==null ? "''," : "'"+list.get(i).get("区域公司code")+"'"+",") +
                    (regionComName==null ? "''," : "'"+regionComName+"'"+",") +
                    (list.get(i).get("项目公司guid")==null ? "''," : "'"+list.get(i).get("项目公司guid")+"'"+",") +
                    (list.get(i).get("项目公司code")==null ? "''," : "'"+list.get(i).get("项目公司code")+"'"+",") +
                    (list.get(i).get("项目公司")==null ? "''," : "'"+list.get(i).get("项目公司")+"'"+",") +
                    (list.get(i).get("项目guid")==null ? "''," : "'"+list.get(i).get("项目guid")+"'"+",") +
                    (list.get(i).get("项目code")==null ? "''," : "'"+list.get(i).get("项目code")+"'"+",") +
                    (list.get(i).get("项目")==null ? "''," : "'"+list.get(i).get("项目")+"'"+",") +
                    "'"+thisDay+"'"+
                    ")");
        }
        XxlJobLogger.log(TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss") + "集团-公司-项目数据更新完成");
    }


    /**
     * 提取项目分期数据
     *
     * */
//    @Transactional
    public static void myOrgComProjectStagesUpdate(JdbcTemplate jdbcTemplate1,JdbcTemplate jdbcTemplate2) throws Exception{

//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String thisDay = TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");

        List<Map<String,Object>> list = jdbcTemplate1.queryForList("select \n" +
                "    m2.buguid as '集团guid', m2.BUName as '集团', m2.BUCode as '集团code',\n" +
                "    m1.QyCompanyCode as '区域公司code', m1.QyCompanyName as '区域公司',\n" +
                "    m1.buguid as '项目公司guid', m1.BUCode as '项目公司code', m1.BUName as '项目公司',\n" +
                "    p2.ProjGUID as '项目guid', p2.ProjCode as '项目code', p2.ProjName as '项目', p2.ProjShortName as '项目短名',\n" +
                "    p1.ProjGUID as '项目分期guid', p1.ProjCode as '项目分期code', p1.ProjName as '项目分期', p1.ProjShortName as '分期短名'\n\n" +
                "from p_Project p1\n" +
                "     inner join p_Project p2 on p1.ParentCode=p2.ProjCode\n" +
                "     inner join myBusinessUnit m1 on p1.BUGUID=m1.BUGUID\n" +
                "     inner join myBusinessUnit m2 on m1.ParentGUID=m2.BUGUID\n" +
                "where p1.IfEnd=1\n" +
                "order by 集团guid,项目公司guid,项目guid,项目分期guid");
        if(list.size()>0){
            jdbcTemplate2.update("DELETE FROM my_org_com_project_stages WHERE length(id)>8");
        }

        for(int i=0;i<list.size();i++){

            String regionComName = "";
            if("".equals(list.get(i).get("区域公司").toString()) || list.get(i).get("区域公司") == null){
                regionComName = list.get(i).get("项目公司").toString();
            }else{
                regionComName = list.get(i).get("区域公司").toString();
            }

            String id = UUID.randomUUID().toString();
            jdbcTemplate2.update("INSERT INTO " +
                    "my_org_com_project_stages(" +
                    "id," +
                    "org_id," +
                    "org_code," +
                    "org_name," +
                    "region_com_code," +
                    "region_com_name," +
                    "project_com_id," +
                    "project_com_code," +
                    "project_com_name," +
                    "project_id," +
                    "project_code," +
                    "project_name," +
                    "proj_short_name," +
                    "project_by_stages_id," +
                    "project_by_stages_code," +
                    "project_by_stages," +
                    "stages_short_name," +
                    "update_time) VALUES ("+
                    "'"+id+"'"+","+
                    (list.get(i).get("集团guid")==null ? "''," : "'"+list.get(i).get("集团guid")+"'"+",") +
                    (list.get(i).get("集团code")==null ? "''," : "'"+list.get(i).get("集团code")+"'"+",") +
                    (list.get(i).get("集团")==null ? "''," : "'"+list.get(i).get("集团")+"'"+",") +
                    (list.get(i).get("区域公司code")==null ? "''," : "'"+list.get(i).get("区域公司code")+"'"+",") +
                    (regionComName==null ? "''," : "'"+regionComName+"'"+",") +
                    (list.get(i).get("项目公司guid")==null ? "''," : "'"+list.get(i).get("项目公司guid")+"'"+",") +
                    (list.get(i).get("项目公司code")==null ? "''," : "'"+list.get(i).get("项目公司code")+"'"+",") +
                    (list.get(i).get("项目公司")==null ? list.get(i).get("项目公司")+"," : "'"+list.get(i).get("项目公司")+"'"+",") +
                    (list.get(i).get("项目guid")==null ? "''," : "'"+list.get(i).get("项目guid")+"'"+",") +
                    (list.get(i).get("项目code")==null ? "''," : "'"+list.get(i).get("项目code")+"'"+",") +
                    (list.get(i).get("项目")==null ? "''," : "'"+list.get(i).get("项目")+"'"+",") +
                    (list.get(i).get("项目短名")==null ? "''," : "'"+list.get(i).get("项目短名")+"'"+",") +
                    (list.get(i).get("项目分期guid")==null ? "''," : "'"+list.get(i).get("项目分期guid")+"'"+",") +
                    (list.get(i).get("项目分期code")==null ? "''," : "'"+list.get(i).get("项目分期code")+"'"+",") +
                    (list.get(i).get("项目分期")==null ? "''," : "'"+list.get(i).get("项目分期")+"'"+",") +
                    (list.get(i).get("分期短名")==null ? "''," : "'"+list.get(i).get("分期短名")+"'"+",") +
                    "'"+thisDay+"'"+
                    ")");
        }
        XxlJobLogger.log(TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss") + "集团-公司-项目-分期数据更新完成");
    }


    /**
     * 按一级产品类型库存报表
     *
     * */
    public static void myProductTypeStockUpdate(JdbcTemplate jdbcTemplate1,JdbcTemplate jdbcTemplate2,String thisDay) throws Exception{
        List<Map<String,Object>>  list = jdbcTemplate1.queryForList("EXEC rpt_bz_pro_kc "+"'"+thisDay+"'");

        if(list.size()>0){
            jdbcTemplate2.update("DELETE FROM my_product_type_stock_report WHERE update_time="+"'"+thisDay+"'");
        }

        for(int i=0;i<list.size();i++) {
            String regionComName = "";
            if("".equals(list.get(i).get("区域公司").toString()) || list.get(i).get("区域公司") == null){
                regionComName = list.get(i).get("项目公司").toString();
            }else{
                regionComName = list.get(i).get("区域公司").toString();
            }
            String id = UUID.randomUUID().toString();
            jdbcTemplate2.update("INSERT INTO " +
                    "my_product_type_stock_report(" +
                    "id," +
                    "org_id," +
                    "org_code," +
                    "org_name," +
                    "region_com_code," +
                    "region_com_name," +
                    "project_com_id," +
                    "project_com_code," +
                    "project_com_name," +
                    "project_id," +
                    "project_code," +
                    "project_name," +
                    "project_by_stages_id," +
                    "project_by_stages_code," +
                    "project_by_stages," +
                    "first_level_type_name," +
                    "begin_year," +
                    "begin_year_area," +
                    "month_add_reduce," +
                    "month_add_reduce_area," +
                    "year_add_reduce," +
                    "year_add_reduce_area," +
                    "final," +
                    "final_area," +
                    "proportion," +
                    "proportion_area," +
                    "remark," +
                    "update_time) VALUES (" +
                    "'" + id + "'" + "," +
                    (list.get(i).get("集团guid") == null ? "''," : "'" + list.get(i).get("集团guid") + "'" + ",") +
                    (list.get(i).get("集团code") == null ? "''," : "'" + list.get(i).get("集团code") + "'" + ",") +
                    (list.get(i).get("集团") == null ? "''," : "'" + list.get(i).get("集团") + "'" + ",") +
                    (list.get(i).get("区域公司code") == null ? "''," : "'" + list.get(i).get("区域公司code") + "'" + ",") +
                    (regionComName == null ? "''," : "'" + regionComName + "'" + ",") +
                    (list.get(i).get("项目公司guid") == null ? "''," : "'" + list.get(i).get("项目公司guid") + "'" + ",") +
                    (list.get(i).get("项目公司code") == null ? "''," : "'" + list.get(i).get("项目公司code") + "'" + ",") +
                    (list.get(i).get("项目公司") == null ? "''," : "'" + list.get(i).get("项目公司") + "'" + ",") +
                    (list.get(i).get("项目guid") == null ? "''," : "'" + list.get(i).get("项目guid") + "'" + ",") +
                    (list.get(i).get("项目code") == null ? "''," : "'" + list.get(i).get("项目code") + "'" + ",") +
                    (list.get(i).get("项目") == null ? "''," : "'" + list.get(i).get("项目") + "'" + ",") +
                    (list.get(i).get("项目分期guid") == null ? "''," : "'" + list.get(i).get("项目分期guid") + "'" + ",") +
                    (list.get(i).get("项目分期code") == null ? "''," : "'" + list.get(i).get("项目分期code") + "'" + ",") +
                    (list.get(i).get("项目分期") == null ? "''," : "'" + list.get(i).get("项目分期") + "'" + ",") +
                    (list.get(i).get("一级产品类型") == null ? "''," : "'" + list.get(i).get("一级产品类型") + "'" + ",") +
                    list.get(i).get("年初") + "," +
                    list.get(i).get("年初面积") + "," +
                    list.get(i).get("本月增减") + "," +
                    list.get(i).get("本月增减面积") + "," +
                    list.get(i).get("本年增减") + "," +
                    list.get(i).get("本年增减面积") + "," +
                    list.get(i).get("期末") + "," +
                    list.get(i).get("期末面积") + "," +
                    list.get(i).get("比例") + "," +
                    list.get(i).get("比例面积") + "," +
                    (list.get(i).get("备注") == null ? "''," : "'" + list.get(i).get("备注") + "'" + ",") +
                    "'" + thisDay + "'" +
                    ")");
        }
        XxlJobLogger.log(thisDay + "数据更新完成");
    }

    /**
     * 项目分期概况
     *
     * */
    public static void myProjectByStagesUpdate(JdbcTemplate jdbcTemplate1,JdbcTemplate jdbcTemplate2) throws Exception{

//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String thisDay = TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");


        List<Map<String,Object>> list = jdbcTemplate1.queryForList("SELECT\n" +
                "          ppw.ProjGUID as '项目分期guid', \n" +
                "\t\t\t\t\tppw.BUGUID as '项目公司guid',\n" +
                "\t\t\t\t\tppw.ProjCode as '项目分期code', \n" +
                "\t\t\t\t\tppw.ProjName as '项目分期', \n" +
                "\t\t\t\t\tppw1.ProjGUID as '项目guid', \n" +
                "\t\t\t\t\tppw1.ProjCode as '项目code', \n" +
                "\t\t\t\t\tppw1.ProjName as '项目', \n" +
                "\t\t\t\t\tm1.BUName as '项目公司', \n" +
                "\t\t\t\t\tm1.bucode as '项目公司code', \n" +
                "\t\t\t\t\tm1.QyCompanyCode as '区域公司code', \n" +
                "\t\t\t\t\tISNULL(m1.QyCompanyName, m1.BUName) as '区域公司', \n" +
                "          m2.buguid as '集团guid', \n" +
                "\t\t\t\t\tm2.BUName as '集团', \n" +
                "\t\t\t\t\tm2.BUCode as '集团code', \n" +
                "\t\t\t\t\tppw.ProjShortCode as '项目代码',\n" +
                "\t\t\t\t\tppw.ParentCode as '父级代码',\n" +
                "\t\t\t\t\tppw.ProjShortName as '项目简称',\n" +
                "\t\t\t\t\tppw.IsGT as '是否公建项目',\n" +
                "          ppw.Level as '项目级数',\n" +
                "\t\t\t\t\tppw.IfEnd as '是否最末级项目',\n" +
                "\t\t\t\t\tppw.Principal as '项目负责人ID',\n" +
                "\t\t\t\t\tppw.BelongAreaGUID as '项目所属区域GUID',\n" +
                "\t\t\t\t\tppw.BelongAreaName as '项目所属区域',\n" +
                "          ppw.BgnSaleDate as '开盘日期-计划',\n" +
                "\t\t\t\t\tppw.EndSaleDate as '交付日期-计划',\n" +
                "\t\t\t\t\tppw.BuildBeginDate as '开工日期-计划',\n" +
                "\t\t\t\t\tppw.BuildEndDate as '竣工日期-计划',\n" +
                "          ppw.FactBgnSaleDate as '开盘日期-实际',\n" +
                "\t\t\t\t\tppw.FactEndSaleDate as '交付日期-实际',\n" +
                "\t\t\t\t\tppw.FactBuildBeginDate as '开工日期-实际',\n" +
                "\t\t\t\t\tppw.FactBuildEndDate as '竣工日期-实际',\n" +
                "          ppw.ProjStatus as '项目状态',\n" +
                "\t\t\t\t\tppw.ProjInfo as '项目描述',\n" +
                "\t\t\t\t\tppw.OccupyArea as '占地面积',\n" +
                "\t\t\t\t\tppw.InnerArea as '套内面积',\n" +
                "\t\t\t\t\tppw.SaleNum as '可售套数',\n" +
                "\t\t\t\t\tppw.SalePrice as '可售单价',\n" +
                "\t\t\t\t\tppw.SaleAmount as '可售金额',\n" +
                "\t\t\t\t\tppw.BuildDensity as '建筑密度',\n" +
                "\t\t\t\t\tppw.ProjAddress as '项目地址',\n" +
                "          (select top 1 ProjShortName from p_HkbProjectWork a where a.ProjCode=ppw.ParentCode) as ParentName,\n" +
                "          ppw.BeginDate as '项目开始日期',\n" +
                "\t\t\t\t\tppw.EndDate as '项目截止日期',\n" +
                "\t\t\t\t\tppw.ApplySys as '应用子系统',\n" +
                "          ppw.ApproveState as '审批状态',\n" +
                "\t\t\t\t\tppw.SaveState as '归档状态',\n" +
                "\t\t\t\t\tppw.ProjYsPicUrl as '项目总图预算版',\n" +
                "\t\t\t\t\tppw.ProjTzPicUrl as '项目总图调整版',\n" +
                "          mu.UserName as '项目负责人',\n" +
                "          '． — ＃ （  ） ' AS usuallyCode,\n" +
                "          ppw.PlotName as '地块名称',\n" +
                "\t\t\t\t\tppw.SpreadName as '项目推广名',\n" +
                "\t\t\t\t\tppw.OnceName as '项目曾用名',\n" +
                "\t\t\t\t\tppw.TargetEffigy as '目标客户肖像',\n" +
                "          piw.AllTerraArea as '总用地面积',\n" +
                "\t\t\t\t\tpiw.BuildTerraArea as '建设用地面积',\n" +
                "\t\t\t\t\tpiw.RoadArea as '道路面积',\n" +
                "\t\t\t\t\tpiw.SightArea as '景观面积',\n" +
                "\t\t\t\t\tpiw.CubageRate as '容积率',\n" +
                "          piw.YdlhArea as '异地绿化面积',\n" +
                "\t\t\t\t\tpiw.BuildArea as '建筑面积',\n" +
                "\t\t\t\t\tpiw.UpperBuildArea as '地上建筑面积',\n" +
                "\t\t\t\t\tpiw.UnderBuildArea as '地下建筑面积',\n" +
                "\t\t\t\t\tpiw.SaleArea as '可售面积',\n" +
                "\t\t\t\t\tpiw.UpperSaleArea as '地上可售面积',\n" +
                "\t\t\t\t\tpiw.UnderSaleArea as '地下可售面积',\n" +
                "\t\t\t\t\tpiw.LendArea as '可租面积',\n" +
                "          piw.UpperLendArea as '地上可租面积',\n" +
                "\t\t\t\t\tpiw.UnderLendArea as '地下可租面积',\n" +
                "\t\t\t\t\tpiw.LargessArea as '赠送面积',\n" +
                "\t\t\t\t\tpiw.FactBuildArea as '实际建设面积',\n" +
                "\t\t\t\t\tpiw.FactCubageRate as '实际容积率',\n" +
                "\t\t\t\t\tpiw.CwNum as '车位数',\n" +
                "\t\t\t\t\tpiw.UpperCwNum as '地上车位数',\n" +
                "\t\t\t\t\tpiw.UnderCwNum as '地下车位数',\n" +
                "\t\t\t\t\tpiw.ZjLength as '周界长度',\n" +
                "\t\t\t\t\tpiw.GateNum as '大门个数',\n" +
                "\t\t\t\t\tpiw.RjArea as '软景面积',\n" +
                "\t\t\t\t\tpiw.RjAreaRate as '软景面积所占比例',\n" +
                "\t\t\t\t\tpiw.YjArea as '硬景面积',\n" +
                "\t\t\t\t\tpiw.YjAreaRate as '硬景面积所占比例',\n" +
                "          piw.SjArea as '水景面积',\n" +
                "\t\t\t\t\tpiw.SjAreaRate as '水景面积所占比例',\n" +
                "\t\t\t\t\tpiw.QwsLength as '区外水接入长度',\n" +
                "\t\t\t\t\tpiw.QwdLength as '区外电接入长度',\n" +
                "\t\t\t\t\tpiw.QwqLength as '区外气接入长度',\n" +
                "\t\t\t\t\tpiw.QwrlLength as '区外热力接入长度',\n" +
                "\t\t\t\t\tpiw.Kbsrj as '开闭所容量',\n" +
                "\t\t\t\t\tpiw.Pdfrj as '配电房容量',\n" +
                "\t\t\t\t\tpiw.PdfNum as '配电房个数',\n" +
                "\t\t\t\t\tpiw.Fdjgl as '发电机功率',\n" +
                "          piw.WfQty as '挖方工程量',\n" +
                "\t\t\t\t\tpiw.TfQty as '填方工程量',\n" +
                "\t\t\t\t\tpiw.WyQty as '外运工程量',\n" +
                "\t\t\t\t\tpiw.ProjectIndexGUID as '项目规划指标GUID',\n" +
                "\t\t\t\t\tppw.RightsRate as '权益比例',\n" +
                "\t\t\t\t\tppw.LandInfoGUID as '地块名称GUID',\n" +
                "\t\t\t\t\tbu.HierarchyCode as '层级代码',\n" +
                "          ppw.VersionGUID as '版本GUID',\n" +
                "\t\t\t\t\tppw.VersionName as '版本名称'\n" +
                "          FROM p_HkbProjectWork ppw\n" +
                "\t\t\t\t\tLEFT JOIN p_hkbprojectwork ppw1 on ppw.parentcode=ppw1.projcode\n" +
                "\t\t\t\t\tLEFT JOIN myBusinessUnit m1 on m1.buguid=ppw.buguid\n" +
                "                    LEFT JOIN myBusinessUnit m2 on m1.parentguid=m2.buguid\n" +
                "                    LEFT JOIN cb_HkbProjectIndexWork piw ON piw.RefGUID=ppw.ProjGUID\n" +
                "                    LEFT JOIN myUser mu ON ppw.Principal=mu.UserGUID\n" +
                "                    LEFT JOIN (select BUGUID,HierarchyCode from myBusinessUnit) bu ON bu.BUGUID=ppw.BUGUID\n" +
                "                    WHERE ppw.IfEnd=1");

        if(list.size()>0){
            jdbcTemplate2.update("DELETE FROM my_project_by_stages_report WHERE length(id)>8");
        }

        for(int i=0;i<list.size();i++){

            String regionComName = "";
            if("".equals(list.get(i).get("区域公司").toString()) || list.get(i).get("区域公司") == null){
                regionComName = list.get(i).get("项目公司").toString();
            }else{
                regionComName = list.get(i).get("区域公司").toString();
            }

            String id = UUID.randomUUID().toString();
            jdbcTemplate2.update("INSERT INTO " +
                    "my_project_by_stages_report(" +
                    "id," +
                    "org_id," +
                    "org_code," +
                    "org_name," +
                    "region_com_code," +
                    "region_com_name," +
                    "project_com_id," +
                    "project_com_code," +
                    "project_com_name," +
                    "project_id," +
                    "project_code," +
                    "project_name," +
                    "project_by_stages_id," +
                    "project_by_stages_code," +
                    "project_by_stages," +
                    "proj_short_code," +
                    "parent_code," +
                    "proj_short_name," +
                    "is_GT," +
                    "level," +
                    "if_end," +
                    "principal," +
                    "belong_area_GUID," +
                    "belong_area_name," +
                    "bgn_sale_date," +
                    "end_sale_date," +
                    "build_begin_date," +
                    "build_end_date," +
                    "fact_bgn_sale_date," +
                    "fact_end_sale_date," +
                    "fact_build_begin_date," +
                    "fact_build_end_date," +
                    "proj_status," +
                    "proj_info," +
                    "occupy_area," +
                    "inner_area," +
                    "sale_num," +
                    "sale_price," +
                    "sale_amount," +
                    "build_density," +
                    "proj_address," +
                    "parent_name," +
                    "begin_date," +
                    "end_date," +
                    "apply_sys," +
                    "approve_state," +
                    "save_state," +
                    "proj_ys_pic_url," +
                    "proj_tz_pic_url," +
                    "user_name," +
                    "usually_code," +
                    "plot_name," +
                    "spread_name," +
                    "once_name," +
                    "target_effigy," +
                    "all_terra_area," +
                    "build_terra_area," +
                    "road_area," +
                    "sight_area," +
                    "cubage_rate," +
                    "ydlh_area," +
                    "build_area," +
                    "upper_build_area," +
                    "under_build_area," +
                    "sale_area," +
                    "upper_sale_area," +
                    "under_sale_area," +
                    "lend_area," +
                    "upper_lend_area," +
                    "under_lend_area," +
                    "largess_area," +
                    "fact_build_area," +
                    "fact_cubage_rate," +
                    "cw_num," +
                    "upper_cw_num," +
                    "under_cw_num," +
                    "zj_length," +
                    "gate_num," +
                    "rj_area," +
                    "rj_area_rate," +
                    "yj_area," +
                    "yj_area_rate," +
                    "sj_area," +
                    "sj_area_rate," +
                    "qws_length," +
                    "qwd_length," +
                    "qwq_length," +
                    "qwrl_length," +
                    "kbsrj," +
                    "pdfrj," +
                    "pdf_num," +
                    "fdjgl," +
                    "wf_qty," +
                    "tf_qty," +
                    "wy_qty," +
                    "project_index_GUID," +
                    "rights_rate," +
                    "land_info_GUID," +
                    "hierarchy_code," +
                    "version_GUID," +
                    "version_name," +
                    "update_time) VALUES ("+
                    "'"+id+"'"+","+
                    (list.get(i).get("集团guid")==null ? "''," : "'"+list.get(i).get("集团guid")+"'"+",") +
                    (list.get(i).get("集团code")==null ? "''," : "'"+list.get(i).get("集团code")+"'"+",") +
                    (list.get(i).get("集团")==null ? "''," : "'"+list.get(i).get("集团")+"'"+",") +
                    (list.get(i).get("区域公司code")==null ? "''," : "'"+list.get(i).get("区域公司code")+"'"+",") +
                    (regionComName==null ? "''," : "'"+regionComName+"'"+",") +
                    (list.get(i).get("项目公司guid")==null ? "''," : "'"+list.get(i).get("项目公司guid")+"'"+",") +
                    (list.get(i).get("项目公司code")==null ? "''," : "'"+list.get(i).get("项目公司code")+"'"+",") +
                    (list.get(i).get("项目公司")==null ? list.get(i).get("项目公司")+"," : "'"+list.get(i).get("项目公司")+"'"+",") +
                    (list.get(i).get("项目guid")==null ? "''," : "'"+list.get(i).get("项目guid")+"'"+",") +
                    (list.get(i).get("项目code")==null ? "''," : "'"+list.get(i).get("项目code")+"'"+",") +
                    (list.get(i).get("项目")==null ? "''," : "'"+list.get(i).get("项目")+"'"+",") +
                    (list.get(i).get("项目分期guid")==null ? "''," : "'"+list.get(i).get("项目分期guid")+"'"+",") +
                    (list.get(i).get("项目分期code")==null ? "''," : "'"+list.get(i).get("项目分期code")+"'"+",") +
                    (list.get(i).get("项目分期")==null ? "''," : "'"+list.get(i).get("项目分期")+"'"+",") +
                    (list.get(i).get("项目代码")==null ? "''," : "'"+list.get(i).get("项目代码")+"'"+",") +
                    (list.get(i).get("父级代码")==null ? "''," : "'"+list.get(i).get("父级代码")+"'"+",") +
                    (list.get(i).get("项目简称")==null ? "''," : "'"+list.get(i).get("项目简称")+"'"+",") +
                    list.get(i).get("是否公建项目")+","+
                    list.get(i).get("项目级数")+","+
                    list.get(i).get("是否最末级项目")+","+
                    (list.get(i).get("项目负责人ID")==null ? "''," : "'"+list.get(i).get("项目负责人ID")+"'"+",") +
                    (list.get(i).get("项目所属区域GUID")==null ? "''," : "'"+list.get(i).get("项目所属区域GUID")+"'"+",") +
                    (list.get(i).get("项目所属区域")==null ? "''," : "'"+list.get(i).get("项目所属区域")+"'"+",") +
                    (list.get(i).get("开盘日期-计划")==null ? null+"," : "'"+list.get(i).get("开盘日期-计划")+"'"+",") +
                    (list.get(i).get("交付日期-计划")==null ? null+"," : "'"+list.get(i).get("交付日期-计划")+"'"+",") +
                    (list.get(i).get("开工日期-计划")==null ? null+"," : "'"+list.get(i).get("开工日期-计划")+"'"+",") +
                    (list.get(i).get("竣工日期-计划")==null ? null+"," : "'"+list.get(i).get("竣工日期-计划")+"'"+",") +
                    (list.get(i).get("开盘日期-实际")==null ? null+"," : "'"+list.get(i).get("开盘日期-实际")+"'"+",") +
                    (list.get(i).get("交付日期-实际")==null ? null+"," : "'"+list.get(i).get("交付日期-实际")+"'"+",") +
                    (list.get(i).get("开工日期-实际")==null ? null+"," : "'"+list.get(i).get("开工日期-实际")+"'"+",") +
                    (list.get(i).get("竣工日期-实际")==null ? null+"," : "'"+list.get(i).get("竣工日期-实际")+"'"+",") +
                    (list.get(i).get("项目状态")==null ? "''," : "'"+list.get(i).get("项目状态")+"'"+",") +
                    (list.get(i).get("项目描述")==null ? "''," : "'"+list.get(i).get("项目描述")+"'"+",") +
                    list.get(i).get("占地面积")+","+
                    list.get(i).get("套内面积")+","+
                    list.get(i).get("可售套数")+","+
                    list.get(i).get("可售单价")+","+
                    list.get(i).get("可售金额")+","+
                    list.get(i).get("建筑密度")+","+
                    (list.get(i).get("项目地址")==null ? "''," : "'"+list.get(i).get("项目地址")+"'"+",") +
                    (list.get(i).get("ParentName")==null ? "''," : "'"+list.get(i).get("ParentName")+"'"+",") +
                    (list.get(i).get("项目开始日期")==null ? null+"," : "'"+list.get(i).get("项目开始日期")+"'"+",") +
                    (list.get(i).get("项目截止日期")==null ? null+"," : "'"+list.get(i).get("项目截止日期")+"'"+",") +
                    (list.get(i).get("应用子系统")==null ? "''," : "'"+list.get(i).get("应用子系统")+"'"+",") +
                    (list.get(i).get("审批状态")==null ? "''," : "'"+list.get(i).get("审批状态")+"'"+",") +
                    (list.get(i).get("归档状态")==null ? "''," : "'"+list.get(i).get("归档状态")+"'"+",") +
                    (list.get(i).get("项目总图预算版")==null ? "''," : "'"+list.get(i).get("项目总图预算版")+"'"+",") +
                    (list.get(i).get("项目总图调整版")==null ? "''," : "'"+list.get(i).get("项目总图调整版")+"'"+",") +
                    (list.get(i).get("项目负责人")==null ? "''," : "'"+list.get(i).get("项目负责人")+"'"+",") +
                    (list.get(i).get("usuallyCode")==null ? "''," : "'"+list.get(i).get("usuallyCode")+"'"+",") +
                    (list.get(i).get("地块名称")==null ? "''," : "'"+list.get(i).get("地块名称")+"'"+",") +
                    (list.get(i).get("项目推广名")==null ? "''," : "'"+list.get(i).get("项目推广名")+"'"+",") +
                    (list.get(i).get("项目曾用名")==null ? "''," : "'"+list.get(i).get("项目曾用名")+"'"+",") +
                    (list.get(i).get("目标客户肖像")==null ? "''," : "'"+list.get(i).get("目标客户肖像")+"'"+",") +
                    list.get(i).get("总用地面积")+","+
                    list.get(i).get("建设用地面积")+","+
                    list.get(i).get("道路面积")+","+
                    list.get(i).get("景观面积")+","+
                    list.get(i).get("容积率")+","+
                    list.get(i).get("异地绿化面积")+","+
                    list.get(i).get("建筑面积")+","+
                    list.get(i).get("地上建筑面积")+","+
                    list.get(i).get("地下建筑面积")+","+
                    list.get(i).get("可售面积")+","+
                    list.get(i).get("地上可售面积")+","+
                    list.get(i).get("地下可售面积")+","+
                    list.get(i).get("可租面积")+","+
                    list.get(i).get("地上可租面积")+","+
                    list.get(i).get("地下可租面积")+","+
                    list.get(i).get("赠送面积")+","+
                    list.get(i).get("实际建设面积")+","+
                    list.get(i).get("实际容积率")+","+
                    list.get(i).get("车位数")+","+
                    list.get(i).get("地上车位数")+","+
                    list.get(i).get("地下车位数")+","+
                    list.get(i).get("周界长度")+","+
                    list.get(i).get("大门个数")+","+
                    list.get(i).get("软景面积")+","+
                    list.get(i).get("软景面积所占比例")+","+
                    list.get(i).get("硬景面积")+","+
                    list.get(i).get("硬景面积所占比例")+","+
                    list.get(i).get("水景面积")+","+
                    list.get(i).get("水景面积所占比例")+","+
                    list.get(i).get("区外水接入长度")+","+
                    list.get(i).get("区外电接入长度")+","+
                    list.get(i).get("区外气接入长度")+","+
                    list.get(i).get("区外热力接入长度")+","+
                    list.get(i).get("开闭所容量")+","+
                    list.get(i).get("配电房容量")+","+
                    list.get(i).get("配电房个数")+","+
                    list.get(i).get("发电机功率")+","+
                    list.get(i).get("挖方工程量")+","+
                    list.get(i).get("填方工程量")+","+
                    list.get(i).get("外运工程量")+","+
                    (list.get(i).get("项目规划指标GUID")==null ? "''," : "'"+list.get(i).get("项目规划指标GUID")+"'"+",") +
                    list.get(i).get("权益比例")+","+
                    (list.get(i).get("地块名称GUID")==null ? "''," : "'"+list.get(i).get("地块名称GUID")+"'"+",") +
                    (list.get(i).get("层级代码")==null ? "''," : "'"+list.get(i).get("层级代码")+"'"+",") +
                    (list.get(i).get("版本GUID")==null ? "''," : "'"+list.get(i).get("版本GUID")+"'"+",") +
                    (list.get(i).get("版本名称")==null ? "''," : "'"+list.get(i).get("版本名称")+"'"+",") +
                    "'"+thisDay+"'"+
                    ")");
        }
        XxlJobLogger.log(TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss") + "项目分期概况数据更新完成");
    }


    /**
     * 提取销售回笼汇总情况表
     *
     * */
    public static void mySalesWithdrawalUpdate(JdbcTemplate jdbcTemplate1,JdbcTemplate jdbcTemplate2,String thisDay) throws Exception{
        List<Map<String,Object>> list = jdbcTemplate1.queryForList("EXEC usp_bz_p_rptxshlqkb '" + thisDay + "'");

        if(list.size()>0){
            jdbcTemplate2.update("DELETE FROM my_sales_withdrawal_report WHERE update_time="+"'"+thisDay+"'");
        }
        for(int i=0;i<list.size();i++){
            String regionComName = "";
            if("".equals(list.get(i).get("区域公司").toString()) || list.get(i).get("区域公司") == null){
                regionComName = list.get(i).get("项目公司").toString();
            }else{
                regionComName = list.get(i).get("区域公司").toString();
            }

            String id = UUID.randomUUID().toString();
            jdbcTemplate2.update("INSERT INTO " +
                    "my_sales_withdrawal_report(" +
                    "id," +
                    "org_id," +
                    "org_code," +
                    "org_name," +
                    "region_com_code," +
                    "region_com_name," +
                    "project_com_id," +
                    "project_com_code," +
                    "project_com_name," +
                    "project_id," +
                    "project_code," +
                    "project_name," +
                    "product_type_id," +
                    "first_level_type_name," +
                    "sec_level_type_name," +
                    "product_type_code," +
                    "month_plan_sales_area," +
                    "month_plan_sales_amount," +
                    "month_plan_withdrawal," +
                    "month_actual_sales," +
                    "month_actual_sales_area," +
                    "month_actual_withdrawal_area," +
                    "month_actual_sales_amount," +
                    "month_actual_withdrawal_amount," +
                    "month_plan_sales_rate," +
                    "month_plan_withdrawal_rate," +
                    "month_an_sales_growth_rate," +
                    "month_an_withdrawal_growth_rate," +
                    "month_mom_sales_growth_rate," +
                    "month_mom_withdrawal_growth_rate," +
                    "cumulative_plan_sales_area," +
                    "cumulative_plan_sales_amount," +
                    "cumulative_plan_withdrawal_amount," +
                    "cumulative_actual_sales," +
                    "cumulative_actual_sales_area," +
                    "cumulative_actual_withdrawal_area," +
                    "cumulative_actual_sales_amount," +
                    "cumulative_actual_withdrawal_amount," +
                    "cumulative_plan_sales_rate," +
                    "cumulative_plan_withdrawal_rate," +
                    "cumulative_an_sales_growth_rate," +
                    "cumulative_an_withdrawal_growth_rate," +
                    "cumulative_mom_sales_growth_rate," +
                    "cumulative_mom_withdrawal_growth_rate," +
                    "month_total_receivable_amount," +
                    "month_expire_receivable_amount," +
                    "not_signed_set," +
                    "not_signed_amount," +
                    "average_price," +
                    "remark," +
                    "project_by_stages_id," +
                    "project_by_stages_code," +
                    "project_by_stages," +
                    "year_budget_total," +
                    "total_area," +
                    "day_actual_sales_area," +
                    "day_actual_sales_amount," +
                    "day_actual_withdrawal_amount," +
                    "day_an_sales_growth_rate," +
                    "day_an_withdrawal_growth_rate," +
                    "day_mom_sales_growth_rate," +
                    "day_mom_withdrawal_growth_rate," +
                    "update_time) VALUES ("+
                    "'"+id+"'"+","+
                    (list.get(i).get("集团guid")==null ? "''," : "'"+list.get(i).get("集团guid")+"'"+",") +
                    (list.get(i).get("集团code")==null ? "''," : "'"+list.get(i).get("集团code")+"'"+",") +
                    (list.get(i).get("集团")==null ? "''," : "'"+list.get(i).get("集团")+"'"+",") +
                    (list.get(i).get("区域公司code")==null ? "''," : "'"+list.get(i).get("区域公司code")+"'"+",") +
                    (regionComName==null ? "''," : "'"+regionComName+"'"+",") +
                    (list.get(i).get("项目公司guid")==null ? "''," : "'"+list.get(i).get("项目公司guid")+"'"+",") +
                    (list.get(i).get("项目公司code")==null ? "''," : "'"+list.get(i).get("项目公司code")+"'"+",") +
                    (list.get(i).get("项目公司")==null ? "''," : "'"+list.get(i).get("项目公司")+"'"+",") +
                    (list.get(i).get("项目guid")==null ? "''," : "'"+list.get(i).get("项目guid")+"'"+",") +
                    (list.get(i).get("项目code")==null ? "''," : "'"+list.get(i).get("项目code")+"'"+",") +
                    (list.get(i).get("项目")==null ? "''," : "'"+list.get(i).get("项目")+"'"+",") +
                    (list.get(i).get("产品类型guid")==null ? "''," : "'"+list.get(i).get("产品类型guid")+"'"+",") +
                    (list.get(i).get("一级产品类型")==null ? "''," : "'"+list.get(i).get("一级产品类型")+"'"+",") +
                    (list.get(i).get("二级产品类型")==null ? "''," : "'"+list.get(i).get("二级产品类型")+"'"+",") +
                    (list.get(i).get("BProductTypeCode")==null ? "''," : "'"+list.get(i).get("BProductTypeCode")+"'"+",") +
                    list.get(i).get("本月计划销售面积")+","+
                    list.get(i).get("本月计划销售金额")+","+
                    list.get(i).get("本月计划销售回笼")+","+
                    list.get(i).get("本月实际销售套数")+","+
                    list.get(i).get("本月实际销售面积")+","+
                    list.get(i).get("本月笼面积")+","+
                    list.get(i).get("本月实际销售金额")+","+
                    list.get(i).get("本月实际销售回笼")+","+
                    list.get(i).get("本月计划销售额完成率")+","+
                    list.get(i).get("本月计划回笼额完成率")+","+
                    list.get(i).get("本月同比销售额增长率")+","+
                    list.get(i).get("本月同比回笼额增长率")+","+
                    list.get(i).get("本月环比销售额增长率")+","+
                    list.get(i).get("本月环比回笼额增长率")+","+
                    list.get(i).get("本年截止到月计划面积")+","+
                    list.get(i).get("本年截止到月计划金额")+","+
                    list.get(i).get("本年截止到月计划回笼")+","+
                    list.get(i).get("本年实际销售套数")+","+
                    list.get(i).get("本年实际销售面积")+","+
                    list.get(i).get("累笼面积")+","+
                    list.get(i).get("本年实际销售金额")+","+
                    list.get(i).get("本年实际回笼")+","+
                    list.get(i).get("本年计划销售额完成率")+","+
                    list.get(i).get("本年计划回笼额完成率")+","+
                    list.get(i).get("本年同比销售额增长率")+","+
                    list.get(i).get("本年同比回笼额增长率")+","+
                    list.get(i).get("本年环比销售额增长率")+","+
                    list.get(i).get("本年环比回笼额增长率")+","+
                    list.get(i).get("截至本月总应收款")+","+
                    list.get(i).get("本月底到期应收款")+","+
                    list.get(i).get("已定未签套数")+","+
                    list.get(i).get("已定未签额")+","+
                    list.get(i).get("平均单价")+","+
                    (list.get(i).get("备注")==null ? "''," : "'"+list.get(i).get("备注")+"'"+",") +
                    (list.get(i).get("项目分期guid")==null ? "''," : "'"+list.get(i).get("项目分期guid")+"'"+",") +
                    (list.get(i).get("项目分期code")==null ? "''," : "'"+list.get(i).get("项目分期code")+"'"+",") +
                    (list.get(i).get("项目分期")==null ? "''," : "'"+list.get(i).get("项目分期")+"'"+",") +
                    list.get(i).get("本年所有计划金额")+","+
                    list.get(i).get("本年所有计划面积")+","+
                    list.get(i).get("本日实际销售面积")+","+
                    list.get(i).get("本日实际销售金额")+","+
                    list.get(i).get("本日实际销售回笼")+","+
                    list.get(i).get("本日同比销售额增长率")+","+
                    list.get(i).get("本日同比回笼额增长率")+","+
                    list.get(i).get("本日环比销售额增长率")+","+
                    list.get(i).get("本日环比回笼额增长率")+","+
                    "'"+thisDay+"'"+
                    ")");
        }
        XxlJobLogger.log(thisDay + "数据更新完成");
    }


    /**
     * 提取库存相关资料数据
     *
     * */
//    @Transactional
    public static void myStockDataUpdate(JdbcTemplate jdbcTemplate1,JdbcTemplate jdbcTemplate2) throws Exception{

//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String thisDay = TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");

        List<Map<String,Object>> list = jdbcTemplate1.queryForList("select a.jtguid as 集团guid,a.jtcode as 集团code,a.jtname as 集团,\n" +
                "a.QyCompanyCode as 区域公司code,a.QyCompanyName as 区域公司,\n" +
                "a.gsguid as 项目公司guid,a.gscode as 项目公司code,a.BUName as 项目公司,\n" +
                "a.xmguid as 项目guid,a.xmcode as 项目code,a.pprojname as 项目, \n" +
                "a.pprojguid as 项目分期guid,a.ppcode as 项目分期code,a.ProjName as 项目分期,\n" +
                "a.BProductTypeCode as 产品类型code, a.FirstLevelName as 一级产品类型,a.BProductTypeName as 二级产品类型,\n" +
                "isnull(nc.年初库存去化金额,0) as 年初库存去化金额,isnull(nc.新增库存去化金额,0) as 新增库存去化金额,\n" +
                "isnull(sy.库存剩余金额,0) as 库存剩余金额,isnull(sy.新增剩余可售金额,0) as 新增剩余可售金额,isnull(sy.新增库存金额,0) as 新增库存金额\n" +
                "from   \n" +
                "(  \n" +
                "\tselect distinct  \n" +
                "\tm1.BUGUID as jtguid,m1.BUCode as jtcode,m1.BUName as jtname,\n" +
                "\tm.QyCompanyCode,m.QyCompanyName,\n" +
                "\tm.BUGUID as gsguid,m.BUCode as gscode,m.BUName,\n" +
                "\tp1.ProjGUID as xmguid,p1.ProjCode as xmcode,p1.ProjName as pprojname,\n" +
                "\tp.ProjGUID as pprojguid,p.ProjCode as ppcode,p.ProjName, \n" +
                "\tbt.FirstLevelName,bt.BProductTypeName,bt.BProductTypeShortName,r.BProductTypeCode \n" +
                "\tfrom p_Project p   \n" +
                "\tleft join p_Project p1 on p.ParentCode = p1.ProjCode  \n" +
                "\tleft join p_room r on p.ProjGUID = r.ProjGUID  \n" +
                "\tleft join myBusinessUnit m on p.BUGUID = m.BUGUID  \n" +
                "\tleft join myBusinessUnit m1 on m.ParentGUID=m1.BUGUID  \n" +
                "\tleft join p_BuildProductType bt on r.BProductTypeCode = bt.BProductTypeCode  \n" +
                "\twhere r.BProductTypeCode is not null and p.IfEnd = 1  \n" +
                "\t--and p.ProjGUID in(select * from dbo.fn_split(@var_projguid,','))  \n" +
                "\tunion \n" +
                "\tselect distinct  \n" +
                "\tm1.BUGUID as jtguid,m1.BUCode as jtcode,m1.BUName as jtname,\n" +
                "\tm.QyCompanyCode,m.QyCompanyName,\n" +
                "\tm.BUGUID as gsguid,m.BUCode as gscode,m.BUName,\n" +
                "\tp1.ProjGUID as xmguid,p1.ProjCode as xmcode,p1.ProjName as pprojname,\n" +
                "\tp.ProjGUID as pprojguid,p.ProjCode as ppcode,p.ProjName,\n" +
                "\tbt.FirstLevelName,bt.BProductTypeName,bt.BProductTypeShortName,bt.BProductTypeCode\n" +
                "\tfrom p_Project p                                                    \n" +
                "\tleft join p_Project p1 on p.ParentCode = p1.ProjCode                                                                  \n" +
                "\tleft join myBusinessUnit m on p.BUGUID = m.BUGUID                                                    \n" +
                "\tleft join myBusinessUnit m1 on m.ParentGUID = m1.BUGUID                                      \n" +
                "\tleft join ys_SaleBudgetDtl bd on p.ProjGUID = bd.ProjGUID                                     \n" +
                "\tleft join cb_Product pp on bd.ProductGUID = pp.ProductGUID                                                   \n" +
                "\tleft join p_BuildProductType bt on pp.BProductTypeCode = bt.BProductTypeCode                                                    \n" +
                "\twhere bt.BProductTypeCode is not null and bt.IfEnd = 1                                                    \n" +
                "\t--and p.ProjGUID in(select * from dbo.fn_split(@var_projguid,','))   \n" +
                ")a  \n" +
                "left join\n" +
                "(\n" +
                "\tselect p.ProjGUID,bt.FirstLevelName,r.BProductTypeCode,\n" +
                "\tSUM(case when YEAR(b.YszDate)<YEAR(GETDATE()) then rmbamount else 0 end) as 年初库存去化金额, --2017签约2017回款但是销售许可证在2017以前,都是审核日期,回款\n" +
                "\tSUM(case when YEAR(b.YszDate)=YEAR(GETDATE()) then rmbamount else 0 end) as 新增库存去化金额 --2017签约2017回款销售许可证在2017,都是审核日期,回款\n" +
                "\tfrom dbo.s_Voucher v    \n" +
                "\tinner join s_getin g on g.VouchGUID=v.VouchGUID    \n" +
                "\tleft join s_Contract c on g.SaleGUID=c.TradeGUID\n" +
                "\tleft join p_room r on v.RoomGUID = r.RoomGUID \n" +
                "\tleft join p_Building b on r.BldGUID = b.BldGUID          \n" +
                "\tleft join p_BuildProductType bt on r.BProductTypeCode = bt.BProductTypeCode          \n" +
                "\tleft join p_Project p on v.ProjGUID = p.ProjGUID          \n" +
                "\tleft join p_Project p1 on p.ParentCode = p1.ProjCode          \n" +
                "\twhere v.VouchType not in ('转账单','换票单')     \n" +
                "\tand g.ItemType in ('非贷款类房款','贷款类房款')    \n" +
                "\tand ShDate is not null     \n" +
                "\tand v.ShDate is not null and c.Status = '激活'\n" +
                "\tand YEAR(c.AuditingDate)=YEAR(GETDATE()) \n" +
                "\tand YEAR(v.ShDate)=YEAR(GETDATE()) \n" +
                "\tgroup  by p.ProjGUID,bt.FirstLevelName,r.BProductTypeCode    \n" +
                ")nc on a.pprojguid = nc.ProjGUID and a.BProductTypeCode = nc.BProductTypeCode\n" +
                "left join\n" +
                "(\n" +
                "\tselect p.ProjGUID,bt.FirstLevelName,r.BProductTypeCode,\n" +
                "\t --销售许可证在2017以前的,底价合计,除了签约和认购\n" +
                "\tSUM(case when r.Status not in('认购','签约') and YEAR(b.YszDate)<YEAR(GETDATE()) then r.TotalDj else 0 end) as 库存剩余金额,\n" +
                "\tSUM(case when YEAR(b.YszDate)=YEAR(GETDATE()) then r.TotalDj else 0 end) as 新增库存金额, --销售许可证在2017所有的底价\n" +
                "\t --销售许可证在2017的,底价合计,除了签约和认购\n" +
                "\tSUM(case when r.Status not in('认购','签约') and YEAR(b.YszDate)=YEAR(GETDATE()) then r.TotalDj else 0 end) as 新增剩余可售金额\n" +
                "\tfrom p_room r \n" +
                "\tleft join p_Building b on r.BldGUID = b.BldGUID          \n" +
                "\tleft join p_BuildProductType bt on r.BProductTypeCode = bt.BProductTypeCode          \n" +
                "\tleft join p_Project p on r.ProjGUID = p.ProjGUID          \n" +
                "\tleft join p_Project p1 on p.ParentCode = p1.ProjCode  \n" +
                "\tgroup  by p.ProjGUID,bt.FirstLevelName,r.BProductTypeCode \n" +
                ")sy on a.pprojguid = sy.ProjGUID and a.BProductTypeCode = sy.BProductTypeCode\n" +
                "order by 集团code,项目公司code,项目code,项目分期code,产品类型code");

        if(list.size()>0){
            jdbcTemplate2.update("DELETE FROM my_stock_data WHERE 1=1");
        }
        for(int i=0;i<list.size();i++){
            String regionComName = "";
            if("".equals(list.get(i).get("区域公司").toString()) || list.get(i).get("区域公司") == null){
                regionComName = list.get(i).get("项目公司").toString();
            }else{
                regionComName = list.get(i).get("区域公司").toString();
            }

            String id = UUID.randomUUID().toString();

            jdbcTemplate2.update("INSERT INTO " +
                    "my_stock_data(" +
                    "id," +
                    "org_id," +
                    "org_code," +
                    "org_name," +
                    "region_com_code," +
                    "region_com_name," +
                    "project_com_id," +
                    "project_com_code," +
                    "project_com_name," +
                    "project_id," +
                    "project_code," +
                    "project_name," +
                    "project_by_stages_id," +
                    "project_by_stages_code," +
                    "project_by_stages," +
                    "product_type_code," +
                    "first_level_type_name," +
                    "sec_level_type_name," +
                    "beginning_stock_go_amount," +
                    "new_stock_go_amount," +
                    "stock_surplus_amount," +
                    "new_surplus_sale_amount," +
                    "new_stock_amount," +
                    "update_time) VALUES ("+
                    "'"+id+"'"+","+
                    (list.get(i).get("集团guid")==null ? "''," : "'"+list.get(i).get("集团guid")+"'"+",") +
                    (list.get(i).get("集团code")==null ? "''," : "'"+list.get(i).get("集团code")+"'"+",") +
                    (list.get(i).get("集团")==null ? "''," : "'"+list.get(i).get("集团")+"'"+",") +
                    (list.get(i).get("区域公司code")==null ? "''," : "'"+list.get(i).get("区域公司code")+"'"+",") +
                    (regionComName==null ? "''," : "'"+regionComName+"'"+",") +
                    (list.get(i).get("项目公司guid")==null ? "''," : "'"+list.get(i).get("项目公司guid")+"'"+",") +
                    (list.get(i).get("项目公司code")==null ? "''," : "'"+list.get(i).get("项目公司code")+"'"+",") +
                    (list.get(i).get("项目公司")==null ? "''," : "'"+list.get(i).get("项目公司")+"'"+",") +
                    (list.get(i).get("项目guid")==null ? "''," : "'"+list.get(i).get("项目guid")+"'"+",") +
                    (list.get(i).get("项目code")==null ? "''," : "'"+list.get(i).get("项目code")+"'"+",") +
                    (list.get(i).get("项目")==null ? "''," : "'"+list.get(i).get("项目")+"'"+",") +
                    (list.get(i).get("项目分期guid")==null ? "''," : "'"+list.get(i).get("项目分期guid")+"'"+",") +
                    (list.get(i).get("项目分期code")==null ? "''," : "'"+list.get(i).get("项目分期code")+"'"+",") +
                    (list.get(i).get("项目分期")==null ? "''," : "'"+list.get(i).get("项目分期")+"'"+",") +
                    (list.get(i).get("产品类型code")==null ? "''," : "'"+list.get(i).get("产品类型code")+"'"+",") +
                    (list.get(i).get("一级产品类型")==null ? "''," : "'"+list.get(i).get("一级产品类型")+"'"+",") +
                    (list.get(i).get("二级产品类型")==null ? "''," : "'"+list.get(i).get("二级产品类型")+"'"+",") +
                    list.get(i).get("年初库存去化金额")+","+
                    list.get(i).get("新增库存去化金额")+","+
                    list.get(i).get("库存剩余金额")+","+
                    list.get(i).get("新增剩余可售金额")+","+
                    list.get(i).get("新增库存金额")+","+
                    "'"+thisDay+"'"+
                    ")");
        }
        XxlJobLogger.log(TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss")+"库存相关资料数据更新完成");
    }
}
