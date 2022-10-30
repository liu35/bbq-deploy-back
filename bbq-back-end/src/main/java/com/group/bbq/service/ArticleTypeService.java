package com.group.bbq.service;

import com.group.bbq.common.lang.Result;
import com.group.bbq.entity.ArticleType;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * article type table
 * </p>
 */
public interface ArticleTypeService extends IService<ArticleType> {

    Result getTypes();

    Result addType(String typeName);


    Result deleteType(Long id);
}
