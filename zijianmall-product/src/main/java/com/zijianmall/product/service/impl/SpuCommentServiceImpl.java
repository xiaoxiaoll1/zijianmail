package com.zijianmall.product.service.impl;

import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.common.utils.Query;
import com.zijianmall.product.dao.SpuCommentDao;
import com.zijianmall.product.entity.SpuCommentEntity;
import com.zijianmall.product.service.SpuCommentService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("spuCommentService")
public class SpuCommentServiceImpl extends ServiceImpl<SpuCommentDao, SpuCommentEntity> implements SpuCommentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuCommentEntity> page = this.page(
                new Query<SpuCommentEntity>().getPage(params),
                new QueryWrapper<SpuCommentEntity>()
        );

        return new PageUtils(page);
    }

}