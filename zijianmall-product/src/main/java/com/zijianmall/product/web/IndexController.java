package com.zijianmall.product.web;

import com.zijianmall.product.entity.CategoryEntity;
import com.zijianmall.product.service.CategoryService;
import com.zijianmall.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author xiaozj
 */
@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;


	@RequestMapping({"/", "index", "/index.html"})
	public String indexPage(Model model) {
		// 获取一级分类所有缓存
		List<CategoryEntity> categorys = categoryService.getLevel1Categories();
		model.addAttribute("categorys", categorys);
		return "index";
	}

	@ResponseBody
	@RequestMapping("index/catalog.json")
	public Map<String, List<Catelog2Vo>> getCatlogJson() {

		Map<String, List<Catelog2Vo>> map = categoryService.getCatelogJson();
		return map;
	}
}
