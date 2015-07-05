package com.qlp.commons.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 截取指定长度的HTML代码
 * Created by admin on 2014/8/1.
 */
public class HtmlSplitTag extends TagSupport {
    
	private static final long serialVersionUID = 1L;
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

    private String htmlStr;
    private Integer length;

    public String getHtmlStr() {
        return htmlStr;
    }

    public void setHtmlStr(String htmlStr) {
        this.htmlStr = htmlStr;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Override
    public int doStartTag() throws JspException {
        StringBuffer sb = new StringBuffer();
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签
        if (htmlStr.length() >= 1) {
            sb.append(htmlStr.trim());
        } else {
            sb.append(htmlStr);
        }
        String str = sb.toString();
        if (length != null && length > 0) {
            str = str.replaceAll("&nbsp;", "");
            if (str.length() > length) {
                str = str.substring(0, length);
                str  = str +"...";
            }
        }
        try {

            pageContext.getOut().println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

}
