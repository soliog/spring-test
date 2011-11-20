package com.sno.spring.test.util;

import java.util.List;

import javax.persistence.EntityManager;

public class EntityFinder<T extends BaseEntity<?>> {
	
	private Class<T> clazz;
	
	public EntityFinder(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public final EntityManager entityManager() throws Exception {
		return clazz.newInstance().entityManager;
	}

	public long size() throws Exception {
		return (Long) entityManager().createQuery(
				"SELECT COUNT(*) FROM " + clazz.getName()).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() throws Exception {
		
		EntityManager em = entityManager();
		return em.createQuery("FROM " + clazz.getName()).getResultList();
	}
	
	public T find(Object id) throws Exception {
		return entityManager().find(clazz, id);
	}
}
