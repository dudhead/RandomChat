package com.randomchat.service.impl;

import java.io.IOException;

import org.jivesoftware.smack.SmackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.randomchat.dao.UserDao;
import com.randomchat.entity.FacebookEntity;
import com.randomchat.entity.User;
import com.randomchat.model.UserRegisterRequest;
import com.randomchat.service.RandomChatService;
import com.randomchat.xmpp.XmppClient;

@Component
public class RandomChatServiceImpl implements RandomChatService {

	@Value("${google.server.key}")
	private String googleServerApiKey;

	@Value("${google.project.id}")
	private String googleProjectId;

	private Logger logger = LoggerFactory
			.getLogger(RandomChatServiceImpl.class);

	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean sendMessage(String message, String deviceRegId) {
		logger.info("Sending message {} to {}", message, deviceRegId);
		try {
			XmppClient.sendMessage(googleProjectId + "@gcm.googleapis.com",
					googleServerApiKey, deviceRegId, message);
			return true;
		} catch (SmackException | IOException e) {
			logger.error("Error:", e);
		}
		return false;
	}

	@Override
	public User registerUser(UserRegisterRequest requestModel) {
		FacebookEntity facebookEntity = new FacebookEntity();
		facebookEntity.setEmail("lll@msdf.com");
		facebookEntity.setFirstName("Karthik");

		User user = new User();
		user.setFacebookEntity(facebookEntity);
		user.setDeviceId("DEVICE KARTHIK MOTO G");

		userDao.save(user);
		
		return null;
	}
}
