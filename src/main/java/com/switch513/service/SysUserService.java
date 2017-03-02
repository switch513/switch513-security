package com.switch513.service;

import com.switch513.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * Created by switch on 17/2/22.
 */
public interface SysUserService {

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
     * 根据用户名,查询用户
     * @param username
     * @return
     */
    SysUserEntity queryByUserName(String username);

    /**
     * 根据用户ID,查询用户
     * @param userId
     * @return
     */
    SysUserEntity queryObject(Long userId);

    /**
     * 查询用户列表
     * @param map
     * @return
     */
    List<SysUserEntity> queryList(Map<String, Object> map);

    /**
     * 查询总数
     * @param map
     * @return
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存用户
     * @param user
     */
    void save(SysUserEntity user);

    /**
     * 修改用户
     * @param user
     */
    void update(SysUserEntity user);

    /**
     * 删除用户
     * @param userIds
     */
    void deleteBatch(Long[] userIds);

    /**
     * 修改密码
     * @param userId
     * @param password
     * @param newPassword
     * @return
     */
    int updatePassword(Long userId, String password, String newPassword);

}
