package com.switch513.shiro;

import com.switch513.entity.SysMenuEntity;
import com.switch513.entity.SysUserEntity;
import com.switch513.service.SysMenuService;
import com.switch513.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 认证
 * @author switch
 * @create 2017-02-22-下午4:54
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUserEntity user = (SysUserEntity) principalCollection.getPrimaryPrincipal();
        Long userId = user.getUserId();

        List<String> permsList = null;

        //系统管理员
        if (userId == 1) {
            List<SysMenuEntity> menuList = sysMenuService.queryList(new HashMap<String, Object>());
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserService.queryAllPerms(userId);
        }

        Set<String> permsSet = new HashSet<String>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());

        SysUserEntity user = sysUserService.queryByUserName(username);

        if(user == null) {
            throw new UnknownAccountException("帐号或密码不正确");
        }

        if(!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("帐号或密码不正确");
        }

        if(user.getStatus() == 0) {
            throw new LockedAccountException("帐号已被锁定，请联系管理员");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }
}
