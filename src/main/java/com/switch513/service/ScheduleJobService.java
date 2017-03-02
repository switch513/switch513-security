package com.switch513.service;

import com.switch513.entity.ScheduleJobEntity;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 * Created by switch on 17/2/28.
 */
public interface ScheduleJobService {

    /**
     * 根据ID，查询定时任务
     * @param jopId
     * @return
     */
    ScheduleJobEntity queryObject(Long jopId);

    /**
     * 查询定时任务列表
     * @param map
     * @return
     */
    List<ScheduleJobEntity> queryList(Map<String, Object> map);

    /**
     * 查询总数
     * @param map
     * @return
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存定时任务
     * @param scheduleJobEntity
     */
    void save(ScheduleJobEntity scheduleJobEntity);

    /**
     * 更新定时任务
     * @param scheduleJobEntity
     */
    void update(ScheduleJobEntity scheduleJobEntity);

    /**
     * 批量删除定时任务
     * @param jobIds
     */
    void deleteBatch(Long[] jobIds);

    /**
     * 批量更新定时任务状态
     * @param jobIds
     * @param status
     * @return
     */
    int updateBatch(Long[] jobIds, int status);

    /**
     * 立即执行
     * @param jobIds
     */
    void run(Long[] jobIds);

    /**
     * 暂停运行
     * @param jobIds
     */
    void pause(Long[] jobIds);

    /**
     * 恢复运行
     * @param jobIds
     */
    void resume(Long[] jobIds);

}
