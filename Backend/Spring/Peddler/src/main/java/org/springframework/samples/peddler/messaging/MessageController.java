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
import org.springframework.samples.peddler.messaging.MessageRepository;

@Controller
@RequestMapping(path = "/message")
public class MessageController {

	@Autowired
	private MessageRepository messageRepo;
	
	@GetMapping(path = "/add")
	public @ResponseBody String addNewMessage(@RequestParam int creatorId, @RequestParam int recipientId, @RequestParam String actualMessage, @RequestParam String date) {
		Messages message = new Messages();
		message.setActualMessage(actualMessage);
		message.setCreatorId(creatorId);
		message.setRecipientId(recipientId);
		message.setDate(date);
		
		//messages.setDate(new Timestamp(System.currentTimeMillis()));
		messageRepo.save(message);
		
		
		
		return "Saved";
	}
	
	@GetMapping(path = "/getBySender")
	public @ResponseBody Iterable<Messages> getAllBySender(@RequestParam int creatorId){
		Iterable<Messages> messagePacket = messageRepo.findAllByCreatorId(creatorId);
		
		
		return messagePacket;
	}
	
	@GetMapping(path = "/getByReceiver")
	public @ResponseBody Iterable<Messages> getAllByReceiver(@RequestParam int recipientId){
		Iterable<Messages> messagePacket = messageRepo.findAllByRecipientId(recipientId);
		

		return messagePacket;
	}
	
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Messages> getAllMessages() {
		return messageRepo.findAll();
	}
	
	
	
}
