package com.yibi.batch.scheduler;

import com.yibi.batch.biz.OdinBiz;
import com.yibi.batch.biz.OrderBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class OrderSchedule {
	
	@Autowired
	private OrderBiz orderBiz;

	/**
	 * 每日价格变动
	 */
//	@Scheduled(cron="0 20 15 * * ?")
	public void changePrice(){
		orderBiz.changePrice();
	}
	/**
	 * 每日撤销订单
	 */
//	@Scheduled(cron="0 20 15 * * ?")
	public void cancelOrder(){
		orderBiz.cancelOrder();
	}
	/**
	 * 每日开放交易功能
	 */
//	@Scheduled(cron="0 20 15 * * ?")
	public void open(){
		orderBiz.open();
	}

}
