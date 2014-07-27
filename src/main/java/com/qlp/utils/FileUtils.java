package com.qlp.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by qlp on 2014/7/27.
 */
public class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 获取文件后缀名
     * @param fileName
     * @return
     */
    public static String getAfterName(String fileName){
        if(StringUtils.isNotBlank(fileName)){
            int dotLocation = StringUtils.lastIndexOf(fileName,'.');
            if((dotLocation > -1 ) && dotLocation < fileName.length()-1){
                return fileName.substring(dotLocation+1);
            }else{
                logger.debug("传入文件名不合法");
            }
        }else{
            logger.debug("传入文件名为blank");
        }
        return "";
    }

    /**
     * 获取文件名
     * @param fileName
     * @return
     */
    public static String getBeforeName(String fileName){
        if(StringUtils.isNotBlank(fileName)){
            int dotLocation = StringUtils.lastIndexOf(fileName,'.');
            if((dotLocation > -1 ) && dotLocation < fileName.length()-1){
                return fileName.substring(0,dotLocation);
            }else{
                logger.debug("传入文件名不合法");
            }
        }else{
            logger.debug("传入文件名为blank");
        }
        return "";
    }

    /**
     * 获取文件的类型
     * @param fileName
     * @return
     */
    public static String getFileType(String fileName){
        String type = getAfterName(fileName);
        return "";
    }
}
