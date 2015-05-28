package com.qlp.sys.service.impl;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.data.repository.NoRepositoryBean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2015/5/3.
 */
public class JCaptchaValidateFilter extends AccessControlFilter {

    private boolean jCaptchaEbabled  = true;//是否开启验证码支持
    private String jCaptchaParam = "jCaptchaCode";//前台提交的验证码参数名
    private String failureKeyAttribute = "shiroLoginFailure"; //验证码验证失败后存储到的属性名

    public boolean getjCaptchaEbabled() {
        return jCaptchaEbabled;
    }

    public void setjCaptchaEbabled(boolean jCaptchaEbabled) {
        this.jCaptchaEbabled = jCaptchaEbabled;
    }

    public String getjCaptchaParam() {
        return jCaptchaParam;
    }

    public void setjCaptchaParam(String jCaptchaParam) {
        this.jCaptchaParam = jCaptchaParam;
    }

    public String getFailureKeyAttribute() {
        return failureKeyAttribute;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //1、设置验证码是否开启属性，页面可以根据该属性来决定是否显示验证码
        request.setAttribute("jCaptchaEbabled", Boolean.valueOf(jCaptchaEbabled));
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        //2、判断验证码是否禁用 或不是表单提交（允许访问）
        if (jCaptchaEbabled == false || !"post".equalsIgnoreCase(httpServletRequest.getMethod())) {
            return true;
        }
        //3、此时是表单提交，验证验证码是否正确
        return JCaptcha.validateResponse(httpServletRequest, httpServletRequest.getParameter(jCaptchaParam));
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //如果验证码失败了，存储失败key属性
        request.setAttribute(failureKeyAttribute, "jCaptcha.error");
        return true;
    }
}
