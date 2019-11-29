package com.example.sqs.resource;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.ACCEPTED;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.sqs.domain.MessageRequest;
import com.example.sqs.response.MessageResponse;
import com.example.sqs.service.MessageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "/messages/", tags = { "Messages" })
@ApiOperation(value = "messages", notes = "API Message", response = MessagesResource.class)
@ApiResponses({
        @ApiResponse(code = 200, message = ""),
        @ApiResponse(code = 401, message = ""),
        @ApiResponse(code = 403, message = ""),
        @ApiResponse(code = 404, message = ""),
        @ApiResponse(code = 500, message = "") })
@RestController
@RequestMapping("/api")
public class MessagesResource {
	
	@Autowired
	private MessageService messageService;
	
	@ResponseStatus(OK)
    @ApiOperation(value = "Post Messages", notes = "Post messages in the queue.")
    @PostMapping("/messages")
    public ResponseEntity<MessageResponse> post(@Valid @RequestBody final MessageRequest message) {
        return ResponseEntity.ok(messageService.postMessage(message));
    }
	
	@ResponseStatus(OK)
    @ApiOperation(value = "Consume Messages", notes = "Consume messages of the Queue.")
    @GetMapping("/messages")
    public ResponseEntity<List<MessageResponse>> consumer() {
        return ResponseEntity.ok(messageService.consumerMessages());
    }
	
	@ResponseStatus(ACCEPTED)
    @ApiOperation(value = "Delete Messages", notes = "Delete message of the Queue.")
    @DeleteMapping("/messages")
    public ResponseEntity<List<String>> delete(@Valid final MessageRequest message) {
		
		List<String> messages = this.messageService.deleteMessageFromQueue(message);
		
		if(!CollectionUtils.isEmpty(messages)) {
			return ResponseEntity.accepted().build();
		}
		return ResponseEntity.noContent().build();
    }
}