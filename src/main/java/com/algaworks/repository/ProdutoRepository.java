package com.algaworks.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.algaworks.model.Produto;

public class ProdutoRepository implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public Produto adicionar(Produto produto) {
		return manager.merge(produto);
	}

	public Produto buscarPorSku(String sku) {
		try {
			return manager.createQuery("from Produto where upper(sku) = :sku", Produto.class)
					.setParameter("sku", sku.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
