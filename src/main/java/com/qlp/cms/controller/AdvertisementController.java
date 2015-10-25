package com.qlp.cms.controller;

import com.qlp.cms.entity.Advertisement;
import com.qlp.cms.service.AdvertisementService;

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
@RequestMapping("/cms/advertisement")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    /**
     * ?????????
     *
     * @param id
     * @return
     */
    @ModelAttribute("advertisement")
    public Advertisement getAdvertisement(@RequestParam(value = "id", required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return advertisementService.get(id);
        } else {
            return new Advertisement();
        }
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(@PageableDefaults(10) Pageable pageable,Model model){
        Map<String,Object> map = new HashMap<String,Object>();
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        PageRequest pr = new PageRequest(pageable.getPageNumber(),pageable.getPageSize(),sort);
        Page<Advertisement> pageInfo =  advertisementService.findPageByMap(map,pr);
        model.addAttribute("pageInfo",pageInfo);
        return "/style/cms/advertisement/list";
    }

    @RequestMapping(value = "create" , method = RequestMethod.GET)
    public String create(HttpServletRequest request,Model model){
        return "/style/cms/advertisement/edit";
    }

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String save(HttpServletRequest request,Model model,@ModelAttribute("advertisement")Advertisement advertisement){
        advertisementService.save(advertisement);
        return "redirect:/cms/advertisement/list";
    }

    @RequestMapping(value = "edit" , method = RequestMethod.GET)
    public String edit(HttpServletRequest request,Model model){
        return "/style/cms/advertisement/edit";
    }

    @RequestMapping(value = "delete" , method = RequestMethod.GET)
    public String delete(HttpServletRequest request,Model model){
        return "redirect:/cms/advertisement/list";
    }
}
