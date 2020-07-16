package com.algaworks.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import com.algaworks.model.Pedido;
import com.algaworks.model.Usuario;
import com.algaworks.model.vo.DataValor;
import com.algaworks.repository.filter.PedidoFilter;

public class PedidoRepository implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Pedido adicionar(Pedido pedido) {
		return manager.merge(pedido);
	}
	
	public Pedido buscarPedidoPorId(Long id) {
		return manager.find(Pedido.class, id);
	}
	
	public Map<Date, BigDecimal> valoresTotaisPorData(Integer numeroDeDias, Usuario criadoPor) {
		Session session = manager.unwrap(Session.class);
		
		numeroDeDias -= 1;
		
		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias * -1);
		
		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDeDias, dataInicial);
		
		@SuppressWarnings("deprecation")
		Criteria criteria =  session.createCriteria(Pedido.class);
		
		criteria.setProjection(Projections.projectionList()
			.add(Projections.sqlGroupProjection("date(data_criacao) as data", "date(data_criacao)", new String[] { "data" }, new Type[] { StandardBasicTypes.DATE }))
			.add(Projections.sum("valorTotal").as("valor"))
		).add(Restrictions.ge("dataCriacao", dataInicial.getTime()));
		
		if (criadoPor != null) {
			criteria.add(Restrictions.eq("vendedor", criadoPor));
		}
		
		@SuppressWarnings({ "unchecked" })
		List<DataValor> valoresPorData = criteria.setResultTransformer(Transformers.aliasToBean(DataValor.class)).list();
		
		for (DataValor dataValor : valoresPorData) {
			resultado.put(dataValor.getData(), dataValor.getValor());
		}
		
		return resultado;
	}

	private Map<Date, BigDecimal> criarMapaVazio(Integer numeroDeDias, Calendar dataInicial) {
		
		dataInicial = (Calendar) dataInicial.clone();
		
		Map<Date, BigDecimal> mapaInicial = new TreeMap<>();
		
		for(int i = 0; i <= numeroDeDias; i++) {
			mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}

		return mapaInicial;
	}

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
