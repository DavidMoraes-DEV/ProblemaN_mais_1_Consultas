package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	public Page<ProductDTO> find(PageRequest pageRequest) {
		Page<Product> list = repository.findAll(pageRequest);
		return list.map(x -> new ProductDTO(x));
	}
}
