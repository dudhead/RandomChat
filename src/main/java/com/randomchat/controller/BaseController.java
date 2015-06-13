package com.randomchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.randomchat.dao.UserDao;
import com.randomchat.entity.FacebookEntity;
import com.randomchat.entity.User;
import com.randomchat.model.UserRegisterRequest;
import com.randomchat.service.RandomChatService;

@RestController
@RequestMapping(value = "/api/")
public class BaseController {

	@Autowired
	private RandomChatService randomChatService;

	@RequestMapping(value = "/login/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<User> registerUser(
			@RequestBody UserRegisterRequest requestModel) {
		randomChatService.registerUser(requestModel);

		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Boolean> sendMessage(@RequestBody String message) {
		return new ResponseEntity<Boolean>(
				randomChatService
						.sendMessage(
								message,
								"ff-JvM06DSI:APA91bFP-AYTbTNRSOiE-qKmY7g6Q-ZiKc9R8rwy0iSjvSfnKJjHrXDag_6S6svS_GJPrIQ5gVwhQ7yy5MRCtzbKVGDQZ-m7i_1-tDypodfPTJDYg2Qa6SAScrTCs5bmTAujsZ7QRl-"),
				HttpStatus.OK);
	}
}
