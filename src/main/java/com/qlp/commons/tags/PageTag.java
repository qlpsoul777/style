package com.qlp.commons.tags;

import org.springframework.data.domain.Page;

import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by qlp on 2014/5/27.
 * 分页标签实现类
 */
public class PageTag extends TagSupport {

    private Page<Object> page;
    private StringBuilder html;
    private String formId;
    private String formAction;

    public String getFormAction() {
        return formAction;
    }

    public void setFormAction(String formAction) {
        this.formAction = formAction;
    }

    public Page<Object> getPage() {
        return page;
    }

    public void setPage(Page<Object> page) {
        this.page = page;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }
}
