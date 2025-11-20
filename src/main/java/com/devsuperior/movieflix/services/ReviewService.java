package com.devsuperior.movieflix.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private AuthService authService;
	
	@Transactional
	public ReviewDTO insert(ReviewDTO dto) {
		
		User user = authService.authenticated();
		
		Review entity = new Review();
		entity.setText(dto.getText());
		
		Movie movie = movieRepository.getReferenceById(dto.getMovieId());
		entity.setMovie(movie);
		entity.setUser(user);
		
		entity = reviewRepository.save(entity);
				
		return new ReviewDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<ReviewDTO> searchReviewsByMovie(Long movieId) {
		movieRepository.findById(movieId).orElseThrow(
				() -> new ResourceNotFoundException("Filme não encontrado")
				);
		
		List<Review> list = reviewRepository.findByMovieId(movieId);
		
		return list.stream().map(ReviewDTO::new).toList();
	}
}
