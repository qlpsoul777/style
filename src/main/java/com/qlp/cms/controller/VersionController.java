package com.qlp.cms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by qlp on 2014/7/23.
 * 附件上传、下载、删除
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
            v.setName(version.getName());
            v.setType(version.getType());
            v.setSize(version.getSize());
            v.setUrl(version.getPath());
            vList.add(v);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObj = "";
        try {
            jsonObj = objectMapper.writeValueAsString(vList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObj);
        return jsonObj;
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

    @RequestMapping(value = "delete/{id}")
    @ResponseBody
    public String delete(@PathVariable String id){
        String status;
        try{
            versionService.delete(id);
            status = "success";
        } catch (Exception e){
            status = "error";
        }
        return status;
    }

    @RequestMapping(value = "/download/{id}")
    public void download(@PathVariable String id, HttpServletResponse response){
        Version version = versionService.get(id);
        if(version == null){
            logger.error("下载文件不存在",id);
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(version.getPath());
            IOUtils.copy(fis,response.getOutputStream());
        } catch (Exception e) {
            logger.error("下载文件{}错误，文件实际保存路径{}不存在");
            e.printStackTrace();
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
