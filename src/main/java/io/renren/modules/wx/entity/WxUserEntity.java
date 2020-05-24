package io.renren.modules.wx.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-24 13:42:22
 */
@Data
@TableName("wx_user")
@Builder
public class WxUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId
	private Integer userId;
	/**
	 * 公众号ID
	 */
	private Integer accountId;
	/**
	 * 微信用户openId
	 */
	private String openId;
	/**
	 * 微信用户unionId
	 */
	private String unionId;
	/**
	 * 用户昵称
	 */
	private String userNick;
	/**
	 * 用户头像
	 */
	private String userAvatar;
	/**
	 * 用户性别（1:男 2:女）
	 */
	private Integer userSex;
	/**
	 * 是否关注公众号（0:否 1:是）
	 */
	private Integer isSubscribe;
	/**
	 * 关注时间
	 */
	private Date subscribeTime;
	/**
	 * 取消关注时间
	 */
	private Date unsubscribeTime;

}
