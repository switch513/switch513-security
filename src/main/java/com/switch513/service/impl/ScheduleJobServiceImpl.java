package com.switch513.service.impl;

import com.switch513.dao.ScheduleJobDao;
import com.switch513.entity.ScheduleJobEntity;
import com.switch513.service.ScheduleJobService;
import com.switch513.utils.Constant;
import com.switch513.utils.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author switch
 * @create 2017-02-28-上午10:39
 */
@Service("scheduleJobService")
public class ScheduleJobServiceImpl implements ScheduleJobService {
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ScheduleJobDao scheduleJobDao;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<ScheduleJobEntity> scheduleJobList = scheduleJobDao.queryList(new HashMap<>());
        for (ScheduleJobEntity scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    @Override
    public ScheduleJobEntity queryObject(Long jopId) {
        return scheduleJobDao.queryObject(jopId);
    }

    @Override
    public List<ScheduleJobEntity> queryList(Map<String, Object> map) {
        return scheduleJobDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return scheduleJobDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(ScheduleJobEntity scheduleJobEntity) {
        scheduleJobEntity.setCreateTime(new Date());
        scheduleJobEntity.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
        scheduleJobDao.save(scheduleJobEntity);
        ScheduleUtils.createScheduleJob(scheduler, scheduleJobEntity);
    }

    @Override
    @Transactional
    public void update(ScheduleJobEntity scheduleJobEntity) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJobEntity);
        scheduleJobDao.update(scheduleJobEntity);
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.deleteScheduleJob(scheduler, jobId);
        }
        //删除数据
        scheduleJobDao.deleteBatch(jobIds);
    }

    @Override
    public int updateBatch(Long[] jobIds, int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", jobIds);
        map.put("status", status);
        return scheduleJobDao.updateBatch(map);
    }

    @Override
    @Transactional
    public void run(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.run(scheduler, queryObject(jobId));
        }
    }

    @Override
    @Transactional
    public void pause(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.pauseJob(scheduler, jobId);
        }

        updateBatch(jobIds, Constant.ScheduleStatus.PAUSE.getValue());
    }

    @Override
    @Transactional
    public void resume(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.resumeJob(scheduler, jobId);
        }
    }
}
