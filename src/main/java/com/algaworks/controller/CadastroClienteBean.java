package com.algaworks.controller;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import com.algaworks.model.Cliente;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Cliente cliente;

	public CadastroClienteBean() {
		cliente = new Cliente();
	}
	
	public Cliente getCliente() {
		return cliente;
	}
}
