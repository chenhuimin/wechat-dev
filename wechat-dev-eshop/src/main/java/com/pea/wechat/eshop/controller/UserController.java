package com.pea.wechat.eshop.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pea.wechat.eshop.entity.User;
import com.pea.wechat.eshop.service.UserService;
import com.pea.wechat.eshop.util.SignUtil;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listUser(Model model) {
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		return "user/list";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerForm() {
		return "user/register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(User user) {
		userService.registerUser(user);
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/bind", method = RequestMethod.GET)
	public String bindForm(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("openID") String openID, Model model, HttpSession session) {
		boolean checkSignature = SignUtil.checkSignature(signature, SignUtil.ESHOP_TOKEN, timestamp, nonce);
		if (checkSignature) {
			// 判断是否失效
			long sendTime = Long.valueOf(timestamp);
			long effectiveTime = 10 * 60 * 1000;
			long currentTime = (new Date()).getTime();
			if (currentTime <= sendTime + effectiveTime) {
				if (StringUtils.isNotBlank(openID)) {
					session.setAttribute("openID", openID);
					return "user/bind";
				} else {
					String errorMsg = "openID为空";
					model.addAttribute("errorMsg", errorMsg);
					return "user/error";
				}

			} else {
				String errorMsg = "本次请求失效，请重新访问";
				model.addAttribute("errorMsg", errorMsg);
				return "user/error";
			}

		} else {
			String errorMsg = "签名验证错误";
			model.addAttribute("errorMsg", errorMsg);
			return "user/error";
		}
	}

	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	public String bind(User user, Model model, HttpSession session) {
		String openID = (String) session.getAttribute("openID");
		user.setOpenID(openID);
		User bindUser = userService.bindUser(user);
		session.removeAttribute("openID");
		if (bindUser != null) {
			return "redirect:/user/list";
		} else {
			String errorMsg = "绑定错误";
			model.addAttribute("errorMsg", errorMsg);
			return "user/error";
		}
	}
}