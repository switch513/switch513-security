package com.switch513.dao;

import com.switch513.entity.SysUserRoleEntity;

import java.util.List;

/**
 * 用户与角色对应关系
 * Created by switch on 17/2/23.
 */
public interface SysUserRoleDao extends BaseDao<SysUserRoleEntity> {

    /**
     * 根据用户ID,获取角色ID列表
     * @param userId
     * @return
     */
    List<Long> queryRoleIdList(Long userId);

}
