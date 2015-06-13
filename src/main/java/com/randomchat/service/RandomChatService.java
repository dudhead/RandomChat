package com.randomchat.service;

import org.springframework.stereotype.Service;

import com.randomchat.entity.UserEntity;
import com.randomchat.model.User;

@Service
public interface RandomChatService {

	boolean sendMessage(String message, String deviceRegId);

	User registerUser(User requestModel);
}
