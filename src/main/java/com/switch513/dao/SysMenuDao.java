package com.switch513.dao;

import com.switch513.entity.SysMenuEntity;

import java.util.List;

/**
 * 菜单管理
 * Created by switch on 17/2/23.
 */
public interface SysMenuDao extends BaseDao<SysMenuEntity> {

    /**
     * 根据父菜单，查询子菜单
     * @param parentId
     * @return
     */
    List<SysMenuEntity> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     * @return
     */
    List<SysMenuEntity> queryNotButtonList();

}
