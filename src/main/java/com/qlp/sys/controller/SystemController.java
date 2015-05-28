package com.qlp.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2015/4/30.
 */
@Controller
@RequestMapping(value = "/platform")
public class SystemController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String first(){
        SecurityUtils.getSubject().logout();
        return "/style/system/backstage/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request,Model model){
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

    public String index(){

        return "/style/system/backstage/index";
    }
}
