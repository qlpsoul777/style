package com.qlp.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlp.user.dto.Application_;
import com.qlp.user.dto.Functions_;
import com.qlp.user.entity.Application;
import com.qlp.user.entity.Functions;
import com.qlp.user.entity.Role;
import com.qlp.user.service.ApplicationService;
import com.qlp.user.service.FunctionService;
import com.qlp.user.service.RoleService;
import com.qlp.utils.ParameterUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qlp on 2014/6/3.
 * Role Manager(角色管理)
 */
@Controller
@RequestMapping(value = "role/")
public class RoleController {
    private RoleService roleService;
    private ApplicationService applicationService;
    private FunctionService functionService;

    @Autowired
    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setFunctionService(FunctionService functionService) {
        this.functionService = functionService;
    }

    /**
     * 角色列表
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value = "roleList", method = RequestMethod.GET)
    public String roleList(HttpServletRequest request, @PageableDefaults(10) Pageable pageable,
                           Model model) {
        Map<String, Object> map = new HashMap<String, Object>();
        String roleName = request.getParameter("roleName");
        if (StringUtils.isNotBlank(roleName)) {
            map.put("name_li", roleName);
        }
        String roleType = request.getParameter("roleType");
        if (StringUtils.isNotBlank(roleType)) {
            map.put("type", roleType);
        }
        Page<Role> pageInfo = roleService.findPageByMap(map, pageable);
        model.addAttribute("roleName", roleName);
        model.addAttribute("roleType", roleType);
        model.addAttribute("pageInfo", pageInfo);
        return "/style/role/roleList";
    }

    /**
     * 批量启用/禁用角色
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "batchUse", method = RequestMethod.GET)
    public String batchUse(HttpServletRequest request) {
        String ids = request.getParameter("ids");
        roleService.batchUse(ids);
        return "redirect:/role/roleList";
    }

    /**
     * 新增角色
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        List<Application_> apps_ = new ArrayList<Application_>();
        List<Application> apps = applicationService.findAllByVisiable();
        for (Application app : apps) {
            Application_ a_ = new Application_();
            a_.setId(app.getId());
            a_.setPid("0");
            a_.setName(app.getName());
            a_.setOpen(Boolean.TRUE);
            List<Functions> funcs = app.getFuncs();
            if (!funcs.isEmpty()) {
                for (Functions f : funcs) {
                    Application_ f_ = new Application_();
                    f_.setId(f.getId());
                    f_.setPid(app.getId());
                    f_.setName(f.getName());
                    apps_.add(f_);
                }
                apps_.add(a_);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObj = null;
        try {
            jsonObj = objectMapper.writeValueAsString(apps_);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObj);
        model.addAttribute("jsonObj", jsonObj);
        return "/style/role/create";
    }

    @RequestMapping(value = "createSave", method = RequestMethod.POST)
    public String createSave(HttpServletRequest request) {
        String roleName = request.getParameter("roleName");
        String state = request.getParameter("state");
        String roleType = request.getParameter("roleType");
        String description = request.getParameter("description");
        String aid = request.getParameter("aid");
        List<Application> apps = new ArrayList<Application>();
        List<Functions> funcs = new ArrayList<Functions>();
        if (StringUtils.isNotBlank(aid)) {
            String[] appId = StringUtils.split(aid, ',');
            for (String id : appId) {
                Application a = applicationService.get(id);
                if (a != null && StringUtils.isNotBlank(a.toString())) {
                    apps.add(a);
                }
                Functions f = functionService.get(id);
                if (f != null && StringUtils.isNotBlank(f.toString())) {
                    funcs.add(f);
                }
            }
        }
        Role r = new Role();
        r.setName(roleName);
        r.setState(state);
        r.setDescription(description);
        r.setType(roleType);
        r.setApps(apps);
        r.setFuncs(funcs);
        roleService.save(r);
        return "redirect:/role/roleList";
    }

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String info(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        Role role = roleService.get(id);
        model.addAttribute("role", role);
        List<Application> apps = role.getApps();
        List<Functions> funcs = role.getFuncs();
        List<Application_> apps_ = new ArrayList<Application_>();
        if (!apps.isEmpty()) {
            for (Application app : apps) {
                Application_ a_ = new Application_();
                a_.setId(app.getId());
                a_.setPid("0");
                a_.setName(app.getName());
                a_.setOpen(Boolean.TRUE);
                apps_.add(a_);
            }
        }
        if (!funcs.isEmpty()) {
            for (Functions f : funcs) {
                Application_ f_ = new Application_();
                f_.setId(f.getId());
                f_.setPid(f.getApplication().getId());
                f_.setName(f.getName());
                f_.setOpen(Boolean.FALSE);
                apps_.add(f_);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObj = "";
        try {
            jsonObj = objectMapper.writeValueAsString(apps_);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObj);
        model.addAttribute("jsonObj", jsonObj);
        return "/style/role/info";
    }

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String update(HttpServletRequest request, Model model){
        List<Application_> apps_ = new ArrayList<Application_>();
        List<Application> apps = applicationService.findAllByVisiable();
        for (Application app : apps) {
            Application_ a_ = new Application_();
            a_.setId(app.getId());
            a_.setPid("0");
            a_.setName(app.getName());
            a_.setOpen(Boolean.TRUE);
            List<Functions> funcs = app.getFuncs();
            if (!funcs.isEmpty()) {
                for (Functions f : funcs) {
                    Application_ f_ = new Application_();
                    f_.setId(f.getId());
                    f_.setPid(app.getId());
                    f_.setName(f.getName());
                    apps_.add(f_);
                }
                apps_.add(a_);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObj = null;
        try {
            jsonObj = objectMapper.writeValueAsString(apps_);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObj);
        model.addAttribute("jsonObj", jsonObj);
        String id = request.getParameter("id");
        Role role = roleService.get(id);
        model.addAttribute("role", role);
        return "/style/role/edit";
    }
}
