package com.springinaction.txexample;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("spitterService")
@Transactional(propagation=Propagation.REQUIRED)
public class SpitterServiceImpl implements SpitterService {
	
	@Autowired
	private SpitterDao spitterDao;

	@Override
	public void saveSpittle(Spittle spittle) {
		spitterDao.saveSpittle(spittle);
	}

	@Override
	public List<Spittle> getRecentSpittles(int count) {
		List<Spittle> recentSpittles = spitterDao.getRecentSpittles();
		Collections.reverse(recentSpittles);
		return recentSpittles.subList(0, Math.min(0, recentSpittles.size()));
	}

	@Override
	public void saveSpitter(Spitter spitter) {
		if (spitter.getId()==null) {
			spitterDao.addSpitter(spitter);
		} else {
			spitterDao.saveSpitter(spitter);
		}
	}

	@Override
	public Spitter getSpitter(long id) {
		return null;
	}

	@Override
	public void startFollowing(Spitter follower, Spitter followee) {
	}
	
	@Override
	public Spitter getSpitter(String username) {
		return spitterDao.getSpitterByUsername(username);
	}

	@Override
	public List<Spittle> getSpittlesForSpitter(Spitter spitter) {
		return spitterDao.getSpittlesForSpitter(spitter);
	}

	@Override
	public List<Spittle> getSpittlesForSpitter(String username) {
		Spitter spitter = spitterDao.getSpitterByUsername(username);
		return spitterDao.getSpittlesForSpitter(spitter);
	}
	
	@Override
	public List<Spitter> getAllSpitters() {
		return spitterDao.getAllSpitters();
	}
	
	@Override
	public void deleteSpittle(long id) {
		spitterDao.deleteSpittle(id);
	}
	

	@Override
	public Spitter getSpitterById(long id) {
		return spitterDao.getSpitterById(id);
	}

}
