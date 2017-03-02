package com.switch513.service;

import java.util.List;

/**
 * 用户与角色对应关系
 * Created by switch on 17/2/23.
 */
public interface SysUserRoleService {

    void saveOrUpate(Long userId, List<Long> roleIdList);

    List<Long> queryRoleIdList(Long userId);

    void delete(Long userId);

}
