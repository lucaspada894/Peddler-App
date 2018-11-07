package org.springframework.samples.peddler.projects;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Entity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.peddler.projects.Projects;
import org.springframework.samples.peddler.user.Users;

public interface ProjectRepository extends CrudRepository<Projects, Integer> {
	
 

	
@Transactional(readOnly = true)
Iterable<Projects> findProjectsByUserID(@Param("userID") Integer userID);	
	


@Query("SELECT title FROM Projects p WHERE p.userID =:userId")
@Transactional(readOnly = true)
String getTitle(@Param("userId") Integer userId);



	
@Transactional
@Modifying
@Query("DELETE FROM Projects p WHERE p.id =:projId AND p.userID =:userId")
void deleteProject(@Param("projId") Integer projId, @Param("userId") Integer userId);




@Transactional
@Modifying(clearAutomatically = true)
@Query("UPDATE Projects p SET p.description =:newDescription  WHERE p.id =:projId AND p.userID =:userId")
void editProjectDescription(@Param("newDescription") String newDescription, @Param("projId") Integer projID, @Param("userId") Integer userid);




@Transactional
@Modifying(clearAutomatically = true)
@Query("UPDATE Projects p SET p.title =:newTitle WHERE p.id =:projId AND p.userID =:userId")
void editProjectTitle(@Param("newTitle") String newTitle, @Param("projId") Integer projId, @Param("userId") Integer userId);

@Transactional
@Modifying
@Query("UPDATE Projects p SET p.requesterId =:requesterId WHERE p.id =:id")
void setNewRequest(@Param("requesterId") int requesterId, @Param("id") int id);

@Transactional
@Modifying
@Query("UPDATE Projects p SET p.requestStatus =:request_status WHERE p.id =:projectId")
void setRequestStatus(@Param("request_status") boolean request_status, @Param("projectId") int projectId);

@Transactional
@Modifying
@Query("UPDATE Users u SET u.projectID =:projectId WHERE u.id =:id")
void setNewProjectId(@Param("projectId") int projectId, @Param("id") int id);


@Transactional
@Modifying
@Query("UPDATE Users u SET u.notification =:notification WHERE u.id =:id ")
void setRequestNotification(@Param("notification") String notification, @Param("id") int id);

@Query("SELECT u FROM Users u WHERE u.id =:id")
Users fetchUser(@Param("id") int id);

@Query("SELECT p FROM Projects p WHERE p.id =:projectId")
Projects fetchProject(@Param("projectId") int projectId);

@Query("SELECT u FROM Users u WHERE u.projectID =:projectId")
Iterable<Users> fetchProjectMembers(@Param("projectId") int projectId);

@Query("SELECT id FROM Projects t WHERE t.title LIKE CONCAT('%',:query,'%') OR t.description LIKE CONCAT('%',:query,'%') OR t.major LIKE CONCAT('%',:query,'%')")
Iterable<Integer> findProjectsWithPartOfName(@Param("query") String query);



}


	
