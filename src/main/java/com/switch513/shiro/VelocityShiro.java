package com.switch513.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author switch
 * @create 2017-02-24-上午9:52
 */
public class VelocityShiro {

    /**
     * 是否拥有该权限
     * @param permission
     * @return
     */
    public boolean hasPermission(String permission) {
        Subject subject = SecurityUtils.getSubject();
        return subject != null && subject.isPermitted(permission);
    }

}
