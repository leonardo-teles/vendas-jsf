package com.algaworks.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.exception.NegocioException;
import com.algaworks.model.Usuario;
import com.algaworks.repository.UsuarioRepository;
import com.algaworks.util.jpa.Transactional;

public class UsuarioService implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioRepository usuarioRepository;

	@Transactional
	public Usuario salvar(Usuario usuario) throws NegocioException {
		Usuario usuarioExistente = usuarioRepository.buscarUsuarioPorEmail(usuario.getEmail());
		
		if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
			throw new NegocioException("Já existe um usuário cadastrado com o e-Mail informado.");
		}
		
		return usuarioRepository.adicionar(usuario);
	}
}
