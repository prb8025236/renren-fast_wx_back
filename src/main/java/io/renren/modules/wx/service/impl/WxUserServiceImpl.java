package io.renren.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.wx.dao.WxUserDao;
import io.renren.modules.wx.entity.WxUserEntity;
import io.renren.modules.wx.service.WxUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("wxUserService")
public class WxUserServiceImpl extends ServiceImpl<WxUserDao, WxUserEntity> implements WxUserService {
    @Resource
    WxUserDao wxUserDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WxUserEntity> page = this.page(
                new Query<WxUserEntity>().getPage(params),
                new QueryWrapper<WxUserEntity>()
        );
        return new PageUtils(page);
    }

}