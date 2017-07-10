package com.youi.business.common.scheduler;

import com.youi.core.codetable.ModifyCodeTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Transactional
@Service
public class ReloadCodeTableJob {
    private static Logger logger = LoggerFactory
            .getLogger(ReloadCodeTableJob.class);
    private boolean enabled = true;



    // every 1 day
    @Scheduled(cron = "0 0 1 * * ?")
    public void reload() {
        if (!enabled) {
            return;
        }
        try {
            logger.info("码表加载start");
            ModifyCodeTable m = new ModifyCodeTable();
            m.loadCodeTable();
            logger.info("码表加载end");
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

}
