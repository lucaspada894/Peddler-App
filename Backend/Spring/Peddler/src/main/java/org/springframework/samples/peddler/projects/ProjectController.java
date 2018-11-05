package org.springframework.samples.peddler.projects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.samples.peddler.projects.Projects;
import org.springframework.samples.peddler.projects.ProjectRepository;

@Controller
@RequestMapping(path="/project")
public class ProjectController {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@GetMapping(path="/add")
	public @ResponseBody String addNewUser(@RequestParam String title, @RequestParam String major,@RequestParam String details) {
		Projects n = new Projects();
		n.setTitle(title);
		n.setMajor(major);
		n.setDetails(details);
		projectRepository.save(n);
		return "Saved";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Projects> getAllUsers() {
		return projectRepository.findAll();
	}
	
	@GetMapping(path="/delete")
	public @ResponseBody String deleteProjects(@RequestParam int id) {
		projectRepository.deleteById(id);
		return "Deleted";
		
	}
}