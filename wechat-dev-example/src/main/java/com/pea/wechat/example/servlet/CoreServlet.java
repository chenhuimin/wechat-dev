package com.pea.wechat.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.pea.wechat.example.service.CoreService;
import com.pea.wechat.example.util.SignUtil;

public class CoreServlet extends HttpServlet {

	private static final long serialVersionUID = 2727457192436021855L;

	/**
	 * 请求校验（确认请求来自微信服务器）
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = null;
		try {
			out = response.getWriter();
			// 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				out.print(echostr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 处理微信服务器发来的消息
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		// 调用核心业务类接收消息、处理消息
		String respXml = CoreService.processRequest(request);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			// 响应消息
			if (StringUtils.isNotBlank(respXml)) {
				out.print(respXml);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}