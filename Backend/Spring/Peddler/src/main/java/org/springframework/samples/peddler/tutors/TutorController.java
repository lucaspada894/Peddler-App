package org.springframework.samples.peddler.tutors;


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
	

	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Tutors> getAllTutors() {
		return tutorRepository.findAll();
	}
}
