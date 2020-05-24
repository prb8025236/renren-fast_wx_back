package io.renren.modules.wx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.wx.entity.WxUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-24 13:42:22
 */
@Mapper
@Component
public interface WxUserDao extends BaseMapper<WxUserEntity> {
    @Select("select * from wx_user where unionid = #{unionid}")
    public List<WxUserEntity> queryForListById(@Param("unionid") String unionid);
	
}
