package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	//Solução mais comum para resolver o problema das N+1 Consultas: Utilizar o JOIN FETCH em uma consulta JPQL personalizada
	@Query("SELECT obj FROM Product obj JOIN FETCH obj.categories")
	List<Product> findProductsCategories();
}
