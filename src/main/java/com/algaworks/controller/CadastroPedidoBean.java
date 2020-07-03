package com.algaworks.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.model.Cliente;
import com.algaworks.model.EnderecoEntrega;
import com.algaworks.model.FormaPagamento;
import com.algaworks.model.Pedido;
import com.algaworks.model.Usuario;
import com.algaworks.repository.ClienteRepository;
import com.algaworks.repository.UsuarioRepository;
import com.algaworks.service.PedidoService;
import com.algaworks.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Inject
	private ClienteRepository clienteRepository;

	@Inject
	private PedidoService pedidoService;
	
	private Pedido pedido;
	private List<Usuario> vendedores;
	
	public CadastroPedidoBean() {
		limpar();
	}
	
	//carrega a lista de vendedores e clientes na inicialização da página
	public void inicializar() {
		if (FacesUtil.isNotPostback()) {
			this.vendedores = usuarioRepository.vendedores();
		}
	}
	
	//limpa os dados da tela após cadastrar um novo pedido
	private void limpar() {
		pedido = new Pedido();
		pedido.setEnderecoEntrega(new EnderecoEntrega());
	}

	//salva um pedido
	public void salvar() {
		this.pedido = this.pedidoService.salvar(this.pedido);
		
		FacesUtil.addInfoMessage("Pedido salvo com sucesso.");
	}
	
	//lista os nomes dos clientes pelo autocomplete
	public List<Cliente> completarCliente(String nome) {
		return this.clienteRepository.buscarPorNome(nome);
	}
	
	//verifica a existência do id do objeto produto para saber se ele é novo ou não
	public boolean isEditando() {
		return this.pedido.getId() != null;
	}
	
	//lista as foras de pagamento
	public FormaPagamento[] getFormasPagamento() {
		return FormaPagamento.values();
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Usuario> getVendedores() {
		return vendedores;
	}
}
