package com.zijianmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.zijianmall.common.valid.AddGroup;
import com.zijianmall.common.valid.ListValues;
import com.zijianmall.common.valid.UpdateGroup;
import com.zijianmall.common.valid.UpdateStatusGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;


/**
 * 品牌
 * 
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-21 22:37:16
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@Null(message = "新增时id必须为null", groups = {AddGroup.class})
	@NotNull(message = "修改时必须指定id", groups = {UpdateGroup.class})
	@TableId
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message = "品牌名必须提交", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
	@URL(message = "logo必须是合法的url地址", groups = {AddGroup.class, UpdateGroup.class})
	@NotEmpty(groups = {AddGroup.class})
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@NotNull(groups = {AddGroup.class, UpdateStatusGroup.class})
	@ListValues(values = {0, 1}, groups = {AddGroup.class, UpdateStatusGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@Pattern(regexp = "^[a-zA-Z]$", message = "首字母必须为一个字母", groups = {AddGroup.class, UpdateGroup.class})
	private String firstLetter;
	/**
	 * 排序
	 */
	@Min(value = 0L, message = "排序必须大于等于零", groups = {AddGroup.class, UpdateGroup.class})
	private Integer sort;

}
