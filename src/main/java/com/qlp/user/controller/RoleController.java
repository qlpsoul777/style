package com.qlp.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlp.user.dto.Application_;
import com.qlp.user.dto.Functions_;
import com.qlp.user.entity.Application;
import com.qlp.user.entity.Functions;
import com.qlp.user.entity.Role;
import com.qlp.user.service.ApplicationService;
import com.qlp.user.service.RoleService;
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

    @Autowired
    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
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
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(HttpServletRequest request, Model model) {
        Map<String, List<Functions_>> map = new HashMap<String, List<Functions_>>();
        List<Application> apps = applicationService.findAllByVisiable();
        for (Application app : apps) {
            List<Functions> funcs = app.getFuncs();
            List<Functions_> funcs_ = new ArrayList<Functions_>();
            if (!funcs.isEmpty()) {
                for (Functions f : funcs) {
                    Functions_ f_ = new Functions_();
                    f_.setfId(f.getId());
                    f_.setfName(f.getName());
                    funcs_.add(f_);
                }
                map.put(app.getName(), funcs_);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonObj = objectMapper.writeValueAsString(map);
            model.addAttribute("jsonObj", jsonObj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "/style/role/create";
    }
}
