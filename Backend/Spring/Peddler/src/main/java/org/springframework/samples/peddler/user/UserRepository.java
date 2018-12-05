package org.springframework.samples.peddler.user;


import org.springframework.transaction.annotation.Transactional;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.peddler.projects.Projects;

public interface UserRepository extends CrudRepository<Users, Integer> {
	
	
	/**
	 * deletes a user and its corresponding info based off their email and password
	 * @param email
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Users t WHERE t.email =:email AND t.password =:password")
    void deleteUser(@Param("email") String email, @Param("password") String password);
	

	/**
	 * returns the users id given their username and password
	 * @param email
	 * @param password
	 * @return
	 */
    @Query("SELECT id FROM Users t WHERE t.email =:email AND t.password =:password")
    @Transactional(readOnly = true)
    Integer viewUser(@Param("email") String email, @Param("password") String password);
    
    
    /**
     * updates the email for the current signed in user
     * @param newEmail
     * @param currentUser
     */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Users t SET t.email =:newEmail  WHERE t.id =:currentUser")
    void editEmail(@Param("newEmail") String newEmail, @Param("currentUser") Integer currentUser);
    
	
	/**
	 * updates the first name for the current signed in user
	 * @param newFirstName
	 * @param currentUser
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Users t SET t.firstName =:newFirstName  WHERE t.id =:currentUser")
    void editFirstName(@Param("newFirstName") String newFirstName, @Param("currentUser") Integer currentUser);
	
	
	/**
	 * updates the last name for the current signed in user
	 * @param newLastName
	 * @param currentUser
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Users t SET t.lastName =:newLastName  WHERE t.id =:currentUser")
    void editLastName(@Param("newLastName") String newLastName, @Param("currentUser") Integer currentUser);

	
	/**
	 * updates the university for the current signed in user
	 * @param newUniversity
	 * @param currentUser
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Users t SET t.university =:newUniversity  WHERE t.id =:currentUser")
    void editUniversity(@Param("newUniversity") String newUniversity, @Param("currentUser") Integer currentUser);
	
	
	/**
	 * updates the phone number for the current signed in user
	 * @param newUniversity
	 * @param currentUser
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Users t SET t.phoneNumber =:newPhoneNumber  WHERE t.id =:currentUser")
    void editPhoneNumber(@Param("newPhoneNumber") String newPhoneNumber, @Param("currentUser") Integer currentUser);
	
	
	/**
	 * updates the grade for the current signed in user
	 * @param newUniversity
	 * @param currentUser
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Users t SET t.year =:newYear  WHERE t.id =:currentUser")
    void editYear(@Param("newYear") String newYear, @Param("currentUser") Integer currentUser);
	
	
	/**
	 * updates the password for the current signed in user
	 * @param newPassword
	 * @param currentUser
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Users t SET t.password =:newPassword  WHERE t.id =:currentUser")
    void editPassword(@Param("newPassword") String newPassword, @Param("currentUser") Integer currentUser);
	
	
	/**
	 * checks the password and returns the name of the user if the password is correct
	 * @param currentPassword
	 * @param currentUser
	 * @return
	 */
    @Query("SELECT firstName FROM Users t WHERE t.password =:currentPassword AND t.id =:currentUser")
    @Transactional(readOnly = true)
    String checkPassword(@Param("currentPassword") String currentPassword, @Param("currentUser") Integer currentUser);
    
    
    
    /**
     * finds the projectID given the users ID
     * @param userID
     * @return
     */
    @Query("SELECT id FROM Projects t WHERE t.ownerId =:ownerId")
    @Transactional(readOnly = true)
    Iterable<Integer> findProjectID(@Param("ownerId")Integer userID);
    
    @Modifying
    @Query("UPDATE Users u SET u.numNotifications = (u.numNotifications + 1) WHERE u.id =:userId")
	@Transactional(readOnly = true)
	void updateNumNotifications(@Param("userId") int userId);

    
    
    
    /*@Query("SELECT title, description, major, id, userID FROM Projects t WHERE t.id =:id")
    @Transactional(readOnly = true)
    Iterable<Users> findProjectInfo(@Param("id")Iterable<Integer> id);*/

	
	
    
}