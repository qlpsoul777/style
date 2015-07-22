package com.qlp.sys.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qlp.commons.entity.MsgInfo;
import com.qlp.commons.enums.Type;
import com.qlp.sys.entity.Role;
import com.qlp.sys.entity.User;
import com.qlp.sys.service.RoleService;
import com.qlp.sys.service.UserService;
import com.qlp.utils.JsonMapper;
import com.qlp.utils.ParameterUtils;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	 @InitBinder  
	 public void initBinder(WebDataBinder binder) {  
	     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	     dateFormat.setLenient(false);  
	     binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false)); 
	 }
	 
	@ModelAttribute("user")
	public User getBidding(@RequestParam(value = "id", required = false) String id){
        if(StringUtils.isNotBlank(id)){
            return userService.findById(id);
        }else{
            return new User();
        }
    }
	
	@RequestMapping(value = "/list/{type}", method = RequestMethod.GET)
	public String list(HttpServletRequest request, @PageableDefaults(10) Pageable pageable,
            @PathVariable("type") Type type, Model model){
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
        int pageNum = Integer.parseInt(request.getParameter("currentPage")== null?"0":request.getParameter("currentPage"));
        pageable = new PageRequest(pageNum, pageable.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
        Page<User> pageInfo = userService.findPageByCriteria(map, pageable);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("type", type);
        model.addAttribute("userName", userName);
        model.addAttribute("roleName", roleName);
		return "/style/system/user/list";
		
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public String addUser(HttpServletRequest request, Model model){
		List<Role> allRoles = roleService.queryAllUsingRoles();
		model.addAttribute("allRoles", allRoles);
		return "/style/system/user/add";
	}
	
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String saveUser(HttpServletRequest request,@ModelAttribute User user){
		if(StringUtils.isBlank(user.getId())){
			user.setType(Type.INNER);
			String roleIds = request.getParameter("roleIds");
			userService.setRoles(user,roleIds);
			userService.createUser(user, ParameterUtils.INITPASSWORD);
			return "redirect:/user/list/INNER";
		}
		userService.save(user);
		return "redirect:/platform/index";
	}
	
	@RequestMapping(value = "/batchDelete", method = RequestMethod.GET)
	public String batchDelete(HttpServletRequest request){
		String userIds = request.getParameter("ids");
		userService.batchDelete(userIds);
		return "redirect:/user/list/INNER";
	}
	
	@RequestMapping(value = "/batchEdit", method = RequestMethod.GET)
	public String batchEdit(HttpServletRequest request){
		String userIds = request.getParameter("ids");
		userService.batchEdit(userIds);
		return "redirect:/user/list/INNER";
	}
	
	@RequestMapping(value = "/batchResetPwd", method = RequestMethod.GET)
	public String batchResetPwd(HttpServletRequest request){
		String userIds = request.getParameter("ids");
		userService.batchResetPwd(userIds);
		return "redirect:/user/list/INNER";
	}
	
	@RequestMapping(value = "/editUserInfo/{id}", method = RequestMethod.GET)
	public String editUserInfo(HttpServletRequest request,@PathVariable("id")String id, Model model){
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "/style/system/user/edit";
	}
	
	@RequestMapping(value = "/editPwd/{id}", method = RequestMethod.GET)
	public String editPwd(HttpServletRequest request,@PathVariable("id")String id, Model model){
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "/style/system/user/editPwd";
	}
	
	@RequestMapping(value = "/savePwd", method = RequestMethod.POST)
	public String savePwd(HttpServletRequest request,@ModelAttribute User user, Model model){
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		String repeatNewPwd = request.getParameter("repeatNewPwd");
		MsgInfo msg = userService.updatePwd(user,oldPwd,newPwd,repeatNewPwd);
		model.addAttribute("msg", msg);
		model.addAttribute("user", user);
		return "/style/system/user/editPwd";
	}
	
	@RequestMapping(value = "/repeatLoginName", method = RequestMethod.GET)
	@ResponseBody
	public String repeatLoginName(HttpServletRequest request){
		String loginName = request.getParameter("loginName");
		boolean flag = userService.repeatLoginName(loginName);
		return new JsonMapper().toJson(flag);
	}

}
