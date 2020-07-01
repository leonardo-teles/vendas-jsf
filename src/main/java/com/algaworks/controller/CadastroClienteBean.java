package com.algaworks.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.model.Cliente;
import com.algaworks.model.Endereco;
import com.algaworks.model.TipoPessoa;
import com.algaworks.repository.ClienteRepository;
import com.algaworks.service.ClienteService;
import com.algaworks.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClienteService clienteService; 
	
	@Inject
	private ClienteRepository clienteRepository;
	
	private Cliente cliente;
	
	private Endereco endereco;
	
	private boolean editandoEndereco;
	
	//inicializa uma instância nova de cliente no carregamento da página se o cliente for nulo
	public void inicializar() {
		if (cliente == null) {
			limpar();
		}
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
	
	//abre a tela de adição de novos endereços
	public void novoEndereco() {
		this.endereco = new Endereco();
		this.endereco.setCliente(this.cliente);
		this.editandoEndereco = false;
	}
  
	//verifica a existência do id do objeto cliente para saber se ele é novo ou não
	public boolean isEditando() {
		return this.cliente.getId() != null && this.cliente.getId() == null;
	}	

	//método que verifica a edição de um endereço
	public boolean isEditandoEndereco() {
		return editandoEndereco;
	}	
	
	//editar endereço
	public void editarEndereco(Endereco endereco) {
		this.endereco = endereco;
		this.editandoEndereco = true;
	}
	
	//excluir endereço
	public void excluirEndereco(Endereco endereco) {
		this.cliente.getEnderecos().remove(endereco);
	}
	
	//confirmar endereço
	public void confirmarEndereco() {
		this.cliente = clienteRepository.buscarClientePorId(cliente.getId());
		
		if (!this.cliente.getEnderecos().contains(this.endereco)) {
			this.cliente.getEnderecos().add(this.endereco);
			this.endereco.setCliente(this.cliente);
		}
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

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}
