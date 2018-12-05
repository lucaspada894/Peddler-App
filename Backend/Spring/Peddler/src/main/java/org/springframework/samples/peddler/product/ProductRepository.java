package org.springframework.samples.peddler.product;


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.peddler.product.Products;

public interface ProductRepository extends CrudRepository<Products, Integer> {
	
	
	@Query("SELECT productID FROM Products t WHERE t.productName LIKE CONCAT('%',:query,'%') OR t.productCondition LIKE CONCAT('%',:query,'%') OR t.productPrice LIKE CONCAT('%',:query,'%')")
	Iterable<Integer> findByAll(@Param("query") String query);

	@Query("SELECT productID FROM Products t WHERE t.productCondition LIKE CONCAT('%',:query,'%')")
	Iterable<Integer> findByCondition(@Param("query") String query);
	
	@Query("SELECT productID FROM Products t WHERE t.productPrice LIKE CONCAT('%',:query,'%')")
	Iterable<Integer> findByPrice(@Param("query") String query);
	
	
    @Query("SELECT productID FROM Products t WHERE t.userID =:userID")
    @Transactional(readOnly = true)
    Iterable<Integer> myProducts(@Param("userID") Integer userID);


}
