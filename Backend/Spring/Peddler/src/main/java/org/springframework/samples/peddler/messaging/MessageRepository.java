package org.springframework.samples.peddler.messaging;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import org.springframework.samples.peddler.messaging.Messages;



public interface MessageRepository extends CrudRepository<Messages, Integer>{
	public Iterable<Messages> findAllByRecipientId(int recipientId);
	public Iterable<Messages> findAllByCreatorId(int creatorId);
	
	
	
}
