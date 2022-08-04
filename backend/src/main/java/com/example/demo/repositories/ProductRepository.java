package com.example.demo.repositories;

import java.util.List;

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
		- PORÉM SE tentar retornar uma paginação do tipo PAGE não funcionará com o JOIN FETCH
		- Então devemos LIMITAR o resultado utilizando o IN do SQL passando os elementos da primeira consulta
		- Se fosse utilizar o SQL padrão o comando seria:
			SELECT * FROM tb_product 
				INNER JOIN tb_product_category ON tb_product.id = tb_product_category.product_id
				INNER JOIN tb_category ON tb_category.id = tb_product_category.category_id
				WHERE tb_product.id IN (1,2,3,4,5)
		- Porém no JPQL é mais simples:		
	*/
	@Query("SELECT obj FROM Product obj JOIN FETCH obj.categories "
			+ "WHERE obj IN :products")
	List<Product> findProductsCategories(List<Product> products);
}
