package com.randomchat.entity;

import java.util.List;

import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class UserEntity {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Field
	private List<String> deviceIds;

	@Field
	private FacebookEntity facebookEntity;

	@Field
	private List<ChatThreads> chatThreads;

	@Field
	private DateTime createdDateTime;

	@Field
	private DateTime updatedDateTime;

	@Field
	private DateTime deletedDateTime;

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

	public DateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(DateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public DateTime getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(DateTime updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public DateTime getDeletedDateTime() {
		return deletedDateTime;
	}

	public void setDeletedDateTime(DateTime deletedDateTime) {
		this.deletedDateTime = deletedDateTime;
	}

	public List<ChatThreads> getChatThreads() {
		return chatThreads;
	}

	public void setChatThreads(List<ChatThreads> chatThreads) {
		this.chatThreads = chatThreads;
	}

	public List<String> getDeviceIds() {
		return deviceIds;
	}

	public void setDeviceIds(List<String> deviceIds) {
		this.deviceIds = deviceIds;
	}

}
