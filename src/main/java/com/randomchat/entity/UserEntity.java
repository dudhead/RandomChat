package com.randomchat.entity;

import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class User {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Field
	private String token;

	@Field
	private String deviceId;

	@Field
	private FacebookEntity facebookEntity;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FacebookEntity getFacebookEntity() {
		return facebookEntity;
	}

	public void setFacebookEntity(FacebookEntity facebookEntity) {
		this.facebookEntity = facebookEntity;
	}

}
