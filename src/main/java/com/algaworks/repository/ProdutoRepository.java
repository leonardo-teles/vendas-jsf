package com.algaworks.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.algaworks.model.Categoria;
import com.algaworks.model.Produto;
import com.algaworks.repository.filter.ProdutoFilter;

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
	
	public List<Produto> produtosFiltrados(ProdutoFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = builder.createQuery(Produto.class);
		List<Predicate> predicates = new ArrayList<>();
		
		Root<Produto> produtoRoot = criteriaQuery.from(Produto.class);
		Fetch<Produto, Categoria> categoriaJoin =  produtoRoot.fetch("categoria", JoinType.INNER);
		categoriaJoin.fetch("categoriaPai", JoinType.INNER);
		
		if (StringUtils.isNotBlank(filtro.getSku())) {
			predicates.add(builder.equal(produtoRoot.get("sku"), filtro.getSku()));
		}
			  
		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(produtoRoot.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
		}
		
		criteriaQuery.select(produtoRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(produtoRoot.get("nome")));
		
		TypedQuery<Produto> query = manager.createQuery(criteriaQuery);
		
		return query.getResultList();
	}
	
	/*
	 * @SuppressWarnings("unchecked") public List<Produto>
	 * produtosFiltrados(ProdutoFilter filtro) { Session session =
	 * manager.unwrap(Session.class);
	 * 
	 * @SuppressWarnings("deprecation") Criteria criteria =
	 * session.createCriteria(Produto.class);
	 * 
	 * if (StringUtils.isNotBlank(filtro.getSku())) {
	 * criteria.add(Restrictions.eq("sku", filtro.getSku())); }
	 * 
	 * if (StringUtils.isNotBlank(filtro.getNome())) {
	 * criteria.add(Restrictions.ilike("nome", filtro.getNome(),
	 * MatchMode.ANYWHERE)); }
	 * 
	 * return criteria.addOrder(Order.asc("nome")).list(); }
	 */
}
