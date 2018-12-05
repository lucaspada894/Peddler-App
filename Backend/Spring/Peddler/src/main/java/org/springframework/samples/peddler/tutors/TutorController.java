package org.springframework.samples.peddler.tutors;


import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.samples.peddler.tutors.Tutors;
import org.springframework.samples.peddler.tutors.TutorRepository;

@Controller
@RequestMapping(path="/tutor")
public class TutorController {
	
	@Autowired
	public TutorRepository tutorRepository;
	
	@Autowired
	public TutorMembersRepository tutorMembersRepo;
	
	@GetMapping(path="/add")
	public @ResponseBody String addNewProject(@RequestParam Integer userID, @RequestParam String tutorTitle,@RequestParam String tutorSubject,@RequestParam String tutorDescription, @RequestParam String tutorTimes) {
		Tutors n = new Tutors();
		n.setUserID(userID);
		n.setTutorTitle(tutorTitle);
		n.setTutorSubject(tutorSubject);
		n.setTutorDescription(tutorDescription);
		n.setTutorTimes(tutorTimes);
		tutorRepository.save(n);
		return "Saved";
	}
	
		
	@Transactional
	@RequestMapping(path="/request")
	public @ResponseBody String requestTutor(@RequestParam Integer tutorID, @RequestParam Integer userID) {
		tutorRepository.updateTutor(userID,tutorID);
		
		
		return "Tutor Requested";
	}

	@Transactional
	@GetMapping(path="/addStudent")
	public @ResponseBody void addStudent(@RequestParam int tutorId, @RequestParam int studentId) {
		TutorMembers newMember = new TutorMembers();
		newMember.setStudentId(studentId);
		newMember.setTutorId(tutorId);
		tutorMembersRepo.save(newMember);
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Tutors> getAllTutors() {
		return tutorRepository.findAll();
	}
	
	
	@GetMapping(path="/search")
	public @ResponseBody Iterable<Tutors> searchTutors(@RequestParam String search) {
		String[] words = search.split(" ");
		ArrayList<Iterable<Integer>> all;
		
		Iterable<Integer> tutorIDs =  tutorRepository.findUsersWithPartOfName(search);

		return tutorRepository.findAllById(tutorIDs);
	}
	
	
	@GetMapping(path="/myLessons")
	public @ResponseBody Iterable<Tutors> getMyLessons(@RequestParam Integer userID) {
		Iterable<Integer> tutorIDs = tutorRepository.myLessons(userID);
		return tutorRepository.findAllById(tutorIDs);
		
	}

}
