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
	public ProjectRepository projectRepository;
	
	@GetMapping(path="/add")
	public @ResponseBody String addNewProject(@RequestParam String title, @RequestParam String major,@RequestParam String description,@RequestParam Integer userID) {
		Projects n = new Projects();
		n.setTitle(title);
		n.setMajor(major);
		n.setDescription(description);
		n.setUserID(userID);
		projectRepository.save(n);
		return "Saved";
	}
	
	
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Projects> getAllUsers() {
		return projectRepository.findAll();
	}
	
	
	@GetMapping(path="/delete")
	public @ResponseBody String deleteProject(@RequestParam int id, @RequestParam int userid) {
		projectRepository.deleteProject(id, userid);
		return "project" + projectRepository.getTitle(userid) + "deleted"; 	
	}
	
	@GetMapping(path="/edit")
	public @ResponseBody void editProject(@RequestParam int userid) {
		
	}
}	