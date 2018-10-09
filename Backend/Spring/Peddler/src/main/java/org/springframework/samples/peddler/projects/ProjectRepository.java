package org.springframework.samples.peddler.projects;


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.peddler.projects.Projects;

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



}

//add FROM * PROJECTS WHERE UserID = userid
	
