package com.pea.wechat.example.message.req;

/**
 * 图片消息
 * 
 */
public class ImageMessage extends BaseMessage {
	// 图片url
	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
}
