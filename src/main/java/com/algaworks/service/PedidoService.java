package com.algaworks.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.algaworks.model.Pedido;
import com.algaworks.repository.PedidoRepository;
import com.algaworks.util.jpa.Transactional;

public class PedidoService implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject 
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public Pedido salvar(Pedido pedido) {
		if (pedido.isNovo()) {
			pedido.setDataCriacao(new Date());
		}
		
		pedido = this.pedidoRepository.adicionar(pedido);
		
		return pedido;
		
	}
}
