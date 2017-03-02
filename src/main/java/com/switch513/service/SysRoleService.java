package com.switch513.service;

import com.switch513.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色
 * Created by switch on 17/2/27.
 */
public interface SysRoleService {

    SysRoleEntity queryObject(Long roleId);

    List<SysRoleEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(SysRoleEntity role);

    void update(SysRoleEntity role);

    void deleteBatch(Long[] roleIds);
}
