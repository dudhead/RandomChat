package com.randomchat.entity;

import java.util.List;

import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class ChatThreads {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Field
	List<ChatMessage> chatMessages;
	
	@Field
	private DateTime createdDateTime;

	@Field
	private DateTime updatedDateTime;

	@Field
	private DateTime deletedDateTime;
}
