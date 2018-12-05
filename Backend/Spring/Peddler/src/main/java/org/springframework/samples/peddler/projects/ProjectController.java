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
	
	@Autowired
	public ProjectRequestsRepository projectRequestsRepository;
	
	@Autowired
	public ProjectUsersRepository projectUsersRepository;
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public ProjectNotificationsRepository projectNotificationsRepository;
	
	@GetMapping(path="/add")
	public @ResponseBody String addNewProject(@RequestParam String title, @RequestParam String major,@RequestParam String description,@RequestParam Integer userID, @RequestParam Integer ownerID) {
		Projects n = new Projects();
		n.setTitle(title);
		n.setMajor(major);
		n.setDescription(description);
		n.setOwnerID(ownerID);
		projectRepository.save(n);
		return "Saved";
	}
	
	public @ResponseBody String addNewRequest(@RequestParam int userId, @RequestParam int projectId, @RequestParam int ownerId) {
		ProjectRequests r = new ProjectRequests();
		r.setUserId(userId);
		r.setOwnerId(ownerId);
		r.setProjectId(projectId);
		projectRequestsRepository.save(r);
		return "saved";
	}
	

	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Projects> getAllProjects() {
		return projectRepository.findAll();
	}
	
	
	
	
	@GetMapping(value = "/myProjects")
	public @ResponseBody Iterable<Projects> usersProjects(Integer userId) {

		return projectRepository.findProjectsByOwnerId(userId);
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
    public @ResponseBody String requestAction(@RequestParam boolean requestStatus, @RequestParam int projectId, int userId) {
    	String status;
    	if(requestStatus) {
    		status = "accepted";
    	}
    	else status = "declined";
    	
    	projectRequestsRepository.setRequestStatus(requestStatus, projectId, userId);
    	Projects p = projectRepository.fetchProject(projectId);
    	ProjectNotifications n = new ProjectNotifications();
    	n.setProjectId(projectId);
    	n.setUserId(userId);
    	userRepository.updateNumNotifications(userId);
    	if(requestStatus == true) {
    		status = "accepted.";
    		ProjectUsers newId = new ProjectUsers();
    		newId.setProjectId(projectId);
    		newId.setUserId(userId);
    		projectUsersRepository.save(newId);
    	//projectUsersRepository.setNewUserId(projectId, userId);
    	}
    	else {
    		status = "declined.";
    	}
    	
    	n.setNotification("your request to join " + p.getTitle() + " has been " + status);
    	projectNotificationsRepository.save(n);
    	
    	return "request has been " + status;
    }
    
    @Transactional
    @GetMapping(path="/getUserNotifications")
    public @ResponseBody Iterable<ProjectNotifications> fetchUserNotifications(@RequestParam int userId){
    	return projectNotificationsRepository.fetchUserNotifications(userId);
    }
    
    @Transactional
    @GetMapping(path="/getProjectUsers")
    public @ResponseBody ArrayList<Users> fetchProjectUsers(@RequestParam int projectId) {
    	
    	Iterable<ProjectUsers> userIds = projectUsersRepository.fetchProjectUsers(projectId);
    	ArrayList<Users> users = new ArrayList<Users>();
    	
    	for(ProjectUsers i: userIds) {
    		Optional<Users> e = userRepository.findById(i.getUserId());
    		Users user = e.get();
    		users.add(user);
    	}
    	
    	return users;
    }
    
    @Transactional
    @GetMapping(path="/sendRequest")
    	public @ResponseBody String sendRequest(@RequestParam int requesterId, @RequestParam int projectId) {
    		ProjectRequests n = new ProjectRequests();
    		n.setUserId(requesterId);
    		n.setProjectId(projectId);
    		n.setOwnerId(projectRepository.fetchProjectOwnerId(projectId));
    		n.setStatus(false);
    		projectRequestsRepository.save(n);
    		
    		ProjectNotifications m = new ProjectNotifications();
    		m.setUserId(projectRepository.fetchProjectOwnerId(projectId));
    		m.setProjectId(projectId);
    		m.setNotification("" + projectRepository.fetchUser(requesterId).getFirstName() + " " + projectRepository.fetchUser(requesterId).getLastName() + "wishes to join your project!");
    		userRepository.updateNumNotifications(projectRepository.fetchProjectOwnerId(projectId));
    		projectNotificationsRepository.save(m);
    		return "" + projectRepository.fetchProjectOwnerId(projectId);
    		//return "request to join " + projectRepository.fetchProject(projectId).getTitle() + " sent!";
    	}
    //@GetMapping(path="/fetchMembers")
    //public @ResponseBody Iterable<Users> fetchMembers(@RequestParam int projectId){
    //	return projectRepository.fetchProjectMembers(projectId);
    //}
    
    
	@GetMapping(path="/search")
	public @ResponseBody Iterable<Projects> searchProjects(@RequestParam String search) {
		String[] words = search.split(" ");
		
		Iterable<Integer> tutorIDs =  projectRepository.findProjectsWithPartOfName(search);

		return projectRepository.findAllById(tutorIDs);
	}
	
	@GetMapping(path="/fetchProject")
	public @ResponseBody Projects fetchProject(@RequestParam int projectId) {
		return projectRepository.fetchProject(projectId);
	}
    
	@GetMapping(path="/fetchProjectRequests")
	public @ResponseBody Iterable<ProjectRequests> fetchProjectRequests(@RequestParam int projectId) {
		return projectRequestsRepository.findAllByProjectId(projectId);
	}
    
}	

