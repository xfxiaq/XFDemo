package com.xfhy.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.xfhy.common.model.Role;


public interface RoleRepository  extends JpaRepository<Role,String>, QueryDslPredicateExecutor<Role>{

}
