package com.qlp.sys.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qlp.sys.entity.Module;
import com.qlp.sys.entity.User;
import com.qlp.sys.service.ModuleService;
import com.qlp.sys.service.UserService;
import com.qlp.utils.ParameterUtils;
import com.qlp.utils.SecurityUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2015/4/30.
 */
@Controller
@RequestMapping(value = "/platform")
public class SystemController {
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String first(){
        SecurityUtils.getSubject().logout();
        return "/style/system/backstage/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginning(HttpServletRequest request,Model model){
        String msg = "";//登录提示信息
        String errorClassName = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if("jCaptcha.error".equals(errorClassName)){
            msg = "验证码错误";
        }else if(UnknownAccountException.class.getName().equals(errorClassName)){
            msg = "用户名/密码错误";
        }else if(IncorrectCredentialsException.class.getName().equals(errorClassName)){
            msg = "用户名/密码错误";
        }else if(LockedAccountException.class.getName().equals(errorClassName)){
            msg ="用户被锁定";
        }else if(errorClassName != null){
            msg = "登录出错";
        }
        model.addAttribute("msg", msg);
        return "/style/system/backstage/login";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request,Model model){
    	String loginName = SecurityUtil.getCurrentUserLoginName();
    	User user = null;
    	if(StringUtils.equals(ParameterUtils.ROOT, loginName)){
    		user = new User(ParameterUtils.ROOT,ParameterUtils.ROOT,ParameterUtils.ROOT_EMAIL);
    	}else{
    		user = userService.findByLoginName(loginName);
    	}
    	List<Module> menus = moduleService.queryMenuByLoginName(loginName);
    	model.addAttribute("menus", menus);
    	model.addAttribute("user",user);
        return "/style/system/backstage/index";
    }
}
