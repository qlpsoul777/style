package com.qlp.user.controller;

import com.qlp.user.entity.Application;
import com.qlp.user.entity.Functions;
import com.qlp.user.entity.Role;
import com.qlp.user.entity.User;
import com.qlp.user.service.ApplicationService;
import com.qlp.user.service.FunctionService;
import com.qlp.user.service.RoleService;
import com.qlp.user.service.UserService;
import com.qlp.utils.ParameterUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qlp on 14-4-15.
 * User Manager(用户管理)
 */
@Controller
@RequestMapping(value = "user/index")
public class UserController {
    private UserService userService;
    private RoleService roleService;
    private ApplicationService applicationService;
    private FunctionService functionService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Autowired
    public void setFunctionService(FunctionService functionService) {
        this.functionService = functionService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value = "first", method = RequestMethod.GET)
    public String first() {
        return "/style/user/index";
    }

    /**
     * 登录
     *
     * @param request client request
     * @param model   service response
     * @return url
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, Model model) {
        String loginName = request.getParameter("loginName");
        String inputPassword = request.getParameter("password");
        User user = userService.findByLoginNameAndPassword(loginName, inputPassword);
        String path = "/style/user/index";
        String errorMessage = "";  //登录失败提示信息
        if (user != null) {
            if (StringUtils.equals(user.getState(), ParameterUtils.ENABLE)) {
                List<Application> apps = applicationService.findApplicationByUser(user.getId());
                List<Functions> funcs = functionService.findFunctionsByUser(user.getId());
                model.addAttribute("funcs", funcs);
                model.addAttribute("apps", apps);
                model.addAttribute("user", user);
                path = "/style/user/hello";
            } else {
                errorMessage = "账户已禁用，如有疑问请联系管理员";
            }
        } else {
            errorMessage = "用户名或密码错误，请重试";
        }
        model.addAttribute("msg", errorMessage);
        return path;
    }

    /**
     * 查询用户列表
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "list/{type}", method = RequestMethod.GET)
    public String list(HttpServletRequest request, @PageableDefaults(10) Pageable pageable,
                       @PathVariable("type") String type, Model model) {
        String userName = request.getParameter("userName");  //用户名
        String roleName = request.getParameter("roleName");  //角色名
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(userName)) {
            map.put("userName", userName);
        }
        if (StringUtils.isNotBlank(roleName)) {
            map.put("roleName", roleName);
        }
        map.put("type", type);
        Page<User> pageInfo = userService.findPageByCriteria(map, pageable);
        String path = "/style/user/outerUserList";
        if (StringUtils.equals(type, ParameterUtils.INNER)) {
            path = "/style/user/innerUserList";
        }
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("type", type);
        model.addAttribute("userName", userName);
        model.addAttribute("roleName", roleName);
        return path;
    }

    /**
     * 新增内部用户
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "createInner", method = RequestMethod.GET)
    public String createInner(Model model, HttpServletRequest request) {
        String type = request.getParameter("type");
        List<Role> roles = roleService.findAllRoles(type);
        model.addAttribute("roles", roles);
        return "/style/user/createInner";
    }

    /**
     * 保存用户信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "saveInner", method = RequestMethod.POST)
    public String saveInner(HttpServletRequest request) {
        String loginName = request.getParameter("loginName");
        String roleIds = request.getParameter("roleIds");
        List<Role> roles = new ArrayList<Role>();
        if (StringUtils.isNotBlank(roleIds)) {
            String[] ids = StringUtils.split(roleIds, ',');
            for (String roleId : ids) {
                Role r = roleService.get(roleId);
                roles.add(r);
            }
        }
        String state = request.getParameter("state");
        String type = ParameterUtils.INNER;
        String password = ParameterUtils.INITPASSWORD;
        User user = new User(loginName, password, state, type, roles);
        userService.save(user);
        return "redirect:/user/index/list/" + type;
    }

    /**
     * 批量启用/禁用用户
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "batchUse", method = RequestMethod.GET)
    public String batchUse(HttpServletRequest request) {
        String type = request.getParameter("type");
        String ids = request.getParameter("ids");
        userService.batchUse(ids);
        return "redirect:/user/index/list/" + type;
    }

    /**
     * 批量删除
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "batchDelete", method = RequestMethod.GET)
    public String batchDelete(HttpServletRequest request) {
        String type = ParameterUtils.INNER;
        String ids = request.getParameter("ids");
        userService.batchDelete(ids);
        return "redirect:/user/index/list/" + type;
    }

    /**
     * 修改页面
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String update(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        User user = userService.get(id);
        String type = request.getParameter("type");
        List<Role> roles = roleService.findAllRoles(type);
        List<Role> selectedRoles = user.getRoles();
        if (!selectedRoles.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Role r : selectedRoles) {
                sb.append(r.getId()).append(',');
            }
            model.addAttribute("selectedIds", sb.toString());
        }
        model.addAttribute("roles", roles);//所有角色
        model.addAttribute("user", user);
        model.addAttribute("type", type);
        return "/style/user/editUser";
    }

    /**
     * 修改保存
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "updateSave", method = RequestMethod.POST)
    public String updateSave(HttpServletRequest request) {
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        String newIds = request.getParameter("roleIds"); //修改后的角色id
        List<Role> newRoles = new ArrayList<Role>();
        if (StringUtils.isNotBlank(newIds)) {
            String[] ids = StringUtils.split(newIds, ',');
            for (String rId : ids) {
                Role r = roleService.get(rId);
                newRoles.add(r);
            }
        }
        String state = request.getParameter("state");
        User user = userService.get(id);
        List<Role> oldRoles = user.getRoles();  //修改前的角色
        // newRoles.removeAll(oldRoles);  //需add的role
        oldRoles.retainAll(newRoles);  //需delete的role
        for (Role a : oldRoles) {
            System.out.println(a.getName() + ",");
        }
//        user.setState(state);
//        userService.save(user);
        return "redirect:/user/index/list/" + type;
    }
}
