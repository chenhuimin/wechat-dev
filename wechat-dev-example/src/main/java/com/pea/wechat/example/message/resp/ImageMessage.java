package com.pea.wechat.example.message.resp;

/**
 * 图片消息
 * 
 */
public class ImageMessage extends BaseMessage {
	// 图片model
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}
}
