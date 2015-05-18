package com.pea.wechat.eshop.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

/**
 * 请求校验工具类
 * 
 */
public class SignUtil {
	// 与开发模式接口配置信息中的Token保持一致
	public static final String WEIXIN_TOKEN = "936d8f0827567bab7e339991107c12ba";

	public static final String ESHOP_TOKEN = "936d8f0827567bab7e339991107c12ba";

	private static SecureRandom random = new SecureRandom();

	/**
	 * 校验签名
	 * 
	 * @param signature
	 *            微信加密签名
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机数
	 * @return
	 */
	public static boolean checkSignature(String signature, String token, String timestamp, String nonce) {
		// 对token、timestamp和nonce按字典排序
		String[] paramArr = new String[] { token, timestamp, nonce };
		Arrays.sort(paramArr);

		// 将排序后的结果拼接成一个字符串
		String content = StringUtils.join(paramArr, "");

		String ciphertext = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// 对接后的字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			ciphertext = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// 将sha1加密后的字符串与signature进行对比
		return ciphertext != null ? ciphertext.equals(signature.toUpperCase()) : false;
	}

	public static Map<String, String> generateSignature(String token) {
		String timestamp = String.valueOf((new Date()).getTime());
		String nonce = String.valueOf(getRandomInt());
		String[] paramArr = new String[] { token, timestamp, nonce };
		Arrays.sort(paramArr);
		// 将排序后的结果拼接成一个字符串
		String content = StringUtils.join(paramArr, "");

		String signature = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// 对接后的字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			signature = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 将sha1加密后的字符串与signature进行对比
		Map<String, String> result = Maps.newHashMap();
		result.put("signature", signature);
		result.put("timestamp", timestamp);
		result.put("nonce", nonce);
		return result;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (byte element : byteArray) {
			strDigest += byteToHexStr(element);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[mByte >>> 4 & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}

	public static int getRandomInt() {
		final int offset = 123456; // offset为固定值，避免被猜到种子来源（和密码学中的加salt有点类似）
		long seed = System.currentTimeMillis() + offset;
		SecureRandom secureRandom1;
		try {
			secureRandom1 = SecureRandom.getInstance("SHA1PRNG");

			secureRandom1.setSeed(seed);
			return secureRandom1.nextInt();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
