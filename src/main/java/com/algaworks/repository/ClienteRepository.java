package com.algaworks.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.algaworks.model.Cliente;

public class ClienteRepository implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Cliente adicionar(Cliente cliente) {
		return manager.merge(cliente);
	}

	public Cliente buscarClientePeloEmail(String email) {
		try {
			return manager.createQuery("from Cliente where upper(email) = :email", Cliente.class)
					.setParameter("email", email.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
