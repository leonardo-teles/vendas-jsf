package com.algaworks.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.model.Pedido;
import com.algaworks.model.StatusPedido;
import com.algaworks.repository.PedidoRepository;
import com.algaworks.repository.filter.PedidoFilter;

@Named
@ViewScoped
public class PesquisaPedidosBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository pedidoRepository;
	
	
	private PedidoFilter filtro;
	private List<Pedido> pedidosFiltrados;
	
	public PesquisaPedidosBean() {
		filtro = new PedidoFilter();
		pedidosFiltrados = new ArrayList<>();
	}
	
	//realiza a pesquisa de pedidos com filtro
	public void pesquisar() {
		pedidosFiltrados = pedidoRepository.filtrados(filtro);
	}
	
	//lista de status de um pedido
	public StatusPedido[] getStatus() {
		return StatusPedido.values();
	}

	public List<Pedido> getPedidosFiltrados() {
		return pedidosFiltrados;
	}

	public PedidoFilter getFiltro() {
		return filtro;
	}
}
