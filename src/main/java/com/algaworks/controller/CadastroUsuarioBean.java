package com.algaworks.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.algaworks.model.Grupo;
import com.algaworks.model.Usuario;
import com.algaworks.repository.GrupoRepository;
import com.algaworks.service.UsuarioService;
import com.algaworks.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private GrupoRepository grupoRepository;
	
	private Usuario usuario;
	private Grupo grupo;
	
	@NotNull
	private List<Grupo> grupos;
	
	private Grupo grupoSelecionado;
	private boolean editandoGrupo;

	public CadastroUsuarioBean() {
		limpar();
	}
	
	//limpa os dados da tela após cadastrar um novo usuário
	private void limpar() {
		usuario = new Usuario();
		grupos = new ArrayList<>();
	}
	
	//carrega a lista de grupos na inicialização da página
	public void inicializar() {
		if (FacesUtil.isNotPostback()) {
			grupos = grupoRepository.listarTodosOsGrupos();
		}
	}
	
	
	//salva um novo grupo
	public void salvarGrupo() {
		if (editandoGrupo) {
			grupos.set(grupos.indexOf(grupo), grupo);
			grupo = new Grupo();
		} else {
			grupos.add(grupo);
			grupo = new Grupo();
		}
	}
	
	//salva um novo produto
	public void salvar() {
		this.usuario.setGrupos(grupos);
		this.usuario = usuarioService.salvar(this.usuario);
		limpar();
		FacesUtil.addInfoMessage("Usuário salvo com sucesso.");
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}
	
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public boolean isEditandoGrupo() {
		return editandoGrupo;
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}
}
