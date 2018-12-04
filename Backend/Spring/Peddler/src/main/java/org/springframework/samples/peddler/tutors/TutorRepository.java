package org.springframework.samples.peddler.tutors;


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.peddler.tutors.Tutors;

public interface TutorRepository extends CrudRepository<Tutors, Integer> {


	/**
	 * deletes a user and its corresponding info based off their email and password
	 * @param email
	 */
	@Transactional
	@Modifying
	@Query("UPDATE FROM Tutors t SET t.tutorRequest =:userID WHERE t.tutorID =:tutorID")
    void updateTutor(@Param("userID") Integer userID, @Param("tutorID") Integer tutorID);
	
	
	
	@Query("SELECT tutorID FROM Tutors t WHERE t.tutorTitle LIKE CONCAT('%',:query,'%') OR t.tutorSubject LIKE CONCAT('%',:query,'%')")
	Iterable<Integer> findUsersWithPartOfName(@Param("query") String query);
    
    @Query("SELECT tutorID FROM Tutors t WHERE t.userID =:userID")
    @Transactional(readOnly = true)
    Iterable<Integer> myLessons(@Param("userID") Integer userID);

}