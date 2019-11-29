package com.example.sqs.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MessageResponse implements Serializable{

	private static final long serialVersionUID = 4419842306425385971L;
	
	@ApiModelProperty(value = "Message id", notes = "Id of the Message", example = "11234")
	private String id;
	
	@ApiModelProperty(value = "Message", notes = "Body of the Message")
	private String body;
}