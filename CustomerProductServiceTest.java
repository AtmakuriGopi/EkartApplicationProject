package com.infy.ekart.service.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.ekart.dto.ProductDTO;
import com.infy.ekart.entity.Product;
import com.infy.ekart.exception.EKartException;
import com.infy.ekart.repository.ProductRepository;
import com.infy.ekart.service.CustomerProductService;
import com.infy.ekart.service.CustomerProductServiceImpl;



@SpringBootTest
class CustomerProductServiceTest {
	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private CustomerProductService customerProductService=new CustomerProductServiceImpl();

	// Write testcases here
	@Test
	public void getAllProductValidTest()throws  EKartException{
		List<Product> products=new ArrayList<>();
		Product pr=new Product();
		pr.setProductId(1);
		pr.setAvailableQuantity(12);
		pr.setBrand("moto");
		pr.setName("mobile");
		pr.setPrice(25000.0);
		Product product2 = new Product();
		product2.setProductId(1);
		product2.setAvailableQuantity(12);
		product2.setBrand("samsung");
		product2.setName("mobile");
		product2.setPrice(25000.0);
		
		products.add(pr);
		products.add(product2);
		Mockito.when(productRepository.findAll()).thenReturn(products);
		List<ProductDTO> productDTOs=new ArrayList<>();
		ProductDTO productDTO1=new ProductDTO();
		productDTO1.setProductId(1);
		productDTO1.setAvailableQuantity(12);
		productDTO1.setBrand("moto");
		productDTO1.setName("Mobile");
		productDTO1.setPrice(24000.00);
		productDTOs.add(productDTO1);
		ProductDTO productDTO2=new ProductDTO();
		productDTO2.setProductId(1);
		productDTO2.setAvailableQuantity(12);
		productDTO2.setBrand("samsung");
		productDTO2.setName("Mobile");
		productDTO2.setPrice(26000.00);
		productDTOs.add(productDTO1);
		productDTOs.add(productDTO2);
		List<ProductDTO> list= customerProductService.getAllProducts();
		Assertions.assertEquals(productDTOs.get(0).getProductId(), list.get(0).getProductId());
		Assertions.assertEquals(productDTOs.get(1).getProductId(), list.get(1).getProductId());
		
		
	}
	
	@Test
	public void getAllProducstByIdValidTest() throws EKartException
	{
		Product product= new Product();
		product.setProductId(1);
		product.setAvailableQuantity(10);
		product.setBrand("Vivo");
		product.setName("mobile");
		product.setPrice(20000.00);
		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(product));
		
		ProductDTO productDTO=new ProductDTO();
		productDTO.setProductId(1);
		productDTO.setAvailableQuantity(10);	
		productDTO.setBrand("realme");
		productDTO.setPrice(25000.00);
		
		
		Assertions.assertEquals(productDTO.getProductId(), customerProductService.getProductById(1).getProductId());
		
	}
	@Test
	public void getProductByIdInvalidTest()throws EKartException
	{
		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		EKartException e= Assertions.assertThrows(EKartException.class, ()->customerProductService.getProductById(1));
		Assertions.assertEquals("ProductService.PRODUCT_NOT_AVAILABLE", e.getMessage());
	}
	
	@Test
	public void reduceAvailableQuantityValidTest() throws EKartException{
	
		Product p=new Product();
		p.setProductId(1);
		p.setAvailableQuantity(12);
		p.setBrand("moto");
		p.setName("mobile");
		p.setPrice(25000.0);
		
		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(p));
		customerProductService.reduceAvailableQuantity(1,2);
		Assertions.assertEquals(p.getAvailableQuantity(), 10);
		
	}
	
	@Test
	public void reduceAvailableQuantityInvalidTest() throws EKartException{
		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		EKartException e=Assertions.assertThrows(EKartException.class, ()->customerProductService.reduceAvailableQuantity(1, 12));
		Assertions.assertEquals("ProductService.PRODUCT_NOT_AVAILABLE", e.getMessage());
		
	}
	

}



