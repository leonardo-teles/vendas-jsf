package com.algaworks.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.model.Cliente;
import com.algaworks.model.TipoPessoa;
import com.algaworks.service.ClienteService;
import com.algaworks.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClienteService clienteService;  
	
	private Cliente cliente;
	
	public CadastroClienteBean() {
		limpar();
	}

	//limpa os dados da tela após cadastrar um novo cliente
	private void limpar() {
		cliente = new Cliente();
		cliente.setTipo(TipoPessoa.FISICA);
	}
	
	//salva um novo cliente 
	public void salvar() {
		this.cliente = clienteService.salvar(this.cliente);
		limpar();
		FacesUtil.addInfoMessage("Cliente salvo com sucesso.");
	}
	
	//verifica a existência do id do objeto usuário para saber se ele é novo ou não
	public boolean isEditando() {
		return this.cliente.getId() != null;
	}	
	
	//retorno dos tipos de pessoa na tela
	public TipoPessoa[] getTipos() {
		return TipoPessoa.values();
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
