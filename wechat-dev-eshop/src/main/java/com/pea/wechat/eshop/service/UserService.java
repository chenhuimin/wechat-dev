package com.pea.wechat.eshop.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pea.wechat.eshop.entity.User;
import com.pea.wechat.eshop.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserService {
	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	protected UserRepository userRepository;

	@Transactional
	public User registerUser(User user) {
		if (user != null) {
			return userRepository.save(user);
		} else {
			return null;
		}
	}

	@Transactional
	public User bindUser(User user) {
		if (user != null) {
			User validUser = checkUser(user);
			if (validUser != null) {
				if (!StringUtils.isNotBlank(validUser.getOpenID())) {
					validUser.setOpenID(user.getOpenID());
					return userRepository.save(validUser);
				} else {
					return null;
				}
			} else {
				return null;
			}

		} else {
			return null;
		}
	}

	private User checkUser(User user) {
		if (user != null) {
			return userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword());

		} else {
			return null;
		}
	}

	public List<User> findAll() {
		return userRepository.findAll();

	}

}
