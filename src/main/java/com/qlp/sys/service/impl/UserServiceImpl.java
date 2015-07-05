package com.qlp.sys.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qlp.commons.enums.Type;
import com.qlp.commons.enums.UserStatus;
import com.qlp.sys.dao.ModuleDao;
import com.qlp.sys.dao.RoleDao;
import com.qlp.sys.dao.UserDao;
import com.qlp.sys.entity.Module;
import com.qlp.sys.entity.Role;
import com.qlp.sys.entity.User;
import com.qlp.sys.service.UserService;
import com.qlp.utils.ParameterUtils;
import com.qlp.utils.PasswordHelper;

/**
 * Created by qlp on 14-4-3.
 */
@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
	
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ModuleDao moduleDao;

    /**
     * 判断登录用户是不是root用户(方便开发测试用，上线后请禁用掉root用户)
     *
     * @param loginName
     * @return
     */
    @Override
    public boolean isRootUser(String loginName) {
        if(StringUtils.equalsIgnoreCase(ParameterUtils.ROOT,loginName)){
            return true;
        }
        return false;
    }

    /**
     * 通过用户名查找用户
     *
     * @param loginName
     * @return
     */
    @Override
    public User findByLoginName(String loginName) {
        if(isRootUser(loginName)){
            User user = new User();
            user.setId(ParameterUtils.ROOT);
            user.setLoginName(ParameterUtils.ROOT);
            user.setName(ParameterUtils.ROOT);
            user.setStatus(UserStatus.ENABLE);
            user.setType(Type.SUPER);
            return user;
        }
        return userDao.findOneByLoginNameOrEmail(loginName);
    }

    /**
     * 通过用户名查找角色集合
     *
     * @param loginName
     * @return
     */
    @Override
    public Set<String> findRolesByLoginName(String loginName) {
        Set<String> set = new HashSet<>();
        List<Role> roles;
        if(isRootUser(loginName)){
            roles = roleDao.findAll();
        }else{
            User user = userDao.findOneByLoginNameOrEmail(loginName);
            roles = user.getRoles();
        }
        for (Role r : roles){
            set.add(r.getId());
        }
        return set;
    }

    /**
     * 通过用户名查找权限集合
     *
     * @param loginName
     * @return
     */
    @Override
    public Set<String> findModulesByLoginName(String loginName) {
        Set<String> set = new HashSet<>();
        List<Module> modules = new ArrayList<>();
        if(isRootUser(loginName)){
            modules.addAll(moduleDao.findAll());
        }else{
            User user = userDao.findOneByLoginNameOrEmail(loginName);
            List<Role> roles = user.getRoles();
            for (Role r : roles){
                modules.addAll(r.getModules());
            }
        }
        for(Module m : modules){
            set.add(m.getPermission());
        }
        return set;
    }

    @Transactional(readOnly=false)
    public User save(User user) {
        return userDao.saveAndFlush(user);
    }

    @Transactional(readOnly=false)
	public User createUser(User user, String password) {
		if(StringUtils.isBlank(user.getId())){
			String[] pwdAndSalt = PasswordHelper.encryptPassword(user.getLoginName(), password);
			user.setPassword(pwdAndSalt[0]);
			user.setSalt(pwdAndSalt[1]);
			return this.userDao.saveAndFlush(user);
		}
		logger.error("%s:"+ "用户已存在");
		return null;
	}
}
