package com.algaworks.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.algaworks.model.Categoria;
import com.algaworks.model.Produto;
import com.algaworks.repository.CategoriaRepository;
import com.algaworks.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CategoriaRepository categoriaRepository;

	private Produto produto;
	
	@NotNull
	private Categoria categoriaPai;
	
	private List<Categoria> categoriasRaizes;
	private List<Categoria> subcategorias;
	
	public CadastroProdutoBean() {
		produto = new Produto();
	}
	
	public void inicializar() {
		if (FacesUtil.isNotPostback()) {
			categoriasRaizes = categoriaRepository.buscarCategoriasRaizes();
		}
	}	
	
	public void carregarSubcategorias() {
		subcategorias = categoriaRepository.carregarSubcategoriasDe(categoriaPai);
	}
	
	
	public void salvar() {
		System.out.println("Categoria pai selecionada: " + categoriaPai.getDescricao());
		System.out.println("SubCategoria selecionada: " + produto.getCategoria().getDescricao());
	}

	public Produto getProduto() {
		return produto;
	}

	public List<Categoria> getCategoriasRaizes() {
		return categoriasRaizes;
	}
	
	public List<Categoria> getSubcategorias() {
		return subcategorias;
	}

	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}
}
