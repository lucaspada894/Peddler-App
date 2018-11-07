package org.springframework.samples.peddler.projects;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.samples.peddler.projects.Projects;
import org.springframework.samples.peddler.tutors.Tutors;
import org.springframework.samples.peddler.projects.ProjectRepository;

@Controller
@RequestMapping(path="/project")
public class ProjectController {
	
	@Autowired
	public ProjectRepository projectRepository;
	
	@GetMapping(path="/add")
	public @ResponseBody String addNewProject(@RequestParam String title, @RequestParam String major,@RequestParam String description,@RequestParam Integer userID, @RequestParam Integer ownerID) {
		Projects n = new Projects();
		n.setTitle(title);
		n.setMajor(major);
		n.setDescription(description);
		n.setUserID(userID);
		n.setOwnerID(ownerID);
		projectRepository.save(n);
		return "Saved";
	}
	

	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Projects> getAllProjects() {
		return projectRepository.findAll();
	}
	
	
	
	
	@GetMapping(value = "/myProjects")
	public @ResponseBody Iterable<Projects> usersProjects(Integer userId) {

		return projectRepository.findProjectsByUserID(userId);
	}
	
	
	
	
    @Transactional
	@RequestMapping(path="/delete")
	public @ResponseBody String deleteProject(@RequestParam Integer projId, @RequestParam Integer userId) {
		projectRepository.deleteProject(projId, userId);
		return "project " + projectRepository.getTitle(userId) + " deleted"; 	
	}
    
    
	
    @Transactional
	@RequestMapping("/editDesc")
	public @ResponseBody String editProjectDesc( @RequestParam String newDesc, @RequestParam Integer projId, @RequestParam Integer userId) {
		projectRepository.editProjectDescription(newDesc, projId, userId);
		return "description changed";
	}
	
    
    
    @Transactional
	@RequestMapping(path="/editTitle")
	public @ResponseBody String editProjectTitle(@RequestParam String newTitle, @RequestParam Integer projId, @RequestParam Integer userId) {
		projectRepository.editProjectTitle(newTitle, projId, userId);
		return "title changed";
	}
    
    @Transactional
    @RequestMapping(path="/requestAction")
    public @ResponseBody String requestAction(@RequestParam boolean request_status, @RequestParam int owner_id) {
    	projectRepository.setRequestStatus(request_status, owner_id);
    	return "request accepted!";
    }
    
    @Transactional
    @RequestMapping(path="/sendRequest")
    	public @ResponseBody String sendRequest(@RequestParam int requester_id, @RequestParam int project_id) {
    		projectRepository.setNewRequest(requester_id, project_id);
    		return "request to join sent!";
    	}
    
    
    
	@GetMapping(path="/search")
	public @ResponseBody Iterable<Projects> searchProjects(@RequestParam String search) {
		String[] words = search.split(" ");
		
		Iterable<Integer> tutorIDs =  projectRepository.findProjectsWithPartOfName(search);

		return projectRepository.findAllById(tutorIDs);
	}
    
    
    
}	

