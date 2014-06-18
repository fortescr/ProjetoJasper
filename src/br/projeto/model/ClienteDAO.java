package br.projeto.model;

import java.util.List;

import javax.persistence.Query;

import br.projeto.entity.Cliente;

public class ClienteDAO extends BaseDAO {
	
	public void salva(Cliente cliente) {
		entityManager.getTransaction().begin();
		if (cliente.getId() == null){
			entityManager.persist(cliente);
		}else{
			entityManager.merge(cliente);
		}
		entityManager.getTransaction().commit();
	}

	public List<Cliente> getLista() {
		Query query = entityManager.createQuery("SELECT c FROM Cliente c");
		List<Cliente> clientes = query.getResultList();
		return clientes;
	}
	
	public void remove(Cliente cliente) {
		entityManager.getTransaction().begin();
		cliente = entityManager.find(Cliente.class, cliente.getId());
		entityManager.remove(cliente);
		entityManager.getTransaction().commit();
	}

}
