package org.springframework.samples.peddler;


import org.springframework.data.repository.CrudRepository;

import org.springframework.samples.peddler.Users;

public interface UserRepository extends CrudRepository<Users, Integer> {
	

}