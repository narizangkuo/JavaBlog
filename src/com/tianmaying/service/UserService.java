package com.tianmaying.service;

import com.tianmaying.model.Blog;
import com.tianmaying.model.BlogAppException;
import com.tianmaying.model.BlogRepository;
import com.tianmaying.model.User;
import com.tianmaying.model.UserRepository;

public class UserService {
	// 判断用户是否存在
	public boolean exist(String username) {
		return UserRepository.getByUsername(username) != null;
	}
	// 返回登录用户
	public User login(String username, String password) {
		User user = UserRepository.getByUsername(username);
		if(user != null && user.getPassword().equals(password)) {
			return user;
		}
		return null;
	}
	// 用户注册
	public User register(User user) throws BlogAppException {
		UserRepository.add(user);
		BlogRepository
				.add(new Blog(
						"这是你在天码营的第一篇博客",
						"天码营秉承让技术学习更加高效和便捷的使命，致力于打造新一代的技术学习服务平台，提供创新并且专业的内容、工具与服务，帮助学习者与从业者实现个人价值",
						user
						)
				);
		return user;
	}
}
