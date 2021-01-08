package com.zijianmall.product.service.impl;

import com.zijianmall.product.dao.AttrDao;
import com.zijianmall.product.entity.AttrEntity;
import com.zijianmall.product.service.AttrService;
import com.zijianmall.product.vo.AttrGroupRelationVo;
import com.zijianmall.product.vo.AttrGroupWithAttrsVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.common.utils.Query;

import com.zijianmall.product.dao.AttrGroupDao;
import com.zijianmall.product.entity.AttrGroupEntity;
import com.zijianmall.product.service.AttrGroupService;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {



    @Autowired
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long categoryId) {
        String key = (String) params.get("key");
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>().eq("catelog_id", categoryId);
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(obj -> {
                obj.eq("attr_group_id", key).or().like("attr_group_name", key);
            });
            }
        if (categoryId == 0) {
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params), wrapper);
            return new PageUtils(page);
        }

        wrapper.eq("catelog_id", categoryId);
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {
        List<AttrGroupEntity> groups = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<AttrGroupWithAttrsVo> vos = groups.stream().map(group -> {
            AttrGroupWithAttrsVo vo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(group, vo);
            List<AttrEntity> attrs = attrService.getAttrRelation(vo.getAttrGroupId());
            vo.setAttrs(attrs);
            return vo;
        }).collect(Collectors.toList());
        return vos;
    }

}