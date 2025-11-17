package com.devsuperior.movieflix.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.movieflix.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	@EntityGraph(attributePaths = "genre")
	Page<Movie> findAll(Pageable pageable);
	
	@EntityGraph(attributePaths = "genre")
	Page<Movie> findByGenreId(Long genreId, Pageable pageable);	

}
