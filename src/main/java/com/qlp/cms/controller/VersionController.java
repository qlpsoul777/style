package com.qlp.cms.controller;

import com.qlp.cms.dto.Version_;
import com.qlp.cms.entity.Version;
import com.qlp.cms.service.VersionService;
import com.qlp.utils.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        List<Version_> vList = new ArrayList<Version_>();
        while (iterator.hasNext()){
            MultipartFile multipartFile = request.getFile((String) iterator.next());
            Version version = createAndSaveVersion(multipartFile, uploadPath);
            Version_ v = new Version_();
            v.setId(version.getId());

        }
        return "";
    }

    private Version createAndSaveVersion(MultipartFile file, String uploadPath) {
        if(file.getSize()>0){
            String date = new DateTime().toString("yyyy-MM-dd");
            String randomStr = RandomStringUtils.randomAlphanumeric(7);  //生成长度为7位的随机字符串
            String fileName = file.getOriginalFilename();
            uploadPath = uploadPath + "/" + date;
            File f = new File(uploadPath);
            if (!f.exists()) {
                f.mkdirs();
            }
            String fullPath = uploadPath + "/" + randomStr;
            String afterName = FileUtils.getAfterName(fileName);
            if (StringUtils.isNotEmpty(afterName)) {
                fullPath += "." + afterName;
            }
            FileOutputStream fos = null;
            try {
                    fos = new FileOutputStream(fullPath);
                    IOUtils.copy(file.getInputStream(),fos);
                } catch (Exception e) {
                    logger.debug("文件上传失败");
                    e.printStackTrace();
                }finally {
                if(fos != null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Version version = new Version();
            version.setName(fileName);
            version.setAfterName(afterName);
            version.setPath(fullPath);
            version.setSize(file.getSize());
            version.setType(FileUtils.getFileType(fileName));
            return versionService.save(version);
        }
        return null;
    }
}
