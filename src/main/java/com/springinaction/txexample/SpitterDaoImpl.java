package com.springinaction.txexample;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("spitterDao")
@Transactional
public class SpitterDaoImpl implements SpitterDao {
	
	private static final String RECENT_SPITTLES = "select s from spittle s";
	private static final String All_SPITTERS = "select s form spitter s";
	private static final String SPITTER_FOR_USERNAME = "select s from spitter s where s.username = :username";
	private static final String SPITTLE_FOR_USERNAME = "select s form spittle s where s.spitter.username = :username";

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void addSpitter(Spitter spitter) {
		em.persist(spitter);
	}

	@Override
	public void saveSpitter(Spitter spitter) {
		em.merge(spitter);
	}

	@Override
	public Spitter getSpitterById(long id) {
		return em.find(Spitter.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Spittle> getRecentSpittles() {
		return (List<Spittle>)em.createQuery(RECENT_SPITTLES).getResultList();
	}

	@Override
	public void saveSpittle(Spittle spittle) {
		em.persist(spittle);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Spittle> getSpittlesForSpitter(Spitter spitter) {
		return (List<Spittle>)em.createQuery(SPITTLE_FOR_USERNAME).setParameter("username", spitter.getUsername()).getResultList();
	}

	@Override
	public Spitter getSpitterByUsername(String username) {
		return  (Spitter)em.createQuery(SPITTER_FOR_USERNAME).setParameter("username", username).getSingleResult();
	}

	@Override
	public void deleteSpittle(long id) {
		em.remove(getSpittleById(id));
	}

	@Override
	public Spittle getSpittleById(long id) {
		return em.find(Spittle.class, id); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Spitter> getAllSpitters() {
		return em.createQuery(All_SPITTERS).getResultList();
	}

}
