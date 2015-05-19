package com.pea.wechat.example.menu;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class OAuth2Test {
	private static String oauth2Url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

	private static String appid = "wx91d907fab52860b0";
	private static String redirect_uri = "http://wechat-shop.aliapp.com/user/bind";

	public void testGetCode() throws UnsupportedEncodingException {
		redirect_uri = URLEncoder.encode(redirect_uri, "utf-8");

		String requestUrl = oauth2Url.replace("APPID", appid).replace("REDIRECT_URI", redirect_uri);

	}

	public static void main(String[] args) {
		try {
			System.out.println(URLEncoder.encode(OAuth2Test.redirect_uri, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
