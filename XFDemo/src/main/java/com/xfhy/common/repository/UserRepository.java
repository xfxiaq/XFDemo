package com.xfhy.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.xfhy.common.model.User;

public interface UserRepository    extends JpaRepository<User,String>, QueryDslPredicateExecutor<User>{

	List<User> findByUserIdAndPassword(String userId,String password);

	User findByUserId(String userId);
	
}
