package com.randomchat.service.impl;

import java.io.IOException;

import org.dozer.DozerBeanMapper;
import org.jivesoftware.smack.SmackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.randomchat.dao.UserDao;
import com.randomchat.entity.UserEntity;
import com.randomchat.model.User;
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

	@Autowired
	private DozerBeanMapper mapper;

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
	public User registerUser(User requestModel) {
		logger.info("Registering user {} ", requestModel.getFacebook()
				.getFirstName());
		UserEntity userEntity = mapper.map(requestModel, UserEntity.class);

		userEntity = userDao.save(userEntity);

		return mapper.map(userEntity, User.class);
	}

}
