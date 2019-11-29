package com.example.sqs.service;

import static com.example.sqs.queues.Queues.QUEUE_CUSTOMER;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.example.sqs.configuration.AWSConfiguration;
import com.example.sqs.domain.MessageRequest;
import com.example.sqs.response.MessageResponse;

@Service
public class MessageService {
	
	public MessageResponse postMessage(final MessageRequest messageRequest) {
		
			SendMessageRequest request = new SendMessageRequest()
					.withQueueUrl(getQueueURL(QUEUE_CUSTOMER))
					.withMessageBody(messageRequest.getBody())
					.withDelaySeconds(5); 
			
			SendMessageResult response = AWSConfiguration.loadConfiguration().sendMessage(request);
			
			return MessageResponse.builder()
					.id(response.getMessageId()).build();
		}
	
	
	public List<String> deleteMessageFromQueue(final MessageRequest messageRequest) {
		
		List<String> deletedMessages = new ArrayList<>(); 

		final AmazonSQS sqs = AWSConfiguration.loadConfiguration();

		List<Message> messages = AWSConfiguration.loadConfiguration()
				.receiveMessage(getQueueURL(messageRequest.getQueue())).getMessages();
		
		messages.forEach(m -> {
			sqs.deleteMessage(getQueueURL(messageRequest.getQueue()), m.getReceiptHandle());
			deletedMessages.add(m.getMessageId());
		});
		
		return deletedMessages;
	}
	
	public List<MessageResponse> consumerMessages() {
		
		final List<MessageResponse> messagesDto = new ArrayList<>();
		
			// consume messages from the queue
			final List<Message> messages = AWSConfiguration.loadConfiguration().receiveMessage(getQueueURL(QUEUE_CUSTOMER)).getMessages();
			
			messages.forEach(me ->{
				
				MessageResponse message = MessageResponse.builder()
						.id(me.getMessageId())
						.body(me.getBody()).build();
				
				messagesDto.add(message);
				
			});
		return messagesDto;
	}
	
	private String getQueueURL(final String queue) {
		return AWSConfiguration.loadConfiguration().getQueueUrl(queue).getQueueUrl();
	}
}