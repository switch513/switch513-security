package com.switch513.service.impl;

import com.switch513.dao.SysMenuDao;
import com.switch513.entity.SysMenuEntity;
import com.switch513.service.SysMenuService;
import com.switch513.service.SysRoleMenuService;
import com.switch513.service.SysUserService;
import com.switch513.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author switch
 * @create 2017-02-23-上午11:08
 */
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenuEntity> menuList = sysMenuDao.queryListParentId(parentId);
        if(menuIdList == null) {
            return menuList;
        }

        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for(SysMenuEntity menu : menuList) {
            if(menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenuEntity> queryNotButtonList() {
        return sysMenuDao.queryNotButtonList();
    }

    @Override
    public List<SysMenuEntity> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if(userId == 1) {
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public SysMenuEntity queryObject(Long menuId) {
        return sysMenuDao.queryObject(menuId);
    }

    @Override
    public List<SysMenuEntity> queryList(Map<String, Object> map) {
        return sysMenuDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysMenuDao.queryTotal(map);
    }

    @Override
    public void save(SysMenuEntity menu) {
        sysMenuDao.save(menu);
    }

    @Override
    public void update(SysMenuEntity menu) {
        sysMenuDao.update(menu);
    }

    @Override
    public void deleteBatch(Long[] menuIds) {
        sysMenuDao.deleteBatch(menuIds);
    }

    /**
     * 获取所有菜单列表
     * @param menuIdList
     * @return
     */
    private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList) {
        List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
        getMenuTreeList(menuList, menuIdList);
        return menuList;
    }

    /**
     * 递归算法
     * @param menuList
     * @param menuIdList
     * @return
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList) {
        List<SysMenuEntity> subMenuList = new ArrayList<>();

        for (SysMenuEntity entity : menuList) {
            if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }
        return subMenuList;
    }
}
