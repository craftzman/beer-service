package org.zot.chai.beerservice;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
public class BeerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerServiceApplication.class, args);
	}

}

@RestController
@RequestMapping("/beers")
class BeerController {

	private static final Logger log = LoggerFactory.getLogger(BeerController.class);
	private final BeerRepository beerRepository;

	BeerController(BeerRepository beerRepository) {
		this.beerRepository = beerRepository;
	}

	@GetMapping
	public List<Beer> findAllBeers(){
		return beerRepository.findAll();
	}

	@GetMapping("{id}")
	Beer findBeerById(@PathVariable Long id){
		log.info("Retrieved beer with id:= {}", id);
		return beerRepository.findById(id).orElseThrow(() -> new BeerNotFoundException(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Beer addBeer(@RequestBody @Valid Beer beer){
		log.info("Adding new beer:= {}",beer.name());
		return beerRepository.save(beer);
	}
}

record Beer(@Id Long id, @NotEmpty String name){}

interface BeerRepository extends ListCrudRepository<Beer,Long>{}

class BeerNotFoundException extends RuntimeException {
	public BeerNotFoundException(Long id) {
		super("Beer with id " + id + " not found.");
	}
}
