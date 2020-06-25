package com.algaworks.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.model.Produto;
import com.algaworks.repository.ProdutoRepository;
import com.algaworks.repository.filter.ProdutoFilter;

@Named
@ViewScoped
public class PesquisaProdutosBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoRepository produtoRepository;
	
	private ProdutoFilter filtro;
	private List<Produto> produtosFiltrados;
	
	public PesquisaProdutosBean() {
		filtro = new ProdutoFilter();
	}
	
	public void pesquisar() {
		produtosFiltrados = produtoRepository.produtosFiltrados(filtro);
	}
	
	public List<Produto> getProdutosFiltrados() {
		return produtosFiltrados;
	}

	public ProdutoFilter getFiltro() {
		return filtro;
	}
}
