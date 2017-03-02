package com.switch513.controller;

import com.switch513.entity.SysRoleEntity;
import com.switch513.service.SysRoleMenuService;
import com.switch513.service.SysRoleService;
import com.switch513.utils.PageUtils;
import com.switch513.utils.R;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色菜单
 * @author switch
 * @create 2017-02-27-下午2:40
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 角色列表
     * @param roleName
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public R list(String roleName, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("roleName", roleName);
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);

        List<SysRoleEntity> list = sysRoleService.queryList(map);
        int total = sysRoleService.queryTotal(map);

        PageUtils pageUtils = new PageUtils(list, total, limit, page);
        return R.ok().put("page", pageUtils);
    }

    /**
     * 角色列表
     * @return
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public R select() {
        List<SysRoleEntity> list = sysRoleService.queryList(new HashMap<String, Object>());
        return R.ok().put("list", list);
    }

    /**
     * 角色信息
     * @param roleId
     * @return
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") Long roleId) {
        SysRoleEntity role = sysRoleService.queryObject(roleId);

        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        return R.ok().put("role", role);
    }

    /**
     * 保存角色
     * @param role
     * @return
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public R save(@RequestBody SysRoleEntity role) {
        if(StringUtils.isBlank(role.getRoleName())) {
            return R.error("角色名称不能为空");
        }
        sysRoleService.save(role);
        return R.ok();
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public R update(@RequestBody SysRoleEntity role) {
        if(StringUtils.isBlank(role.getRoleName())) {
            return R.error("角色名称不能为空");
        }
        sysRoleService.update(role);
        return R.ok();
    }

    /**
     * 删除角色
     * @param roleIds
     * @return
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public R delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);
        return R.ok();
    }

}
