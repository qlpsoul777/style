package com.qlp.cms.controller;

import com.qlp.cms.entity.Site;
import com.qlp.cms.service.SiteService;

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
import java.util.Map;

/**
*Created by July At 2015-10-25
*
*/
@Controller
@RequestMapping("/cms/site")
public class SiteController {

    @Autowired
    private SiteService siteService;

    /**
     * ?????????
     *
     * @param id
     * @return
     */
    @ModelAttribute("site")
    public Site getSite(@RequestParam(value = "id", required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return siteService.get(id);
        } else {
            return new Site();
        }
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(@PageableDefaults(10) Pageable pageable,Model model){
        Map<String,Object> map = new HashMap<String,Object>();
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        PageRequest pr = new PageRequest(pageable.getPageNumber(),pageable.getPageSize(),sort);
        Page<Site> pageInfo =  siteService.findPageByMap(map,pr);
        model.addAttribute("pageInfo",pageInfo);
        return "/style/cms/site/list";
    }

    @RequestMapping(value = "create" , method = RequestMethod.GET)
    public String create(HttpServletRequest request,Model model){
        return "/style/cms/site/edit";
    }

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String save(HttpServletRequest request,Model model,@ModelAttribute("site")Site site){
        siteService.save(site);
        return "redirect:/cms/site/list";
    }

    @RequestMapping(value = "edit" , method = RequestMethod.GET)
    public String edit(HttpServletRequest request,Model model){
        return "/style/cms/site/edit";
    }

    @RequestMapping(value = "delete" , method = RequestMethod.GET)
    public String delete(HttpServletRequest request,Model model){
        return "redirect:/cms/site/list";
    }
}
