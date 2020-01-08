package hn46.sa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hn46.sa.dao.RatingDao;
import hn46.sa.entity.Rating;

@Service
public class RatingService {
	@Autowired
	private RatingDao ratingDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void doRating(Rating rating) {
		int idRating = ratingDao.existsByIdData(rating);
		if (idRating != 0) {
			rating.setIdRating(idRating);
			ratingDao.update(rating);
		}
		else
			ratingDao.save(rating);
	}
	
	public int getRating(Rating rating) {
		int idRating =  ratingDao.existsByIdData(rating);
		if (idRating != 0)
			return ratingDao.findById(idRating).getStar();
		else
			return 0;
	}
	
	public double getRatingStar(Rating rating) {
		return ratingDao.getRatingStar(rating);
	}
}
