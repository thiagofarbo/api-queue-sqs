package com.example.sqs.configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

public class AWSConfiguration {
	
	public static AmazonSQS loadConfiguration() {
		
		AWSCredentialsProvider provider = new DefaultAWSCredentialsProviderChain();
		
		return AmazonSQSClientBuilder.standard()
				 .withRegion(Regions.US_EAST_1)
				 .withCredentials(provider)
				 .build();
	}
}