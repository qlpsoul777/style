package com.qlp.user.controller;

import com.qlp.user.entity.Application;
import com.qlp.user.entity.Functions;
import com.qlp.user.entity.User;
import com.qlp.user.service.ApplicationService;
import com.qlp.user.service.FunctionService;
import com.qlp.user.service.UserService;
import com.qlp.utils.ParameterUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by qlp on 14-4-15.
 * User Manager(用户管理)
 */
@Controller
@RequestMapping(value = "user/index")
public class UserController {
    private UserService userService;
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

    @RequestMapping(value = "first" , method = RequestMethod.GET)
    public String first(){
        return "/style/user/index";
    }

    /**
     * 登录
     * @param request  client request
     * @param model   service response
     * @return url
     */
    @RequestMapping(value = "login" , method = RequestMethod.POST)
    public String login(HttpServletRequest request,Model model){
        String loginName = request.getParameter("loginName");
        String inputPassword = request.getParameter("password");
        User user = userService.findByLoginNameAndPassword(loginName,inputPassword);
        String path = "/style/user/index";
        String errorMessage = "";  //登录失败提示信息
        if(user != null){
            if(StringUtils.equals(user.getState(),ParameterUtils.ENABLE)){
                List<Application> apps = applicationService.findApplicationByUser(user.getId());
                List<Functions> funcs = functionService.findFunctionsByUser(user.getId());
                model.addAttribute("funcs", funcs);
                model.addAttribute("apps", apps);
                model.addAttribute("user",user);
                path = "/style/user/hello";
            }else{
                errorMessage = "账户已禁用，如有疑问请联系管理员";
            }
        }else{
            errorMessage = "用户名或密码错误，请重试";
        }
        model.addAttribute("msg",errorMessage);
        return path;
    }
}
