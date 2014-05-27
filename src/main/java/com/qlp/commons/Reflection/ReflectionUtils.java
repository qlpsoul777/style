package com.qlp.commons.Reflection;

import java.lang.reflect.Field;

/**
 * Created by qlp on 2014/5/26.
 * 反射工具类
 */
public class ReflectionUtils {

    /**
     * 获取指定对象指定属性名的值
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        Class clazz = obj.getClass();
        Field field = org.springframework.util.ReflectionUtils.findField(clazz, fieldName);
        org.springframework.util.ReflectionUtils.makeAccessible(field);
        Object fieldValue = org.springframework.util.ReflectionUtils.getField(field, obj);
        return fieldValue;
    }

    /**
     * 将指定对象的指定属性的值设置为指定值
     *
     * @param obj
     * @param fieldName
     * @param fieldValue
     */
    public static void setFieldValue(Object obj, String fieldName, Object fieldValue) {
        Class clazz = obj.getClass();
        Field field = org.springframework.util.ReflectionUtils.findField(clazz, fieldName);
        org.springframework.util.ReflectionUtils.makeAccessible(field);
        org.springframework.util.ReflectionUtils.setField(field, obj, fieldValue);
    }
}
