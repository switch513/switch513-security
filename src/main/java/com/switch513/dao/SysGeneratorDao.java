package com.switch513.dao;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 * Created by switch on 17/3/1.
 */
public interface SysGeneratorDao {

    List<Map<String, Object>> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);

}
