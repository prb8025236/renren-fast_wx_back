package io.renren.modules.wx.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.wx.entity.WxUserEntity;
import io.renren.modules.wx.service.WxUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-24 13:42:22
 */
@RestController
@RequestMapping("wx/wxuser")
public class WxUserController {
    @Resource
    private WxUserService wxUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wx:wxuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wxUserService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("wx:wxuser:info")
    public R info(@PathVariable("userId") Integer userId){
		WxUserEntity wxUser = wxUserService.getById(userId);

        return R.ok().put("wxUser", wxUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wx:wxuser:save")
    public R save(@RequestBody WxUserEntity wxUser){
		wxUserService.save(wxUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wx:wxuser:update")
    public R update(@RequestBody WxUserEntity wxUser){
		wxUserService.updateById(wxUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wx:wxuser:delete")
    public R delete(@RequestBody Integer[] userIds){
		wxUserService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
