package org.springframework.samples.peddler.product;


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.peddler.product.Products;

public interface ProductRepository extends CrudRepository<Products, Integer> {



}
