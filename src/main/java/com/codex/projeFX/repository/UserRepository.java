package com.codex.projeFX.repository;

import java.util.List;

import com.codex.projeFX.entity.UserEntity;

public interface UserRepository {

	void createUser(UserEntity user);

	UserEntity getUserById(long id);
	
	UserEntity findUserByName(String name);

	void updateUser(UserEntity user);

	void deleteUser(UserEntity user);

	void deleteAllUsers();
	
	List<UserEntity> getUsersByRank(String rank);

	List<UserEntity> getAllUsers();
}