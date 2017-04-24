package br.com.eduardo.hubtest.entitymanager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaEntityManager {

	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("hubtest");
	private EntityManager em = factory.createEntityManager();

	public EntityManager getEntityManager() {
		return em;
	}

}
