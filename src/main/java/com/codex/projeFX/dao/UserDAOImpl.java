package com.codex.projeFX.dao;

import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.codex.projeFX.entity.UserEntity;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

	private final Session session;

	@Autowired
	public UserDAOImpl(Session session) {
		this.session = session;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void createUser(UserEntity user) {
		session.save(user);
	}

	@Override
	public UserEntity getUserById(long id) {
		return session.get(UserEntity.class, id);
	}

	@Override
	public void updateUser(UserEntity user) {
		session.merge(user);
	}

	@Override
	public void deleteUser(UserEntity user) {
		session.remove(user);
	}

	@Override
	public void deleteAllUsers() {
		Query<Void> query = session.createQuery("DELETE FROM UserEntity");
		query.executeUpdate();
	}

	@Override
	public List<UserEntity> getAllUsers() {

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);

		Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
		criteriaQuery.select(root);
		Query<UserEntity> query = session.createQuery(criteriaQuery);
		return query.getResultList();
	}

	@Override
	public UserEntity findUserByName(String name) {
		String queryString = "FROM UserEntity WHERE username = :name";
		Query<UserEntity> query = session.createQuery(queryString, UserEntity.class);
		query.setParameter("name", name);

		return query.uniqueResult();
	}

	@Override
	public List<UserEntity> getUsersByRank(String rank) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<UserEntity> query = builder.createQuery(UserEntity.class);
		Root<UserEntity> root = query.from(UserEntity.class);

		query.select(root);
		query.where(builder.equal(root.get("rank"), rank));

		TypedQuery<UserEntity> typedQuery = session.createQuery(query);
		return typedQuery.getResultList();
	}
}