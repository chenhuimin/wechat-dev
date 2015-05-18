package com.pea.wechat.example.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pea.wechat.example.message.resp.TextMessage;
import com.pea.wechat.example.util.MessageUtil;
import com.pea.wechat.example.util.SignUtil;

/**
 * 核心服务类
 * 
 */
public class CoreService {
	private static final Logger logger = LoggerFactory.getLogger(CoreService.class);

	private static final String baseBindUrl = "http://wechat-shop.aliapp.com/user/bind?";

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		// xml格式的消息数据
		String respXml = null;
		// 调用parseXml方法解析请求消息
		Map<String, String> requestMap = MessageUtil.parseXml(request);
		// 发送方帐号
		String fromUserName = requestMap.get("FromUserName");
		// 开发者微信号
		String toUserName = requestMap.get("ToUserName");
		// 消息类型
		String msgType = requestMap.get("MsgType");
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		// 事件推送
		if (StringUtils.isNotBlank(msgType) && msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
			// 事件类型
			String eventType = requestMap.get("Event");
			if (StringUtils.isNotBlank(eventType) && eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
				textMessage.setContent("您好，欢迎关注太保E购商城。精彩生活，从这里开始！");
				respXml = MessageUtil.messageToXml(textMessage);
			}
			// 取消订阅
			else if (StringUtils.isNotBlank(eventType) && eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
				logger.info("取消订阅");
			}
			// 自定义菜单点击事件
			else if (StringUtils.isNotBlank(eventType) && eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
				// 事件KEY值，与创建菜单时的key值对应
				String eventKey = requestMap.get("EventKey");
				// 根据key值判断用户点击的按钮
				if (StringUtils.isNotBlank(eventKey) && eventKey.equals("bind_user")) {
					Map<String, String> signatureMap = SignUtil.generateSignature(SignUtil.ESHOP_TOKEN);
					String bindUrl = baseBindUrl + "signature=" + signatureMap.get("signature") + "&timestamp="
							+ signatureMap.get("timestamp") + "&nonce=" + signatureMap.get("nonce") + "&OpenID=" + fromUserName;
					textMessage.setContent("哎呦呦，客观，您还没有绑定账户哦！<a href='" + bindUrl + "'>点击绑定<a>，就可以购买了");
					respXml = MessageUtil.messageToXml(textMessage);
				}
			}
		}
		// 当用户发消息时
		else {
			textMessage.setContent("请通过菜单使用网址导航服务！");
			respXml = MessageUtil.messageToXml(textMessage);
		}
		return respXml;
	}
}
