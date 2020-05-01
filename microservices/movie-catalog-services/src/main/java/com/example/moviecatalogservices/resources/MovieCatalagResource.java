package com.example.moviecatalogservices.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.moviecatalogservices.model.CatalogItem;
import com.example.moviecatalogservices.model.Movie;
import com.example.moviecatalogservices.model.Rating;
import com.example.moviecatalogservices.model.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalagResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId, String url) {
				
		
		// we are calling two api
		UserRating ratings = restTemplate.getForObject("http://ratings-data-services/ratingsdata/users/" + userId, UserRating.class);
		
//		List<Rating> ratings = Arrays.asList(
//				new Rating("1234", 4),
//				new Rating("5678", 3)
//				);
		
		return ratings.getUserRating().stream().map(rating -> {
			// For each movie ID, call movie info service and get details
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
			
//			Movie movie = webClientBuilder.build()
//				.get()
//				.uri("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class)
//				.retrieve()
//				.bodyToMono(Movie.class)
//				.block();
			
			// put them all together
			return new CatalogItem(movie.getName(), "Test", rating.getRating());	
		})
				.collect(Collectors.toList());
		
	}
}
