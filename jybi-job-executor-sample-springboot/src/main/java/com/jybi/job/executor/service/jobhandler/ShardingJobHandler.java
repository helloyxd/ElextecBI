package com.jybi.job.executor.service.jobhandler;

import com.jybi.job.core.biz.model.ReturnT;
import com.jybi.job.core.handler.IJobHandler;
import com.jybi.job.core.handler.annotation.JobHander;
import com.jybi.job.core.log.XxlJobLogger;
import com.jybi.job.core.util.ShardingUtil;
import org.springframework.stereotype.Service;


/**
 * 分片广播任务
 *
 * @author xuxueli 2017-07-25 20:56:50
 */
@JobHander(value="shardingJobHandler")
@Service
public class ShardingJobHandler extends IJobHandler {

	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		System.out.println("333333333");
		// 分片参数
		ShardingUtil.ShardingVO shardingVO = ShardingUtil.getShardingVo();
		XxlJobLogger.log("分片参数：当前分片序号 = {0}, 总分片数 = {1}", shardingVO.getIndex(), shardingVO.getTotal());

		// 业务逻辑
		for (int i = 0; i < shardingVO.getTotal(); i++) {
			if (i == shardingVO.getIndex()) {
				XxlJobLogger.log("第 {0} 片, 命中分片开始处理", i);
			} else {
				XxlJobLogger.log("第 {0} 片, 忽略", i);
			}
		}

		return ReturnT.SUCCESS;
	}

}
