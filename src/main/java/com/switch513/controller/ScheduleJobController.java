package com.switch513.controller;

import com.switch513.entity.ScheduleJobEntity;
import com.switch513.service.ScheduleJobService;
import com.switch513.utils.PageUtils;
import com.switch513.utils.R;
import com.switch513.utils.RRException;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author switch
 * @create 2017-02-28-上午10:30
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 定时任务列表
     * @param beanName
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:list")
    public R list(String beanName, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("beanName", beanName);
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);

        List<ScheduleJobEntity> jobList = scheduleJobService.queryList(map);
        int total = scheduleJobService.queryTotal(map);
        PageUtils pageUtils = new PageUtils(jobList, total, limit, page);
        return R.ok().put("page", pageUtils);
    }

    /**
     * 定时任务信息
     * @param jobId
     * @return
     */
    @RequestMapping("/info/{jobId}")
    @RequiresPermissions("sys:schedule:info")
    public R info(@PathVariable("jobId") Long jobId) {
        ScheduleJobEntity scheduleJobEntity = scheduleJobService.queryObject(jobId);
        return R.ok().put("schedule", scheduleJobEntity);
    }

    /**
     * 保存定时任务
     * @param scheduleJobEntity
     * @return
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:schedule:save")
    public R save(@RequestBody ScheduleJobEntity scheduleJobEntity) {
        //数据校验
        verifyForm(scheduleJobEntity);
        scheduleJobService.save(scheduleJobEntity);
        return R.ok();
    }

    /**
     * 修改定时任务
     * @param scheduleJobEntity
     * @return
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:schedule:update")
    public R update(@RequestBody ScheduleJobEntity scheduleJobEntity) {
        verifyForm(scheduleJobEntity);
        scheduleJobService.update(scheduleJobEntity);
        return R.ok();
    }

    /**
     * 删除定时任务
     * @param jobIds
     * @return
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:schedule:delete")
    public R delete(@RequestBody Long[] jobIds) {
        scheduleJobService.deleteBatch(jobIds);
        return R.ok();
    }

    /**
     * 立即执行任务
     * @param jobIds
     * @return
     */
    @RequestMapping("/run")
    @RequiresPermissions("sys:schedule:run")
    public R run(@RequestBody Long[] jobIds) {
        scheduleJobService.run(jobIds);
        return R.ok();
    }

    /**
     * 暂停定时任务
     * @param jobIds
     * @return
     */
    @RequestMapping("/pause")
    @RequiresPermissions("sys:schedule:pause")
    public R pause(@RequestBody Long[] jobIds) {
        scheduleJobService.pause(jobIds);
        return R.ok();
    }

    /**
     * 恢复定时任务
     * @param jobIds
     * @return
     */
    @RequestMapping("/resume")
    @RequiresPermissions("sys:schedule:resume")
    public R resume(@RequestBody Long[] jobIds) {
        scheduleJobService.resume(jobIds);
        return R.ok();
    }

    /**
     * 验证参数是否正确
     * @param scheduleJobEntity
     */
    private void verifyForm(ScheduleJobEntity scheduleJobEntity) {
        if (StringUtils.isBlank(scheduleJobEntity.getBeanName())) {
            throw new RRException("bean名称不能为空");
        }
        if (StringUtils.isBlank(scheduleJobEntity.getMethodName())) {
            throw new RRException("方法名称不能为空");
        }
        if (StringUtils.isBlank(scheduleJobEntity.getCronExpression())) {
            throw new RRException("cron表达式不能为空");
        }
    }

}
