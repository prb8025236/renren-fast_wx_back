package io.renren.modules.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.wx.entity.WxUserEntity;

import java.util.Map;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-24 13:42:22
 */
public interface WxUserService extends IService<WxUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

