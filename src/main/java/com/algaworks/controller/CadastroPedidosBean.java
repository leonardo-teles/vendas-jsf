package com.algaworks.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class CadastroPedidosBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Integer> itens;
	
	public CadastroPedidosBean() {
		itens = new ArrayList<>();
		itens.add(1);
	}

	public List<Integer> getItens() {
		return itens;
	}
}
