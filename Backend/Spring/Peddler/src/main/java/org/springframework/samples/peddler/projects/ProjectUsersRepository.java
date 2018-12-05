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
import org.springframework.samples.peddler.projects.ProjectUsers;

public interface ProjectUsersRepository extends CrudRepository<ProjectUsers, Integer>{
	
	@Transactional
	@Modifying
	@Query("SELECT p FROM ProjectUsers p WHERE p.projectId =:projectId")
	Iterable<ProjectUsers> fetchProjectUsers(@Param("projectId") int projectId);
}
