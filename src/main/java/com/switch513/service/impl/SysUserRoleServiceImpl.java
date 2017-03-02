package com.switch513.service.impl;

import com.switch513.dao.SysUserRoleDao;
import com.switch513.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author switch
 * @create 2017-02-23-上午8:42
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public void saveOrUpate(Long userId, List<Long> roleIdList) {
        if(roleIdList.size() == 0) {
            return;
        }

        //先删除用户和角色关系
        sysUserRoleDao.delete(userId);

        //保存用户与角色关系
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("roleIdList", roleIdList);
        sysUserRoleDao.save(map);
    }

    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return sysUserRoleDao.queryRoleIdList(userId);
    }

    @Override
    public void delete(Long userId) {
        sysUserRoleDao.delete(userId);
    }
}
