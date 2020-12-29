package com.zijianmall.member.dao;

import com.zijianmall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-26 20:53:13
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
