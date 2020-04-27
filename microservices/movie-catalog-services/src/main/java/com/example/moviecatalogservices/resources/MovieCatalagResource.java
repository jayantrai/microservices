package com.example.moviecatalogservices.resources;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviecatalogservices.model.CatalogItem;

@RestController
@RequestMapping("/catalog")
public class MovieCatalagResource {
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		return Collections.singletonList(
				new CatalogItem("Transformers", "Test", 4)
				);
	}
}
