package com.qlp.cms.controller;

import com.qlp.cms.entity.Category;
import com.qlp.cms.entity.Site;
import com.qlp.cms.service.CategoryService;
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
 * 新闻栏目
 * Created by qlp on 2014/6/20.
 */
@Controller
@RequestMapping(value = "cms/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SiteService siteService;

    /**
     * 做二次值绑定
     *
     * @param id
     * @return
     */
    @ModelAttribute("category")
    public Category getCategory(@RequestParam(value = "id", required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return categoryService.get(id);
        } else {
            return new Category();
        }
    }

    /**
     * 已发布站点名
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
        return "/style/cms/category/list";
    }

    /**
     * 左侧栏目树
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "look", method = RequestMethod.GET)
    public String look(HttpServletRequest request,Model model){
        String siteId = request.getParameter("siteId");
        List<TreeNode> nodes = categoryService.findAllCategory(siteId);
        String s = new JsonMapper().toJson(nodes);
        model.addAttribute("treeNodes",s);
        model.addAttribute("siteId",siteId);
        return "/style/cms/category/categoryList";
    }

    /**
     * 当前节点下的所有栏目
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "childrenList", method = RequestMethod.GET)
    public String childrenList(@PageableDefaults(10) Pageable pageable,HttpServletRequest request,Model model){
        String currentNodeId = request.getParameter("currentNodeId");
        String allFlag = request.getParameter("allFlag");
        Map<String,Object> map = new HashMap<>();
        Sort sort = new Sort(Sort.Direction.ASC,"sort");
        PageRequest pr = new PageRequest(pageable.getPageNumber(),pageable.getPageSize(),sort);
        Page<Category> pageInfo;
        if(StringUtils.isNotBlank(allFlag)){
            map.put("site.id",currentNodeId);
            pageInfo = categoryService.findPageByMap(map,pr);
        }else{
            pageInfo = categoryService.findPageByIdAndMap(map,currentNodeId,pr);
        }
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("currentNodeId",currentNodeId);
        model.addAttribute("allFlag",allFlag);
        return "/style/cms/category/childrenList";
    }

    /**
     * 新增栏目
     * @param siteId
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "create" , method = RequestMethod.GET)
    public String create(@CookieValue("siteId")String siteId,HttpServletRequest request,Model model){
        String pid = request.getParameter("nodeId");
        model.addAttribute("pid",pid);
        model.addAttribute("siteId",siteId);
        return "/style/cms/category/edit";
    }

    /**
     * 新增和修改的保存
     * @param request
     * @param model
     * @param category
     * @return
     */
    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String save(HttpServletRequest request,Model model,@ModelAttribute("category")Category category){
        String pid = request.getParameter("pid");
        Category parent = categoryService.get(pid);
        if(parent != null){
            category.setParentCategory(parent);
        }
        String siteId = request.getParameter("siteId");
        Site site = siteService.get(siteId);
        category.setSite(site);
        categoryService.save(category);
        if(StringUtils.isBlank(pid)){
            model.addAttribute("currentNodeId",siteId);
            model.addAttribute("allFlag","all");
        }else {
            model.addAttribute("currentNodeId",pid);
        }
        return "redirect:/cms/category/childrenList";
    }

    /**
     * 右键删除栏目
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteOnRight/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteOnRight(@PathVariable("id") String id) {
        String flag = "";
        try {
            categoryService.deleteCategory(id);
            flag = "success";
        } catch (Exception e) {
            flag = "error";
        }
        return flag;
    }

    /**
     * 右键修改栏目
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "update" , method = RequestMethod.GET)
    public String update(HttpServletRequest request,Model model){
        String nodeId = request.getParameter("nodeId");
        Category category = categoryService.get(nodeId);
        model.addAttribute("category",category);
        String pid = "";
        Category parent = category.getParentCategory();
        if(parent != null){
            pid = parent.getId();
        }
        model.addAttribute("pid",pid);
        model.addAttribute("siteId",category.getSite().getId());
        return "/style/cms/category/edit";
    }

    /**
     * 右键发布栏目
     * @param id
     * @return
     */
    @RequestMapping(value = "publish/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String publish(@PathVariable("id") String id) {
        String flag = "";
        try {
            categoryService.publish(id);
            flag = "success";
        } catch (Exception e) {
            flag = "error";
        }
        return flag;
    }

}
