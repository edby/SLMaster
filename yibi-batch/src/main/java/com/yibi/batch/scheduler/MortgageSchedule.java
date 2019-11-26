package com.yibi.batch.scheduler;

import com.yibi.batch.biz.MortgageBiz;
import com.yibi.batch.biz.OrderBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 特殊币种定时处理
 */
@Component
public class MortgageSchedule {
	
	@Autowired
	private MortgageBiz mortgageBiz;

	/**
	 * 每日发放抵押收益
	 */
	@Scheduled(cron="0 1 0 * * ?")
	public void changePrice(){
		mortgageBiz.release();
	}
}
