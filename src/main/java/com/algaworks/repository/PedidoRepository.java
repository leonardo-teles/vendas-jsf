package com.algaworks.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.algaworks.model.Pedido;
import com.algaworks.repository.filter.PedidoFilter;

public class PedidoRepository implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public List<Pedido> filtrados(PedidoFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = builder.createQuery(Pedido.class);
		List<Predicate> predicates = new ArrayList<>();
		
		Root<Pedido> pedidoRoot = criteriaQuery.from(Pedido.class);
		From<?, ?> clienteJoin = (From<?, ?>) pedidoRoot.fetch("cliente", JoinType.INNER);
		From<?, ?> vendedorJoin = (From<?, ?>) pedidoRoot.fetch("vendedor", JoinType.INNER);
		
		if (filtro.getNumeroDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(pedidoRoot.get("id"), filtro.getNumeroDe()));
		}

		if (filtro.getNumeroAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(pedidoRoot.get("id"), filtro.getNumeroAte()));
		}

		if (filtro.getDataCriacaoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(pedidoRoot.get("dataCriacao"), filtro.getDataCriacaoDe()));
		}
		
		if (filtro.getDataCriacaoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(pedidoRoot.get("dataCriacao"), filtro.getDataCriacaoAte()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNomeCliente())) {
			predicates.add(builder.like(clienteJoin.get("nome"), "%" + filtro.getNomeCliente() + "%"));
		}
		
		if (StringUtils.isNotBlank(filtro.getNomeVendedor())) {
			predicates.add(builder.like(vendedorJoin.get("nome"), "%" + filtro.getNomeVendedor() + "%"));
		}
		
		if (filtro.getStatus() != null && filtro.getStatus().length > 0) {
			predicates.add(pedidoRoot.get("status").in(Arrays.asList(filtro.getStatus())));
		}
		
		criteriaQuery.select(pedidoRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(pedidoRoot.get("id")));
		
		TypedQuery<Pedido> query = manager.createQuery(criteriaQuery);
		
		return query.getResultList();
	}
}
