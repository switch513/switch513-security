package com.switch513.service.impl;

import com.switch513.dao.SysRoleMenuDao;
import com.switch513.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author switch
 * @create 2017-02-23-上午11:23
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    @Override
    @Transactional
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        if(menuIdList.size() == 0) {
            return;
        }
        sysRoleMenuDao.delete(roleId);

        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("menuIdList", menuIdList);
        sysRoleMenuDao.save(map);
    }

    @Override
    public List<Long> queryMenuIdList(Long roleId) {
        return sysRoleMenuDao.queryMenuIdList(roleId);
    }
}
