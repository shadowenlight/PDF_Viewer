package com.codex.projeFX.test;

import com.codex.projeFX.entity.UserEntity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserEntityTest {

	@Test
	public void test_userId() {
		UserEntity userEntity = new UserEntity();

		userEntity.setUserId(1L);

		Long expectedId = 1L;
		Long actualId = userEntity.getUserId();

		Assertions.assertEquals(expectedId, actualId);
	}

	@Test
	public void test_Username() {
		UserEntity userEntity = new UserEntity();

		userEntity.setUsername("JTest User");

		String expectedUsername = "JTest User";
		String actualName = userEntity.getUserName();

		Assertions.assertEquals(expectedUsername, actualName);
	}
	
	@Test
	public void test_Password() {
		UserEntity userEntity = new UserEntity();

		userEntity.setPassword("JTest Password");

		String expectedPassword = "JTest Password";
		String actualPassword = userEntity.getPasssword();

		Assertions.assertEquals(expectedPassword, actualPassword);
	}
	
	@Test
	public void test_Rank() {
		UserEntity userEntity = new UserEntity();

		userEntity.setRole("JTest Rank");

		String expectedRank = "JTest Rank";
		String actualRank = userEntity.getRole();

		Assertions.assertEquals(expectedRank, actualRank);
	}
}
