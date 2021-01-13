package com.zijianmall.product.service.impl;

import com.zijianmall.product.service.CategoryBrandRelationService;
import com.zijianmall.product.vo.Catalog3Vo;
import com.zijianmall.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.common.utils.Query;

import com.zijianmall.product.dao.CategoryDao;
import com.zijianmall.product.entity.CategoryEntity;
import com.zijianmall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 以树形列表形式查询所有的菜单分类
     *
     * @return
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        //1、查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);

        //2、组装成父子的树形结构

        //2.1）、找到所有的一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0
        ).map((menu) -> {
            menu.setChildren(getChildren(menu, entities));
            return menu;
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());


        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        // TODO 检查当前删除的菜单，是否被其他的地方引用
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> list = new ArrayList<>();
        List<Long> parentPath = getParentId(catelogId, list);
        Collections.reverse(parentPath);
        Long[] path = parentPath.toArray(new Long[parentPath.size()]);
        return path;
    }

    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());

    }

    @Override
    public List<CategoryEntity> getLevel1Categories() {
        QueryWrapper<CategoryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_cid", 0);
        List<CategoryEntity> categoryEntities = this.list(wrapper);
        return categoryEntities;
    }

@Override
	public Map<String, List<Catelog2Vo>> getCatelogJson() {
		List<CategoryEntity> entityList = baseMapper.selectList(null);
		// 查询所有一级分类
		List<CategoryEntity> level1 = getCategoryEntities(entityList, 0L);
		Map<String, List<Catelog2Vo>> parent_cid = level1.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
			// 拿到每一个一级分类 然后查询他们的二级分类
			List<CategoryEntity> entities = getCategoryEntities(entityList, v.getCatId());
			List<Catelog2Vo> catelog2Vos = null;
			if (entities != null) {
				catelog2Vos = entities.stream().map(l2 -> {
					Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), l2.getName(), l2.getCatId().toString(), null);
					// 找当前二级分类的三级分类
					List<CategoryEntity> level3 = getCategoryEntities(entityList, l2.getCatId());
					// 三级分类有数据的情况下
					if (level3 != null) {
						List<Catalog3Vo> catalog3Vos = level3.stream().map(l3 -> new Catalog3Vo(l3.getCatId().toString(), l3.getName(), l2.getCatId().toString())).collect(Collectors.toList());
						catelog2Vo.setCatalog3List(catalog3Vos);
					}
					return catelog2Vo;
				}).collect(Collectors.toList());
			}
			return catelog2Vos;
		}));
		return parent_cid;
	}

    private List<Long> getParentId(Long catelogId, List<Long> list) {
        list.add(catelogId);
        CategoryEntity category = this.getById(catelogId);
        if (category.getParentCid() != 0 ) {
            getParentId(category.getParentCid(), list);
        }
        return list;
    }

    /**
     * 递归查询子菜单
     *
     * @param root
     * @param all
     * @return
     */
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {

        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid().equals(root.getCatId());
        }).map(categoryEntity -> {
            //1、找到子菜单
            categoryEntity.setChildren(getChildren(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1, menu2) -> {
            //2、菜单的排序
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return children;
    }

    	/**
	 * 第一次查询的所有 CategoryEntity 然后根据 parent_cid去这里找
	 */
	private List<CategoryEntity> getCategoryEntities(List<CategoryEntity> entityList, Long parent_cid) {

		return entityList.stream().filter(item -> item.getParentCid().equals(parent_cid)).collect(Collectors.toList());
	}

}