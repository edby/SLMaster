package com.yibi.batch.scheduler;

import com.yibi.batch.biz.OdinBiz;
import com.yibi.batch.biz.OdinWalletBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class OdinWalletSchedule {
	
	@Autowired
	private OdinWalletBiz odinWalletBiz;

	/**
	 * 释放币
	 */
	@Scheduled(cron="0 2 1 * * ?")
	public void job3(){
		odinWalletBiz.start();
	}
}
