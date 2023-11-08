package com.codex.projeFX.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.codex.projeFX.dao.UserDAO;
import com.codex.projeFX.entity.UserEntity;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

	private UserDAO userDAO;

	@Autowired
	public UserRepositoryImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public void createUser(UserEntity user) {
		userDAO.createUser(user);
	}

	@Override
	public UserEntity getUserById(long id) {
		return userDAO.getUserById(id);
	}
	
	@Override
	public UserEntity findUserByName(String name) {
		return userDAO.findUserByName(name);
	}

	@Override
	public void updateUser(UserEntity user) {
		userDAO.updateUser(user);
	}

	@Override
	public void deleteUser(UserEntity user) {
		userDAO.deleteUser(user);
	}

	@Override
	public void deleteAllUsers() {
		userDAO.deleteAllUsers();
	}

	@Override
	public List<UserEntity> getAllUsers() {
		return userDAO.getAllUsers();
	}
	
	@Override
	public List<UserEntity> getUsersByRank(String rank) {
		return userDAO.getUsersByRank(rank);
	}
}