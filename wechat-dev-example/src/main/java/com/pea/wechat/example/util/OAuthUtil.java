package com.pea.wechat.example.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.pea.wechat.example.constant.WeiXinApiUrl;

public class OAuthUtil {
	public static String getOauth2CodeUrl(String appId, String redirectUrl, String scope) {
		String OAuth2CodeBaseUrl = WeiXinApiUrl.OAUTH2_AUTHORIZE_CODE;
		try {
			String encodedUrl = URLEncoder.encode(redirectUrl, "utf-8");
			String OAuth2CodeApiUrl = OAuth2CodeBaseUrl.replace("APPID", appId).replace("REDIRECT_URI", encodedUrl)
					.replace("SCOPE", scope);
			return OAuth2CodeApiUrl;

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

	}

}
