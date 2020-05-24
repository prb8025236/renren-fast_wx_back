package io.renren.modules.wx.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxMessageHandler;
import com.soecode.wxtools.bean.WxUserList;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutMessage;
import com.soecode.wxtools.exception.WxErrorException;
import io.renren.modules.wx.entity.WxUserEntity;
import io.renren.modules.wx.service.WxUserService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
@Configuration
public class SubscribeHandler implements WxMessageHandler {
    @Resource
    private WxUserService wxUserService;

    public static SubscribeHandler getInstance() {
        return new SubscribeHandler();
    }

    @Override
    public WxXmlOutMessage handle(WxXmlMessage wxXmlMessage, Map<String, Object> map, IService iService) throws WxErrorException {
        String openid = wxXmlMessage.getFromUserName();
        WxUserList.WxUser.WxUserGet wxUserGet = new WxUserList.WxUser.WxUserGet();
        wxUserGet.setOpenid(openid);
        wxUserGet.setLang("zh_CN");
        System.out.println(iService.getAccessToken());
        WxUserList.WxUser wxUser = iService.getUserInfoByOpenId(wxUserGet);
        WxUserEntity wxUserEntity = wxUserService.getOne(new QueryWrapper<WxUserEntity>().eq("open_id", openid));
        if (wxUserEntity != null) {
            //todo 不为空则修改状态
        } else {
            WxUserEntity wxUserEntityNew = WxUserEntity.builder()
                    .accountId(1)
                    .openId(openid)
                    .userAvatar(wxUser.getHeadimgurl())
                    .userNick(wxUser.getNickname())
                    .userSex(wxUser.getSex())
                    .isSubscribe(1)
                    .subscribeTime(new Date())
                    .unionId(wxUser.getUnionid())
                    .build();
            wxUserService.save(wxUserEntityNew);
        }

        try {
            System.out.println(wxUser.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
