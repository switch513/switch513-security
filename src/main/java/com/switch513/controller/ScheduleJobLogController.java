package com.switch513.controller;

import com.switch513.entity.ScheduleJobLogEntity;
import com.switch513.service.ScheduleJobLogService;
import com.switch513.utils.PageUtils;
import com.switch513.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务日志
 * @author switch
 * @create 2017-02-28-下午5:17
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:log")
    public R list(Integer page, Integer limit, Integer jobId) {
        Map<String, Object> map = new HashMap<>();
        map.put("jobId", jobId);
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);

        List<ScheduleJobLogEntity> jobList = scheduleJobLogService.queryList(map);
        int total = scheduleJobLogService.queryTotal(map);

        PageUtils pageUtils = new PageUtils(jobList, total, limit, page);

        return R.ok().put("page", pageUtils);
    }

    /**
     * 定时任务日志信息
     * @param logId
     * @return
     */
    @RequestMapping("/info/{logId}")
    public R info(@PathVariable("logId") Long logId) {
        ScheduleJobLogEntity log = scheduleJobLogService.queryObject(logId);
        return R.ok().put("log", log);
    }

}
