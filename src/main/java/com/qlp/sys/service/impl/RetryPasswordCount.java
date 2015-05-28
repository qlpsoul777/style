package com.qlp.sys.service.impl;

import com.qlp.commons.enums.UserStatus;
import com.qlp.sys.entity.User;
import com.qlp.sys.service.UserService;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2015/5/1.
 */
public class RetryPasswordCount extends HashedCredentialsMatcher {

    private int limit = 3;  //密码重试次数限制，默认为3次，可通过配置文件修改次数限制数

    private Ehcache ehcache;

    @Autowired
    private UserService userService;

    @Resource(name="shiroEhcacheManager")
    private EhCacheManager shiroEhcacheManager;

    public void init(){
        this.ehcache = shiroEhcacheManager.getCacheManager().getEhcache("passwordRetryCache");
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String loginName = (String) token.getPrincipal();
        Element element = ehcache.get(loginName);
        if(element == null){
            element = new Element(loginName,new AtomicInteger(0));
            ehcache.put(element);
        }
        AtomicInteger retryCount = (AtomicInteger) element.getObjectValue();
        if(retryCount.incrementAndGet() > limit){
            User user = userService.findByLoginName(loginName);
            if(user != null){
                user.setStatus(UserStatus.LOCKED);
                userService.save(user);
                ehcache.remove(loginName);
                throw new ExcessiveAttemptsException();
            }
        }
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches){
            ehcache.remove(loginName);
        }
        return matches;
    }


}
