package com.example.sqs.domain;

import java.io.Serializable;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest implements Serializable {
	
	private static final long serialVersionUID = 7684264627208425528L;
	
	@Nullable
	private String queue;
	
	private String body;
}