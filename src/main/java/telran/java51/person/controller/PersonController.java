package telran.java51.person.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.service.PersonService;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

	final PersonService personService;

	@PostMapping
	public Boolean addPerson(@RequestBody PersonDto personDto) {
		return personService.addPerson(personDto);
	}

	@GetMapping("/{id}")
	public PersonDto finPersonById(@PathVariable Integer id) {
		return personService.findPersonById(id);
	}

	@PutMapping("/{id}/name/{name}")
	public PersonDto updateName(@PathVariable Integer id, @PathVariable String name) {
		return personService.updateName(id, name);
	}

	@PutMapping("/{id}/address")
	public PersonDto updateAddress(@PathVariable Integer id, @RequestBody AddressDto addressDto) {
		return personService.updateAddress(id, addressDto);
	}

	@DeleteMapping("/{id}")
	public PersonDto deletePerson(@PathVariable Integer id) {
		return personService.deletePerson(id);
	}

	@GetMapping("/city/{city}")
	public Iterable<PersonDto> findPersonByCity(@PathVariable String city) {
		return personService.findPersonByCity(city);
	}

	@GetMapping("/ages/{minAge}/{maxAge}")
	public Iterable<PersonDto> findPersonByAges(@PathVariable Integer minAge, @PathVariable Integer maxAge) {
		return personService.findPersonByAges(minAge, maxAge);
	}

	@GetMapping("/name/{name}")
	public Iterable<PersonDto> findPersonByName(@PathVariable String name) {
		return personService.findPersonByName(name);
	}
	
	@GetMapping("/population/city")
	public Iterable<CityPopulationDto> getCitiesPopulation(){
		return personService.getCitiesPopulation();
	}

}
