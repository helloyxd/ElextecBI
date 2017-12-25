package com.jybi.job.executor.service.jobhandler;


import com.alibaba.druid.util.StringUtils;
import com.jybi.job.core.biz.model.ReturnT;
import com.jybi.job.core.handler.IJobHandler;
import com.jybi.job.core.handler.annotation.JobHander;
import com.jybi.job.core.log.XxlJobLogger;
import com.jybi.job.executor.util.TimeUtil;
import com.jybi.job.executor.util.UpdateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 明源数据更新公共类（无事务处理）
 *
 * 开发步骤：
 * 1、继承 “IJobHandler” ；
 * 2、装配到Spring，例如加 “@Service” 注解；
 * 3、加 “@JobHander” 注解，注解value值为新增任务生成的JobKey的值;多个JobKey用逗号分割;
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author xuxueli 2015-12-19 19:43:36
 */
@JobHander(value="myJobHandler")
@Service
public class MyJobHandler extends IJobHandler {

	@Autowired
	@Qualifier("source_jdbcTemplate")
	protected JdbcTemplate jdbcTemplate1;

	@Autowired
	@Qualifier("goal_jdbcTemplate")
	protected JdbcTemplate jdbcTemplate2;

	@Override
	public ReturnT<String> execute(String... params) throws Exception {
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
//		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		//params参数解析
		if(params != null){
			//开始年月日
			StringBuilder beginDate = new StringBuilder("");
			//结束年月日
			StringBuilder endDate = new StringBuilder("");
			//固定区间
			if("fixed".equals(params[0]) && params.length == 4){
				beginDate.append(params[1]);
				endDate.append(params[2]);
			}
			//动态区间
			else if("dynamic".equals(params[0]) && params.length == 6){
				//获取当前年月日
				Calendar today = Calendar.getInstance();
				int year = today.get(Calendar.YEAR);
				int month = today.get(Calendar.MONTH )+1;
				int day = today.get(Calendar.DAY_OF_MONTH);
				if("year".equals(params[2])){
					int beginYear = year - Integer.valueOf(params[1]);
					int beginMonth = month;
					beginDate.append(String.valueOf(beginYear));
					beginDate.append("-");
					beginDate.append(String.valueOf(beginMonth));
					beginDate.append("-");
					beginDate.append("1");
				}else if("month".equals(params[2])){
					int beginYear = year;
					int beginMonth = month - Integer.valueOf(params[1]);
					beginDate.append(String.valueOf(beginYear));
					beginDate.append("-");
					beginDate.append(String.valueOf(beginMonth));
					beginDate.append("-");
					beginDate.append("1");
				}else if("day".equals(params[2])){
					Calendar temp = Calendar.getInstance();
					temp.add(Calendar.DAY_OF_MONTH,-(Integer.valueOf(params[1])));
					beginDate.append(TimeUtil.dateToStr(temp.getTime(),"yyyy-MM-dd"));
				}else{
					XxlJobLogger.log(TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss")+"开始时间类型无法识别");
					return ReturnT.FAIL;
				}

				if("year".equals(params[4])){
					int endYear = year - Integer.valueOf(params[3]);
					int endMonth = month;
					endDate.append(String.valueOf(endYear));
					endDate.append("-");
					endDate.append(String.valueOf(endMonth));
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(TimeUtil.strToDate(endDate.toString(),"yyyy-MM"));
					int maxLastMonthDays = calendar.getActualMaximum(Calendar.DATE);
					beginDate.append("-");
					beginDate.append(String.valueOf(maxLastMonthDays));
				}else if("month".equals(params[4])){
					int endYear = year;
					int endMonth = month - Integer.valueOf(params[3]);
					endDate.append(String.valueOf(endYear));
					endDate.append("-");
					endDate.append(String.valueOf(endMonth));
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(TimeUtil.strToDate(endDate.toString(),"yyyy-MM"));
					int maxLastMonthDays = calendar.getActualMaximum(Calendar.DATE);
					beginDate.append("-");
					beginDate.append(String.valueOf(maxLastMonthDays));
				}else if("day".equals(params[4])){
					Calendar temp = Calendar.getInstance();
					temp.add(Calendar.DAY_OF_MONTH,-(Integer.valueOf(params[3])));
					endDate.append(TimeUtil.dateToStr(temp.getTime(),"yyyy-MM-dd"));
				}else{
					XxlJobLogger.log(TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss")+"结束时间类型无法识别");
					return ReturnT.FAIL;
				}
			}
			else if(params.length == 1){
				//提取预算编制数据（当年）
				if("budgetOrganization".equals(params[params.length-1])){
					UpdateUtil.myBudgetOrganizationUpdate(jdbcTemplate1,jdbcTemplate2);
				}
				//去库存销售数据
				else if("delStockSales".equals(params[params.length-1])){
					UpdateUtil.myDelStockSalesUpdate(jdbcTemplate1,jdbcTemplate2);
				}
				//提取项目资料数据
				else if("orgComProject".equals(params[params.length-1])){
					UpdateUtil.myOrgComProjectUpdate(jdbcTemplate1,jdbcTemplate2);
				}
				//提取项目分期数据
				else if("orgComProjectStages".equals(params[params.length-1])){
					UpdateUtil.myOrgComProjectStagesUpdate(jdbcTemplate1,jdbcTemplate2);
				}
				//项目分期概况
				else if("projectByStages".equals(params[params.length-1])){
					UpdateUtil.myProjectByStagesUpdate(jdbcTemplate1,jdbcTemplate2);
				}
				//提取库存相关资料数据
				else if("stockData".equals(params[params.length-1])){
					UpdateUtil.myStockDataUpdate(jdbcTemplate1,jdbcTemplate2);
				}
			}
			//特殊条件:
			else if(params.length == 3){
				//年初至某天
				if("begin_year".equals(params[0])){
					Calendar today = Calendar.getInstance();
					int year = today.get(Calendar.YEAR);
					beginDate.append(year);
					beginDate.append("-1-1");
					today.add(Calendar.DAY_OF_MONTH,-(Integer.valueOf(params[1])));
					endDate.append(TimeUtil.dateToStr(today.getTime(),"yyyy-MM-dd"));
					Calendar beginCalendar = Calendar.getInstance();
					beginCalendar.setTime(TimeUtil.strToDate(beginDate.toString(),"yyyy-MM-dd"));
					Calendar endCalendar = Calendar.getInstance();
					endCalendar.setTime(TimeUtil.strToDate(endDate.toString(),"yyyy-MM-dd"));
					if(beginCalendar.getTimeInMillis() > endCalendar.getTimeInMillis()){
						return ReturnT.SUCCESS;
					}
				}
				//固定每月某一天，本年当月前的所有月份
				if("fixed_date".equals(params[0]) && "max_day".equals(params[1])){
					Calendar today = Calendar.getInstance();
					int year = today.get(Calendar.YEAR);
					int month = today.get(Calendar.MONTH )+1;
					Calendar cal = Calendar.getInstance();
					for(int i=1;i<month;i++){
						StringBuilder thisDay = new StringBuilder("");
						thisDay.append(year);
						thisDay.append("-");
						thisDay.append(i);
//						String temp = thisDay.toString() + "-1";
						cal.setTime(TimeUtil.strToDate(thisDay.toString(),"yyyy-MM"));
						int maxMonthDay = cal.getActualMaximum(Calendar.DATE);
						thisDay.append("-");
						thisDay.append(maxMonthDay);
						//销售日报表更新
						if("dailySales".equals(params[params.length-1])){
							UpdateUtil.myDailySalesUpdate(jdbcTemplate1,jdbcTemplate2,thisDay.toString());
						}
						//提取销售回笼汇总情况表
						else if("salesWithdrawal".equals(params[params.length-1])){
							UpdateUtil.mySalesWithdrawalUpdate(jdbcTemplate1,jdbcTemplate2,thisDay.toString());
						}
						//按一级产品类型库存报表
						else if("productTypeStock".equals(params[params.length-1])){
							UpdateUtil.myProductTypeStockUpdate(jdbcTemplate1,jdbcTemplate2,thisDay.toString());
						}
					}
				}
			}
			else
			{
				XxlJobLogger.log(TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss")+"参数类型无法识别");
				return ReturnT.FAIL;
			}
			if(!StringUtils.isEmpty(beginDate.toString())&&!StringUtils.isEmpty(endDate.toString())){
				Calendar beginCalendar = Calendar.getInstance();
				beginCalendar.setTime(TimeUtil.strToDate(beginDate.toString(),"yyyy-MM-dd"));
				Calendar endCalendar = Calendar.getInstance();
				endCalendar.setTime(TimeUtil.strToDate(endDate.toString(),"yyyy-MM-dd"));
				if(beginCalendar.getTimeInMillis() > endCalendar.getTimeInMillis()){
					XxlJobLogger.log(TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss")+"结束时间不能小于开始时间");
					return ReturnT.FAIL;
				}else{
					//计算两个日期之间相差的天数
					long days=(endCalendar.getTimeInMillis()-beginCalendar.getTimeInMillis())/(1000*60*60*24);
					for(int i=0;i<=days;i++){
						Calendar temp = Calendar.getInstance();
						temp.setTime(TimeUtil.strToDate(beginDate.toString(),"yyyy-MM-dd"));
						temp.add(Calendar.DAY_OF_MONTH,i);
						String thisDay = TimeUtil.dateToStr(temp.getTime(),"yyyy-MM-dd");
						//销售日报表更新
						if("dailySales".equals(params[params.length-1])){
							UpdateUtil.myDailySalesUpdate(jdbcTemplate1,jdbcTemplate2,thisDay);
						}
						//提取销售回笼汇总情况表
						else if("salesWithdrawal".equals(params[params.length-1])){
							UpdateUtil.mySalesWithdrawalUpdate(jdbcTemplate1,jdbcTemplate2,thisDay);
						}
						//按一级产品类型库存报表
						else if("productTypeStock".equals(params[params.length-1])){
							UpdateUtil.myProductTypeStockUpdate(jdbcTemplate1,jdbcTemplate2,thisDay);
						}
					}
				}
			}
		}else{
			XxlJobLogger.log(TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss")+"任务无参数");
			return ReturnT.FAIL;
		}
		return ReturnT.SUCCESS;
	}
}
