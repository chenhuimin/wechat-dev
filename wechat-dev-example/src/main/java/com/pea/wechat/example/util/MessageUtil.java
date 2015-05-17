package com.pea.wechat.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.pea.wechat.example.message.resp.Article;
import com.pea.wechat.example.message.resp.NewsMessage;
import com.pea.wechat.example.message.resp.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageUtil {
	private static final Logger logger = LoggerFactory.getLogger(MessageUtil.class);
	// 请求消息类型：文本
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	// 请求消息类型：图片
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	// 请求消息类型：语音
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	// 请求消息类型：视频
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	// 请求消息类型：地理位置
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	// 请求消息类型：链接
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	// 请求消息类型：事件推送
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	// 事件类型：subscribe(订阅)
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	// 事件类型：unsubscribe(取消订阅)
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	// 事件类型：scan(用户已关注时的扫描带参数二维码)
	public static final String EVENT_TYPE_SCAN = "scan";
	// 事件类型：LOCATION(上报地理位置)
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	// 事件类型：CLICK(自定义菜单)
	public static final String EVENT_TYPE_CLICK = "CLICK";

	// 响应消息类型：文本
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	// 响应消息类型：图片
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
	// 响应消息类型：语音
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
	// 响应消息类型：视频
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
	// 响应消息类型：音乐
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	// 响应消息类型：图文
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 扩展xstream使其支持CDATA
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		@Override
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@Override
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				@Override
				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}

				}
			};
		}
	});

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> parseXml(HttpServletRequest request) {
		Map<String, String> map = Maps.newHashMap();
		InputStream inputStream = null;
		try {
			inputStream = request.getInputStream();
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			Element root = document.getRootElement();
			List<Element> elements = root.elements();
			for (Element e : elements) {
				map.put(e.getName(), e.getText());
			}

		} catch (IOException e) {
			logger.error("读取request输入流错误", e);
		} catch (DocumentException e) {
			logger.error("DOM4J解析request输入流错误", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage
	 * @return
	 */

	public static String messageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage
	 * @return
	 */

	public static String messageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}
}
