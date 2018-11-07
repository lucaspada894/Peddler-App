package org.springframework.samples.peddler.projects;

import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.samples.peddler.projects.Projects;
import org.springframework.samples.peddler.user.UserRepository;
import org.springframework.samples.peddler.tutors.Tutors;
import org.springframework.samples.peddler.projects.ProjectRepository;
import org.springframework.samples.peddler.user.Users;

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
	@GetMapping(path="/delete")
	public @ResponseBody String deleteProject(@RequestParam Integer projId, @RequestParam Integer userId) {
		projectRepository.deleteProject(projId, userId);
		return "project " + projectRepository.getTitle(userId) + " deleted"; 	
	}
    
    
	
    @Transactional
	@GetMapping("/editDesc")
	public @ResponseBody String editProjectDesc( @RequestParam String newDesc, @RequestParam Integer projId, @RequestParam Integer userId) {
		projectRepository.editProjectDescription(newDesc, projId, userId);
		return "description changed";
	}
	
    
    
    @Transactional
	@GetMapping(path="/editTitle")
	public @ResponseBody String editProjectTitle(@RequestParam String newTitle, @RequestParam Integer projId, @RequestParam Integer userId) {
		projectRepository.editProjectTitle(newTitle, projId, userId);
		return "title changed";
	}
    
    @Transactional
    @GetMapping(path="/requestAction")
    public @ResponseBody String requestAction(@RequestParam boolean requestStatus, @RequestParam int projectId) {
    	String status;
    	if(requestStatus) {
    		status = "accepted";
    	}
    	else status = "declined";
    	
    	projectRepository.setRequestStatus(requestStatus, projectId);
    	Projects p = projectRepository.fetchProject(projectId);
    	projectRepository.setRequestNotification("you have been " + status +  " for project " + p.getTitle() + "!", p.getRequesterId());
    	if(requestStatus) {
    	projectRepository.setNewProjectId(projectId, p.getRequesterId());
    	}
    	return "request has been " + status;
    }
    
    @Transactional
    @GetMapping(path="/sendRequest")
    	public @ResponseBody String sendRequest(@RequestParam int requesterId, @RequestParam int projectId) {
    		projectRepository.setNewRequest(requesterId, projectId);
    		Projects p = projectRepository.fetchProject(projectId);
    		projectRepository.setRequestNotification("" + projectRepository.fetchUser(requesterId).getFirstName() + " " + projectRepository.fetchUser(requesterId).getLastName() + " wishes to join your project!", p.getUserID());
    		return "request to join sent!";
    	}
    @GetMapping(path="/fetchMembers")
    public @ResponseBody Iterable<Users> fetchMembers(@RequestParam int projectId){
    	return projectRepository.fetchProjectMembers(projectId);
    }
    
    
	@GetMapping(path="/search")
	public @ResponseBody Iterable<Projects> searchProjects(@RequestParam String search) {
		String[] words = search.split(" ");
		
		Iterable<Integer> tutorIDs =  projectRepository.findProjectsWithPartOfName(search);

		return projectRepository.findAllById(tutorIDs);
	}
    
    
    
}	

