package com.randomchat.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.randomchat.entity.User;

@Service
public interface UserDao  extends CrudRepository<User, String>{

}
