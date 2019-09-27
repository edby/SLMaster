package com.yibi.batch.scheduler;

import com.yibi.batch.biz.OrderBiz;
import com.yibi.batch.biz.StatisticsBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 每日统计定时
 */
@Component
public class StatisticsSchedule {
	
	@Autowired
	private StatisticsBiz statisticsBiz;

	/**
	 * 每日统计
	 */
//	@Scheduled(cron="0 0 1 * * ?")
	public void statistiscDay(){
		statisticsBiz.statistiscDay();
	}

}
