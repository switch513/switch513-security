package com.switch513.controller;

import com.alibaba.fastjson.JSON;
import com.switch513.service.SysGeneratorService;
import com.switch513.utils.PageUtils;
import com.switch513.utils.R;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 * @author switch
 * @create 2017-03-01-下午5:04
 */
@Controller
@RequestMapping("/sys/generator")
public class SysGeneratorController {
    @Autowired
    private SysGeneratorService sysGeneratorService;

    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions("sys:generator:list")
    public R list(String tableName, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableName);
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);

        List<Map<String, Object>> list = sysGeneratorService.queryList(map);
        int total = sysGeneratorService.queryTotal(map);

        PageUtils pageUtils = new PageUtils(list, total, limit, page);
        return R.ok().put("page", pageUtils);
    }

    /**
     * 生成代码
     * @param tables
     * @param response
     * @throws IOException
     */
    @RequestMapping("/code")
    @RequiresPermissions("sys:generator:code")
    public void code(String tables, HttpServletResponse response) throws IOException {
        String[] tableNames = new String[]{};
        tableNames = JSON.parseArray(tables).toArray(tableNames);

        byte[] data = sysGeneratorService.generatorCode(tableNames);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"switch.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }

}
