package com.framework.task;

import com.framework.entity.TyStatisticsEntity;
import com.framework.service.TyStatisticsService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务(演示Demo，可删除) testTask为spring bean的名称
 * @author R & D
 * @email 
 * @date 2016年11月30日 下午1:34:24
 */
@Component("testTask")
public class TestTask {

	@Autowired
	private TyStatisticsService tyStatisticsService;

	public void statisticsNow() {
		TyStatisticsEntity tyStatisticsEntity = tyStatisticsService.queryByNow();
		tyStatisticsEntity.setCreatetime(new Date());
		tyStatisticsService.save(tyStatisticsEntity);
	}
	
}





//时间配置
//每天23:30触发：0 30 23 * * ? * 
//每天凌晨2点 0 0 2 * * ?和每天隔一小时 0 * */1 * * ?
//例1：每隔5秒执行一次：*/5 * * * * ?
//例2：每隔5分执行一次：0 */5 * * * ?
//在26分、29分、33分执行一次：0 26,29,33 * * * ?
//例3：每天半夜12点30分执行一次：0 30 0 * * ? （注意日期域为0不是24）
//每天凌晨1点执行一次：0 0 1 * * ?
//每天上午10：15执行一次： 0 15 10 ? * * 或 0 15 10 * * ? 或 0 15 10 * * ? *
//每天中午十二点执行一次：0 0 12 * * ?
//每天14点到14：59分，每1分钟执行一次：0 * 14 * * ?
//每天14点到14：05分，每1分钟执行一次：0 0-5 14 * * ?
//每天14点到14：55分，每5分钟执行一次：0 0/5 14 * * ?
//每天14点到14：55分，和18点到18点55分，每5分钟执行一次：0 0/5 14,18 * * ?
//每天18点执行一次：0 0 18 * * ?
//每天18点、22点执行一次：0 0 18,22 * * ?
//每天7点到23点，每整点执行一次：0 0 7-23 * * ?
//每个整点执行一次：0 0 0/1 * * ?





