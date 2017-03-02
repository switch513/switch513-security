package com.switch513.service;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 * Created by switch on 17/3/1.
 */
public interface SysGeneratorService {

    List<Map<String, Object>> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);

    /**
     * 生产代码
     * @param tableNames
     * @return
     */
    byte[] generatorCode(String[] tableNames);

}
