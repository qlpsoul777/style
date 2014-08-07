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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(value = "look", method = RequestMethod.GET)
    public String look(HttpServletRequest request,Model model){
        String siteId = request.getParameter("siteId");
        List<TreeNode> nodes = categoryService.findAllCategory(siteId);
        String s = JsonMapper.nonDefaultMapper().toJson(nodes);
        System.out.print(s);
        return "/style/cms/category/categoryList";
    }

}
