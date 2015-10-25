package com.qlp.commons.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.qlp.utils.TagUtils;

/**
 * 父页面目标定义标签
 * @author july
 *
 */
public class JspExtendsTag extends BodyTagSupport{
	
	private static final long serialVersionUID = -2707424830654373173L;
	private String name;  
	   
	public void setName(String name) {  
	  this.name = name;  
	} 
	
	public int doStartTag() throws JspException{
		return isOverrided() ? SKIP_BODY : EVAL_BODY_BUFFERED;
	}
	
	public int doEndTag() throws JspException{
		if(isOverrided()) {  
		   return EVAL_PAGE;  
		}  
		BodyContent body = getBodyContent();
		String varName = TagUtils.getOverrideVariableName(name);
		pageContext.setAttribute(varName, body.getString());
		return EVAL_PAGE;
	}

	private boolean isOverrided() {
		String varName = TagUtils.getOverrideVariableName(name);
		return pageContext.getAttribute(varName) != null;
	}

}
