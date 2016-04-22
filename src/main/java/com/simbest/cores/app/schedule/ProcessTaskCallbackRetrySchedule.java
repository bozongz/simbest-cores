/**
 * 版权所有 © 北京晟壁科技有限公司 2008-2016。保留一切权利!
 */
package com.simbest.cores.app.schedule;

import com.simbest.cores.app.model.ProcessTask;
import com.simbest.cores.app.model.ProcessTaskCallbackRetry;
import com.simbest.cores.app.service.IProcessService;
import com.simbest.cores.app.service.IProcessTaskService;
import com.simbest.cores.exceptions.Exceptions;
import com.simbest.cores.service.IGenericService;
import com.simbest.cores.utils.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 用途：每十分钟检查一次待办生成与待办核销的回调接口执行情况，执行回调失败的任务，一个尝试执行12次
 * 作者: lishuyi 
 * 时间: 2016-04-18  18:08 
 */
@Component
public class ProcessTaskCallbackRetrySchedule extends ApplicationObjectSupport {
    private static transient final Log log = LogFactory.getLog(ProcessTaskCallbackRetrySchedule.class);

    @Autowired
    @Qualifier("processTaskCallbackRetryService")
    private IGenericService<ProcessTaskCallbackRetry, Integer> processTaskCallbackRetryService;

    @Autowired
    private IProcessTaskService processTaskService;

    @Scheduled(cron = "0 0/10 * * * ?")
    private void checkFailedAndRun() throws ClassNotFoundException {
        log.debug("ProcessTaskCallbackRetrySchedule Start .....................................");
        Collection<ProcessTaskCallbackRetry> list = processTaskCallbackRetryService.getAll();
        for(ProcessTaskCallbackRetry o:list){
            boolean tryResult = true;
            try {
                IProcessService processService = (IProcessService)getApplicationContext().getBean(Class.forName(o.getProcessServiceClass()));
                if (o.getCallbackType().equals("CreateCallback")) {
                    if(null != o.getTaskId()){
                        ProcessTask task = processTaskService.getById(o.getTaskId());
                        if(null != task)
                            processService.createProcessTaskCreateCallback().execute(task);
                    }
                } else {
                    ProcessTask deleteTasks = new ProcessTask();
                    deleteTasks.setTypeId(o.getTypeId());
                    deleteTasks.setHeaderId(o.getHeaderId());
                    deleteTasks.setReceiptId(o.getReceiptId());
                    deleteTasks.setStepId(o.getStepId());
                    deleteTasks.setCurrentUserId(o.getCurrentUserId());
                    processService.createProcessTaskRemoveCallback().execute(deleteTasks);
                }
            }catch (Exception e){
                Exceptions.printException(e);
                tryResult = false;
            }finally {
                int ret;
                if(tryResult){
                    ret = processTaskCallbackRetryService.delete(o.getId());
                }else{
                    o.setLastExecuteDate(DateUtil.getCurrent());
                    ret = processTaskCallbackRetryService.update(o);
                }
                log.debug(ret);
            }
        }
        log.debug("ProcessTaskCallbackRetrySchedule End .....................................");
    }
}
