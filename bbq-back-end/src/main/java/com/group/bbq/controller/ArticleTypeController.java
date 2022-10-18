package com.group.bbq.controller;


import com.group.bbq.common.lang.Result;
import com.group.bbq.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * article type table
 * </p>

 */
@RestController
@RequestMapping("/article")
public class ArticleTypeController {

    private final ArticleTypeService articleTypeService;

    @Autowired
    public ArticleTypeController(ArticleTypeService articleTypeService) {
        this.articleTypeService = articleTypeService;
    }

    @GetMapping("/types")
    public Result getTypes() {
        return articleTypeService.getTypes();
    }

    @GetMapping("/admin/type/add")
    public Result addType(@RequestParam String typeName) {
        return articleTypeService.addType(typeName);
    }
}
