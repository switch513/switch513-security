package com.switch513.dao;

import com.switch513.entity.ScheduleJobEntity;

import java.util.Map;

/**
 * Created by switch on 17/2/28.
 */
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {

    /**
     * 批量更新状态
     * @param map
     * @return
     */
    int updateBatch(Map<String, Object> map);

}
