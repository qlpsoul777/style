package com.qlp.commons.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.qlp.utils.TagUtils;
/**
 * 子页面填充内容标签
 * @author july
 *
 */
public class JspFillTag extends BodyTagSupport{

	private static final long serialVersionUID = -4650374695575055614L;
	private String name;  
	   
	public void setName(String name) {  
	  this.name = name;  
	} 
	
	public int doStartTag() throws JspException{
		return getOverriedContent() == null ? EVAL_BODY_INCLUDE : SKIP_BODY;
	}
	
	public int doEndTag() throws JspException{
		String overriedContent = getOverriedContent();
		if(overriedContent == null) {  
			return EVAL_PAGE;  
		} 
		try {  
			pageContext.getOut().write(overriedContent);  
		} catch (IOException e) {  
			throw new JspException("tag output error",e);  
		}  
		return EVAL_PAGE;
	}

	private String getOverriedContent() {  
	    String varName = TagUtils.getOverrideVariableName(name);  
	    return (String)pageContext.getAttribute(varName);  
	}  
}
