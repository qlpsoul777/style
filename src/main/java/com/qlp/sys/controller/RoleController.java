package com.qlp.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qlp.sys.dto._Module;
import com.qlp.sys.entity.Module;
import com.qlp.sys.entity.Role;
import com.qlp.sys.service.ModuleService;
import com.qlp.sys.service.RoleService;
import com.qlp.utils.JsonMapper;

@Controller
@RequestMapping(value = "/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, @PageableDefaults(10) Pageable pageable,
            Model model){
        String roleName = request.getParameter("name_li");  //角色名
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(roleName)) {
            map.put("name_li", roleName);
        }
        int pageNum = Integer.parseInt(request.getParameter("currentPage")== null?"0":request.getParameter("currentPage"));
        pageable = new PageRequest(pageNum, pageable.getPageSize(), new Sort(Sort.Direction.ASC, "createTime"));
        Page<Role> pageInfo = roleService.findPageByMap(map, pageable);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("roleName", roleName);
		return "/style/system/role/list";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model){
		List<_Module> allModules = moduleService.findAllUsingModule();
		model.addAttribute("modules", new JsonMapper().toJson(allModules));
		return "/style/system/role/add";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		Role role = roleService.findById(id);
		String selectedIds = toStr(role);
		List<_Module> allModules = moduleService.findAllUsingModule();
		model.addAttribute("role", role);
		model.addAttribute("selectedIds", selectedIds);
		model.addAttribute("modules", new JsonMapper().toJson(allModules));
		return "/style/system/role/edit";
	}
	
	private String toStr(Role role) {
		List<Module> modules = role.getModules();
		if(modules != null && !modules.isEmpty()){
			List<Long> ids = new ArrayList<>(modules.size());
			for (Module module : modules) {
				ids.add(module.getId());
			}
			return new JsonMapper().toJson(ids);
		}
		return null;
	}

	@RequestMapping(value = "/saveAdd", method = RequestMethod.POST)
	public String saveAdd(HttpServletRequest request,@ModelAttribute Role role){
		String moduleIds = request.getParameter("moduleIds");
		roleService.setModules(role,moduleIds);
		roleService.save(role);
		return "redirect:/role/list";
	}
	
	@RequestMapping(value = "/batchOperate/{mark}", method = RequestMethod.GET)
	public String batchOperate(HttpServletRequest request,@PathVariable("mark")char mark){
		String roleIds = request.getParameter("ids");
		switch(mark){
			case 'D':
				roleService.batchDelete(roleIds);
				break;
			case 'E':
				roleService.batchEdit(roleIds);
				break;
		}
		return "redirect:/role/list";
	}
	
	@RequestMapping(value = "/repeatRoleName", method = RequestMethod.GET)
	@ResponseBody
	public String repeatRoleName(HttpServletRequest request){
		String name = request.getParameter("name");
		boolean flag = roleService.repeatLoginName(name);
		return new JsonMapper().toJson(flag);
	}

}
