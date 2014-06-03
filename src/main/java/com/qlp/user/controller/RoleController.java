package com.qlp.user.controller;

import com.qlp.user.entity.Role;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qlp on 2014/6/3.
 * Role Manager(角色管理)
 */
@Controller
@RequestMapping(value = "role/")
public class RoleController {
    private RoleService roleService;

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
}
