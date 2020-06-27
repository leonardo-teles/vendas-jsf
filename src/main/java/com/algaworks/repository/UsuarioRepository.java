package com.algaworks.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.algaworks.model.Usuario;

public class UsuarioRepository implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Usuario adicionar(Usuario usuario) {
		return manager.merge(usuario);
	}

	public Usuario buscarUsuarioPorEmail(String email) {
		try {
			return manager.createQuery("from Usuario where upper(email) = :email", Usuario.class)
					.setParameter("email", email.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Usuario buscarUsuarioPorId(Long id) {
		return manager.find(Usuario.class, id);
	}
}
