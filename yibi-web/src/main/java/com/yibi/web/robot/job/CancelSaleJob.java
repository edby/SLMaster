package com.yibi.web.robot.job;


import com.yibi.core.entity.OrderSpot;
import com.yibi.core.entity.RobotTask;
import com.yibi.core.service.*;
import lombok.extern.log4j.Log4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Log4j
public class CancelSaleJob extends BaseJob implements Job {
    @Autowired
    private RobotTaskService robotTaskService;
    @Autowired
    private UserService userService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderSpotService orderSpotService;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            JobDataMap data = context.getJobDetail().getJobDataMap();

            int taskId = data.getInt("taskId");
            RobotTask task =  robotTaskService.selectByPrimaryKey(taskId);
            if(task == null){
                log.info("【自动撤销卖单】-任务不存在："+taskId);
                return ;
            }
            //撤销操作
            Integer times = task.getCountmax();
            if (times == null){
                log.info("【自动撤销卖单】-撤销截止时间没设置:"+task.getId());
                return;
            }
            //查询所有订单状态是未成交的买单
            Map<Object,Object> map = new HashMap<>();
            map.put("ordercointype",task.getCointype());
            map .put("type",0); //买入
            map.put("state",0);//为成交的
            map.put("times",times);//截止时间
            List<OrderSpot> list = orderSpotService.selectAllCondition(map);
            if (list == null || list.size()== 0){
                log.info("【自动撤销卖单】-撤销截止时间范围内，没有可以撤销的订单");
                return;
            }
            for (OrderSpot orderSpot:list){
                //更新订单状态
                orderSpot.setState(2);//已撤销
                int result = orderSpotService.updateConcurrencyOrder(orderSpot);
                if (result != 1){
                    continue;
                }
                //更新账号余额和流水  返回计价币种
                accountService.updateAccountAndInsertFlow(orderSpot.getUserid(),1,orderSpot.getOrdercointype(),
                        orderSpot.getRemain(),BigDecimal.ZERO,task.getExcuteuserid(),"卖出撤销",orderSpot.getId());
            }
            log.info("自动撤销卖单完成！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
