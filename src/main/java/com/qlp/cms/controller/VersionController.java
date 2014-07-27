package com.qlp.cms.controller;

import com.qlp.cms.service.VersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Created by qlp on 2014/7/23.
 * 附件上传
 */
@Controller
@RequestMapping("/cms/version")
public class VersionController {

    private static Logger logger = LoggerFactory.getLogger(VersionController.class);

    @Autowired
    private VersionService versionService;

    @RequestMapping(value = "fileUpload", method = RequestMethod.POST)
    @ResponseBody
    public String fileUpload(MultipartHttpServletRequest request){
        String uploadPath = request.getSession().getServletContext().getInitParameter("uploadPath");

        return "";
    }
}
