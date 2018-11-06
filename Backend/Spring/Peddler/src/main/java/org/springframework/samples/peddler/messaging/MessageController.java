package org.springframework.samples.peddler.messaging;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



import org.springframework.samples.peddler.messaging.Messages;
import org.springframework.samples.peddler.projects.Projects;
import org.springframework.samples.peddler.messaging.MessageRepository;

@Controller
@RequestMapping(path = "/message")
public class MessageController {

	@Autowired
	private MessageRepository messageRepo;
	
	@GetMapping(path = "/add")
	public @ResponseBody String addNewMessage(@RequestParam String message) {
		Messages messages = new Messages();
		messages.setRecipientId(Integer.parseInt(message.substring(0, 5))); //still have to check for invalidId exception.
		messages.setCreatorId(Integer.parseInt(message.substring(6, 11))); //still have to check for invalidId exception.
		messages.setType(Integer.parseInt(message.substring(12, 12)));
		messages.setActualMessage(message.substring(13));
		messages.setDate(new Timestamp(System.currentTimeMillis()));
		messageRepo.save(messages);
		return "Saved";
	}
	
	@GetMapping(path = "/getBySender")
	public @ResponseBody ArrayList<String> getAllBySender(@RequestParam int creatorId){
		ArrayList<String> sentMessages = new ArrayList<String>();
		Iterable<Messages> messagePacket = messageRepo.findAllByCreatorId(creatorId);
		
		for(Messages s: messagePacket) {
			sentMessages.add(s.getActualMessage());
		}
		
		return sentMessages;
	}
	
	@GetMapping(path = "/getByReceiver")
	public @ResponseBody ArrayList<String> getAllByReceiver(@RequestParam int recipientId){
		ArrayList<String> receivedMessages = new ArrayList<String>();
		Iterable<Messages> messagePacket = messageRepo.findAllByRecipientId(recipientId);
		
		for(Messages s: messagePacket) {
			receivedMessages.add(s.getActualMessage());
		}
		return receivedMessages;
	}
	
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Messages> getAllMessages() {
		return messageRepo.findAll();
	}
	
	
	
}
