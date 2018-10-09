package org.springframework.samples.peddler.user;



import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	public boolean loginStatus = false;
	private int currentUser;
	
	
	@GetMapping(path="/add")
	public @ResponseBody String addNewUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String phoneNumber, @RequestParam String year, @RequestParam String university, @RequestParam String password) {
		Users n = new Users();
		n.setFirstName(firstName);
		n.setLastName(lastName);
		n.setEmail(email);
		n.setPhoneNumber(phoneNumber);
		n.setYear(year);
		n.setUniversity(university);
		n.setPassword(password);
		userRepository.save(n);
		return "User Created";
	}
	
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Users> getAllUsers() {
		return userRepository.findAll();
	}
	
	
    @Transactional
	@RequestMapping("/deleteUser")
	public @ResponseBody String deleteUsers(@RequestParam String email, @RequestParam String password) {
			userRepository.deleteUser(email, password);
			return "User " + email + " deleted!";

	}
    
    
	@GetMapping(value = "/login")
	public @ResponseBody String login(@RequestParam String email, @RequestParam String password) {
		Integer userID = userRepository.viewUser(email,password);
		if(userID == null) {
			return "Email or password is incorrect!";
		}
		else {
			currentUser = userID;
			loginStatus = true;
			return "User has been logged in!";
		}
	}
	
	
	@GetMapping(value = "/logout")
	public @ResponseBody String logout() {
		currentUser = 0;
		loginStatus = false;
		return "You have logged out!";
	}
	
	
	
	@GetMapping(value = "/myInfo")
	public @ResponseBody Optional<Users> login() {
		if(loginStatus == true) {
			return userRepository.findById(currentUser);
		}
		else {
			throw new NullPointerException("You are not logged in!");
		}
	}
	
	
	
    @Transactional
	@RequestMapping("/editEmail")
	public @ResponseBody String editUserEmail(@RequestParam String newEmail) {
    	if(loginStatus == true) {
    		userRepository.editEmail(newEmail, currentUser);
    		return "Email Changed to " + newEmail;
    	}
    	else {
    		return "You are not logged in!";
    	}
	}
    
    
    
    @Transactional
	@RequestMapping("/editFirstName")
	public @ResponseBody String editUserFirstName(@RequestParam String newFirstName) {
    	if(loginStatus == true) {
    		userRepository.editFirstName(newFirstName, currentUser);
    		return "First Name Changed to " + newFirstName;
    	}
    	else {
    		return "You are not logged in!";
    	}
		
	}
    
    
    
    @Transactional
	@RequestMapping("/editLastName")
	public @ResponseBody String editUserLastName(@RequestParam String newLastName) {
    	if(loginStatus == true) {
    		userRepository.editLastName(newLastName, currentUser);
    		return "Last Name Changed to " + newLastName;
    	}
    	else {
    		return "You are not logged in!";
    	}
		
	}
    
    
    
    @Transactional
	@RequestMapping("/editUniversity")
	public @ResponseBody String editUserUniversity(@RequestParam String newUniversity) {
    	if(loginStatus == true) {
    		userRepository.editUniversity(newUniversity, currentUser);
    		return "University Changed to " + newUniversity;
    	}
    	else {
    		return "You are not logged in!";
    	}
		
	}
    
    
    
    @Transactional
	@RequestMapping("/editPhoneNumber")
	public @ResponseBody String editPhoneNumber(@RequestParam String newPhoneNumber) {
    	if(loginStatus == true) {
    		userRepository.editPhoneNumber(newPhoneNumber, currentUser);
    		return "Phone Number Changed to " + newPhoneNumber;
    	}
    	else {
    		return "You are not logged in!";
    	}
		
	}
    
    
    
    @Transactional
	@RequestMapping("/editYear")
	public @ResponseBody String editYear(@RequestParam String newYear) {
    	if(loginStatus == true) {
    		userRepository.editYear(newYear, currentUser);
    		return "Year Changed to " + newYear;
    	}
    	else {
    		return "You are not logged in!";
    	}
		
	}
    
    
    
    @Transactional
	@RequestMapping("/editPassword")
	public @ResponseBody String editUserPassword(@RequestParam String currentPassword,@RequestParam String newPassword,@RequestParam String newPassword2) {
		String passwordCheck = userRepository.checkPassword(currentPassword, currentUser);
		
		if(loginStatus == false) {
    		return "You are not logged in!";
		}
		
		if(passwordCheck == null) {
			return "Incorrect password";
		}
		
		if(newPassword.equals(newPassword2)) {
        	userRepository.editPassword(newPassword, currentUser);
        	return "Password Changed!";
    	}
		
		else {
			return "The 2 passwords do not match!";
		}
    }
    
    
    
    
    
	/*@GetMapping(value = "/projects")
	public @ResponseBody Iterable<Users> usersProjects(@RequestParam String email, @RequestParam String password) {
		
		int userID = userRepository.viewUser(email,password);
		Iterable<Integer> projectID = userRepository.findProjectID(userID);
		return userRepository.findProjectInfo(projectID);
	}*/

}