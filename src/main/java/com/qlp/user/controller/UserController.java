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
//        Page<Object[]> pageInfo = userService.findByNameAndType(userName,roleName,type,pageable);
//        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
//        for (int i = 0; i < pageInfo.getContent().size(); i++){
//            Object[] obj = pageInfo.getContent().get(i);
//            Map<String, Object> maps = new HashMap<String, Object>();
//            maps.put("uid", obj[0]);
//            maps.put("uname", obj[1]);
//            maps.put("sex", obj[2]);
//            maps.put("email", obj[3]);
//            maps.put("ustate", obj[4]);
//            maps.put("rname", obj[5]);
//            mapList.add(maps);
//        }
//        model.addAttribute("mapList", mapList);
        String path = "/style/user/userList";
        if (StringUtils.equals(type, ParameterUtils.INNER)) {
            path = "/style/user/innerUserList";
        }
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("type", type);
        model.addAttribute("userName", userName);
        model.addAttribute("roleName", roleName);
        return path;
    }

    @RequestMapping(value = "createInner", method = RequestMethod.GET)
    public String createInner(HttpServletRequest request, Model model) {
        List<Role> roles = roleService.findAllRoles();
        model.addAttribute("roles", roles);
        return "/style/user/createInner";
    }

    @RequestMapping(value = "saveInner", method = RequestMethod.POST)
    public String saveInner(HttpServletRequest request, Model model) {
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
}
