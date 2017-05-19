package com.birdcall.userbird;

import com.birdcall.userbird.UserBirdDao;

import java.util.List;

import com.birdcall.userbird.UserBird;

public class UserBirdBo {

	UserBirdDao userBirdDao;

	public void setUserBirdDao(UserBirdDao userBirdDao) {
		this.userBirdDao = userBirdDao;
	}

	public void save(UserBird userBird){
		userBirdDao.save(userBird);
	}

	public void update(UserBird userBird){
		userBirdDao.update(userBird);
	}

	public void delete(UserBird userBird){
		userBirdDao.delete(userBird);
	}
	
	public List<UserBird> getBirdsForUser(Long userId){ 
		return userBirdDao.getBirdsForUser(userId);
	}

}