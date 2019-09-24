package com.yibi.batch.scheduler;

import com.yibi.batch.biz.MarketBiz;
import com.yibi.batch.biz.OrderBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MarketSchedule {
	
	@Autowired
	private MarketBiz marketBiz;

	/**
	 * 行情变化推送
	 */
	@Scheduled(cron="1/5 * * * * ?")
	public void changePrice(){
		marketBiz.changeMarket();
	}

	/**
	 * 记录每日价格
	 */
	@Scheduled(cron="0 1 * * * ?")
	public void getPrice(){
		marketBiz.getDayPrice();
	}

}
