package org.springframework.samples.peddler.projects;


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.peddler.projects.Projects;

public interface ProjectRepository extends CrudRepository<Projects, Integer> {
	
 
	
@Transactional
@Modifying
@Query("SELECT FROM Projects p WHERE p.id =:id")
String getTitle(@Param("userid") int userid);	
	
@Transactional
@Modifying
@Query("DELETE FROM Projects p WHERE p.id =:id AND p.userid =:userid")
void deleteProject(@Param("id") int id, @Param("userid") int userid);

@Transactional
@Modifying
@Query("UPDATE Projects p SET description =:description WHERE p.id =:id AND p.userid =:userid")
void editProjectDesc(@Param("id") int id, @Param("userid") int userid);

@Transactional
@Modifying
@Query("UPDATE Projects p SET title =:title WHERE p.id =:id AND p.userid =:userid")
void editProjectTitle(@Param("id") int id, @Param("userid") int userid);
}

//add FROM * PROJECTS WHERE UserID = userid
	
