package com.randomchat.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.randomchat.entity.UserEntity;

@Service
public interface UserDao  extends CrudRepository<UserEntity, String>{

}
