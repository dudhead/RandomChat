package com.randomchat.service;

import org.springframework.stereotype.Service;

@Service
public interface RandomChatService {

	boolean sendMessage(String message, String deviceRegId);
}
