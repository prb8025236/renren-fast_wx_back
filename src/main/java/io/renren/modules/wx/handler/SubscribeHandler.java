package io.renren.modules.wx.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxMessageHandler;
import com.soecode.wxtools.bean.WxUserList;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutImageMessage;
import com.soecode.wxtools.bean.WxXmlOutMessage;
import com.soecode.wxtools.exception.WxErrorException;
import io.renren.modules.wx.dao.WxUserDao;
import io.renren.modules.wx.entity.WxUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Component
public class SubscribeHandler implements WxMessageHandler {
    private static SubscribeHandler subscribeHandler;
    @Autowired
    private WxUserDao wxUserDao;

    public static SubscribeHandler getInstance() {
        return new SubscribeHandler();
    }

    @PostConstruct
    public void init() {
        subscribeHandler = this;
        subscribeHandler.wxUserDao = this.wxUserDao;
    }

    @Override
    public WxXmlOutMessage handle(WxXmlMessage wxXmlMessage, Map<String, Object> map, IService iService) throws WxErrorException {
        System.out.println(wxXmlMessage.toString());
        String openid = wxXmlMessage.getFromUserName();
        WxUserList.WxUser.WxUserGet wxUserGet = new WxUserList.WxUser.WxUserGet();
        wxUserGet.setOpenid(openid);
        wxUserGet.setLang("zh_CN");
        WxUserList.WxUser wxUser = iService.getUserInfoByOpenId(wxUserGet);
        WxUserEntity wxUserEntity = subscribeHandler.wxUserDao.selectOne(new QueryWrapper<WxUserEntity>().eq("open_id", openid));
        if ("unsubscribe".equals(wxXmlMessage.getEvent())) {//取消订阅
            wxUserEntity.setUnsubscribeTime(new Date());
            wxUserEntity.setIsSubscribe(0);
            subscribeHandler.wxUserDao.updateById(wxUserEntity);
        } else {//订阅
            if (wxUserEntity != null) {//不为空则修改订阅状态、订阅时间
                wxUserEntity.setSubscribeTime(new Date());
                wxUserEntity.setIsSubscribe(1);
                subscribeHandler.wxUserDao.updateById(wxUserEntity);
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
                subscribeHandler.wxUserDao.insert(wxUserEntityNew);
            }
        }
        return null;
    }
}
