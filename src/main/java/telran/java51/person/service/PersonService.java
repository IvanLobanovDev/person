package telran.java51.person.service;

import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.PersonDto;

public interface PersonService {
	
	Boolean addPerson(PersonDto personDto);
	
	PersonDto findPersonById(Integer id);
	
	PersonDto updateName(Integer id, String name);
	
	PersonDto updateAddress(Integer id, AddressDto addressDto);
	
	PersonDto deletePerson(Integer id);
	
	Iterable<PersonDto> findPersonByCity(String city);
	
	Iterable<PersonDto> findPersonByAges(Integer minAge, Integer maxAge);
	
	Iterable<PersonDto> findPersonByName(String name);

}
