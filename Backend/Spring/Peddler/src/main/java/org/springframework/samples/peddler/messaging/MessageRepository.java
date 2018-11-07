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
	
	@Transactional
	@Modifying
	@Query("UPDATE Projects p set p.requester_id = :requester_id WHERE p.owner_id =:owner_id")
	void setNewRequest(@Param("requester_id") int requester_id, @Param("owner_id") int owner_id );
	
}
