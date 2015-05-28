package com.qlp.sys.service.impl;

import com.qlp.commons.enums.UserStatus;
import com.qlp.sys.entity.User;
import com.qlp.sys.service.UserService;
import com.qlp.utils.ParameterUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2015/4/30.
 */
public class CustomRealm extends AuthorizingRealm {


    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    /**
     * 获取授权信息
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String loginName = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo sa = new SimpleAuthorizationInfo();
        sa.setRoles(userService.findRolesByLoginName(loginName));
        sa.setStringPermissions(userService.findModulesByLoginName(loginName));
        return sa;
    }

    @Override
    /**
     * 获取身份验证信息
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String loginName = (String) token.getPrincipal();
        if(userService.isRootUser(loginName)){
            return new SimpleAuthenticationInfo(ParameterUtils.ROOT,ParameterUtils.ROOT_ACCOUNT[0],ByteSource.Util.bytes(ParameterUtils.ROOT+ParameterUtils.ROOT_ACCOUNT[1]),getName());
        }
        User user = userService.findByLoginName(loginName);
        if(user == null){
            throw new UnknownAccountException();
        }
        if(UserStatus.LOCKED == user.getStatus()){
            throw new LockedAccountException();
        }
        return new SimpleAuthenticationInfo(loginName,user.getPassword(), ByteSource.Util.bytes(loginName + user.getSalt()),getName());
    }
}
