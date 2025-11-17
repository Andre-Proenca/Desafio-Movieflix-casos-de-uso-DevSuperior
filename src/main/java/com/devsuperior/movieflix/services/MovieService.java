package com.devsuperior.movieflix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;
	
	public MovieDetailsDTO findById(Long id) {
		Optional<Movie> obj = repository.findById(id);
		Movie entity = obj.orElseThrow(() -> new  ResourceNotFoundException("Entity not found"));
		return new MovieDetailsDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findAllPaged(Long genreId, Pageable pageable) {
		
		Pageable sorted = PageRequest.of(
				pageable.getPageNumber(),
				pageable.getPageSize(),
				Sort.by("title")
		);
		
		Page<Movie> page;
		
		if(genreId == null || genreId == 0) {
			page = repository.findAll(sorted);
		} else {
			page = repository.findByGenreId(genreId, pageable);
		}
		
		return page.map(MovieCardDTO::new);
	}

}
