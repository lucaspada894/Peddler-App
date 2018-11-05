package org.springframework.samples.peddler.messaging;
import org.springframework.data.repository.CrudRepository;

import org.springframework.samples.peddler.messaging.Messages;;


public interface MessageRepository extends CrudRepository<Messages, Integer>{
	public Iterable<Messages> findAllByRecipientId(int recipientId);
	public Iterable<Messages> findAllByCreatorId(int creatorId);
}
