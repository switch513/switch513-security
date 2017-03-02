package com.switch513.service;

import java.util.List;

/**
 * 角色与菜单对应关系
 * Created by switch on 17/2/23.
 */
public interface SysRoleMenuService {

    void saveOrUpdate(Long roleId, List<Long> menuIdList);

    List<Long> queryMenuIdList(Long roleId);

}
