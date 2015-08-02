package com.qlp.sys.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qlp.commons.entity.MsgInfo;
import com.qlp.commons.enums.Type;
import com.qlp.commons.enums.UserStatus;
import com.qlp.sys.dao.ModuleDao;
import com.qlp.sys.dao.RoleDao;
import com.qlp.sys.dao.UserDao;
import com.qlp.sys.entity.Module;
import com.qlp.sys.entity.Role;
import com.qlp.sys.entity.User;
import com.qlp.sys.service.RoleService;
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
    @Autowired
    private RoleService roleService;

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
        return userDao.findByLoginName(loginName);
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
            User user = userDao.findByLoginName(loginName);
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
            User user = userDao.findByLoginName(loginName);
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
			String[] pwdAndSalt = PasswordHelper.encryptPassword(user, password,true);
			user.setPassword(pwdAndSalt[0]);
			user.setSalt(pwdAndSalt[1]);
			return this.userDao.saveAndFlush(user);
		}
		logger.error("%s:"+ "用户已存在");
		return null;
	}

    public Page<User> findPageByCriteria(Map<String, Object> map, Pageable pageable) {
        Map<String, Object> searchMap = new HashMap<String, Object>();
        String userName = (String) map.get("userName");
        String roleName = (String) map.get("roleName");
        Type type = (Type) map.get("type");
        if (StringUtils.isNotBlank(userName)) {
            searchMap.put("name_li", userName);
        }
        searchMap.put("type", type);
        Criteria criteria = userDao.mapToCriteria(searchMap);
        if (StringUtils.isNotBlank(roleName)) {
            criteria.createAlias("roles", "r")
                    .add(Restrictions.eq("r.name", roleName))
                    .add(Restrictions.eq("r.enable", Boolean.TRUE));
        }
        return userDao.queryPageByCriteria(criteria, pageable);
    }

	public boolean repeatLoginName(String loginName) {
		User user = findByLoginName(loginName);
		if(user != null){
			return false;
		}
		return true;
	}

	public void setRoles(User user, String roleIds) {
		List<Role> roles = roleService.findByIds(roleIds);
		user.setRoles(roles);
	}

	@Transactional(readOnly=false)
	public void batchDelete(String userIds) {
		List<User> users = findByIds(userIds);
		if((users !=null) && (!users.isEmpty())){
			userDao.delete(users);
		}
	}

	public List<User> findByIds(String userIds) {
		List<User> users = null;
		if(StringUtils.isNotBlank(userIds)){
			String[] ids = StringUtils.split(userIds, ",");
			users = (List<User>) userDao.findAll(Arrays.asList(ids));
		}else{
			logger.debug("%s:"+ "未选择任何用户");
		}
		return users;
	}

	@Transactional(readOnly=false)
	public void batchEdit(String userIds) {
		List<User> users = findByIds(userIds);
		if((users !=null) && (!users.isEmpty())){
			for (User user : users) {
				switch(user.getStatus()){
				case ENABLE:
					user.setStatus(UserStatus.NOTENABLE);
					break;
				case NOTENABLE:
					user.setStatus(UserStatus.ENABLE);
					break;
				default:
					break;
				}
				userDao.saveAndFlush(user);
			}
		}
	}

	@Transactional(readOnly=false)
	public void batchResetPwd(String userIds) {
		List<User> users = findByIds(userIds);
		if((users !=null) && (!users.isEmpty())){
			for (User user : users) {
				String[] pwdAndSalt = PasswordHelper.encryptPassword(user, ParameterUtils.INITPASSWORD,true);
				user.setPassword(pwdAndSalt[0]);
				user.setSalt(pwdAndSalt[1]);
				userDao.saveAndFlush(user);
			}
		}
	}

	public User findById(String userIds) {
		return userDao.findOne(userIds);
	}

	public MsgInfo updatePwd(User user, String oldPwd, String newPwd,
			String repeatNewPwd) {
		String oldPwdEncrypted = PasswordHelper.encryptPassword(user,oldPwd,false)[0];
		if(!StringUtils.equals(user.getPassword(), oldPwdEncrypted)){
			return new MsgInfo(-1,"原始密码输入错误");
		}
		if(!StringUtils.equals(newPwd, repeatNewPwd)){
			return  new MsgInfo(-2,"新密码两次输入不一致");
		}
		String[] pwdAndSalt = PasswordHelper.encryptPassword(user,newPwd,true);
		user.setPassword(pwdAndSalt[0]);
		user.setSalt(pwdAndSalt[1]);
		userDao.saveAndFlush(user);
		return new MsgInfo(1,"密码修改成功,请重新登录");
	}
}
