package br.projeto.model;

import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import br.projeto.entity.Entity;

public class BaseDAO {

	protected EntityManager entityManager;
	
	public BaseDAO(){
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest)ec.getRequest ();
		entityManager = (EntityManager) request.getAttribute("EntityManager");
	}
	
	public <T extends Entity> T salvar(T entity){
		entityManager.getTransaction().begin();
		if (entity.getId() != null){
			entityManager.persist(entity);
		}else {
			entityManager.merge(entity);
		}
		entityManager.getTransaction().commit();
		return entity;
	}
	
	public <T extends Entity> T excluir(T entity){
		entityManager.getTransaction().begin();
		entityManager.remove(
				entityManager.getReference(entity.getClass(),
						entity.getId()));
		entityManager.getTransaction().commit();
		return entity;
	}
	
	public <T extends Entity> List<T> getLista(Class<T> entity){
		return entityManager.createQuery("select x from "+ entity.getName() + " x ").getResultList();
	}
}
