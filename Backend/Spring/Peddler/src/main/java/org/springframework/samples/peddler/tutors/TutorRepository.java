package org.springframework.samples.peddler.tutors;


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.peddler.tutors.Tutors;

public interface TutorRepository extends CrudRepository<Tutors, Integer> {



}