package com.qlp.commons.tags;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

/**
 * Created by qlp on 2014/5/27.
 * 分页标签实现类
 */
public class PageTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(PageTag.class);
    private Page<Object> page;  //分页对象
    private StringBuilder html;  //输出的HTML内容
    private String formId;  //表单id
    private String cssStyle = "styleOne";  //分页样式，默认为样式1，如果需要其他样式可自定义

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
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

    @SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
        this.html = new StringBuilder();
        if (this.page.getContent().isEmpty()) {
            return super.EVAL_PAGE;
        }
        this.html.append("<ul>").append("\n");
        if (StringUtils.equals(this.cssStyle, "styleOne")) {
            this.html.append(styleOne());
        }
        try {
            this.pageContext.getOut().write(this.html.toString());  //输出HTML内容到jsp页面
        } catch (IOException e) {
            logger.debug("分页标签出错了");
            throw new JspException();
        }
        return super.EVAL_PAGE;//让服务器继续执行jsp页面
    }

    private StringBuilder styleOne() {
        StringBuilder sb = new StringBuilder();
        int currentPage = this.page.getNumber();
        //如果总页数只有1页
        if (this.page.getTotalPages() == 1) {
            sb.append("<li class=\"disabled\">");
            sb.append("<a href=\"#\">").append(1).append("</a>");
            sb.append("</li>").append("\n");
        }
        //如果总页数大于1页
        else {
            //如果当前页是第一页
            if (this.page.isFirstPage()) {
                sb.append("<li class=\"disabled\">");
                sb.append("<a href=\"#\">").append("上一页").append("</a>");
                sb.append("</li>").append("\n");
                sb.append(styleOneLess(currentPage));
                sb.append("<li>");
                sb.append("<a href=\"javascript:toPage('").append(this.formId).append("', ")
                        .append(currentPage + 2).append(");\">").append("下一页").append("</a>");
                sb.append("</li>").append("\n");
            }
            //如果当前页是最后一页
            else if (this.page.isLastPage()) {
                sb.append("<li>");
                sb.append("<a href=\"javascript:toPage('").append(this.formId).append("', ")
                        .append(currentPage).append(");\">").append("上一页").append("</a>");
                sb.append("</li>").append("\n");
                sb.append(styleOneLess(currentPage));
                sb.append("<li class=\"disabled\">");
                sb.append("<a href=\"#\">").append("下一页").append("</a>");
                sb.append("</li>").append("\n");
            }
            //当前页不是第一页也不是最后一页
            else {
                sb.append("<li>");
                sb.append("<a href=\"javascript:toPage('").append(this.formId).append("', ")
                        .append(currentPage).append(");\">").append("上一页").append("</a>");
                sb.append("</li>").append("\n");
                sb.append(styleOneLess(currentPage));
                sb.append("<li>");
                sb.append("<a href=\"javascript:toPage('").append(this.formId).append("', ")
                        .append(currentPage + 2).append(");\">").append("下一页").append("</a>");
                sb.append("</li>").append("\n");
            }
        }
        sb.append("<li><input type=\"hidden\" id=\"").append(this.formId)
                .append("_page_id\" name=\"page.page\" value=\"").append(this.page.getNumber() + 1)
                .append("\"></li>").append("\n");
        sb.append("<script type=\"text/javascript\">").append("\n");
        sb.append("function toPage(formId, page) {").append("\n");
        sb.append("var $form = $('#' + formId);").append("\n");
        sb.append("reg = /^[0-9]*$/;").append("\n");
        sb.append("if (reg.exec(page + \"\")) {").append("\n");
        sb.append("$(\"#\" + formId + \"_page_id\").val(page);").append("\n");
        sb.append("}").append("\n");
        sb.append("$form[0].submit();").append("\n");
        sb.append("}").append("\n");
        sb.append("</script>").append("\n");
        return sb;
    }

    private StringBuilder styleOneLess(int currentPage) {
        StringBuilder sbLess = new StringBuilder();
        //总页数大于等于1小于10
        if (this.page.getTotalPages() <= 10) {
            for (int i = 1; i <= this.page.getTotalPages(); i++) {
                if (i == currentPage + 1) {
                    sbLess.append("<li class=\"active\">");
                    sbLess.append("<a href=\"javascript:toPage('").append(this.formId).append("', ")
                            .append(i).append(");\">").append(i).append("</a>");
                    sbLess.append("</li>").append("\n");
                } else {
                    sbLess.append("<li>");
                    sbLess.append("<a href=\"javascript:toPage('").append(this.formId).append("', ")
                            .append(i).append(");\">").append(i).append("</a>");
                    sbLess.append("</li>").append("\n");
                }
            }
        }
        //总页数大于10页
        else {
            int onePageShow = 10;  //一页显示的页数
            int half = onePageShow >> 1;
            int halfPageShow = half + 1;
            int havePages = this.page.getTotalPages() - currentPage;  //总页数减去当前页
            int stopPage = this.page.getTotalPages() - onePageShow;  //总页数减去一页显示的页数
            if (currentPage <= halfPageShow) {
                for (int i = 1; i <= onePageShow; i++) {
                    if (i == currentPage + 1) {
                        sbLess.append("<li class=\"active\">");
                        sbLess.append("<a href=\"javascript:toPage('").append(this.formId).append("', ")
                                .append(i).append(");\">").append(i).append("</a>");
                        sbLess.append("</li>").append("\n");
                    } else {
                        sbLess.append("<li>");
                        sbLess.append("<a href=\"javascript:toPage('").append(this.formId).append("', ")
                                .append(i).append(");\">").append(i).append("</a>");
                        sbLess.append("</li>").append("\n");
                    }
                }
            } else if (havePages >= 4) {
                for (int i = currentPage - 5; i < currentPage + 5; i++) {
                    if (i == currentPage + 1) {
                        sbLess.append("<li class=\"active\">");
                        sbLess.append("<a href=\"javascript:toPage('").append(this.formId).append("', ")
                                .append(i).append(");\">").append(i).append("</a>");
                        sbLess.append("</li>").append("\n");
                    } else {
                        sbLess.append("<li>");
                        sbLess.append("<a href=\"javascript:toPage('").append(this.formId).append("', ")
                                .append(i).append(");\">").append(i).append("</a>");
                        sbLess.append("</li>").append("\n");
                    }
                }
            } else {
                for (int i = stopPage; i <= this.page.getTotalPages(); i++) {
                    if (i == currentPage + 1) {
                        sbLess.append("<li class=\"active\">");
                        sbLess.append("<a href=\"javascript:toPage('").append(this.formId).append("', ")
                                .append(i).append(");\">").append(i).append("</a>");
                        sbLess.append("</li>").append("\n");
                    } else {
                        sbLess.append("<li>");
                        sbLess.append("<a href=\"javascript:toPage('").append(this.formId).append("', ")
                                .append(i).append(");\">").append(i).append("</a>");
                        sbLess.append("</li>").append("\n");
                    }
                }
            }
        }
        return sbLess;
    }
}
