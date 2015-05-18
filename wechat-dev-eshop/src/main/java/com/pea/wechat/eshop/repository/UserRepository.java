package com.pea.wechat.eshop.repository;

import com.pea.wechat.eshop.entity.User;

/**
 */
public interface UserRepository extends BaseRepository<User, Long> {
	User findByUserNameAndPassword(String userName, String password);

}
