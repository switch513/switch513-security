package com.switch513.dao;

import com.switch513.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by switch on 17/2/22.
 */
public interface SysUserDao extends BaseDao<SysUserEntity> {

    /**
     * 查询用户的所有权限
     * @param userId
     * @return
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     * @param userId
     * @return
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 根据用户名,查询系统用户
     * @param username
     * @return
     */
    SysUserEntity queryByUserName(String username);

    /**
     * 修改密码
     * @param map
     * @return
     */
    int updatePassword(Map<String, Object> map);

}
