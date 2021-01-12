package com.bright.schedule.task.handle;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 任务Handler示例（Bean模式）
 * 开发步骤：
 * 1、继承"IJobHandler"：“com.xxl.job.core.handler.IJobHandler”；
 * 2、注册到Spring容器：添加“@Component”注解，被Spring容器扫描为Bean实例；
 * 3、注册到执行器工厂：添加“@JobHandler(value="自定义job-handler名称")”注解，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 * 5、任务执行结果枚举：SUCCESS、FAIL、FAIL_TIMEOUT
 */
@JobHandler(value = "testJobHandler")
@Component
public class DemoJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(DemoJobHandler.class);

    @Override
    public ReturnT<String> execute(String param) {
        XxlJobLogger.log("XXL-JOB, testJobHandler.");
        logger.info("XXL-JOB test");
        return SUCCESS;
    }
}
