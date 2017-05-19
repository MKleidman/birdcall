package com.birdcall.user;

import com.birdcall.user.UserDao;
import com.birdcall.user.User;

public class UserBo {

	UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void save(User user){
		userDao.save(user);
	}

	public void update(User user){
		userDao.update(user);
	}

	public void delete(User user){
		userDao.delete(user);
	}

	public User findByUserEmail(String userEmail){
		return userDao.findByUserEmail(userEmail);
	}
}