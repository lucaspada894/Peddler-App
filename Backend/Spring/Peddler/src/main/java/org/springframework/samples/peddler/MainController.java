package org.springframework.samples.peddler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.samples.peddler.Users;
import org.springframework.samples.peddler.UserRepository;

@Controller
@RequestMapping(path="/user")
public class MainController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path="/add")
	public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email,@RequestParam String password) {
		Users n = new Users();
		n.setName(name);
		n.setEmail(email);
		n.setPassword(password);
		userRepository.save(n);
		return "Saved";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Users> getAllUsers() {
		return userRepository.findAll();
	}
	
	
	@RequestMapping("/delete")
	public String deleteUsers(@RequestParam Integer id) {
		userRepository.deleteById(id);
		return id + " Deleted";
		
	}
}