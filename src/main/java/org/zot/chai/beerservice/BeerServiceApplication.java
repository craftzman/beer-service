package org.zot.chai.beerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
public class BeerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerServiceApplication.class, args);
	}

}

@RestController
class BeerController {

	@GetMapping("/beers")
	public List<Beer> findAllBeers(){
		return List.of(
				new Beer(1L,"Erdinger"),
				new Beer(2L,"Duvel"),
				new Beer(3L,"Leffe")
		);
	}
}

record Beer(Long id, String name){}
