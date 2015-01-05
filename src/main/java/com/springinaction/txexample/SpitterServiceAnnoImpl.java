package com.springinaction.txexample;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("spitterAnnoService")
@Transactional(propagation=Propagation.REQUIRED)
public class SpitterServiceAnnoImpl implements SpitterService {
	
	@Autowired
	private SpitterDao spitterDao;
	
	@Override
	public void saveSpittle(Spittle spittle) {
		spittle.setWhen(new Date());
		spitterDao.saveSpittle(spittle);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<Spittle> getRecentSpittles(int count) {
		List<Spittle> recentSpittles = spitterDao.getRecentSpittles();
		Collections.reverse(recentSpittles);
		return recentSpittles.subList(0, Math.min(49, recentSpittles.size()));
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
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public Spitter getSpitter(long id) {
		return spitterDao.getSpitterById(id);
	}

	@Override
	public void startFollowing(Spitter follower, Spitter followee) {

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
	public Spitter getSpitter(String username) {
		return spitterDao.getSpitterByUsername(username);
	}

	@Override
	public Spitter getSpitterById(long id) {
		return spitterDao.getSpitterById(id);
	}

	@Override
	public void deleteSpittle(long id) {
		spitterDao.deleteSpittle(id);
	}

	@Override
	public List<Spitter> getAllSpitters() {
		return spitterDao.getAllSpitters();
	}

}
