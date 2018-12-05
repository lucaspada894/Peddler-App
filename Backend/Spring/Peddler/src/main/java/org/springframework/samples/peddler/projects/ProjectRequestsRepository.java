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
import org.springframework.samples.peddler.projects.ProjectRequests;

public interface ProjectRequestsRepository extends CrudRepository<ProjectRequests, Integer>
{
	@Transactional
	@Modifying
	@Query("UPDATE ProjectRequests p SET p.status =:request_status WHERE p.projectId =:projectId AND p.userId =:userId")
	void setRequestStatus(@Param("request_status") boolean request_status, @Param("projectId") int projectId, @Param("userId") int userId);
	
	@Transactional
	@Modifying
	@Query("SELECT p FROM ProjectRequests p WHERE p.projectId =:projectId")
	Iterable<ProjectRequests> findAllByProjectId(@Param("projectId") int projectId);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM ProjectRequests p WHERE p.projectId =:projectId AND p.userId =:userId")
	void deleteRequest(@Param("projectId") int projectId, @Param("userId") int userId);
	
	@Transactional
	@Query("SELECT p FROM ProjectRequests p WHERE p.projectId =:projectId AND p.userId =:userId")
	ProjectRequests getRequest(@Param("projectId") int projectId, @Param("userId") int userId);
}

