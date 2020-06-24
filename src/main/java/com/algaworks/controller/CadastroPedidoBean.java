package com.algaworks.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import com.algaworks.exception.NegocioException;
import com.algaworks.model.EnderecoEntrega;
import com.algaworks.model.Pedido;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Integer> itens;
	
	private Pedido pedido;
	
	public CadastroPedidoBean() {
		pedido = new Pedido();
		pedido.setEnderecoEntrega(new EnderecoEntrega());
		itens = new ArrayList<>();
		itens.add(1);
	}

	public void salvar() {
		throw new NegocioException("Pedido não pode ser salvo, pois ainda não foi implementado.");
	}

	public List<Integer> getItens() {
		return itens;
	}

	public Pedido getPedido() {
		return pedido;
	}
}
