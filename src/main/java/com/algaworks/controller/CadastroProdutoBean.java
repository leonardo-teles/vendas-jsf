package com.algaworks.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.model.Categoria;
import com.algaworks.model.Produto;
import com.algaworks.repository.CategoriaRepository;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CategoriaRepository categoriaRepository;

	private Produto produto;
	
	private List<Categoria> categoriasRaizes;
	
	public CadastroProdutoBean() {
		produto = new Produto();
	}
	
	public void inicializar() {
		System.out.println("Inicializando o bean...");
		
		categoriasRaizes = categoriaRepository.buscarCategoriasRaizes();
	}	
	
	public void salvar() {
		
	}

	public Produto getProduto() {
		return produto;
	}

	public List<Categoria> getCategoriasRaizes() {
		return categoriasRaizes;
	}
}
