package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	/*
	 * Um DTO é importante de se utilizar porque:
	 	- Como o DTO é um objeto pra transferir dados e com ele podemos:
	 		- Tranferir apenas os dados que desejamos
	 		- É retirado o monitoramento dos dados pela JPA
	 			- Ou seja um DTO não é monitorado
	 			- E é independente da conexão com o banco de dados
	 			- Isso segnifica que ao passar para o DTO:
	 				- Não se tem mais o compromisso de mexer com banco de dados
	 				- E transações com o banco, pois as transações estão encerradas
	 		- Tudo isso é uma BOA PRÁTICA		
	*/
	@Transactional(readOnly = true)
	public List<ProductDTO> find(PageRequest pageRequest) {
		/*
		* Para resolver o problema ao invés de utilizar o findAll do repository, utilizamos uma consulta personalizada(findProductsCateogires()) utilizando o JOIN FETCH
			- Dessa forma a consulta irá no banco apenas 1 vez para buscar os produtos e depois irá apenas mais 1 vez para buscar as categorias de cada produtos
			- Porém na consulta dessa forma NÃO esta paginada, retorna todos os produtos vinculados a suas categorias SEM PAGINAÇÃO
			- Nesse caso já não esta mais acontecendo o problema da N+1 Consultas, mais ainda tem o problema de ela não estar paginada
		*/
		List<Product> list = repository.findProductsCategories();
		return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
	}
}
