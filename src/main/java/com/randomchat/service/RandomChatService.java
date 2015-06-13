package com.randomchat.service;

import org.springframework.stereotype.Service;

import com.randomchat.entity.User;
import com.randomchat.model.UserRegisterRequest;

@Service
public interface RandomChatService {

	boolean sendMessage(String message, String deviceRegId);

	User registerUser(UserRegisterRequest requestModel);
}
