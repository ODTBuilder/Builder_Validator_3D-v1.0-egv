package com.gitrnd.qaproducer.user.service;

import java.util.Map;


import com.gitrnd.qaproducer.user.domain.User;

public interface UserService {
	public User loginUserByInfo(Map<String, Object> infoMap);
	
	public User retrieveUserById(String uid);

	public User retrieveUserByIdx(int idx);

	public void createUser(User user);

	public User checkUserById(User user);

	public User checkUserByEmail(User user);

	public User checkDuplicatedById(String uid);

	public User checkDuplicatedByEmail(String email);

	public boolean setActiveUserById(User user);
}
