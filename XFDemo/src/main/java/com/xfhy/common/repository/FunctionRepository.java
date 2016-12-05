package com.xfhy.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.xfhy.common.model.Function;


public interface FunctionRepository  extends JpaRepository<Function,String>, QueryDslPredicateExecutor<Function>{


}
