package com.yibi.batch.scheduler;

import com.yibi.batch.biz.OdinBiz;
import com.yibi.batch.biz.YubiProfitBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OdinSchedule {
	
	@Autowired
	private OdinBiz odinBiz;

	/**
	 * 修改期数 当前价 下期价
	 */
	@Scheduled(cron="0 5 1 * * ?")
	public void job(){
		odinBiz.start();
	}

	/**
	 * 更新最新交易价格
	 */
	@Scheduled(cron="0 0 */2 * * ?")
	public void job2(){
		odinBiz.changeOrderPrice();
	}
	/**
	 * 释放币
	 */
	@Scheduled(cron="0 0 1 * * ?")
	public void job3(){
		odinBiz.release();
	}
	/**
	 * 计算排名
	 */
	@Scheduled(cron="0 59 23 ? * L")
	public void job4(){
		odinBiz.calculationRank();
	}
}
