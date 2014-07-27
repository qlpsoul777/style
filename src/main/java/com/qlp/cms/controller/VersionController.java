package com.qlp.cms.controller;

import com.qlp.cms.entity.Version;
import com.qlp.cms.service.VersionService;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;

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
        Iterator<?> iterator = request.getFileNames();
        while (iterator.hasNext()){
            MultipartFile multipartFile = request.getFile((String) iterator.next());
            Version version = createAndSaveVersion(multipartFile, uploadPath);
        }
        return "";
    }

    private Version createAndSaveVersion(MultipartFile file, String uploadPath) {
        if(file.getSize()>0){
            String date = new DateTime().toString("yyyy-MM-dd");
            String randomStr = RandomStringUtils.randomAlphanumeric(7);  //生成长度为7位的随机字符串
            String fileName = file.getOriginalFilename();

        }
        return null;
    }
}
