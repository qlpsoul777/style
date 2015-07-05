package com.qlp.commons.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

/**
 * Created by qlp on 14-5-13.
 */
@Repository
public class SpringApplicationContext implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    public static Object getBean(String name)
            throws BeansException
    {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> type)
            throws BeansException
    {
        return applicationContext.getBean(name, type);
    }

    public static <T> T getBean(Class<T> type)
            throws BeansException
    {
        return applicationContext.getBean(type);
    }
}
