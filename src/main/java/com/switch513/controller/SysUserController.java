package com.switch513.controller;

import com.switch513.entity.SysUserEntity;
import com.switch513.service.SysUserRoleService;
import com.switch513.service.SysUserService;
import com.switch513.utils.PageUtils;
import com.switch513.utils.R;
import com.switch513.utils.ShiroUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * @author switch
 * @create 2017-02-27-上午10:00
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;


    /**
     * 所有用户列表
     * @param username
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R list(String username, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);

        //查询列表数据
        List<SysUserEntity> userList = sysUserService.queryList(map);
        int total = sysUserService.queryTotal(map);

        PageUtils pageUtils = new PageUtils(userList, total, limit, page);
        return R.ok().put("page", pageUtils);
    }

    /**
     * 获取登录的用户信息
     * @return
     */
    @RequestMapping("/info")
    public R info() {
        return R.ok().put("user", getUser());
    }

    /**
     * 修改用户登录密码
     * @param password
     * @param newPassword
     * @return
     */
    @RequestMapping("/password")
    public R password(String password, String newPassword) {
        if(StringUtils.isBlank(newPassword)) {
            return R.error("新密码不能为空");
        }

        password = new Sha256Hash(password).toHex();
        newPassword = new Sha256Hash(newPassword).toHex();

        int count = sysUserService.updatePassword(getUserId(), password, newPassword);
        if(count == 0) {
            return R.error("原密码不正确");
        }
        ShiroUtils.logout();
        return R.ok();
    }


    /**
     * 用户信息
     * @param userId
     * @return
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.queryObject(userId);
        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);;
        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody SysUserEntity user) {
        if(StringUtils.isBlank(user.getUsername())) {
            return R.error("用户名不能为空");
        }
        if(StringUtils.isBlank(user.getPassword())) {
            return R.error("密码不能为空");
        }

        sysUserService.save(user);
        return R.ok();
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody SysUserEntity user) {
        if(StringUtils.isBlank(user.getUsername())) {
            return R.error("用户名不能为空");
        }
        sysUserService.update(user);
        return R.ok();
    }

    /**
     * 删除用户
     * @param userIds
     * @return
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody Long[] userIds) {
        if(ArrayUtils.contains(userIds, 1L)) {
            return R.error("系统管理员不能删除");
        }
        if(ArrayUtils.contains(userIds, getUserId())) {
            return R.error("当前用户不能删除");
        }
        sysUserService.deleteBatch(userIds);
        return R.ok();
    }

}
