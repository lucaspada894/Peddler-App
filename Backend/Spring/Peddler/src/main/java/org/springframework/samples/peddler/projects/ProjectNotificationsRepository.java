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
import org.springframework.samples.peddler.projects.ProjectNotifications;

public interface ProjectNotificationsRepository extends CrudRepository<ProjectNotifications, Integer>{

	@Query("SELECT p FROM ProjectNotifications p WHERE p.userId =:userId")
	@Transactional(readOnly = true)
	Iterable<ProjectNotifications> fetchUserNotifications(@Param("userId") int userId);
	
}


