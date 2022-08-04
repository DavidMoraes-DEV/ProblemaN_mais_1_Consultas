package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entities.Product;

public class ProductDTO {

	private Long id;
	private String name;
	
	private List<CategoryDTO> categories = new ArrayList<>();
	
	public ProductDTO() {
	}

	public ProductDTO(Long id, String name, List<CategoryDTO> categories) {
		super();
		this.id = id;
		this.name = name;
		this.categories = categories;
	}
	
	public ProductDTO(Product product) {
		id = product.getId();
		name = product.getName();
		/*AQUI que é gerado o problema das N+1 Consuntas, pois se remover esse acesso do DTO para a lista de categorias de cada produto
		 	- o Hibernate Não irá fazer aquelas N consultas para vincular as categorias de cada produto e apenas executará o comando inicial buscando os produtos
				- Porém as categorias irá retornar VAZIA
			- Então podemos dizer que se não acessarmos as categorias de cada produto
				- Não serão feitas as N consultas para buscar as categorias de cada produto buscado na primeira consulta
			- O JPA por padrão:
				- Quando se tem um objeto e associado a ele tem vários outros objetos
					- Esses vários outros objeto por padrão não são carregados
						- Isso é chamado de Lazy Loading
							- O que é Lazy Loading: Não será carregados os objetos associados. Apenas será carregado se PRECISAR
							- Nesse exemplo precisa e por isso foi colocado a consulta abaixo, porém isso gera o problema das N+1 Consultas
								- Pois o JPA tem que voltar no banco para buscar as categorias de cada produto da primeira consulta*/
		categories = product.getCategories().stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}
}
