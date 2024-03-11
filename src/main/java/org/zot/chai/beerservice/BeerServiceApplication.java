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
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@SpringBootApplication
public class BeerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerServiceApplication.class, args);
	}

}

@RestController
@RequestMapping("/api/beers")
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

	@DeleteMapping("{id}")
	ResponseEntity deleteBeer(@PathVariable Long id){
		log.info("Deleting Beer id:= {}", id);
		beerRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}

record Beer(@Id Long id, @NotEmpty String name){}

interface BeerRepository extends ListCrudRepository<Beer,Long>{}

class BeerNotFoundException extends RuntimeException {
	public BeerNotFoundException(Long id) {
		super("Beer with id " + id + " not found.");
	}
}
@RestControllerAdvice
class HttpExceptionHandler {

	@ExceptionHandler(BeerNotFoundException.class)
	ProblemDetail handleBeerNotFoundException(BeerNotFoundException ex){
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
		problemDetail.setDetail("Beer Not Found");
		problemDetail.setType(URI.create("https://example.net/not-found"));
		problemDetail.setProperty("severity", "low");
		return problemDetail;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		var invalidFields = ex.getBindingResult().getAllErrors().stream()
				.map(error -> {
					String name = ((FieldError) error).getField();
					String reason = error.getDefaultMessage();
					return new InvalidField(name, reason);
				})
				.toList();

		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setTitle("Input data not valid");
		problemDetail.setType(URI.create("https://example.net/validation-error"));
		problemDetail.setProperty("invalid-fields", invalidFields);
		return problemDetail;
	}

	record InvalidField(String name, String reason) {}
}
