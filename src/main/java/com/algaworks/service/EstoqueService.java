package com.algaworks.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.model.ItemPedido;
import com.algaworks.model.Pedido;
import com.algaworks.repository.PedidoRepository;
import com.algaworks.util.jpa.Transactional;

public class EstoqueService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public void baixarItensEstoque(Pedido pedido) {
		pedido = this.pedidoRepository.buscarPedidoPorId(pedido.getId());
		
		for(ItemPedido item : pedido.getItens()) {
			item.getProduto().baixarEstoque(item.getQuantidade());
		}
	}

	public void retornarItensEstoque(Pedido pedido) {
		pedido = this.pedidoRepository.buscarPedidoPorId(pedido.getId());
		
		for(ItemPedido item : pedido.getItens()) {
			item.getProduto().adicionarEstoque(item.getQuantidade());
		}
		
	}
}
