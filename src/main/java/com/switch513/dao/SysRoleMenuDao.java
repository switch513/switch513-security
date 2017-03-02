package com.switch513.dao;

import com.switch513.entity.SysRoleMenuEntity;

import java.util.List;

/**
 * 角色与菜单对应关系
 * Created by switch on 17/2/23.
 */
public interface SysRoleMenuDao extends BaseDao<SysRoleMenuEntity> {

    /**
     * 根据角色ID，获取菜单ID列表
     * @param roleId
     * @return
     */
    List<Long> queryMenuIdList(Long roleId);

}
