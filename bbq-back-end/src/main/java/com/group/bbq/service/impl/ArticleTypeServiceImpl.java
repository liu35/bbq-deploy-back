package com.group.bbq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.bbq.common.lang.Result;
import com.group.bbq.entity.ArticleType;
import com.group.bbq.mapper.ArticleTypeMapper;
import com.group.bbq.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * article type table
 * </p>
 */
@Service
public class ArticleTypeServiceImpl extends ServiceImpl<ArticleTypeMapper, ArticleType> implements ArticleTypeService {

    private final ArticleTypeMapper articleTypeMapper;

    @Autowired
    public ArticleTypeServiceImpl(ArticleTypeMapper articleTypeMapper) {
        this.articleTypeMapper = articleTypeMapper;
    }

    @Override
    public Result getTypes() {
        List<ArticleType> articleTypes = articleTypeMapper.selectList(new QueryWrapper<>(new ArticleType()).eq("is_delete", 0));
        return Result.succ(articleTypes);
    }

    @Override
    public Result addType(String typeName) {
        ArticleType articleType = new ArticleType();
        articleType.setRefCount((long)0);
        articleType.setScope("USER");
        articleType.setDescription(typeName);
        articleType.setName(typeName);
        articleType.setCreateTime(LocalDateTime.now());
        articleType.setUpdateTime(LocalDateTime.now());
        articleType.setIsDelete(0);
        articleTypeMapper.insert(articleType);
        return Result.succ("add success");
    }

    @Override
    public Result deleteType(Long id) {
        int i = articleTypeMapper.deleteById(id);
        if (i == 0){
            return Result.fail("delete fail");
        }
        return Result.succ("delete success");
    }
}
