package com.init.products.rest;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.init.products.dao.ProductsDAO;
import com.init.products.entitys.Product;

@RestController
@RequestMapping("products")
public class ProductsREST {

	@Autowired
	private ProductsDAO productsDAO;
	
	//Obtener la lista completa
	@GetMapping
	public ResponseEntity<List<Product>> getProduct(){
		List<Product> products = productsDAO.findAll();
		return ResponseEntity.ok(products);
	}
	
	//Buscar por Id
	@RequestMapping(value = "{productId}") //	/products/{productId} -> /products/1
	public ResponseEntity<Product> getProductByID(@PathVariable("productId") Long productId){
		
		Optional<Product> optionalProduct = productsDAO.findById(productId);
		
		if (optionalProduct.isPresent()) {
			return ResponseEntity.ok(optionalProduct.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
	//Insertar
	@PostMapping //	/products (POST)
	public ResponseEntity<Product> createProduct(@RequestBody Product product){
		Product newProduct = productsDAO.save(product);
		return ResponseEntity.ok(newProduct);
	}
	
	//Eliminar
	@DeleteMapping(value="{productId}") //	/products (DELETE)
	public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId){
		productsDAO.deleteById(productId);
		return ResponseEntity.ok(null);
	}
	
	//Actualizar
	@PutMapping
	public ResponseEntity<Product> updateProduct(@RequestBody Product product){
		Optional<Product> optionalProduct = productsDAO.findById(product.getId());
		if (optionalProduct.isPresent()) {
			Product updateProduct = optionalProduct.get();
			updateProduct.setName(product.getName());
			productsDAO.save(updateProduct);
			return ResponseEntity.ok(updateProduct);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	//@GetMapping //localhost:8080
	@RequestMapping(value="hello", method=RequestMethod.GET)
	public String hello() {
		return "Hello Word!";
	}
}
