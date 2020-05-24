package io.renren.modules.wx.controller;

import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxConsts;
import com.soecode.wxtools.api.WxMessageRouter;
import com.soecode.wxtools.api.WxService;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutMessage;
import com.soecode.wxtools.util.xml.XStreamTransformer;
import io.renren.modules.wx.handler.SubscribeHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/wx")
public class WxBackController {
    private IService iService = new WxService();

    @GetMapping
    public String check(String signature, String timestamp, String nonce, String echostr) {
        if (iService.checkSignature(signature, timestamp, nonce, echostr)) {
            return echostr;
        }
        return null;
    }

    @PostMapping
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        // 创建一个路由器
        WxMessageRouter router = new WxMessageRouter(iService);
        try {
            // 微信服务器推送过来的是XML格式。
            WxXmlMessage wx = XStreamTransformer.fromXml(WxXmlMessage.class, request.getInputStream());
            System.out.println(wx.toString());
            router.rule().event(WxConsts.EVT_SUBSCRIBE).handler(SubscribeHandler.getInstance()).end();
            // 把消息传递给路由器进行处理
            WxXmlOutMessage xmlOutMsg = router.route(wx);
            if (xmlOutMsg != null){
                // 因为是明文，所以不用加密，直接返回给用户
                out.print(xmlOutMsg.toXml());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }

    }
}
