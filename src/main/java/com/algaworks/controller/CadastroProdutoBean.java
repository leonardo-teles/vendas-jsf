package com.algaworks.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.algaworks.model.Categoria;
import com.algaworks.model.Produto;
import com.algaworks.repository.CategoriaRepository;
import com.algaworks.service.ProdutoService;
import com.algaworks.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CategoriaRepository categoriaRepository;

	@Inject
	private ProdutoService produtoService;
	
	private Produto produto;
	@NotNull
	private Categoria categoriaPai;
	
	private List<Categoria> categoriasRaizes;
	private List<Categoria> subcategorias;
	
	public CadastroProdutoBean() {
		limpar();
	}
	
	//carrega a lista de categorias na inicialização da página
	public void inicializar() {
		if (FacesUtil.isNotPostback()) {
			categoriasRaizes = categoriaRepository.buscarCategoriasRaizes();
		}
	}	
	
	//carrega as subcategorias através da categoria selecionada
	public void carregarSubcategorias() {
		subcategorias = categoriaRepository.carregarSubcategoriasDe(categoriaPai);
	}
	
	//limpa os dados da tela após cadastrar um novo produto
	private void limpar() {
		produto = new Produto();
		categoriaPai = null;
		subcategorias = new ArrayList<>();
	}	
	
	//salva um novo produto
	public void salvar() {
		this.produto = produtoService.salvar(this.produto);
		limpar();
		FacesUtil.addInfoMessage("Produto salvo com sucesso.");
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
