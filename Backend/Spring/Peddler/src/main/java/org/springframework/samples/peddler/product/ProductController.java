package org.springframework.samples.peddler.product;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.peddler.projects.Projects;
import org.springframework.samples.peddler.tutors.Tutors;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/product")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@GetMapping(path="/add")
	public @ResponseBody String addNewUser(@RequestParam Integer userID, @RequestParam String productName, @RequestParam String productDescription, @RequestParam String productCondition, @RequestParam String productPrice) {
		Products n = new Products();
		n.setUserID(userID);
		n.setProductName(productName);
		n.setProductDescription(productDescription);
		n.setProductCondition(productCondition);
		n.setProductPrice(productPrice);

		productRepository.save(n);
		return "User Created";
	}
	
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Products> getAllProducts() {
		return productRepository.findAll();
	}
	
	
	
	@GetMapping(path="/search")
	public @ResponseBody Iterable<Products> searchProducts(@RequestParam Integer type, @RequestParam String search) {
		
		Iterable<Integer> productIDs = null;
		
		if(type == 0) {
			productIDs =  productRepository.findByAll(search);
		}

		else if(type == 1) {
			productIDs =  productRepository.findByCondition(search);
		}
		
		else if(type == 2) {
			productIDs =  productRepository.findByPrice(search);
		}

		return productRepository.findAllById(productIDs);
	}
	
	
	@GetMapping(path="/myProducts")
	public @ResponseBody Iterable<Products> getMyProducts(@RequestParam Integer userID) {
		Iterable<Integer> productIDs = productRepository.myProducts(userID);
		return productRepository.findAllById(productIDs);
		
	}
}
