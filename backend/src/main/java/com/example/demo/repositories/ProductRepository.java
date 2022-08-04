package com.example.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	/*
	* Solução mais comum para resolver o problema das N+1 Consultas:
 		- Utilizar o JOIN FETCH em uma consulta JPQL personalizada
	@Query("SELECT obj FROM Product obj JOIN FETCH obj.categories")
	List<Product> findProductsCategories();
	*/

	/*
	* Solução para resolver o problema retornando uma PAGINAÇÃO:
		- PORÉM SE tentar retornar uma paginação do tipo PAGE não funcionará com o JOIN FETCH então devemos LIMITAR o resultado utilizando o IN do SQL passando os elementos da primeira consulta
	*/
	@Query("SELECT obj FROM Product obj JOIN FETCH obj.categories")
	Page<Product> findProductsCategories(Pageable pageable);
}
