package com.codex.projeFX.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.codex.projeFX.dao.UserDAO;
import com.codex.projeFX.repository.UserRepositoryImpl;
import com.codex.projeFX.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryTest {

	@InjectMocks
	private UserRepositoryImpl userRepos;

	@Mock
	private UserDAO userDAO;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	// ----------------USERREPOSITORY-TESTS
	@Test
	public void test_CreateUser() {
		UserEntity user = new UserEntity();
		user.setUsername("JTest Username");

		userRepos.createUser(user);

		Mockito.verify(userDAO).createUser(user);
	}

	@Test
	public void test_getUserById() {
		long userId = 1;
		UserEntity expectedUser = new UserEntity();

		expectedUser.setUserId(userId);
		expectedUser.setUsername("JTest Username");
		expectedUser.setPassword("JTest Password");
		expectedUser.setRole("JTest Rank");

		Mockito.when(userDAO.getUserById(userId)).thenReturn(expectedUser);

		UserEntity actualUser = userRepos.getUserById(userId);

		Assertions.assertEquals(expectedUser, actualUser);
	}

	@Test
	public void test_updateUser() {
		UserEntity user = new UserEntity();
		user.setUserId(1L);
		user.setUsername("JTest Username");
		user.setPassword("JTest Password");
		user.setRole("JTest Rank");

		Mockito.doNothing().when(userDAO).updateUser(user);

		userRepos.updateUser(user);

		Mockito.verify(userDAO).updateUser(user);		
	}

	@Test
	public void test_deleteUser() {
		UserEntity user = new UserEntity();
		user.setUserId(1L);
		user.setUsername("JTest Username");
		user.setPassword("JTest Password");
		user.setRole("JTest Rank");

		Mockito.doNothing().when(userDAO).deleteUser(user);

		userRepos.deleteUser(user);

		Mockito.verify(userDAO).deleteUser(user);
	}

	@Test
	public void test_getAllUsers() {
		UserEntity user1 = new UserEntity();
		UserEntity user2 = new UserEntity();
		UserEntity user3 = new UserEntity();
		UserEntity user4 = new UserEntity();

		user1.setUserId(0L);
		user1.setUsername("JTest User1");
		user1.setPassword("JTest Password1");
		user1.setRole("JTest Rank1");

		user2.setUserId(1L);
		user2.setUsername("JTest User2");
		user2.setPassword("JTest Password2");
		user2.setRole("JTest Rank2");

		user3.setUserId(2L);
		user3.setUsername("JTest User3");
		user3.setPassword("JTest Password3");
		user3.setRole("JTest Rank3");

		user4.setUserId(3L);
		user4.setUsername("JTest Book4");
		user4.setPassword("JTest Password4");
		user4.setRole("JTest Rank4");

		List<UserEntity> expectedUsers = new ArrayList<>();

		expectedUsers.add(user1);
		expectedUsers.add(user2);
		expectedUsers.add(user3);
		expectedUsers.add(user4);

		Mockito.when(userDAO.getAllUsers()).thenReturn(expectedUsers);

		List<UserEntity> result = userRepos.getAllUsers();

		Mockito.verify(userDAO).getAllUsers();

		Assertions.assertEquals(expectedUsers, result);
	}

	@Test
	public void test_getAllBooksByRank() {

		UserEntity user1 = new UserEntity();
		UserEntity user2 = new UserEntity();
		UserEntity user3 = new UserEntity();
		UserEntity user4 = new UserEntity();

		user1.setUserId(0L);
		user1.setUsername("JTest Username1");
		user1.setPassword("JTest Password1");
		user1.setRole("JTest Rank1");

		user2.setUserId(1L);
		user2.setUsername("JTest Username2");
		user2.setPassword("JTest Password2");
		user2.setRole("JTest Rank2");

		user3.setUserId(2L);
		user3.setUsername("JTest Username3");
		user3.setPassword("JTest Password3");
		user3.setRole("JTest Rank3");

		user4.setUserId(3L);
		user4.setUsername("JTest Username4");
		user4.setPassword("JTest Password4");
		user4.setRole("JTest Rank4");			

		List<UserEntity> expectedUsers = new ArrayList<>();

		expectedUsers.add(user1);
		expectedUsers.add(user2);
		expectedUsers.add(user3);
		expectedUsers.add(user4);

		String rank = "JTest Rank3";
		Mockito.when(userDAO.getUsersByRank(rank)).thenReturn(expectedUsers);

		List<UserEntity> result = userRepos.getUsersByRank(rank);

		Mockito.verify(userDAO).getUsersByRank(rank);

		Assertions.assertEquals(expectedUsers, result);
	}
}
