package org.springframework.samples.peddler.product;

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
@RequestMapping(path="/product")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@GetMapping(path="/add")
	public @ResponseBody String addNewUser(@RequestParam Integer productID, @RequestParam Integer userID, @RequestParam String productName, @RequestParam String productDescription, @RequestParam String productCondition, @RequestParam String productPrice) {
		Products n = new Products();
		n.setProductID(productID);
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
}
