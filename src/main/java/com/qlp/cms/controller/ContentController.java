package com.qlp.cms.controller;

import com.qlp.cms.entity.Category;
import com.qlp.cms.entity.Content;
import com.qlp.cms.entity.Site;
import com.qlp.cms.service.CategoryService;
import com.qlp.cms.service.ContentService;
import com.qlp.cms.service.SiteService;
import com.qlp.commons.entity.TreeNode;
import com.qlp.utils.JsonMapper;
import com.qlp.utils.ParameterUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2014/8/19.
 * 新闻内容
 */
@Controller
@RequestMapping(value = "cms/content")
public class ContentController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private ContentService contentService;

    /**
     * 做二次值绑定
     *
     * @param id
     * @return
     */
    @ModelAttribute("content")
    public Content getContent(@RequestParam(value = "id", required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return contentService.get(id);
        } else {
            return new Content();
        }
    }

    /**
     * 已发布站点列表
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(@PageableDefaults(10) Pageable pageable,Model model){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("visiable", ParameterUtils.YES);
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        PageRequest pr = new PageRequest(pageable.getPageNumber(),pageable.getPageSize(),sort);
        Page<Site> pageInfo =  siteService.findPageByMap(map,pr);
        model.addAttribute("pageInfo",pageInfo);
        return "/style/cms/content/list";
    }

    /**
     * 已发布栏目树
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "look", method = RequestMethod.GET)
    public String look(HttpServletRequest request,Model model){
        String siteId = request.getParameter("siteId");
        List<TreeNode> nodes = categoryService.findAllVisiableCategory(siteId);
        String s = new JsonMapper().toJson(nodes);
        model.addAttribute("treeNodes",s);
        model.addAttribute("siteId",siteId);
        return "/style/cms/content/categoryList";
    }

    /**
     * 节点下的新闻列表
     * @param pageable
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "contentList", method = RequestMethod.GET)
    public String contentList(@CookieValue("siteId")String siteId,@PageableDefaults(10) Pageable pageable,HttpServletRequest request,Model model){
        String currentNodeId = request.getParameter("currentNodeId");
        String allFlag = request.getParameter("allFlag");
        Map<String,Object> map = new HashMap<>();
        Sort sort = new Sort(Sort.Direction.ASC,"createTime");
        PageRequest pr = new PageRequest(pageable.getPageNumber(),pageable.getPageSize(),sort);
        Page<Content> pageInfo;
        if(StringUtils.isNotBlank(allFlag)){
            map.put("site.id",currentNodeId);
            pageInfo = contentService.findPageByMap(map,pr);
        }else{
            map.put("category.id",currentNodeId);
            pageInfo = contentService.findPageByMap(map,pr);
        }
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("siteId", siteId);
        model.addAttribute("currentNodeId",currentNodeId);
        model.addAttribute("allFlag",allFlag);
        return "/style/cms/content/contentList";
    }

    @RequestMapping(value = "create" , method = RequestMethod.GET)
    public String create(@CookieValue("siteId")String siteId,HttpServletRequest request,Model model){
        String currentNodeId = request.getParameter("currentNodeId");
        model.addAttribute("categoryId",currentNodeId);
        model.addAttribute("siteId",siteId);
        return "/style/cms/content/edit";
    }
}
